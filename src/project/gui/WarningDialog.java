package project.gui;

import java.awt.*;
import project.util.InactivityTimer;

public class WarningDialog {
    private final Dialog dialog;
    private final Label label;
    private static final String message = "Program se zatvara za: ";

    public WarningDialog(Frame parent) {

        dialog = new Dialog(parent, "Upozorenje", false);
        dialog.setLayout(new BorderLayout(10, 10));

        label = new Label(message, Label.CENTER);
        dialog.add(label, BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(e -> dialog.dispose());
        dialog.add(ok, BorderLayout.SOUTH);

        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);
    }

    public void tick(int time) {
        label.setText(message + time + " s");
        dialog.setVisible(true);
    }
}