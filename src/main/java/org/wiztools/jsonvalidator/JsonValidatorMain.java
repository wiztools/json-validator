package org.wiztools.jsonvalidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        out.println("Usage: json-validator [options] [files]");
        out.println("When files are not given, STDIN is read for input.");
        out.println("Supported options are:");
        out.println("  --help     Display this help.");
        out.println("  --noout    Do not print formatted JSON to STDOUT.");
        out.println("  --noformat Do not format JSON.");
        out.println("  --gson     Use Gson instead of default Jackson parser.");
    }
    
    public static void main(String[] arg) throws IOException {
        OptionParser cli = new OptionParser("h");
        cli.accepts("help");
        cli.accepts("noout");
        cli.accepts("noformat");
        cli.accepts("gson");
        OptionSet options = cli.parse(arg);
        
        if(options.has("h") || options.has("help")) {
            printHelp(System.out);
            System.exit(0);
        }
        
        boolean printOut = true;
        if(options.has("noout")) {
            printOut = false;
        }
        
        final Config config = new Config();
        if(options.has("noformat")) {
            config.setPrettyPrint(false);
        }
        
        // Choose the engine between Gson and Jackson:
        final JsonValidator validator;
        if(options.has("gson")) {
            validator = new GsonValidator();
        }
        else {
            validator = new JacksonValidator();
        }
        
        List<String> files = (List<String>) options.nonOptionArguments();
        
        if(files.isEmpty()) { // read from STDIN:
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in))) {
                
                final String out = validator.validate(br, config);
                if(printOut) {
                    if(System.console() != null) {
                        System.out.println();
                    }
                    System.out.println(out);
                }
            }
            catch(JsonParseException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
        else { // open files given in commandline:
            int exitStatus = 0;
            for(String file: files) {
                File f = new File(file);
                try(FileReader fr = new FileReader(f)) {
                    final String out = validator.validate(fr, config);
                    if(printOut) {
                        System.out.println(out);
                    }
                }
                catch(JsonParseException ex) {
                    System.err.println(f.getName() + ": " + ex.getMessage());
                    exitStatus++;
                }
            }
            System.exit(exitStatus);
        }
    }
}
