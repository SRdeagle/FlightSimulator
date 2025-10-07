package project.gui;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import project.models.*;
import project.logic.*;

public class SimulationPanel extends Panel {
    private FlightManager fm;
    private Panel leftPanel = new Panel();
    private Map<Airport, Checkbox> airportCheckboxes = new HashMap<>();
    private mapCanvas canvas;
    private static int margin = 30;
    private Airport selectedAirport = null;
    private SimulationClock clock = new SimulationClock();

    private class mapCanvas extends Canvas {
        private boolean blinkState = false;
        mapCanvas() {
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    int mx = e.getX();
                    int my = e.getY();
                    Airport clicked = findAirportAt(mx, my);
                    if (clicked != null) {
                        Checkbox cb = airportCheckboxes.get(clicked);
                        if (cb == null || !cb.getState()) {
                            if (clicked == selectedAirport)
                                selectedAirport = null;
                            else
                                selectedAirport = clicked;
                        }
                    } else {
                        selectedAirport = null;
                    }
                    repaint();
                }
            });

            new Thread(() -> {
                while (true) {
                    try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                    blinkState = !blinkState;
                    if (selectedAirport != null) EventQueue.invokeLater(this::repaint);
                }
            }).start();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int width = getWidth();
            int height = getHeight();

            g.setColor(Color.lightGray);
            g.fillRect(0, 0, width, height);

            for (Airport a : fm.getAirports()) {
                Checkbox cb = airportCheckboxes.get(a);
                boolean hidden = cb != null && cb.getState();
                if (hidden) continue;

                int sx = (int) (margin + ((a.getX() + 90) / 180.0) * (width - 2 * margin));
                int sy = (int) (margin + ((90 - a.getY()) / 180.0) * (height - 2 * margin));
                g.setColor(Color.gray);
                g.fillRect(sx - 4, sy - 4, 8, 8);
                g.setColor(Color.black);
                g.drawString(a.getCode(), sx + 6, sy - 6);
            }

            g.setColor(Color.blue);
            for (Flight f : fm.getFlights()) {
                Airport start = f.getStartAirport();
                Airport end = f.getEndAirport();
                if (start == null || end == null) continue;

                Checkbox startCb = airportCheckboxes.get(start);
                Checkbox endCb = airportCheckboxes.get(end);
                boolean hideStart = startCb != null && startCb.getState();
                boolean hideEnd = endCb != null && endCb.getState();
                if (hideStart || hideEnd) continue;

                int x1 = (int) (margin + ((start.getX() + 90) / 180.0) * (width - 2 * margin));
                int y1 = (int) (margin + ((90 - start.getY()) / 180.0) * (height - 2 * margin));
                int x2 = (int) (margin + ((end.getX() + 90) / 180.0) * (width - 2 * margin));
                int y2 = (int) (margin + ((90 - end.getY()) / 180.0) * (height - 2 * margin));
                g.drawLine(x1, y1, x2, y2);
            }

            g.setColor(Color.red);
            int currentMinutes = clock.getCurrentMinutes();

            for (Flight f : fm.getFlights()) {
                int startTime = f.getHour() * 60 + f.getMinute();
                int endTime = startTime + f.getDuration();
                if (currentMinutes < startTime || currentMinutes > endTime) continue;

                double progress = (currentMinutes - startTime) / (double) f.getDuration();
                Airport start = f.getStartAirport();
                Airport end = f.getEndAirport();

                int x1 = (int) (margin + ((start.getX() + 90) / 180.0) * (width - 2 * margin));
                int y1 = (int) (margin + ((90 - start.getY()) / 180.0) * (height - 2 * margin));
                int x2 = (int) (margin + ((end.getX() + 90) / 180.0) * (width - 2 * margin));
                int y2 = (int) (margin + ((90 - end.getY()) / 180.0) * (height - 2 * margin));

                int x = (int) (x1 + (x2 - x1) * progress);
                int y = (int) (y1 + (y2 - y1) * progress);
                g.fillOval(x - 3, y - 3, 6, 6);
            }

            if (selectedAirport != null && blinkState) {
                int sx = (int) (margin + ((selectedAirport.getX() + 90) / 180.0) * (width - 2 * margin));
                int sy = (int) (margin + ((90 - selectedAirport.getY()) / 180.0) * (height - 2 * margin));
                g.setColor(Color.red);
                g.drawRect(sx - 6, sy - 6, 12, 12);
            }
        }
    }

    public SimulationPanel(FlightManager fm, MainFrame parent) {
        setPreferredSize(new Dimension(800, 600));
        this.fm = fm;
        setLayout(new BorderLayout());

        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        add(leftPanel, BorderLayout.WEST);

        Panel controlPanel = new Panel();
        Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button reset = new Button("Reset");

        start.addActionListener(e -> clock.start());
        pause.addActionListener(e -> clock.pause());
        reset.addActionListener(e -> {
            clock.reset();
            canvas.repaint();
        });

        controlPanel.add(start);
        controlPanel.add(pause);
        controlPanel.add(reset);
        add(controlPanel, BorderLayout.NORTH);

        canvas = new mapCanvas();
        add(canvas, BorderLayout.CENTER);

        Button backButton = new Button("Nazad na unos");
        backButton.addActionListener(e -> parent.showPanel("input"));
        add(backButton, BorderLayout.SOUTH);

        rebuildAirportList();

        new Thread(() -> {
            while (true) {
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                if (clock.isRunning()) {
                    clock.tick();
                    System.out.println("Clock time: " + clock.getCurrentMinutes());
                    EventQueue.invokeLater(() -> {
                        System.out.println("Repainting at " + clock.getCurrentMinutes());
                        canvas.repaint();
                    });
                }
            }
        }).start();
    }

    public void refresh() {
        rebuildAirportList();
        canvas.repaint();
    }

    private void rebuildAirportList() {
        leftPanel.removeAll();
        for (Airport a : fm.getAirports()) {
            Checkbox cb = airportCheckboxes.get(a);
            if (cb == null) {
                cb = new Checkbox(a.getCode() + " - " + a.getName() + " (" + a.getX() + ", " + a.getY() + ")");
                final Checkbox t = cb;
                t.addItemListener(e -> {
                    if (t.getState() && a == selectedAirport) selectedAirport = null;
                    canvas.repaint();
                });
                airportCheckboxes.put(a, cb);
            }
            leftPanel.add(cb);
        }
        leftPanel.validate();
        leftPanel.repaint();
    }

    private Airport findAirportAt(int mx, int my) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        for (Airport a : fm.getAirports()) {
            int sx = (int) (margin + ((a.getX() + 90) / 180.0) * (width - 2 * margin));
            int sy = (int) (margin + ((90 - a.getY()) / 180.0) * (height - 2 * margin));
            if (Math.abs(mx - sx) <= 6 && Math.abs(my - sy) <= 6) return a;
        }
        return null;
    }
}
