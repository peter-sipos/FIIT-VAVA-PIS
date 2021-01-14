package logika;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

// Zdroje:
// http://tutorials.jenkov.com/java-logging/handlers.html

public class KonfiguraciaLoggera {

    public static Logger vratErrorLogger(String nazov, Level level) {
        Logger logger = Logger.getLogger(nazov);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        FileHandler fileHandler = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();

        String cesta = "cesta"
                + nazov + "_" + formatter.format(localDate) + ".log";

        try {

            fileHandler = new FileHandler(cesta, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        consoleHandler.setLevel(Level.WARNING);
        fileHandler.setLevel(Level.SEVERE);

        consoleHandler.setFormatter(new SimpleFormatter());
        fileHandler.setFormatter(new SimpleFormatter());

        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);

        logger.setLevel(level);

        return logger;
    }

    public static Logger vratPridanieAkcieLogger(String nazov, Level level) {
        Logger logger = Logger.getLogger(nazov);

        FileHandler fileHandler = null;


        String cesta = "cesta";

        try {

            fileHandler = new FileHandler(cesta, 1024*1024, 3, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        fileHandler.setLevel(Level.ALL);

        fileHandler.setFormatter(new SimpleFormatter());

        logger.addHandler(fileHandler);

        logger.setLevel(level);

        return logger;
    }


}
