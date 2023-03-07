package com.example.demo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
@Configuration
@EnableWebSecurity
public class Configure extends WebSecurityConfigurerAdapter
{
   @Autowired
   SuccessHandler myhandler;
   @Bean
   public BCryptPasswordEncoder getEncoder()
   {
	   return new BCryptPasswordEncoder();
   }
   @Bean
   public  UserDetailsService getUserService()
   {
	   return new UserService();
   }
   @Bean
   public DaoAuthenticationProvider getDao()
   {
	   DaoAuthenticationProvider p=new DaoAuthenticationProvider();
	   p.setUserDetailsService(this.getUserService());
	   p.setPasswordEncoder(this.getEncoder());
	   return p;
   }
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception 
{
	auth.authenticationProvider(getDao());
}
@Override
protected void configure(HttpSecurity http) throws Exception 
{
   //http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll().and().formLogin().loginPage("/signin").loginProcessingUrl("/do-login").defaultSuccessUrl("/user/index");
	http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll().and().formLogin().loginPage("/signin").loginProcessingUrl("/do-login").successHandler(myhandler).permitAll();
}
}