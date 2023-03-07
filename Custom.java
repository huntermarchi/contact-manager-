package com.example.demo.Project;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class Custom implements UserDetails
{
    User u;
	public Custom(User u) 
	{
		this.u = u;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority g=new SimpleGrantedAuthority(u.getRole());
		return List.of(g);
	}

	@Override
	public String getPassword() {
		return u.getPass();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return u.getEm();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
