package project.util;
import project.gui.WarningDialog;

public class InactivityTimer extends Thread {
    private int inactivityTime = 0;
    private WarningDialog warningDialog;
    private static int allowedTime = 60;

    private volatile boolean dialogActive = false;

    public void setDialogActive(boolean active) {
        dialogActive = active;
    }

    public boolean isDialogActive() {
        return dialogActive;
    }

    public InactivityTimer(WarningDialog warningDialog) {
        this.warningDialog = warningDialog;
    }
    public void run() {
        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
            inactivityTime += 1;
            if (inactivityTime>=allowedTime-5) {
                if (inactivityTime>=allowedTime) {
                    System.exit(0);
                }
                warningDialog.tick(allowedTime-inactivityTime);
            }
        }
    }
    public synchronized long getInactivityTime() {
        return inactivityTime;
    }

    public synchronized void resetInactivityTime() {
        if (dialogActive) return;
        inactivityTime = 0;
    }
}