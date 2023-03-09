package com.phyros.mymoney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean()
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http.cors().and()
			.csrf().disable()
				//Desabilita a autenticação por meio de sessões statefull e habilita o modo stateless
				//Desbloqueia todas as urls		
//				.authorizeHttpRequests(auth -> {
//					auth.anyRequest().authenticated().and().addFilter(new AuthenticationFilter());
//				})
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests( 
						auth -> { 
							auth.requestMatchers(HttpMethod.POST, "/login")
							.permitAll()
							.anyRequest()
							.authenticated()
							.and()
							.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);});

//				.authorizeHttpRequests((authorize) -> authorize
//						.anyRequest().authenticated()
//				)
//				.httpBasic(withDefaults())
//				.formLogin(withDefaults());
		// @formatter:on
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		@SuppressWarnings("deprecation")
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}
	
	//"Ensinar" ao spring como injetar esse objeto, sabe criar o objeto AuthenticationManager
	@Bean
	//o Bean serve para exportar uma classe para o spring, fazendo com que ele consiga carregá-la e realize
	//a sua injeção de dependência em outra classes. O spring pode usar internamente ou pelo desenvolvedor
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
