package project.util;
import project.gui.WarningDialog;

public class InactivityTimer extends Thread {
    private int inactivityTime = 0;
    private WarningDialog warningDialog;
    private static int allowedTime = 60;
    public InactivityTimer(WarningDialog warningDialog) {
        this.warningDialog = warningDialog;
    }
    public void run() {
        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
            //System.out.println("probudio sam se " + inactivityTime + "\n");
            //inactivityTime += 1;
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

    synchronized void resetInactivityTime() {
        inactivityTime = 0;
    }
}