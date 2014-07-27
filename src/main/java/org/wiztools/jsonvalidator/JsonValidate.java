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
public final class JsonValidate {

    private JsonValidate() {}
    
    public static String validate(Reader input) throws IOException, JsonSyntaxException {
        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement el = parser.parse(input);
        String str = gson.toJson(el);
        return str;
    }
    
}
