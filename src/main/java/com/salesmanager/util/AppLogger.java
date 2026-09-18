package com.salesmanager.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AppLogger {
    private static final Map<String, AppLogger> INSTANCES = new ConcurrentHashMap<>();

    private final Logger logger;

    private AppLogger(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
        this.logger.setUseParentHandlers(false);
        if (this.logger.getHandlers().length == 0) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            this.logger.addHandler(handler);
        }
        this.logger.setLevel(Level.ALL);
    }

    public static AppLogger get(Class<?> clazz) {
        return INSTANCES.computeIfAbsent(clazz.getName(), key -> new AppLogger(clazz));
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