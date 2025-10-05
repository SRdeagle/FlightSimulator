package project.exceptions;

public class SimulationException extends Exception{
	public SimulationException (String msg) {
		super("Greska u simulaciji - " + msg);
	}
}