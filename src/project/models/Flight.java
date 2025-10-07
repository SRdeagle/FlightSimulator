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
            throw new FlightException("Pocetni i krajnji aerodromi moraju biti navedeni");
        }
        if (startAirport.equals(endAirport)) {
            throw new FlightException("Pocetni i krajnji aerodromi ne mogu biti isti");
        }
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new FlightException("Neispravno vreme poletanja (Sat 0-23, minut 0-59)");
        }
        if (duration <= 0) {
            throw new FlightException("Trajanje leta mora biti pozitivno");
        }

        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.hour = hour;
        this.minute = minute;
        this.duration = duration;
    }
    
    public Flight(int hour, int minute, int duration) throws FlightException {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new FlightException("Neispravno vreme poletanja (Sat 0-23, minut 0-59)");
        }
        if (duration <= 0) {
            throw new FlightException("Trajanje leta mora biti pozitivno");
        }
        this.startAirport = null;
        this.endAirport = null;
        this.hour = hour;
        this.minute = minute;
        this.duration = duration;
    }
    
    public void setStart (Airport a) throws FlightException{
    	if (a == null) {
            throw new FlightException("Pocetni i krajnji aerodromi moraju biti izabrani");
    	}
    	if (endAirport != null && a == endAirport) {
            throw new FlightException("Pocetni i krajnji aerodromi ne mogu biti isti");
    	}
    	startAirport = a;
    	
    }
    
    public void setEnd (Airport a) throws FlightException{
    	if (a == null) {
            throw new FlightException("Pocetni i krajnji aerodromi moraju biti izabrani");
    	}
    	if (startAirport != null && a == startAirport) {
            throw new FlightException("Pocetni i krajnji aerodromi ne mogu biti isti");
    	}
    	endAirport = a;
    	
    }
    public Airport getStartAirport() { return startAirport; }
    public Airport getEndAirport() { return endAirport; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getDuration() { return duration; }

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