package validation;

import constant.REGEX_PATTERN_CONSTANT;
import constant.VALIDATION_MSG_CONSTANT;
import org.apache.logging.log4j.Logger;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.apache.logging.log4j.LogManager.getLogger;
import static utility.ClassNameUtil.getClassName;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Password.PasswordValidator.class})
public @interface Password {
    String regularExpression() default REGEX_PATTERN_CONSTANT.PASSWORD;
    String message() default VALIDATION_MSG_CONSTANT.PASSWORD_WRONG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class PasswordValidator implements ConstraintValidator<Password, String> {

        private static final Logger LOG = getLogger(getClassName());
        private String regEX;
        private String message;

        public PasswordValidator() { }

        @Override
        public void initialize(Password a) {
            regEX = a.regularExpression();
            message = a.message();
        }

        @Override
        public boolean isValid(String t, ConstraintValidatorContext cvc) {
            if (t != null && !(t.matches(regEX))) {
                LOG.debug(message);
                return false;
            }
            return true;
        }
    }
}