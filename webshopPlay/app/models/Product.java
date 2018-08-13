package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue
	private int id;
	private String productname;
	private String description;
	private double cost;
	private double rrp;


	@ManyToOne
	private Category category;
	
	public Product() {
		super();
	}

	public Product(String productname) {
		super();
		this.productname = productname;
	}

	public Product( String productname, String description, double cost,
			double rrp) {
		super();
		this.productname = productname;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getRrp() {
		return rrp;
	}

	public void setRrp(double rrp) {
		this.rrp = rrp;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return productname;
	}

}
