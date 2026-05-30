/**
 * Author: Tomas Salaz  File: LineSorter.java
 * CS251L  Lab 6: Line Sorter
 *
 * Line Sorter uses the console to read in a Text file, sort first by
 * length then in descending ABC order, then returns a file named 'output.txt'
 * in the same folder as the initial input file using relative paths. The
 * Sorting algorithm uses a comparator with nested if statements to sort in the
 * order of required priority (1st Length, 2nd descending ABC). Files are read
 * line by line using Buffered Readers and are printed using Buffered Writers.
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class LineSorter {
    public static void main(String[] args) throws IOException{
        ArrayList<String> outList = new ArrayList<>();
        Path file = Paths.get(args[0]);
        try (InputStream in = Files.newInputStream(file)){
            BufferedReader nextLine = new BufferedReader(new InputStreamReader(in));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            String line;
            while ((line = nextLine.readLine()) != null){
                if (!line.startsWith("%")){
                    outList.add(line);
                }
            }
            sort(outList);
            for (String s: outList){
                writer.write(s + "\n");
            }
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //Helper method
    /**
     * @param list is an ArrayList containing string that will be sorted using a
     * Comparator
     */
    public static void sort(ArrayList<String> list) {
        Comparator<String> len  = new Comparator<String>() {
            public int compare(String s1, String s2) {
                int x = s1.compareTo(s2);
                if (s1.length() > s2.length()){
                    return 1;
                } else if (s1.length() < s2.length()) {
                    return -1;
                } else {
                    if (x > 0){
                        return -1;
                    } else if (x < 0){
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        };
        Collections.sort(list, len);
    }
}