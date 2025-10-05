package project.models;

import project.exceptions.FlightException;

public class Flight {
    private Airport startAirport;
    private Airport endAirport;
    private int hour;        // 0–23
    private int minute;      // 0–59
    private int duration;    // trajanje u minutima

    public Flight(Airport startAirport, Airport endAirport, int hour, int minute, int duration)
            throws FlightException {

        if (startAirport == null || endAirport == null) {
            throw new FlightException("Start and end airports must be selected.");
        }
        if (startAirport.equals(endAirport)) {
            throw new FlightException("Start and end airports cannot be the same.");
        }
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new FlightException("Invalid departure time. Hour 0–23, minute 0–59.");
        }
        if (duration <= 0) {
            throw new FlightException("Flight duration must be positive.");
        }

        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.hour = hour;
        this.minute = minute;
        this.duration = duration;
    }
    
    public Flight(int hour, int minute, int duration) throws FlightException {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new FlightException("Invalid departure time. Hour 0–23, minute 0–59.");
        }
        if (duration <= 0) {
            throw new FlightException("Flight duration must be positive.");
        }
        this.startAirport = null;
        this.endAirport = null;
        this.hour = hour;
        this.minute = minute;
        this.duration = duration;
    }
    
    public void setStart (Airport a) throws FlightException{
    	if (a == null) {
            throw new FlightException("Start and end airports must be selected.");
    	}
    	if (endAirport != null && a == endAirport) {
            throw new FlightException("Start and end airports cannot be the same.");
    	}
    	startAirport = a;
    	
    }
    
    public void setEnd (Airport a) throws FlightException{
    	if (a == null) {
            throw new FlightException("Start and end airports must be selected.");
    	}
    	if (startAirport != null && a == startAirport) {
            throw new FlightException("Start and end airports cannot be the same.");
    	}
    	endAirport = a;
    	
    }
    // Getters
    public Airport getStartAirport() { return startAirport; }
    public Airport getEndAirport() { return endAirport; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getDuration() { return duration; }

    // Helper: formatted departure time
    public String getFormattedTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    @Override
    public String toString() {
        return startAirport.getCode() + " → " + endAirport.getCode()
               + " | " + getFormattedTime()
               + " | " + duration + " min";
    }
}