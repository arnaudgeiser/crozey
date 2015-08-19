package ch.waterbead.models;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails {
	private static final long serialVersionUID = -4124437701361743585L;
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="name")
	private String firstNameLastName;
	private String password;
	@Column(unique=true)
	private String username;
	
	public String getFirstNameLastName() {
		return firstNameLastName;
	}
	public void setFirstNameLastName(String firstNameLastName) {
		this.firstNameLastName = firstNameLastName;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public boolean isAccountNonExpired() {
		return true;
	}
	public boolean isAccountNonLocked() {
		return true;
	}
	public boolean isCredentialsNonExpired() {
		return true;
	}
	public boolean isEnabled() {
		return true;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof User) {
			if(((User) other).username.equals(this.username)) {
				return true;
			}
		}
		return false;
	}
}
