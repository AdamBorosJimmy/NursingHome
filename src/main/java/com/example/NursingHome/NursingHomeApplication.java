package com.example.NursingHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NursingHomeApplication {
// TODO - Írd meg az NI részt, valamint regisztrációkor ha nem töltesz fel képet, akkor az az üzenet jelenjen meg,
//  hogy "XY hozzáadva profilkép nélkül. A mobile.html-t szedd szét, hogy a CSS külön fájlba kerüljön és írd át a kódot,
	// hogy az olvashatóbb legyen
	public static void main(String[] args) {
		SpringApplication.run(NursingHomeApplication.class, args);
	}

}
