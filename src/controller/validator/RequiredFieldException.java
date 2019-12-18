package controller.validator;

public class RequiredFieldException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequiredFieldException(String field) { //exception message
		super(field + " is required!");
	}
}
