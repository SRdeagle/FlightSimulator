package project.logic;

import java.util.ArrayList;
import java.util.List;
import java.awt.Choice;
import java.io.*;
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


    public void loadFromCSV(String path, Choice startAirportChoice, Choice endAirportChoice) throws CSVException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            airports.clear();
            flights.clear();
            String line;
            boolean inAirports = false;
            boolean inFlights = false;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.equalsIgnoreCase("Airports:")) {
                    inAirports = true;
                    inFlights = false;
                    continue;
                }
                if (line.equalsIgnoreCase("Flights:")) {
                    inFlights = true;
                    inAirports = false;
                    continue;
                }
                if (inAirports) {

                    String[] parts = line.split(",");
                    if (parts.length != 4) continue;
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    int x = Integer.parseInt(parts[2].trim());
                    int y = Integer.parseInt(parts[3].trim());
                    airports.add(new Airport(name, code, x, y));
					startAirportChoice.add(code);
					endAirportChoice.add(code);

                } else if (inFlights) {

                    String[] parts = line.split(",");
                    if (parts.length != 5) continue;
                    String startCode = parts[0].trim();
                    String endCode = parts[1].trim();
                    int hour = Integer.parseInt(parts[2].trim());
                    int minute = Integer.parseInt(parts[3].trim());
                    int duration = Integer.parseInt(parts[4].trim());
                    Airport start = null, end = null;
                    for (Airport a : airports) {
                        if (a.getCode().equals(startCode)) start = a;
                        if (a.getCode().equals(endCode)) end = a;
                    }
                    if (start == null || end == null) continue;
                    Flight f = new Flight(hour, minute, duration);
                    f.setStart(start);
                    f.setEnd(end);
                    flights.add(f);
                }
            }
        } catch (Exception e) {
            airports.clear();
            flights.clear();
            startAirportChoice.removeAll();
            endAirportChoice.removeAll();
            throw new CSVException(e.getMessage());
        }
    }


    public void saveToCSV(String path) throws CSVException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println("Airports:");
            for (Airport a : airports) {
                pw.printf("%s,%s,%d,%d%n", a.getCode(), a.getName(), a.getX(), a.getY());
            }
            pw.println();
            pw.println("Flights:");
            for (Flight f : flights) {
                Airport start = f.getStartAirport();
                Airport end = f.getEndAirport();
                if (start == null || end == null) continue;
                pw.printf("%s,%s,%d,%d,%d%n", start.getCode(), end.getCode(), f.getHour(), f.getMinute(), f.getDuration());
            }
        } catch (Exception e) {
            throw new CSVException(e.getMessage());
        }
    }
}