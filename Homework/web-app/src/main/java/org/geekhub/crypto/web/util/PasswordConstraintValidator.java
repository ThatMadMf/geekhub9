package org.geekhub.crypto.web.util;

import org.geekhub.crypto.web.model.ValidationResult;
import org.passay.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PasswordConstraintValidator {

    private static final char[] ALLOWED_CHARACTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};

    public static ValidationResult isValid(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(12, 30),
                new CharacterOccurrencesRule(1),
                new AllowedCharacterRule(ALLOWED_CHARACTERS)));

        RuleResult result = validator.validate(new PasswordData(password));
        var test = result.getDetails();
        if (result.isValid()) {
            return new ValidationResult(true, null);
        } else {
            return new ValidationResult(false,
                    result.getDetails().stream()
                            .map(RuleResultDetail::getErrorCode)
                            .collect(Collectors.toList())
            );
        }
    }
}