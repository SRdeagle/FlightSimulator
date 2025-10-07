package project.util;

import java.awt.event.*;

/**
 * Listener koji hvata sve osnovne aktivnosti korisnika (klik, pomeranje miša, tastaturu)
 * i resetuje tajmer neaktivnosti.
 */
public class UserActivityListener extends MouseAdapter implements MouseMotionListener, KeyListener {
    private final InactivityTimer timer;

    public UserActivityListener(InactivityTimer timer) {
        this.timer = timer;
    }

    // klik mišem
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("klik!!!!!!!!!!!!!!!!!");
        timer.resetInactivityTime();
    }

    // pomeranje miša
    @Override
    public void mouseMoved(MouseEvent e) {
        timer.resetInactivityTime();
    }

    // bilo koji pritisak tastera
    @Override
    public void keyPressed(KeyEvent e) {
        timer.resetInactivityTime();
    }

    // ne koristi se, ali moraju postojati
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
