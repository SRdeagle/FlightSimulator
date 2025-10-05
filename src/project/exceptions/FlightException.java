package project.exceptions;

public class FlightException extends Exception{
	public FlightException (String msg) {
		super("Let greska - " + msg);
	}
}