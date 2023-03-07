package com.example.demo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class UserService implements UserDetailsService
{
    @Autowired
    Repo r1;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User u=r1.findByEm(username);
		if(u==null)
		{
			throw new UsernameNotFoundException ("User Not Found");
		}
		Custom cu=new Custom(u);
		return cu;
	}

}
