public class CircleDemo {


    public static void main(String[] args) {
        // Create new display object
        Display panel = new Display(400, 3);

        // Calculate center point
        int centerX = panel.getWidth() / 2;
        int centerY = panel.getHeight() / 2;

        // Draw a circle starting at the top and going clock wise
        double degAng = 270;
        double radius = 150;
        while (true) {

            double radAng = Math.toRadians(degAng);
            double x = centerX + radius * Math.cos(radAng);
            double y = centerY + radius * Math.sin(radAng);
            panel.drawPointAt((int) x, (int) y);

            degAng += 0.5;
        }

    }
}
