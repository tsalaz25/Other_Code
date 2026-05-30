/*  Tomas Salaz
 *  csim.c
 *  CS341L
 */


#include "cachelab.h"
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <getopt.h> //For parsing Args 
#include <unistd.h> //Also for parsing args
#include <errno.h> //Error catching on tracefile


/*Structs for Cache Sim*/
typedef struct {
	int valid;
	uint64_t tag;		//Keep Correct Shifting
	unsigned long lru;	
} Line;

typedef struct{
		Line *lines;
} Set;

typedef struct {
	int s,E,b;
	size_t S;
	Set *sets;
	unsigned long hits,misses,evicts,tick;
	int verbose;
} Cache;

/*Helper Method Signatures*/
static void usage(const char *prog);
static Cache* create_cache (int s, int E, int b);
static void destroy_cache (Cache *C);
static void access_cache(Cache *C, uint64_t addr);
static inline uint64_t get_tag (const Cache *C, uint64_t addr);
static inline size_t get_setIdx (const Cache *C, uint64_t addr);

int main (int argc, char **argv){
	int opt;
	int s = -1;
	int E = -1;
	int b = -1;
	const char* tracefile = NULL;
	int verbose = 0;

	while ((opt = getopt(argc, argv, "hvs:E:b:t:")) != -1 ){
		switch (opt){
			case 'h' : 
				usage (argv[0]);
				return 0;
			case 'v':
				verbose = 1;
				break;
			case 's':
				s = atoi(optarg);
				break;
			case 'E':
				E = atoi(optarg);
				break;
			case 'b':
				b = atoi(optarg);
				break;
			case 't':
				tracefile = optarg;
				break;
			default :
				usage(argv[0]);
				return 1;
		}
	}

	if (s < 0 || E <= 0 || b < 0 || !tracefile){
		usage(argv[0]);
		return 1;
	}

	Cache *C = create_cache(s, E, b);
	if (!C){
		fprintf (stderr, "ERROR: Failed to Allocate Cache\n");
		return 1;
	}

	C->verbose = verbose;
	FILE *fp = fopen(tracefile, "r");
	if (!fp) {
		fprintf (stderr, "ERROR: Cannot open Trace: %s : %s\n", tracefile, strerror(errno));
		destroy_cache(C);
		return 1;
	}

	char line[256];
	char op;
	uint64_t addr;
	int size;

	while (fgets(line, sizeof(line), fp)){
		//Instruction Fetching
		if (line[0] == 'I'){
			continue;
		}
		
		//Skip the invalid Line
		if (sscanf(line, " %c %lx,%d", &op, &addr, &size) != 3){
			continue;
		}

		if (op == 'L' || op == 'S' || op == 'M'){
			//Print Header
			if (C->verbose){
				printf ("%c %llx,%d ", op, (unsigned long long)addr, size);
			}

			//Call Access, M is Load then Store
			access_cache(C, addr);
			if (op == 'M'){
				access_cache(C,addr);
			}

			if (C->verbose){
				putchar('\n');
			}
		}
	}
	fclose(fp);
	printSummary(C->hits, C->misses, C->evicts);
	destroy_cache(C);
	return 0;
}

/*Helpers*/
static void usage(const char *prog){
		fprintf (stderr, 
		"Usage: %s [-hv] -S <s> -E <E> -b <b> -t <trace> \n"
		"-h\t Print Help Message\n"
		"-v\t Print Verbose\n"
		"-s <s> Number of Sets S = 2^s\n"
		"-E <E> Lines per Set \n"
		"-b <b> Block bits B = 2^b\n"
		"-t <trace> Valgrind Trace File\n"
		,prog);
}

static Cache *create_cache (int s, int E, int b){
		Cache *C = (Cache*)calloc(1, sizeof(Cache)); //Allocate Contiguously
	
		//Error Catch
		if (!C) {
			return NULL;
		}

		//Set Args in Cache
		C->s = s;
		C->E = E;
		C->b = b;

		C->S = (size_t) 1u << s; //Assign num of sets
		C->sets = (Set*)calloc (C->S, sizeof(Set)); //Allocate Contiguousely
		if (!C->sets){
			free(C);
			return NULL;
		}

		//Allocate Lines in Loop
		for (size_t i = 0; i < C->S; i++){
			C->sets[i].lines = (Line*)calloc(E, sizeof(Line));
			if (!C->sets[i].lines){
				for (size_t j = 0; j < i; j++){
					free (C->sets[j].lines);
				}
				free(C->sets);
				free(C);
				return NULL;
			}
		}
		return C;
}


static void destroy_cache (Cache *C){
	if (!C) {
		return;
	}

	if (C->sets){
		for (size_t i = 0; i < C->S; i++){
			free(C->sets[i].lines);
		}
		free(C->sets);
	}
	free(C);
}

static inline uint64_t get_tag (const Cache *C, uint64_t addr){
	return addr >> (C->s + C->b);
}

static inline size_t get_setIdx (const Cache *C, uint64_t addr){
	uint64_t mask = ((uint64_t)1 << C->s) - 1u;
	return (size_t) ((addr >> C->b) & mask);	
}

static void access_cache(Cache *C, uint64_t addr){
	const uint64_t tag = get_tag(C, addr);
	const size_t setIdx = get_setIdx (C, addr);
	Set *S = &C->sets[setIdx];

	//Look For Hit
	int hitIdx = -1;
	for (int i = 0; i < C->E; i++){
		Line *L = &S->lines[i];
		if (L->valid && L->tag == tag){
			hitIdx = i;
			break;
		}
	}

	//LRU update
	if (hitIdx >= 0){
		S->lines[hitIdx].lru = ++C->tick;
		C->hits++;
		if (C->verbose) {
			printf("hit ");
		}
		return;
	}

	//Miss 
	C->misses++;
	if (C->verbose){
		printf("miss ");
	}

	//Look for Empty Line or Evict 
	int emptIdx = -1;
	for (int i = 0; i < C->E; i++){
		if (!S->lines[i].valid){
			emptIdx = i;
			break;
		}
	}

	int evictIdx = emptIdx;
	if (evictIdx < 0){
		unsigned long best_lru = (unsigned long)-1; //Max Idx
		int best_i = 0;
		for (int i = 0; i < C->E; i++){
			if (S->lines[i].lru < best_lru){
				best_lru = S->lines[i].lru;
				best_i = i;
			}
		}

		evictIdx = best_i;
		C->evicts++;
		if (C->verbose){
			printf("eviction ");
		}
	}
	
	//Fill or Replace lines
	Line *V = &S->lines[evictIdx];
	V->valid = 1;
	V->tag = tag;
	V->lru = ++C->tick;
}
