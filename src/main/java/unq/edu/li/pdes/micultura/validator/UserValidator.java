package unq.edu.li.pdes.micultura.validator;

import java.util.Vector;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import unq.edu.li.pdes.micultura.model.User;

@Component
public class UserValidator {

    public static boolean validate(User user, Vector<String> erros) {
        Boolean isValid = Boolean.TRUE;
        if (!validatePassword(user.getPassword())) {
            erros.add("Error password more to 8 charaters");
            erros.add("Error password no blanks allowed");
            erros.add("Error password must have at least one capital letter");
            erros.add("Error password must have at least one special character {'@','#','$','%','^','&','+','='}");
            erros.add("Error password must have at least one capital letter");
            erros.add("Error password must have at least one number");
            isValid = Boolean.FALSE;
        }

        if (!validateEmail(user.getEmail())) {
            erros.add("Error email invalid");
            isValid = Boolean.FALSE;
        }

        return isValid;
    }

    public static void validateSamePassword(String password, String repeatPassword, Vector<String> erros) {
        if (!password.equals(repeatPassword)) {
            erros.add("Error the password must be the same");
        }
    }

    private static boolean validateEmail(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    private static boolean validatePassword(String password) {
        final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}");
        return PASSWORD_REGEX.matcher(password).matches();
    }

}
