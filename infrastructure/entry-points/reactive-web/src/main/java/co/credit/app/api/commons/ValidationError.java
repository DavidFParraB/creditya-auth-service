package co.credit.app.api.commons;

import java.util.Set;

public class ValidationError extends RuntimeException implements ApplicationError {

    private final Set<String> fields;

    public ValidationError(String message, Set<String> fields) {
        super(message);
        this.fields = fields;
    }

    @Override
    public Set<String> getFields() {
        return fields;
    }

}