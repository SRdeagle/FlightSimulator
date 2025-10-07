package project.gui;

import java.awt.*;
import project.util.InactivityTimer;

public class WarningDialog {
    private final Dialog dialog;
    private final Label label;
    private static final String message = "Program se zatvara za: ";
    private InactivityTimer timer; // postavlja se naknadno preko setTimer()

    public WarningDialog(Frame parent) {
        dialog = new Dialog(parent, "Upozorenje", false);
        dialog.setLayout(new BorderLayout(10, 10));

        label = new Label(message, Label.CENTER);
        dialog.add(label, BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(e -> {
            dialog.setVisible(false);
            if (timer != null) {
                timer.setDialogActive(false);
                timer.resetInactivityTime();
            }
        });
        dialog.add(ok, BorderLayout.SOUTH);

        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);
    }

    public void setTimer(InactivityTimer timer) {
        this.timer = timer;
    }

    public void tick(int time) {
        if (!dialog.isVisible()) {
            if (timer != null) timer.setDialogActive(true);
            dialog.setVisible(true);
        }
        label.setText(message + time + " s");
    }
}