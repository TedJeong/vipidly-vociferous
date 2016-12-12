package summer;

import java.sql.Timestamp;

public class CardDto {
	private int cardnumber;
	private String username;
	private String title;
	private String content;
	private Timestamp time;
	private int hit;
	private String category;
	private String imagepath;
	
	public CardDto(int cardnumber, String username, String title, String content, Timestamp time, int hit, 
			String category, String imagepath) {
		super();
		this.cardnumber = cardnumber;
		this.username = username;
		this.title = title;
		this.content = content;
		this.time = time;
		this.hit = hit;
		this.category = category;
		this.imagepath = imagepath;
	}

	public int getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(int cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	
	
}
