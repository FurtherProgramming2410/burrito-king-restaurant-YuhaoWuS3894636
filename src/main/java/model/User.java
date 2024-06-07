package model;

public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Double burritonumber;
	private Double friesnumber;
	private Double sodanumber;
	private Double mealnumber;
	private Double waitingtime;
	private Double totalprice;

	private String email;
	private String status;


	public User() {
	}

	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public User(Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber) {
		this.burritonumber = burritonumber;
		this.friesnumber = friesnumber;
		this.sodanumber = sodanumber;
		this.mealnumber = mealnumber;
	}
	public User(Double waitingtime, Double totalprice, Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber) {
		this.burritonumber = burritonumber;
		this.friesnumber = friesnumber;
		this.sodanumber = sodanumber;
		this.mealnumber = mealnumber;
		this.waitingtime = waitingtime;
		this.totalprice = totalprice;
	}

	public User(Double waitingtime, Double totalprice){
		this.waitingtime = waitingtime;
		this.totalprice = totalprice;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public void setBurritonumber(Double burritonumber) {
		this.burritonumber = burritonumber;
	}

	public Double getFriesnumber() {
		return friesnumber;
	}

	public void setFriesnumber(Double friesnumber) {
		this.friesnumber = friesnumber;
	}

	public Double getSodanumber() {
		return sodanumber;
	}

	public void setSodanumber(Double sodanumber) {
		this.sodanumber = sodanumber;
	}
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getBurritonumber() {
		return burritonumber;
	}

	public void setWaitingTime(double waitingtime) {
		this.waitingtime = waitingtime;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}
	public void setMealnumber(double mealnumber) {
		this.mealnumber = mealnumber;
	}


	public Double getWaitingtime() {
		return waitingtime;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public Double getMealnumber() {

		return mealnumber;
	}


	public void setWaitingtime(double waitingtime) {
		this.waitingtime = waitingtime;
	}
	public void clearData() {

		this.burritonumber = 0.0;
		this.friesnumber = 0.0;
		this.sodanumber = 0.0;
		this.mealnumber = 0.0;
		this.totalprice = 0.0;
		this.waitingtime = 0.0;

	}
	@Override
	public String toString() {
		return
				"Burrito Number: " + burritonumber + "\n" +
				"Fries Number: " + friesnumber + "\n" +
				"Soda Number: " + sodanumber + "\n" +
				"Meal Number: " + mealnumber + "\n" +
						"Waiting Time: " + waitingtime + "\n" +
				"Total Price: " + totalprice + "\n";

	}
}



