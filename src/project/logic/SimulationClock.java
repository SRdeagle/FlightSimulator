package project.logic;


public class SimulationClock {
    private boolean running = false;
    private int currentMinutes = 0;      // proteklo simulaciono vreme u minutima
    private int minutesPerTick = 10;     // koliko simulacionih minuta prolazi svakih 200ms


    public void start() { running = true; }


    public void pause() { running = false; }


    public void reset() {
        currentMinutes = 0;
        running = false;
    }


    public boolean isRunning() { return running; }


    public int getCurrentMinutes() { return currentMinutes; }


    public void setMinutesPerTick(int m) {
        if (m > 0) minutesPerTick = m;
    }


    public void tick() {
        if (running) currentMinutes += minutesPerTick;
    }
}
