package project.gui;

import java.awt.*;
import project.logic.FlightManager;

public class MainFrame extends Frame {
    private CardLayout cardLayout;
    private Panel cardPanel;
    private FlightManager manager;

    public MainFrame() {
        super("Airport Project");

        // osnovna podešavanja prozora
        setSize(800, 600);
        setLayout(new BorderLayout());

        // centralni menadžer podataka (deljen među panelima)
        manager = new FlightManager();

        // koristi CardLayout za prebacivanje faza
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // paneli
        InputPanel inputPanel = new InputPanel(manager, this);
        SimulationPanel simulationPanel = new SimulationPanel(manager, this);

        // dodaj ih u "karte"
        cardPanel.add(inputPanel, "input");
        cardPanel.add(simulationPanel, "simulation");

        add(cardPanel, BorderLayout.CENTER);

        // prva faza – unos
        cardLayout.show(cardPanel, "input");

        // zatvaranje prozora
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    // metoda za promenu prikaza
    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
    }
}