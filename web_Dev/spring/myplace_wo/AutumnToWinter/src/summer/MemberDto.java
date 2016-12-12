package summer;

public class MemberDto {
	String email;
	String password;
	String username;
	String province;
	
	public MemberDto(String Email,String Password,String Username,String Province){
		this.email = Email;
		this.password = Password;
		this.username = Username;
		this.province = Province;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
}
