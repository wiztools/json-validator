package org.wiztools.jsonvalidator;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

/**
 *
 * @author subwiz
 */
public class JacksonValidator implements JsonValidator {
    
    private static final ObjectMapper jsonObjMapper = new ObjectMapper();
    static {
        jsonObjMapper.enable(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS);
        jsonObjMapper.enable(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS);
    }

    @Override
    public String validate(Reader input, Config config) throws IOException, JsonParseException {
        JsonFactory fac = new JsonFactory();
        JsonParser parser = fac.createJsonParser(input);
        JsonNode node = null;
        try{
            node = jsonObjMapper.readTree(parser);
        }
        catch(org.codehaus.jackson.JsonParseException ex){
            throw new JsonParseException(ex);
        }
        StringWriter out = new StringWriter();
        // Create pretty printer:
        JsonGenerator gen = fac.createJsonGenerator(out);
        
        if(config.isPrettyPrint()) {
            DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
            pp.indentArraysWith(new DefaultPrettyPrinter.Lf2SpacesIndenter());
            gen.setPrettyPrinter(pp);
        }

        // Now write:
        jsonObjMapper.writeTree(gen, node);

        gen.flush();
        gen.close();
        return out.toString();
    }
    
}
