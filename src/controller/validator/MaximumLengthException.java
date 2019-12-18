package controller.validator;

public class MaximumLengthException extends Exception {

	private static final long serialVersionUID = 1L;

	public MaximumLengthException(String field, int maximum) { //err message
		super(field + " must be less than or equal to " + maximum + "characters.");
	}
}
