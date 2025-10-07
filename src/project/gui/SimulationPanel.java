package project.gui;
import java.awt.*;
import project.models.*;
import project.logic.*;

public class SimulationPanel extends Panel{
    private final FlightManager fm;
    private class mapCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int width = getWidth();
            int height = getHeight();

            // pozadina
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, width, height);

            // aerodromi
            g.setColor(Color.gray);
            for (Airport a : fm.getAirports()) {
                int sx = (int) ((a.getX() + 90) / 180.0 * width);
                int sy = (int) ((90 - a.getY()) / 180.0 * height);
                g.fillRect(sx - 4, sy - 4, 8, 8);
                g.drawString(a.getCode(), sx + 6, sy - 6);
            }

            // letovi
            g.setColor(Color.blue);
            for (Flight f : fm.getFlights()) {
                Airport start = f.getStartAirport();
                Airport end = f.getEndAirport();
                if (start == null || end == null) continue;

                int x1 = (int) ((start.getX() + 90) / 180.0 * width);
                int y1 = (int) ((90 - start.getY()) / 180.0 * height);
                int x2 = (int) ((end.getX() + 90) / 180.0 * width);
                int y2 = (int) ((90 - end.getY()) / 180.0 * height);
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
    private mapCanvas canvas;

    public SimulationPanel (FlightManager fm, MainFrame parent) {
		setPreferredSize(new Dimension(800, 600));
        this.fm = fm;
        setLayout(new BorderLayout());
        Label placeholderLabel = new Label("Simulation view (placeholder)", Label.CENTER);
        add(placeholderLabel, BorderLayout.NORTH);
        canvas = new mapCanvas();
        add(canvas, BorderLayout.CENTER);

        Button backButton = new Button("Nazad na unos");
        backButton.addActionListener(e -> parent.showPanel("input"));
        add(backButton, BorderLayout.SOUTH);

        repaint();
    }
}
