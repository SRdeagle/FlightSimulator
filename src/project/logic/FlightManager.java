package project.logic;

import java.util.ArrayList;
import java.util.List;
import project.models.Airport;
import project.models.Flight;
import project.exceptions.*;

public class FlightManager {
    private List<Airport> airports = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();

    public void addAirport(String name, String code, int x, int y) throws AirportException {
    	
    	Airport airport = new Airport(name,code,x,y);
        // duplikat?
        for (Airport a : airports) {
            if (a.getCode().equals(airport.getCode())) {
                throw new AirportException("Airport with this code already exists: " + airport.getCode());
            }
        }
        airports.add(airport);
    }
    
    public void addFlight(String from, String to, int hour, int minute, int duration) throws FlightException{
    	Flight flight = new Flight (hour,minute,duration);
    	boolean foundStart=false, foundEnd=false;
    	
    	for (Airport a : airports) {
    		if (to == a.getCode()) {
    			flight.setEnd(a);
    			foundEnd = true;
    		}
    		if (from == a.getCode()) {
    			flight.setStart(a);
    			foundStart = true;
    		}
    	}
    	if (!(foundStart && foundEnd)) {
    		String s = "";
    		if (!foundEnd) {
    			s = flight.getEndAirport().getCode();
    		}
    		if (!foundStart) {
    			s = flight.getStartAirport().getCode();
    		}
    		throw new FlightException("Invalid airport selected: " + s);
    	}
    	flights.add(flight);
    }
    
    public List<Airport> getAirports() {
        return airports;
    }
 
    public List<Flight> getFlights() {
    	return flights;
    }

    public void clearAirports() {
        airports.clear();
    }
    
    public void clearFlights() {
    	
    }
}