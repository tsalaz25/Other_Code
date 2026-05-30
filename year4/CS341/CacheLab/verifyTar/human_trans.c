/* 
Name: Rafael Campose, ID: 101967145
Name: Tomas Salaz, ID: 101988985 
 * trans.c - Matrix transpose B = A^T
 *
 * Each transpose function must have a prototype of the form:
 * void trans(int M, int N, int A[N][M], int B[M][N]);
 *
 * A transpose function is evaluated by counting the number of misses
 * on a 1KB direct mapped cache with a block size of 32 bytes.
 */ 
#include <stdio.h>
#include "cachelab.h"

int is_transpose(int M, int N, int A[N][M], int B[M][N]);

void transpose_32_by_32(int M, int N, int A[N][M], int B[M][N]){

    const int BLOCK_SIZE = 8;

    for(int block_row = 0; block_row < N; block_row += BLOCK_SIZE){
        for(int block_column = 0; block_column < M; block_column += BLOCK_SIZE){
            for(int row = block_row; row < block_row + BLOCK_SIZE; row++){
                int diag_tmp = -1;
                int diag_r = -1;
                int diag_c = -1;

                int normalized_row = row;
                if(normalized_row >= BLOCK_SIZE){
                    normalized_row -= block_row;
                }

                for(int column = block_column; column < block_column + BLOCK_SIZE; column++){
                    int normalize_column = column;
                    if(normalize_column >= BLOCK_SIZE){
                        normalize_column -= block_column;
                    }
                    if(normalized_row == normalize_column){
                        diag_tmp = A[row][column];
                        diag_r = row;
                        diag_c = column;
                    }else{
                        int tmp = A[row][column];
                        B[column][row] = tmp;
                    } 
                }
                if(diag_tmp != -1 && diag_r != -1 && diag_c != -1){
                    B[diag_c][diag_r] = diag_tmp;
                }
            }

        }
    }
}

void transpose_64_by_64(int M, int N, int A[N][M], int B[M][N]){
    int t0,t1,t2,t3,t4,t5,t6,t7,row,column;
    for(int block_row = 0; block_row < N; block_row += 8){
        for(int block_column = 0; block_column<M; block_column+=8){
            row = block_row;
            column = block_column;


            for(; row < block_row+4; row++){
                t0 = A[row][column];
                t1 = A[row][column+1];
                t2 = A[row][column+2];
                t3 = A[row][column+3];
                t4 = A[row][column+4];
                t5 = A[row][column+5];
                t6 = A[row][column+6];
                t7 = A[row][column+7];

                B[column][row] = t0;
                B[column+1][row] = t1;
                B[column+2][row] = t2;
                B[column+3][row] = t3;

                B[column][row+4] = t7;
                B[column+1][row+4] = t6;
                B[column+2][row+4] = t5;
                B[column+3][row+4] = t4;
            }

            for(; row < block_row+8; row++){
                t0 = A[row][column];
                t1 = A[row][column+1];
                t2 = A[row][column+2];
                t3 = A[row][column+3];
                t4 = A[row][column+4];
                t5 = A[row][column+5];
                t6 = A[row][column+6];
                t7 = A[row][column+7];

                B[column+4][row-4] = t3;
                B[column+5][row-4] = t2;
                B[column+6][row-4] = t1;
                B[column+7][row-4] = t0;

                B[column+4][row] = t4;
                B[column+5][row] = t5;
                B[column+6][row] = t6;
                B[column+7][row] = t7;
            }

            row = block_row;
    

            t0 = B[column+4][row];
            t1 = B[column+4][row+1];
            t2 = B[column+4][row+2];
            t3 = B[column+4][row+3];

            t4 = B[column+3][row+4];
            t5 = B[column+3][row+5];
            t6 = B[column+3][row+6];
            t7 = B[column+3][row+7];

            B[column+4][row] = t4;
            B[column+4][row+1] = t5;
            B[column+4][row+2] = t6;
            B[column+4][row+3] = t7;

            B[column+3][row+4] = t0;
            B[column+3][row+5] = t1;
            B[column+3][row+6] = t2;
            B[column+3][row+7] = t3;

            //row 2
            t0 = B[column+5][row];
            t1 = B[column+5][row+1];
            t2 = B[column+5][row+2];
            t3 = B[column+5][row+3];

            t4 = B[column+2][row+4];
            t5 = B[column+2][row+5];
            t6 = B[column+2][row+6];
            t7 = B[column+2][row+7];

            B[column+5][row] = t4;
            B[column+5][row+1] = t5;
            B[column+5][row+2] = t6;
            B[column+5][row+3] = t7;

            B[column+2][row+4] = t0;
            B[column+2][row+5] = t1;
            B[column+2][row+6] = t2;
            B[column+2][row+7] = t3;

            //row 3
            t0 = B[column+6][row];
            t1 = B[column+6][row+1];
            t2 = B[column+6][row+2];
            t3 = B[column+6][row+3];

            t4 = B[column+1][row+4];
            t5 = B[column+1][row+5];
            t6 = B[column+1][row+6];
            t7 = B[column+1][row+7];

            B[column+6][row] = t4;
            B[column+6][row+1] = t5;
            B[column+6][row+2] = t6;
            B[column+6][row+3] = t7;

            B[column+1][row+4] = t0;
            B[column+1][row+5] = t1;
            B[column+1][row+6] = t2;
            B[column+1][row+7] = t3;

            //row 4
            t0 = B[column+7][row];
            t1 = B[column+7][row+1];
            t2 = B[column+7][row+2];
            t3 = B[column+7][row+3];

            t4 = B[column][row+4];
            t5 = B[column][row+5];
            t6 = B[column][row+6];
            t7 = B[column][row+7];

            B[column+7][row] = t4;
            B[column+7][row+1] = t5;
            B[column+7][row+2] = t6;
            B[column+7][row+3] = t7;

            B[column][row+4] = t0;
            B[column][row+5] = t1;
            B[column][row+6] = t2;
            B[column][row+7] = t3;
        }
    }
}

void transpose_61_by_67(int M, int N, int A[N][M], int B[M][N]){
    //12 Local Variables
	int i, j ,k; //Loop iters
	int t0, t1, t2, t3, t4, t5, t6, t7; //Temps for 8, blk size is 8
	
	const int BLK = 8;

	//Outer Loop for 8x8
	for (i = 0; i < N; i += BLK){
		for (j = 0; j < M; j += BLK){

			//Inner Transposing Loops
			for (k = i; k < i + BLK && k < N; k++){

				//Load a row of A into local at a time to min A accesses, 1 miss per row
				if (j + 0 < M) t0 = A[k][j+0];
				if (j + 1 < M) t1 = A[k][j+1];
				if (j + 2 < M) t2 = A[k][j+2];
				if (j + 3 < M) t3 = A[k][j+3];
				if (j + 4 < M) t4 = A[k][j+4];
				if (j + 5 < M) t5 = A[k][j+5];
				if (j + 6 < M) t6 = A[k][j+6];
				if (j + 7 < M) t7 = A[k][j+7];

				//Store Row into the Same B Col
				if (j + 0 < M) B[j+0][k] = t0;
				if (j + 1 < M) B[j+1][k] = t1;
				if (j + 2 < M) B[j+2][k] = t2;
				if (j + 3 < M) B[j+3][k] = t3;
				if (j + 4 < M) B[j+4][k] = t4;
				if (j + 5 < M) B[j+5][k] = t5;
				if (j + 6 < M) B[j+6][k] = t6;
				if (j + 7 < M) B[j+7][k] = t7;
			}
		}
	}

}









/* 
 * transpose_submit - This is the solution transpose function that you
 *     will be graded on for Part B of the assignment. Do not change
 *     the description string "Transpose submission", as the driver
 *     searches for that string to identify the transpose function to
 *     be graded. 
 */
char transpose_submit_desc[] = "Transpose submission";
void transpose_submit(int M, int N, int A[N][M], int B[M][N]){
    if(N == 32 && M == 32){
        transpose_32_by_32(M,N,A,B);
    }else if(M == 64 && N == 64){
        transpose_64_by_64(M,N,A,B);
    }else{
        transpose_61_by_67(M,N,A,B);
    }
}

/* 
 * You can define additional transpose functions below. We've defined
 * a simple one below to help you get started. 
 */ 

/* 
 * trans - A simple baseline transpose function, not optimized for the cache.
 */
// char trans_desc[] = "Simple row-wise scan transpose";
// void trans(int M, int N, int A[N][M], int B[M][N])
// {
//     int i, j, tmp;

//     for (i = 0; i < N; i++) {
//         for (j = 0; j < M; j++) {
//             tmp = A[i][j];
//             B[j][i] = tmp;
//         }
//     }    

// }

/*
 * registerFunctions - This function registers your transpose
 *     functions with the driver.  At runtime, the driver will
 *     evaluate each of the registered functions and summarize their
 *     performance. This is a handy way to experiment with different
 *     transpose strategies.
 */
void registerFunctions()
{
    /* Register your solution function */
    registerTransFunction(transpose_submit, transpose_submit_desc); 
    


    

}

/* 
 * is_transpose - This helper function checks if B is the transpose of
 *     A. You can check the correctness of your transpose by calling
 *     it before returning from the transpose function.
 */
int is_transpose(int M, int N, int A[N][M], int B[M][N])
{
    int i, j;

    for (i = 0; i < N; i++) {
        for (j = 0; j < M; ++j) {
            if (A[i][j] != B[j][i]) {
                return 0;
            }
        }
    }
    return 1;
}



