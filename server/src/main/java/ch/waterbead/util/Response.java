package ch.waterbead.util;

import javax.servlet.http.HttpServletResponse;

public class Response {
	public final int code;
	
	public static final Response ok() {
		return new Response(HttpServletResponse.SC_OK);
	}
	
	private Response(int code) {
		this.code = code;
	}
}
