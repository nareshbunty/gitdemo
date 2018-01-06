package com.cba.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
@EnableWebSecurity
@Configuration
public class SecurityConfig extends
WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	@Autowired
	public void configureGlobal
	(AuthenticationManagerBuilder builder)
			throws Exception{
builder.jdbcAuthentication().
dataSource(dataSource).
passwordEncoder(new BCryptPasswordEncoder()).
usersByUsernameQuery("select user_name,password,enabled from user_authentication where user_name=?").
authoritiesByUsernameQuery("select user_name,user_role from USER_AUTHORITIES where user_name=?");
//builder.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}
	@Override
	public void configure(HttpSecurity http) throws Exception{
	http.authorizeRequests().
	antMatchers("/").permitAll().
	antMatchers("/dashboard/**").permitAll().
	and().formLogin().loginPage("/login").
	defaultSuccessUrl("/dashboard").
	failureUrl("/login?error=Invalid").
	usernameParameter("username").
	passwordParameter("password").
	and().logout().
	logoutSuccessUrl("/logout").and().
	csrf()
    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

	// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		
	
	}
}
