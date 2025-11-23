package io.akikr.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class RedisApp {

	private static final Logger log = LoggerFactory.getLogger(RedisApp.class);

	public static void main(String[] args) {
		log.info("Starting application with args:[{}]", Arrays.toString(args));
		SpringApplication.run(RedisApp.class, args);
		log.info("Completed execution of main method");
	}
}
