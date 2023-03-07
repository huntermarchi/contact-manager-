package com.example.demo.Project;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Entity
@Table(name="User")
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	@NotBlank(message="Please! Enter the Name")
	@Size(min=1,max=20,message="Min Length is 1 and Max Length is 20")
	@Column(name="Name")
    private String na;
	@NotBlank(message="Please! Enter the Password")
	@Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,100}$",message="Password must be min 8 characters and contains 1 Uppercase latter,1 lowercase,1 numeric value and 1 special symbol")
	@Column(name="Password")
	private String pass;
	@NotBlank(message="Please! Enter the Email")
	@Email(message="Email is Incorrect Format")
	@Column(name="Email")
    private String em;
	@Column(name="Role")
    private String role;
	@Column(name="Image")
	private String img;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@OneToMany(cascade=CascadeType.ALL,mappedBy="u")
	private List<Contact>con;
	public User() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNa() {
		return na;
	}
	public void setNa(String na) {
		this.na = na;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Contact> getCon() {
		return con;
	}
	public void setCon(List<Contact> con) {
		this.con = con;
	}
	
	public User(int id,
			@NotBlank(message = "Please! Enter the Name") @Size(min = 1, max = 20, message = "Min Length is 1 and Max Length is 20") String na,
			@NotBlank(message = "Please! Enter the Password") @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,100}$", message = "Password must be min 8 characters and contains 1 Uppercase latter,1 lowercase,1 numeric value and 1 special symbol") String pass,
			@NotBlank(message = "Please! Enter the Email") @Email(message = "Email is Incorrect Format") String em,
			@NotBlank(message = "Please! Select the Role") String role, String img, List<Contact> con) {
		super();
		this.id = id;
		this.na = na;
		this.pass = pass;
		this.em = em;
		this.role = role;
		this.img = img;
		this.con = con;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", na=" + na + ", pass=" + pass + ", em=" + em + ", role=" + role + ", img=" + img
				+ ", con=" + con + "]";
	}
	
	
	
}
