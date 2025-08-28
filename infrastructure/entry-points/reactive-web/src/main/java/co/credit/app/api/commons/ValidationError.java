package co.credit.app.api.commons;

import java.util.List;

public class ValidationError extends RuntimeException {

    private final List<String> errors;

    public ValidationError(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}