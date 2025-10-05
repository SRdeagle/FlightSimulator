package project.gui;

import java.awt.*;
import project.logic.FlightManager;
import project.models.Airport;
import project.exceptions.AirportException;

public class InputPanel extends Panel {
    private TextField nameField;
    private TextField codeField;
    private TextField xField;
    private TextField yField;
    private FlightManager manager;
    private MainFrame parent;

    public InputPanel(FlightManager manager, MainFrame parent) {
        this.manager = manager;
        this.parent = parent;

        setLayout(new BorderLayout());

        Panel formPanel = new Panel(new GridLayout(5, 2, 5, 5));

        formPanel.add(new Label("Ime:"));
        nameField = new TextField();
        formPanel.add(nameField);

        formPanel.add(new Label("Kod:"));
        codeField = new TextField();
        formPanel.add(codeField);

        formPanel.add(new Label("X koordinata:"));
        xField = new TextField();
        formPanel.add(xField);

        formPanel.add(new Label("Y koordinata:"));
        yField = new TextField();
        formPanel.add(yField);

        Button addButton = new Button("Dodaj aerodrom");
        formPanel.add(addButton);

        // Dugme za prelazak na sledeću fazu
        Button nextButton = new Button("Pokreni simulaciju");
        formPanel.add(nextButton);

        add(formPanel, BorderLayout.CENTER);

        // zona za ispis poruka
        TextArea outputArea = new TextArea();
        add(outputArea, BorderLayout.SOUTH);

        // event: dodavanje aerodroma
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String code = codeField.getText();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                manager.addAirport(name,code,x,y);
                outputArea.append("Dodat aerodrom: " + code + "\n");

                // reset polja
                nameField.setText("");
                codeField.setText("");
                xField.setText("");
                yField.setText("");

            } catch (AirportException ex) {
                outputArea.append("Greška: " + ex.getMessage() + "\n"); // ovde treba videti malo za exceptione
            } catch (NumberFormatException ex) {						// koji mi sve trebaju
                outputArea.append("Koordinate moraju biti brojevi!\n");
            }
        });

        // event: prelazak na simulaciju
        nextButton.addActionListener(e -> {
            parent.showPanel("simulation");
        });
    }
}