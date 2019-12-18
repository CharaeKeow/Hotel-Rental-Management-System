package controller.validator;

public class Validator {
	public static String validate(String field, String value, boolean required, int maximum)
			throws RequiredFieldException, MaximumLengthException {
		
		if (required && (value == null || value.trim().isEmpty())) {
			throw new RequiredFieldException(field);
		}
		
		value = value.trim();
		
		if (value.length() > maximum) {
			throw new MaximumLengthException(field, maximum);
		}
		
		return value;
	}
	
	public static String validate(String field, String value, boolean required)
			throws RequiredFieldException {
		
		if (required && (value == null || value.trim().isEmpty())) {
			throw new RequiredFieldException(field);
		}
		
		value = value.trim();
	
		return value;
	}
	
	
	public static double validate(String field, double value, boolean required) 
	throws RequiredFieldException {
		if (required && (value <= 0)) {
			throw new RequiredFieldException(field);
		}
		return value;
	}
	
	public static int validate(String field, int value, boolean required) 
			throws RequiredFieldException {
				if (required && (value <= 0)) {
					throw new RequiredFieldException(field);
				}
				return value;
			}
}
