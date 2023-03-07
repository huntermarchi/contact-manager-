package com.example.demo.Project;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="Contact")
public class Contact 
{
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
      private int cid;
	  @NotBlank(message="Please Enter Name")
	  @Size(min=1,max=20,message="Min length is 1 and Max length is 20")
      private String cname;
	  @NotBlank(message="Please Enter Work")
	  @Size(min=1,max=20,message="Min length is 1 and Max length is 20")
	  private String work;
	  @Email
	  @NotBlank(message="Please Enter Email")
      private String email;
	  @NotBlank(message="Please Enter Mobile Number")
	  @Pattern(regexp="^[\\d]{1,10}$",message="Only Digits are Allowed and Max Digit is 10")
      private String phone;
      private String image;	
	  @NotBlank(message="Something Write this Field")
      @Size(min=1,max=3000,message="Min length is 1 and Max length is 100000 characters")
      @Column(name="Description",length=10000)
      private String description;
	  @Override
	public String toString() {
		return "Contact [cid=" + cid + ", cname=" + cname + ", work=" + work + ", email=" + email + ", phone=" + phone
				+ ", image=" + image + ", description=" + description + ", u=" + u + "]";
	}
	public Contact(int cid,
			@NotBlank(message = "Please Enter Name") @Size(min = 1, max = 20, message = "Min length is 1 and Max length is 20") String cname,
			@NotBlank(message = "Please Enter Work") @Size(min = 1, max = 20, message = "Min length is 1 and Max length is 20") String work,
			@Email @NotBlank(message = "Please Enter Email") String email,
			@NotBlank(message = "Please Enter Mobile Number") @Pattern(regexp = "^[\\d]{1,10}$", message = "Only Digits are Allowed and Max Digit is 10") String phone,
			String image,
			@NotBlank(message = "Something Write this Field") @Size(min = 1, max = 3000, message = "Min length is 1 and Max length is 100000 characters") String description,
			User u) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.description = description;
		this.u = u;
	}
	  @ManyToOne
	  @JsonIgnore
	  public User u;
	  public Contact() {
		// TODO Auto-generated constructor stub		  
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	

	
	
}
