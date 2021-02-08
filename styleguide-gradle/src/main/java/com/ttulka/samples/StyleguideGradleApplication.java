package com.ttulka.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StyleguideGradleApplication {

	public static void main(String[] args) {
		THISmethodISNotCorreCT();
		a = true;
		SpringApplication.run(StyleguideGradleApplication.class, args);
	}

	private static boolean a =       true;
	private static boolean b =       true;

	public static    void     THISmethodISNotCorreCT () {

a = a | b;
b = a | b;
		if(a & b) return;

		b = false;
	}

}
