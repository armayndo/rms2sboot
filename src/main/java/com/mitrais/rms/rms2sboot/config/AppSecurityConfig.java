package com.mitrais.rms.rms2sboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mitrais.rms.rms2sboot.config.CustomAuthenticationSuccessHandler;
import com.mitrais.rms.rms2sboot.service.UserService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
    private UserService userService;
	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*
		//Before
		// add our users for in memory authentication
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
			.withUser(users.username("mary").password("test123").roles("MANAGER"))
			.withUser(users.username("susan").password("test123").roles("ADMIN"))
			.withUser(users.username("roma").password("test123").roles("ADMIN","EMPLOYEE"));
		// */
		
		//after
		auth.authenticationProvider(authenticationProvider());
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		//*
//		 //Before using roles
//		 http.authorizeRequests()
//				.anyRequest().authenticated()
//			.and()
//			.formLogin()
//				.loginPage("/showMyLoginPage")
//				.loginProcessingUrl("/authenticateTheUser")
//				.permitAll()
//			.and()
//			.logout().permitAll();
//		 // */
//		
//		
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//*
		//after using roles
		http.authorizeRequests()
				//belum bisa
		        //.antMatchers("/public/**", "/resources/**","/resources/public/**","/*.css").permitAll()
				.antMatchers("/", "/profile/**").hasRole("EMPLOYEE")
				.antMatchers("/leaders/**").hasRole("MANAGER")
				.antMatchers("/systems/**").hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		// */
	}

	 	//beans
		//bcrypt bean definition, if active cannot use User.withDefaultPasswordEncoder
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		//authenticationProvider bean definition
		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
			auth.setUserDetailsService(userService); //set the custom user details service
			auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
			return auth;
		}
		
		@Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring()
	                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/bower_components/**","/dist/**","/plugins/**");
	    }
		
		
}
