package project.models;

import project.exceptions.AirportException;

public class Airport {
    private String name;
    private String code;  
    private int x;         
    private int y;         


    public Airport(String name, String code, int x, int y) throws AirportException {
        if (name == null || name.isEmpty()) {
            throw new AirportException("Ime aerodroma ne sme biti prazno");
        }
        if (code == null || !code.matches("[A-Z]{3}")) {
            throw new AirportException("Kod aerodroma mora biti tacno 3 velika slova");
        }
        if (x < -90 || x > 90 || y < -90 || y > 90) {
            throw new AirportException("Koordinate aerodroma moraju biti izmedju -90 i 90");
        }

        this.name = name;
        this.code = code;
        this.x = x;
        this.y = y;
    }


    public String getName() { return name; }
    public String getCode() { return code; }
    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public String toString() {
        return code + " - " + name + " (" + x + ", " + y + ")";
    }
}
