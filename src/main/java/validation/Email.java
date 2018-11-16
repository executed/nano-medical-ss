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
@Constraint(validatedBy = {Email.EmailValidator.class})
public @interface Email {
    String regularExpression() default REGEX_PATTERN_CONSTANT.EMAIL;
    String message() default VALIDATION_MSG_CONSTANT.EMAIL_WRONG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class EmailValidator implements ConstraintValidator<Email, String> {

        private static final Logger LOG = getLogger(getClassName());
        private String regEX;
        private String message;

        public EmailValidator() { }

        @Override
        public void initialize(Email a) {
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