package org.wiztools.jsonvalidator;

import com.google.gson.JsonSyntaxException;
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
        out.println("Usage: json-validator [json-files]");
    }
    
    public static void main(String[] arg) throws IOException {
        OptionParser cli = new OptionParser("h");
        cli.accepts("help");
        cli.accepts("noout");
        OptionSet options = cli.parse(arg);
        
        if(options.has("h") || options.has("help")) {
            printHelp(System.out);
            System.exit(0);
        }
        
        boolean printFormattedOut = true;
        if(options.has("noout")) {
            printFormattedOut = false;
        }
        
        List<String> files = (List<String>) options.nonOptionArguments();
        
        if(files.isEmpty()) { // read from STDIN:
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in))) {
                final String out = JsonValidate.validate(br);
                if(printFormattedOut) {
                    if(System.console() != null) {
                        System.out.println();
                    }
                    System.out.println(out);
                }
            }
            catch(JsonSyntaxException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
        else { // open files given in commandline:
            int exitStatus = 0;
            for(String file: files) {
                File f = new File(file);
                try(FileReader fr = new FileReader(f)) {
                    final String out = JsonValidate.validate(fr);
                    if(printFormattedOut) {
                        System.out.println(out);
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
}
