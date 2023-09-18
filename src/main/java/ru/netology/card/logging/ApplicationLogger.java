package ru.netology.card.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationLogger {
    private static final Logger LOGGER = LogManager.getLogger("Logger");
    public static Logger getLogger(){
        return LOGGER;
    }
}
