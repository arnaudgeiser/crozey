package ch.waterbead.services;

import java.io.Serializable;

public class AuthenticationObject implements Serializable {
	private static final long serialVersionUID = 2396712745135595546L;
	
	public static final AuthenticationObject NOT_AUTHENTICATED = new AuthenticationObject(false, null);
	
	private final boolean isLogged;
	private final String name;
	
	public AuthenticationObject(String name) {
		this(true, name);
	}

	public AuthenticationObject(boolean isLogged, String name) {
		this.isLogged = isLogged;
		this.name = name;
	}
	
	public boolean isLogged() {
		return isLogged;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isValid() {
		return !NOT_AUTHENTICATED.equals(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isLogged ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationObject other = (AuthenticationObject) obj;
		if (isLogged != other.isLogged)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
