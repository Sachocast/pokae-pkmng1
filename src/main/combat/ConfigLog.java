package combat;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConfigLog {
    
    public static void setup() {
    	Logger copieLOGGER = Logger.getLogger(""); 
    	FileHandler fileHandler;
		try {
			fileHandler = new FileHandler( "docs/logsCombat.txt" );
	    	fileHandler.setFormatter( new SimpleFormatter() );
	    	copieLOGGER.addHandler( fileHandler );
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (copieLOGGER.getHandlers()[0] instanceof ConsoleHandler) {
		    copieLOGGER.removeHandler(copieLOGGER.getHandlers()[0]); 
		}
    	
    }

}
