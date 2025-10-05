package project.models;

import project.exceptions.AirportException;

public class Airport {
    private String name;
    private String code;   // must be 3 uppercase letters
    private int x;         // range -90 to 90
    private int y;         // range -90 to 90

    // Constructor that throws AirportException
    public Airport(String name, String code, int x, int y) throws AirportException {
        if (name == null || name.isEmpty()) {
            throw new AirportException("Airport name cannot be empty.");
        }
        if (code == null || !code.matches("[A-Z]{3}")) {
            throw new AirportException("Airport code must be exactly 3 uppercase letters.");
        }
        if (x < -90 || x > 90 || y < -90 || y > 90) {
            throw new AirportException("Coordinates must be between -90 and 90.");
        }

        this.name = name;
        this.code = code;
        this.x = x;
        this.y = y;
    }

    // Getters
    public String getName() { return name; }
    public String getCode() { return code; }
    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public String toString() {
        return code + " - " + name + " (" + x + ", " + y + ")";
    }
}
