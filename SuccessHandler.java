package com.example.demo.Project;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
@Service
public class SuccessHandler implements AuthenticationSuccessHandler
{
    String redirectUrl=null;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Collection<? extends GrantedAuthority> auth=authentication.getAuthorities();
		for(GrantedAuthority ga:auth)
		{
			if(ga.getAuthority().equals("ROLE_USER"))
			{
				redirectUrl="user/index";
				break;
			}
			else if(ga.getAuthority().equals("ROLE_ADMIN"))
			{
				redirectUrl="admin/index";
				break;
			}
		}
		if(redirectUrl==null)
		{
			throw new IllegalStateException("NO ROLE");
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

}
