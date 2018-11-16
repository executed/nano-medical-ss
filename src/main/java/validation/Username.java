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
@Constraint(validatedBy = {Username.UsernameValidator.class})
public @interface Username {
    String regularExpression() default REGEX_PATTERN_CONSTANT.USERNAME;
    String message() default VALIDATION_MSG_CONSTANT.USERNAME_WRONG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class UsernameValidator implements ConstraintValidator<Username, String> {

        private static final Logger LOG = getLogger(getClassName());
        private String regEX;
        private String message;

        public UsernameValidator() { }

        @Override
        public void initialize(Username a) {
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