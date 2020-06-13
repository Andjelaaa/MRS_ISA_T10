package main.mrs.security.auth;

public class JwtAuthenticationRequest {

	private String username;
	private String password;

	public JwtAuthenticationRequest() {

	}

	public JwtAuthenticationRequest(String email, String lozinka) {
		this.setUsername(email);
		this.setPassword(lozinka);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String lozinka) {
		this.password = lozinka;
	}

}
