package project.util;

import java.awt.*;
import java.awt.event.*;

public class UserActivityListener implements AWTEventListener {
    private final InactivityTimer timer;
    private long lastResetTime = 0;
    private static final long MIN_INTERVAL = 100; // minimalni razmak između reset-a (ms)

    public UserActivityListener(InactivityTimer timer) {
        this.timer = timer;

        Toolkit.getDefaultToolkit().addAWTEventListener(
            this,
            AWTEvent.MOUSE_EVENT_MASK |
            AWTEvent.MOUSE_MOTION_EVENT_MASK |
            AWTEvent.KEY_EVENT_MASK
        );
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        // throttling da se ne resetuje cesto
        long now = System.currentTimeMillis();
        if (now - lastResetTime < MIN_INTERVAL) return;
        lastResetTime = now;

        // ako je dijalog aktivan, ne resetuj
        if (timer.isDialogActive()) return;

        timer.resetInactivityTime();
    }
}
