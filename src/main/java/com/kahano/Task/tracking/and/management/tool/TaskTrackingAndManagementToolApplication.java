package com.kahano.Task.tracking.and.management.tool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackingAndManagementToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackingAndManagementToolApplication.class, args);

	}

	public CommandLineRunner runner(){

		return (args->{
		});
	}

}
