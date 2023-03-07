package com.example.demo.Project;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Controller
@RequestMapping("/user")
public class Security 
{
	  
	  @Autowired
	  Repo ro;
	  @Autowired
	  RepoContact rc;
	  @Autowired
	  BCryptPasswordEncoder cry;
	  @ModelAttribute
	  public void CommonData(Model m,Principal p)
	  {
		  String n=p.getName();
    	  User u=ro.findByEm(n);
    	  m.addAttribute("user", u);
	  }
      @GetMapping("/index")
      public String get()
      {  	  
    	  return "normal/client";
      }
      @GetMapping("/donateUs")
      public String getPay()
      {  	  
    	  return "normal/payment";
      }
      @GetMapping("/add-Contact")
      public String addContact(Model m)
      {
    	  m.addAttribute("contact", new Contact());
    	  return "normal/add-contact";
      }
      @PostMapping("/sc")
      public String saveContact(@Valid@ModelAttribute Contact c,BindingResult resu,@RequestParam("profile")MultipartFile f,Principal p,Model m,HttpSession d,HttpServletRequest req1) throws IOException
      {
     	    String pa=req1.getServletContext().getRealPath("Images/");
    	    String name=f.getOriginalFilename();
    	    String con=f.getContentType();
    	    String spl[]=con.split("/");
    	    String newName=spl[1].toLowerCase();
    	    long size=f.getSize();
    	    List<String>ar=Arrays.asList("jpeg","jpg","png");
    	    if(resu.hasErrors())
    	    {
    	    	m.addAttribute("contact",c);
    	    	return "normal/add-contact";
    	    }
    	    else if(f.isEmpty())
    	    {
    	    	d.setAttribute("message", new Message("Please Upload the Image","alert-danger"));
    	    	return "normal/add-contact";
    	    }
    	    else if(!ar.contains(newName))
    	    {
    	    	d.setAttribute("message", new Message("Incorrect File Format","alert-danger"));
    	    	return "normal/add-contact";
    	    }
    	    else if(size>=50000)
    	    {
    	    	d.setAttribute("message", new Message("File Size is Max","alert-danger"));
    	    	return "normal/add-contact";
    	    }
    	    else
    	    {
    	        String u=p.getName();
    	        User u1=ro.findByEm(u);
    	        List<Contact>l=new ArrayList<>();
    	        l.add(c);
    	        c.setU(u1);
    	        c.setImage(c.getCname()+name);
    	        u1.setCon(l);
    	        ro.save(u1);   	       
    	        Files.copy(f.getInputStream(),Paths.get(pa+File.separator+c.getCname()+name), StandardCopyOption.REPLACE_EXISTING);
    	        m.addAttribute("contact", new Contact());
    	        d.setAttribute("message", new Message("Contact has been saved Successfully","alert-success"));
    	        return "normal/add-contact";
    	    }
      }
      @GetMapping("/show-Contact/{page}")
      public String showContact(@PathVariable("page")int i,Model m,Principal p) throws IOException
      {
    	  if(i<0)
    	  {
    		  return "redirect:/user/show-Contact/0";
    	  }
    	  else
    	  {
    	  int perPage=3;
    	  String n=p.getName();
    	  User u1=ro.findByEm(n);
    	  Pageable p1=PageRequest.of(i,perPage);
          Page<Contact>pu=rc.findContactsById(u1.getId(), p1); 	  
    	  m.addAttribute("show",pu);
    	  m.addAttribute("current",i);
    	  m.addAttribute("total",pu.getTotalPages());
    	  return "normal/show-contact";
    	  }
      }
      @GetMapping("/profile1")
      public String showProfile(Model m)
      {
    	  m.addAttribute("status1",true);
    	  return "normal/pro";
      }
      @PostMapping("/profile_user")
      public String saveUserProfile(@RequestParam("profile1")MultipartFile f2,Principal p,HttpSession d1,HttpServletRequest req) throws IOException
      {
    	  String n=p.getName();
    	  
    	  User u=ro.findByEm(n);
    	  String pa=req.getServletContext().getRealPath("Images/");
  	      String name=f2.getOriginalFilename();
  	      String con1=f2.getContentType();
  	      String spl[]=con1.split("/");
  	      String newName=spl[1].toLowerCase();
  	      long size=f2.getSize();
  	      List<String>ar=Arrays.asList("jpeg","jpg","png");
  	     if(f2.isEmpty())
	     {
	    	return "normal/pro";
	    }
	    else if(!ar.contains(newName))
	    {
	    	return "normal/pro";
	    }
	    else if(size>=50000)
	    {
	    	return "normal/pro";
	    }
	    else
	    {
	    	 u.setImg(u.getNa()+name);
	    	 ro.save(u);
	    	 Files.copy(f2.getInputStream(),Paths.get(pa+File.separator+u.getNa()+name), StandardCopyOption.REPLACE_EXISTING);
	    	 return "normal/pro";
	    }
      }
      @PostMapping("/user_pass")
      public String upc(@RequestParam("old")String o,@RequestParam("New")String n,Principal p,HttpSession d3)
      {
    	  String nam=p.getName();
    	  User uo=ro.findByEm(nam);
    	  if(o.equals("")||n.equals(""))
    	  {
    		  d3.setAttribute("message", new Message("Please Enter the Password","alert-danger"));
    		  return "normal/pro";
    	  }
    	  else
    	  {
    		if(cry.matches(o,uo.getPass()))
    		{
    			uo.setPass(cry.encode(n));
    			ro.save(uo);
    			d3.setAttribute("message", new Message("New Password has been Set successfully","alert-success"));
    		}
    		else
    		{
    			d3.setAttribute("message", new Message("Password is Not Matched","alert-danger"));
    		}
    	      return "normal/pro";
    	  }
      }
      @GetMapping("/delete/{id}")
      public String deleteContact(@PathVariable("id")int id,Principal p,HttpSession h,Model m,HttpServletRequest req)
      {
    	  User u=ro.findByEm(p.getName());
    	  Optional<Contact>c1=rc.findById(id);
    	  if(c1.isPresent())
    	  {
    		Contact c=c1.get();
    	    String pa=req.getServletContext().getRealPath("Images/");
    	    if(u.getId()==c.getU().getId())
    	    {
    		    String iname=c.getImage();
    		    File f=new File(pa,iname);
    		    f.delete();
                rc.deleteById(c.getCid());
                return "redirect:/user/show-Contact/0";
    	    }
    	    else
    	    {
              return "redirect:/user/show-Contact/0";
    	    }
    	  }
    	  else
    	  {
    		  return "redirect:/user/show-Contact/0";
    	  }
      }
      @PostMapping("/updateForm")
      public String UpdateF(HttpServletRequest req,Model m)
      {
    	    int i=Integer.parseInt(req.getParameter("id"));
    	    Optional<Contact>o=rc.findById(i);
    		Contact l=o.get();
    	    m.addAttribute("contact", l);
    	    return "normal/update";
    	    
      }
      @PostMapping("/editRecord")
      public String recordUpdate(@Valid@ModelAttribute Contact c,BindingResult res,@RequestParam("profile3")MultipartFile f,Principal p,HttpSession d5,HttpServletRequest hr,Model m) throws IOException
      {
    	String pa=hr.getServletContext().getRealPath("Images/");
    	Contact c2=rc.findById(c.getCid()).get();
    	String iname=c2.getImage();
	    File f1=new File(pa,iname);
	    f1.delete();
  	    String name=f.getOriginalFilename();
  	    String con=f.getContentType();
  	    String spl[]=con.split("/");
  	    String newName=spl[1].toLowerCase();
  	    long size=f.getSize();
  	    List<String>ar=Arrays.asList("jpeg","jpg","png");
  	    if(res.hasErrors())
  	    {
  	    	m.addAttribute("contact",c);
  	    	return "redirect:/user/show-Contact/0";
  	    }
  	    else if(f.isEmpty())
  	    {
  	    	d5.setAttribute("message", new Message("Please Upload the Image","alert-danger"));
  	    	return "redirect:/user/show-Contact/0";
  	    }
  	    else if(!ar.contains(newName))
  	    {
  	    	d5.setAttribute("message", new Message("Incorrect File Format","alert-danger"));
  	    	return "redirect:/user/show-Contact/0";
  	    }
  	    else if(size>=50000)
  	    {
  	    	d5.setAttribute("message", new Message("File Size is Max","alert-danger"));
  	    	return "redirect:/user/show-Contact/0";
  	    }
  	    else
  	    {
  	        String u=p.getName();
  	        User u1=ro.findByEm(u);
  	        List<Contact>l=new ArrayList<>();
  	        l.add(c);
  	        c.setU(u1);
  	        c.setImage(c.getCname()+name);
  	        u1.setCon(l);
  	        ro.save(u1);   	       
  	        m.addAttribute("contact", new Contact());
  	        d5.setAttribute("message", new Message("Records Updated Successfully","alert-success"));
  	        Files.copy(f.getInputStream(),Paths.get(pa+File.separator+c.getCname()+name), StandardCopyOption.REPLACE_EXISTING);
  	        return "redirect:/user/show-Contact/0";
  	    }
      }
      @GetMapping("/showProfile/{id}")
      public String showProfile1(@PathVariable("id")int id,Model m)
      {
    	  Contact c=rc.findById(id).get();
    	  m.addAttribute("contact", c);
    	  return "normal/u_profile";
      }
}
