package com.ins.web.security.date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultDateTimeProvider implements DateTimeProvider {

	private static final Logger logger = LogManager.getLogger(DefaultDateTimeProvider.class);

    @Override
    public String getCurrentDateTime() {
		logger.log(Level.INFO, "From DefaultDateTimeProvider class -> START -> (DefaultDateTimeProvider)-> (getCurrentDateTime)");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		logger.log(Level.INFO, "From DefaultDateTimeProvider class -> END -> (DefaultDateTimeProvider)-> (getCurrentDateTime)");
        return now.format(formatter);
    }
}
