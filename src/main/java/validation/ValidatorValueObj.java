package validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorValueObj {

    private static ValidatorFactory factory;
    private static Validator validator;

    public static Validator getDefValidator(){
        if (validator != null) return validator;
        factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
