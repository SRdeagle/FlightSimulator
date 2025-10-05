package project.exceptions;

public class CSVException extends Exception{
	public CSVException (String msg) {
		super("Greska u radu sa fajlom - " + msg);
	}
}