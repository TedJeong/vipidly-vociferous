package pinfo;

public class playerDTO {
	String player_name;
	String player_birth_date;
	String player_height;
	String player_team;
	String player_position;
	String player_number;
	String player_school;
	
	public playerDTO() {
	}
		
	public playerDTO(String player_name, String player_birth_date, String player_height, String player_team,
			String player_position, String player_number, String player_school) {
		super();
		this.player_name = player_name;
		this.player_birth_date = player_birth_date;
		this.player_height = player_height;
		this.player_team = player_team;
		this.player_position = player_position;
		this.player_number = player_number;
		this.player_school = player_school;
	}



	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public String getPlayer_birth_date() {
		return player_birth_date;
	}
	public void setPlayer_birth_date(String player_birth_date) {
		this.player_birth_date = player_birth_date;
	}
	public String getPlayer_height() {
		return player_height;
	}
	public void setPlayer_height(String player_height) {
		this.player_height = player_height;
	}
	public String getPlayer_team() {
		return player_team;
	}
	public void setPlayer_team(String player_team) {
		this.player_team = player_team;
	}
	public String getPlayer_position() {
		return player_position;
	}
	public void setPlayer_position(String player_position) {
		this.player_position = player_position;
	}
	public String getPlayer_number() {
		return player_number;
	}
	public void setPlayer_number(String player_number) {
		this.player_number = player_number;
	}
	public String getPlayer_school() {
		return player_school;
	}
	public void setPlayer_school(String player_school) {
		this.player_school = player_school;
	}
	
	
	
	
	
}
