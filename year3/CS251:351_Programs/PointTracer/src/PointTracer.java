/**
 * PointTracer.java is a program that traces a point and makes shapes. When
 * you run the program a display window will pop-up. In this display window,
 * A moving point will trace out a square 3 times, then a spiral going from
 * the outside inwards once. The final shape will be a capital T. The Final
 * shape will go on forever. Note that the previous shape will fade out as
 * the other one begins
 * */
public class PointTracer {
    private static final int POINT_SIZE = 5;
    private static final int TRACE_LENGTH = 750;

    public static void main(String[] arg) {
        Display panel = new Display(TRACE_LENGTH, POINT_SIZE);
         int x = panel.getWidth() / 4;
         int h = (panel.getWidth() / 4) * 3;
         int y = panel.getHeight() / 4;
         int z = (panel.getHeight() / 4) * 3;
         int a = 0;
         while (x <= h && a <= 402) {
         panel.drawPointAt(x, y);
         ++x;

         while (x == h && y <= z) {
         panel.drawPointAt(h, y);
         ++y;

         while (x <= h && y == z && x != (panel.getWidth() / 4) - 1) {
         panel.drawPointAt(x, z);
         --x;

         while (x == panel.getWidth() / 4 && y <= z && y !=
         (panel.getHeight()) / 4 - 1) {
         panel.drawPointAt(x, y);
         --y;
         ++a;

         }
         }
         }
         }

         while (a >= 403){
         int cx = panel.getWidth()/2;
         int cy = panel.getWidth()/2;
         int dAng = 270;
         int rad = 150;
         while(rad > 10){
         if (dAng % 90 == 0){
         rad -= 5;
         ++a;
         }
         double ang = Math.toRadians(dAng);
         double w = cx + rad * Math.cos(ang);
         double k = cy + rad * Math.sin(ang);
         panel.drawPointAt((int)w ,(int)k);
         dAng += 1;
         }
         break;
         }

        if (a >=631) {// Change to a>= 631 when finished.
            int t = panel.getHeight() / 5;
            int l = panel.getWidth() / 5;
            while (true) {
                do {
                    panel.drawPointAt(l, t);
                    l++;
                } while (l <= (panel.getWidth() / 5) * 4);
                do {
                    panel.drawPointAt(l, t);
                    t++;
                } while (t <= (panel.getHeight() / 5) * 2);
                do {
                    panel.drawPointAt(l, t);
                    l--;
                } while (l >= (panel.getWidth() / 5) * 3);
                do {
                    panel.drawPointAt(l, t);
                    t++;
                } while (t <= (panel.getHeight() / 5) * 4);
                do {
                    panel.drawPointAt(l, t);
                    l--;
                } while (l >= (panel.getWidth() / 5) * 2);
                do {
                    panel.drawPointAt(l, t);
                    t--;
                } while (t >= (panel.getHeight() / 5) * 2);
                do {
                    panel.drawPointAt(l, t);
                    l--;
                } while (l >= panel.getWidth() / 5);
                do {
                    panel.drawPointAt(l, t);
                    t--;
                } while (t >= panel.getHeight() / 5);
            }
        }
    }
}