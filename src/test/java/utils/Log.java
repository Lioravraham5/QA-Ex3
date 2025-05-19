package utils;

import org.apache.logging.log4j.*;

public class Log {

	public static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger(cls);
    }
	
}
