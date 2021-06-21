package br.com.tsmweb.biblioteca.models.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.tsmweb.biblioteca.models.service.security.jwt.JwtConfigurer;
import br.com.tsmweb.biblioteca.models.service.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
//	@Override
//	public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
//		var password = passwordEncoder().encode("123456");
//		
//		System.out.println(password);
//		
//		authBuilder.inMemoryAuthentication()
//			.withUser("tiago.tsmweb@gmail.com")
//			.password(passwordEncoder().encode("123456"))
//			.roles("ADMIN");
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic();
		
		http.authorizeRequests()
			.antMatchers("/rest/login").permitAll()
			.antMatchers("/rest/usuario/**").hasRole("ADMIN")
			.antMatchers("/rest/departamento/**").hasRole("ADMIN")
			.antMatchers("/rest/role/**").hasRole("ADMIN")
			.antMatchers("/rest/livro/**").hasRole("ADMIN")
			.antMatchers("/rest/editora/**").hasRole("ADMIN")
			.anyRequest().authenticated();
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.csrf().disable();
		http.cors();
		
		http.apply(new JwtConfigurer(tokenProvider));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
