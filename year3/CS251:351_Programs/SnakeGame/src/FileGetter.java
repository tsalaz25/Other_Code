import java.util.*;
import java.io.*;
public class FileGetter {
    int height;
    int width;
    LinkedList<int[]> Walls = new LinkedList<>();

    public FileGetter (File F){
        try{
            Scanner scanner = new Scanner(F);
            if (scanner.hasNextLine()){
                String[] Dims = scanner.nextLine().split(" ");
                this.width = Integer.parseInt(Dims[0]);
                this.width = Integer.parseInt(Dims[1]);
            }

            while (scanner.hasNextLine()){
                int[] WallCoords = new int[4];
                String[] temp =scanner.nextLine().split(" ");
                for (int i = 0; i < 4; i++){
                    WallCoords[i] = Integer.parseInt(temp[i]);
                }
                Walls.add(WallCoords);
            }

        }catch (Exception E){
            E.printStackTrace();
        }
    }
}
