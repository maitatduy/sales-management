package com.salesmanager.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AppLogger {
    private final Logger logger;

    private AppLogger(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
        this.logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        this.logger.addHandler(handler);
        this.logger.setLevel(Level.ALL);
    }

    public static AppLogger get(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    public void info(String message) {
        logger.log(Level.INFO, message);
    }

    public void warn(String message) {
        logger.log(Level.WARNING, message);
    }

    public void error(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }

    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }
}