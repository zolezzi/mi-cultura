package unq.edu.li.pdes.micultura.utils;

import java.util.Vector;

import org.springframework.stereotype.Component;

@Component
public class ErrorUtils {

	public static Vector<String> addError(Vector<String> errors, String error){
		errors.add(error);
		return errors;
	}
}
