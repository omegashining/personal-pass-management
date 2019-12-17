package mx.gob.org.ipn.cic.cyber.pm.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gabriel
 */
public class LogbackSample {

    private static final Logger logger = LoggerFactory.getLogger(LogbackSample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.trace("Hello World!");

        String name = "Gabriel";
        logger.debug("Hi, {}", name);
        logger.info("Welcome to the HelloWorld example of Logback.");
        logger.warn("Dummy warning message.");
        logger.error("Dummy error message.");
    }
    
}
