package com.example.demo.Project;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
public class MainPart 
{
	     @Autowired
	     Repo r;
	     @Autowired
	     private BCryptPasswordEncoder en;
	     @Autowired
	     EmailService ser;
         @GetMapping(value={"/","/main"})
         public String base(Model m)
         {
        	 return "base";
         }
         @GetMapping("/about")
         public String about(Model m)
         {
        	 return "about";
         }
         @GetMapping("/signup")
         public String signup(Model m)
         {
        	 m.addAttribute("user", new User());
        	 return "signup";
         }
         @GetMapping("/signin")
         public String signin()
         {
        	 return "login";
         }
         @GetMapping("/about1")
         public String AboutSection()
         {
        	 return "About";
         }
         @PostMapping("/register")
         public String register(@Valid@ModelAttribute User u,BindingResult result,HttpSession s,Model m,@RequestParam(value="check",defaultValue="false")boolean b)
         {
        	 try
        	 {
        	   User u1=r.findByEm(u.getEm());
        	   if(u1!=null)
        	   {
        		 s.setAttribute("message", new Message("Email Already Registered","alert-danger")); 
         		 m.addAttribute("user",u);
         		 return "signup"; 
        	   }
        	 }
        	 catch(Exception e) {}
             if(!b)
        	 {
        		s.setAttribute("message", new Message("Please check the Terms and Conditions","alert-danger")); 
        		m.addAttribute("user",u);
        		return "signup";
        	 }
        	 else if(result.hasErrors())
        	 {
        		 m.addAttribute("user",u);
        		 return "signup";
        	 }
        	 else
        	 {
        		 s.setAttribute("message", new Message("Signup Successfull","alert-success"));
        		 m.addAttribute("user",new User());
        		 u.setRole("ROLE_USER");
        		 u.setPass(en.encode(u.getPass()));
        		 r.save(u);
        	     return "signup";
        	 }
         }
         @GetMapping("/search")
         public void getSearch(@RequestParam(value="srch")String s,HttpServletResponse rs) throws IOException
         {
        	
        	String s1="https://www.google.com/search?query="+s;
        	rs.sendRedirect(s1);
         }
         @PostMapping(value="/forget",produces = "application/json")
         public void forgot(@RequestParam("em")String p,HttpServletResponse rs,HttpSession sd,HttpSession sd1) throws IOException, MessagingException
         {
        	  User u=r.findByEm(p);
        	  PrintWriter out=rs.getWriter();
        	  Message mn=new Message("Otp is sent Successfull","alert-success");
        	  Message mn1=new Message("Email is Incorrect","alert-danger");
        	  ObjectMapper m=new ObjectMapper();
        	  HashMap<String,String>m1=new HashMap<>();
        	  String obj=null;
        	  if(u!=null)
        	  {
        		  Random r=new Random();
        		  int op=r.nextInt(10000);
        		  boolean b=ser.sendMail(p, op);
        		  if(b)
        		  {
        		    sd.setAttribute("otp",op);
        		    sd1.setAttribute("em",u.getEm());
        		    m1.put("message",mn.getContent());
        		    m1.put("type",mn.getType());
        		    m1.put("status","true");
        		    obj=m.writeValueAsString(m1);
        		    out.println(obj);
        		  }
        		  else
        		  {
        			m1.put("message","Some Techinical Issue");
          		    m1.put("type","alert-danger");
          		    m1.put("status","false");
          		    obj=m.writeValueAsString(m1);
          		    out.println(obj); 
        		  }
        		 
        	  }
        	  else
        	  {
        		  m1.put("message",mn1.getContent());
        		  m1.put("type",mn1.getType());
        		  m1.put("status","false");
        		  obj=m.writeValueAsString(m1);
        		  out.println(obj);
        	  }
         }
         @PostMapping("/verify")
         public String verifyOTP(@RequestParam("otp1")String s,HttpSession se,HttpSession s2)
         {
        	    int o=(int)se.getAttribute("otp");
        	    try
        	    {
        	     int fo=Integer.parseInt(s);
        	     if(o==fo)
        	     {
        	          return "reset";	     
        	     }
        	     else
        	     {
        	    	 return "redirect:signin?change=Otp is Incorrect!Try Again";
        	     }
        	    }
        	    catch(Exception e) {}
        	    return "redirect:signin?change=Enter Only 4 Digits of Number not Any Characters";
         }
         @PostMapping("/newPass")
         public String setNewPass(@RequestParam("pass") String s2,Model m,HttpSession s,HttpSession s1)
         {
        	 String past="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])(?!.*\\s).{8,100}$";
        	 boolean b=Pattern.matches(past,s2);
        	 if(!b)
        	 {
        		 s1.setAttribute("message", new Message("Your Password is Incorrect Format","alert-danger"));
        		 return "reset";
        	 }
        	 else
        	 {
        	   String email=(String)s.getAttribute("em");
        	   User u1=r.findByEm(email);
        	   u1.setPass(en.encode(s2));
        	   r.save(u1);
        	   s.removeAttribute("otp");
        	   s.removeAttribute("em");
        	   s1.setAttribute("message",new Message("New Password has been Created Successfully","alert-success"));
        	   return "reset";
        	 }
         }
}
