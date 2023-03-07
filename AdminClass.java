package com.example.demo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin")
public class AdminClass 
{
	  @Autowired
	  Repo ro;
	  
	@GetMapping("/index")
    public String get()
    {  	  
  	  return "Admin/adminh";
    }
}
