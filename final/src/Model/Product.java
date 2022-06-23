package Model;

public class Product {
	private int id;
	private String name;
	private double price;
	
	public Product() {
	}
	
	public Product(int id, String name, double price) {
		this.name = name;
		this.price = price;
		this.id	= id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
