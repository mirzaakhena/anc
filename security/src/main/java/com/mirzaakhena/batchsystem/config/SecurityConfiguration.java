package com.mirzaakhena.batchsystem.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

//@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private RememberMeServices rememberMeServices;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//            .antMatchers(HttpMethod.OPTIONS, "/**")
//            .antMatchers("/app/**/*.{js,html}")
//            .antMatchers("/bower_components/**")
//            .antMatchers("/i18n/**")
//            .antMatchers("/content/**")
//            .antMatchers("/swagger-ui/index.html")
//            .antMatchers("/test/**")
//            .antMatchers("/scripts/**")
//            .antMatchers("/styles/**")
//            .antMatchers("/scripts/**")
//            .antMatchers("/h2-console/**");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
    	.userDetailsService(userDetailsService)
    	.passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//        	.userDetailsService(userDetailsService)
            .csrf().csrfTokenRepository(csrfTokenRepository())
//            .ignoringAntMatchers("/websocket/**")
        .and()
            .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
            .exceptionHandling()
//            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint((req, res, authExp)->res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied"))
//        .and()
//            .rememberMe()
//            .rememberMeServices(rememberMeServices)
//            .rememberMeParameter("remember-me")
//            .key(jHipsterProperties.getSecurity().getRememberMe().getKey())
        .and()
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .successHandler((req, res, auth)->res.setStatus(HttpServletResponse.SC_OK))
            .failureHandler((req, res, authExp)->res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed"))
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler((req, res, auth)->res.setStatus(HttpServletResponse.SC_OK))
            .deleteCookies("JSESSIONID", "XSRF-TOKEN")
            .permitAll()
//        .and()
//            .headers()
//            .frameOptions()
//            .disable()
        .and()
            .authorizeRequests()
            .anyRequest().permitAll()
//            .antMatchers("/api/register").permitAll()
//            .antMatchers("/api/activate").permitAll()
//            .antMatchers("/api/authenticate").permitAll()
//            .antMatchers("/api/account/reset_password/init").permitAll()
//            .antMatchers("/api/account/reset_password/finish").permitAll()
//            .antMatchers("/api/profile-info").permitAll()
//            .antMatchers("/api/**").authenticated()
//            .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/websocket/**").permitAll()
//            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/v2/api-docs/**").permitAll()
//            .antMatchers("/configuration/ui").permitAll()
//            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);
            ;
    }
    
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
    
    private static class CsrfCookieGeneratorFilter extends OncePerRequestFilter {
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
			CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			if (csrf != null) {
				Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
				String token = csrf.getToken();
				if (cookie == null || token != null && !token.equals(cookie.getValue())) {
					cookie = new Cookie("XSRF-TOKEN", token);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}

			filterChain.doFilter(request, response);
		}
	}

}
