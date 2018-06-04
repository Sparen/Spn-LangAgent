package com.spnlangagent.langagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LangagentApplication {

	public static void main(String[] args) {
        System.out.println("Langagent Application: Now running Spring Application.");
		SpringApplication.run(LangagentApplication.class, args);
	}
}
