package org.wiztools.jsonvalidator;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author subwiz
 */
public interface JsonValidator {
    String validate(Reader input,
            Config config) throws IOException, JsonParseException;
}
