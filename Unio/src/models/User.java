package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ThreadLocalRandom;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name = "";
	private String surname = "";
	private String birthday = "";
	private String gender = ""; 
	private String username = "";
	private Integer uid;
	private String email = "";
	private String pwd = "";
	private String pwd2 = "";
	private String avatar = "avatar/user_23.png";
	private Integer roll = 0;
	
	private boolean[] error  = {false,false,false,false,false,false,false,false,false};
	
	public User() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
		this.setRandomAvatar();
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mail);
		if (matcher.matches()) {
			this.email = mail;
		} else {
			error[1]=true;
		}
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setRandomAvatar() {
		int randomNum = 23;
		if (this.gender.equals("male")) {
			randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
		} else if (this.gender.equals("female")) {
			randomNum = ThreadLocalRandom.current().nextInt(11, 22 + 1);
		} 
		String s1="avatar/user_";  
		String s3=".png";  
		String s2 = String.valueOf(randomNum);
		String avatar=s1.concat(s2).concat(s3);
		
		this.avatar = avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Integer getRoll() {
		return roll;
	}

	public void setRoll(Integer roll) {
		this.roll = roll;
	}

	public boolean[] getError() {
		return error;
	}
	
	public void setError(int index) {
		this.error[index]= true;
	}
		
}
