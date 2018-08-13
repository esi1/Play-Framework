package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	private String password;
	private String firstname;
	private String lastname;
	private String streetaddress;
	private String phonenumber;
	private String email;
	private String city;
	private String postcode;

	 @OneToMany(mappedBy="user")
	 private List<Order> orders;

	public User() {
		super();
	}

	public User(String password, String firstname, String lastname,
			String streetaddress, String phonenumber, String email,
			String city, String postcode) {
		super();
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.streetaddress = streetaddress;
		this.phonenumber = phonenumber;
		this.email = email;
		this.city = city;
		this.postcode = postcode;
	}

	public User(String password, String firstname, String email) {
		super();
		this.password = password;
		this.firstname = firstname;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStreetaddress() {
		return streetaddress;
	}

	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	 public List<Order> getOrders() {
	 return orders;
	 }
	
	 public void setOrders(List<Order> orders) {
	 this.orders = orders;
	 }

}
