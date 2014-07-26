package org.wiztools.jsonvalidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 *
 * @author subwiz
 */
public class JsonValidatorMain {
    
    private static void printHelp(PrintStream out) {
        out.println("Usage: json-validator [json-files]");
    }
    
    public static void main(String[] arg) throws IOException {
        OptionParser cli = new OptionParser("h");
        cli.accepts("noout");
        OptionSet options = cli.parse(arg);
        
        if(options.has("h")) {
            printHelp(System.out);
            System.exit(0);
        }
        
        boolean printFormattedOut = true;
        if(options.has("noout")) {
            printFormattedOut = false;
        }
        
        List<String> files = (List<String>) options.nonOptionArguments();
        
        if(files.isEmpty()) {
            printHelp(System.err);
            System.exit(1);
        }
        
        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        int exitStatus = 0;
        for(String file: files) {
            File f = new File(file);
            try {
                JsonElement el = parser.parse(new FileReader(f));
                String str = gson.toJson(el);
                if(printFormattedOut) {
                    System.out.println(str);
                }
            }
            catch(JsonSyntaxException ex) {
                System.err.println(f.getName() + ": " + ex.getMessage());
                exitStatus++;
            }
        }
        System.exit(exitStatus);
    }
}
