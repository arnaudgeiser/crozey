package ch.waterbead.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("pass"));
	}
}
