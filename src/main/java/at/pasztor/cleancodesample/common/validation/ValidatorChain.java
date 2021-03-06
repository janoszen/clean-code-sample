package at.pasztor.cleancodesample.common.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ValidatorChain {
    private final Map<String, Collection<Validator>> validators = new HashMap<>();

    public synchronized void addValidator(String field, Validator validator) {
        if (!validators.containsKey(field)) {
            validators.put(field, new ArrayList<>());
        }
        validators.get(field).add(validator);
    }

    public synchronized void addValidator(String field, Collection<Validator> validators) {
        validators.forEach(validator -> addValidator(field, validator));
    }

    public void validate(Map<String, Object> data) throws at.pasztor.cleancodesample.common.validation.InvalidParameters {
        Map<String, Collection<at.pasztor.cleancodesample.common.validation.Error>> errors = new HashMap<>();
        for (Map.Entry<String, Object> entries : data.entrySet()) {
            if (validators.containsKey(entries.getKey())) {
                for (Validator validator : validators.get(entries.getKey())) {
                    if (!validator.isValid(entries.getValue())) {
                        if (!errors.containsKey(entries.getKey())) {
                            errors.put(entries.getKey(), new ArrayList<>());
                        }
                        errors.get(entries.getKey()).add(new at.pasztor.cleancodesample.common.validation.Error(validator));
                    }
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new at.pasztor.cleancodesample.common.validation.InvalidParameters(errors);
        }
    }
}
