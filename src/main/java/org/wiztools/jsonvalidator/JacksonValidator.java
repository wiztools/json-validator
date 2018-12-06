package org.wiztools.jsonvalidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;
import java.io.Reader;

public class JacksonValidator implements JsonValidator {
    @Override
    public String validate(Reader input, Config config) throws IOException, JsonParseException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object jsonObject = mapper.readValue(input, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (com.fasterxml.jackson.core.JsonParseException | MismatchedInputException ex) {
            throw new JsonParseException(ex);
        }
    }
}
