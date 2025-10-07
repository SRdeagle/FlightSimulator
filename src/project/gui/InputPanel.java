package project.gui;

import java.awt.*;
import project.logic.FlightManager;
import project.models.Airport;
import project.models.Flight;
import project.exceptions.AirportException;
import project.exceptions.FlightException;

public class InputPanel extends Panel {
    private TextField nameField;
    private TextField codeField;
    private TextField xField;
    private TextField yField;
    private FlightManager manager;
    private MainFrame parent;

    private Choice startAirportChoice;
    private Choice endAirportChoice;
    private TextField hourField;
    private TextField minuteField;
    private TextField durationField;

    public InputPanel(FlightManager manager, MainFrame parent) {
        this.manager = manager;
        this.parent = parent;

        setLayout(new BorderLayout());

        Panel mainPanel = new Panel(new GridLayout(2, 1, 5, 5));

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

        mainPanel.add(formPanel);

        Panel flightPanel = new Panel(new GridLayout(0, 2, 5, 5));

        flightPanel.add(new Label("Polazni aerodrom:"));
        startAirportChoice = new Choice();
        flightPanel.add(startAirportChoice);

        flightPanel.add(new Label("Dolazni aerodrom:"));
        endAirportChoice = new Choice();
        flightPanel.add(endAirportChoice);

        flightPanel.add(new Label("Sat polaska:"));
        hourField = new TextField();
        flightPanel.add(hourField);

        flightPanel.add(new Label("Minut polaska:"));
        minuteField = new TextField();
        flightPanel.add(minuteField);

        flightPanel.add(new Label("Trajanje leta (minuti):"));
        durationField = new TextField();
        flightPanel.add(durationField);

        Button addFlightButton = new Button("Dodaj let");
        flightPanel.add(addFlightButton);

        mainPanel.add(flightPanel);

        add(mainPanel, BorderLayout.CENTER);

        // zona za ispis poruka
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        // panel za CSV dugmad
        Panel csvPanel = new Panel(new FlowLayout());
        Button loadCSVButton = new Button("Učitaj CSV");
        Button saveCSVButton = new Button("Sačuvaj CSV");
        csvPanel.add(loadCSVButton);
        csvPanel.add(saveCSVButton);

        // kombinovani donji panel (tekst + dugmad)
        Panel bottomPanel = new Panel(new BorderLayout());
        bottomPanel.add(outputArea, BorderLayout.CENTER);
        bottomPanel.add(csvPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // event: dodavanje aerodroma
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String code = codeField.getText();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                manager.addAirport(name,code,x,y);
                outputArea.append("Dodat aerodrom: " + code + "\n");

                // dodavanje aerodroma u Choice dropdownove
                startAirportChoice.add(code);
                endAirportChoice.add(code);

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

        // event: dodavanje leta
        addFlightButton.addActionListener(e -> {
            try {
                String startCode = startAirportChoice.getSelectedItem();
                String endCode = endAirportChoice.getSelectedItem();
                int hour = Integer.parseInt(hourField.getText());
                int minute = Integer.parseInt(minuteField.getText());
                int duration = Integer.parseInt(durationField.getText());



                manager.addFlight(startCode, endCode, hour, minute, duration);
                outputArea.append("Dodat let: " + startCode + " -> " + endCode + "\n");

                // reset polja
                hourField.setText("");
                minuteField.setText("");
                durationField.setText("");

            } catch (NumberFormatException ex) {
                outputArea.append("Sat, minut i trajanje moraju biti brojevi!\n");
            } catch (FlightException ex) {
                outputArea.append("Greška: " + ex.getMessage() + "\n");
            }
        });

        loadCSVButton.addActionListener(e -> {
            FileDialog fd = new FileDialog(parent, "Učitaj CSV fajl", FileDialog.LOAD);
            fd.setVisible(true);
            String directory = fd.getDirectory();
            String file = fd.getFile();
            if (directory != null && file != null) {
                String path = directory + file;
                try {
                    manager.loadFromCSV(path,startAirportChoice,endAirportChoice);
                    outputArea.append("Uspješno učitan CSV fajl: " + path + "\n");
                } catch (Exception ex) {
                    outputArea.append("Greška pri učitavanju CSV fajla: " + ex.getMessage() + "\n");
                }
            }
        });

        saveCSVButton.addActionListener(e -> {
            FileDialog fd = new FileDialog(parent, "Sačuvaj CSV fajl", FileDialog.SAVE);
            fd.setVisible(true);
            String directory = fd.getDirectory();
            String file = fd.getFile();
            if (directory != null && file != null) {
                String path = directory + file;
                try {
                    manager.saveToCSV(path);
                    outputArea.append("Uspješno sačuvan CSV fajl: " + path + "\n");
                } catch (Exception ex) {
                    outputArea.append("Greška pri čuvanju CSV fajla: " + ex.getMessage() + "\n");
                }
            }
        });
    }
}