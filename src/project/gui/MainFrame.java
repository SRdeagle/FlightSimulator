package project.gui;

import java.awt.*;
import project.logic.FlightManager;
import project.util.UserActivityListener;
import project.util.InactivityTimer;

public class MainFrame extends Frame {
    private CardLayout cardLayout;
    private Panel cardPanel;
    private FlightManager manager;
    private InputPanel inputPanel;
    private SimulationPanel simulationPanel;
    private UserActivityListener listener;
    private InactivityTimer timer;
    private WarningDialog warningDialog;

    public MainFrame() {
        super("Airport Project");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setFocusable(true);

        manager = new FlightManager();
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);


        inputPanel = new InputPanel(manager, this);
        simulationPanel = new SimulationPanel(manager, this);
        warningDialog = new WarningDialog(this);
        timer = new InactivityTimer(warningDialog);
        listener = new UserActivityListener(timer);
        timer.start();

        cardPanel.add(inputPanel, "input");
        cardPanel.add(simulationPanel, "simulation");
        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "input");

        simulationPanel.addMouseMotionListener(listener);
        simulationPanel.addMouseListener(listener);
        inputPanel.addMouseMotionListener(listener);
        inputPanel.addMouseListener(listener);
        addKeyListener(listener);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
        System.out.println(name);
        if ("simulation".equals(name)) {
            simulationPanel.repaint();
            setSize(800, 600);
        }
        else if ("input".equals(name)) {
            setSize(500,600);
        }
    }
}