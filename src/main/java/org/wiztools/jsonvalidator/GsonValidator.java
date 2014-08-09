package org.wiztools.jsonvalidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author subwiz
 */
public final class GsonValidator implements JsonValidator {
    
    @Override
    public String validate(Reader input,
            Config config) throws IOException, JsonParseException {
        try {
            JsonParser parser = new JsonParser();
            GsonBuilder builder = new GsonBuilder();
            if(config.isPrettyPrint()) {
                builder.setPrettyPrinting();
            }
            Gson gson = builder.create();
            JsonElement el = parser.parse(input);
            String str = gson.toJson(el);
            return str;
        }
        catch(JsonSyntaxException ex) {
            throw new JsonParseException(ex);
        }
    }
    
}
