package product.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "products")
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlAttribute
	private long id;
	private String name;
//	@XmlElement(required=true,nillable=false)
	private String description;
	private double cost;
	private double price;
	private double weight;
	private String size;
	private String sex;
	private double quantity;
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Product() {

	}

//	public Product() {
//		
//	}

	public Product(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("[%ld] %s (%s)", id, name, description);
	}

	/**
	 * Two contacts are equal if they have the same id, even if other attributes
	 * differ.
	 * 
	 * @param other
	 *            another contact to compare to this one.
	 */
	public boolean equals(Object other) {
		if (other == null || other.getClass() != this.getClass())
			return false;
		Product product = (Product) other;
		return product.getId() == this.getId();
	}


	/**
	 * Test if a string is null or only whitespace.
	 * 
	 * @param arg
	 *            the string to test
	 * @return true if string variable is null or contains only whitespace
	 */
	private static boolean isEmpty(String arg) {
		return arg == null || arg.matches("\\s*");
	}
	
	/**
	 * Construct sha1(secure hash) of a text string.
	 * @return string string of sha1
	 */
	public String sha1() {
		String input = ""+id+name+description+price;
        MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
}
