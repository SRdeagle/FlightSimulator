package project.exceptions;

public class AirportException extends Exception{
	public AirportException (String msg) {
		super("Aerodrom greska - " + msg);
	}
}
