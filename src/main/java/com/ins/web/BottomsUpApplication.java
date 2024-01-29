package com.ins.web;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = "com.ins.web")
@EnableAspectJAutoProxy
public class BottomsUpApplication implements CommandLineRunner{
	
	private static final Logger logger = LogManager.getLogger(BottomsUpApplication.class);


    public static void main(String[] args) {
		logger.log(Level.INFO, "Starting from Main function -(BottomsUpApplication)");
        SpringApplication.run(BottomsUpApplication.class, args);
    }

    public void run(String... args) throws Exception {
		logger.log(Level.INFO, "Running the code.....");
    }
}
