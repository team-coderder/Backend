package com.coderder.colorMeeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@SpringBootApplication
public class ColorMeetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColorMeetingApplication.class, args);
	}

}
