package com.java.backend.config;

import com.java.backend.enums.Role;
import com.java.backend.jwt.JwtAuthenticationEntryPoint;
import com.java.backend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
			throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(registry -> {
					registry.requestMatchers("/api/v1/auth/**").permitAll();
					// Public route
					registry.requestMatchers(HttpMethod.GET, "/api/v1/category/**").permitAll();
					registry.requestMatchers(HttpMethod.GET, "/api/v1/blog/**").permitAll();
					registry.requestMatchers(HttpMethod.GET, "/api/v1/course/**").permitAll();
					// Admin/Instructor permission
					registry.requestMatchers(HttpMethod.POST, "/api/v1/blog/**")
							.hasAnyAuthority(Role.ADMIN.name(), Role.INSTRUCTOR.name());
					registry.requestMatchers(HttpMethod.PUT, "/api/v1/blog/**")
							.hasAnyAuthority(Role.ADMIN.name(), Role.INSTRUCTOR.name());
					registry.requestMatchers(HttpMethod.DELETE, "/api/v1/blog/**")
							.hasAnyAuthority(Role.ADMIN.name(), Role.INSTRUCTOR.name());
					registry.requestMatchers(HttpMethod.DELETE, "/api/v1/course/**")
							.hasAnyAuthority(Role.ADMIN.name(), Role.INSTRUCTOR.name());
					// Only admin permission
					registry.requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasAuthority(Role.ADMIN.name());
					registry.requestMatchers(HttpMethod.PUT, "/api/v1/category/**").hasAuthority(Role.ADMIN.name());
					registry.requestMatchers(HttpMethod.DELETE, "/api/v1/category/**").hasAuthority(Role.ADMIN.name());
					// Only instructor permission
					registry.requestMatchers(HttpMethod.POST, "/api/v1/course/**").hasAuthority(Role.INSTRUCTOR.name());
					registry.requestMatchers(HttpMethod.PUT, "/api/v1/course/**").hasAuthority(Role.INSTRUCTOR.name());
					registry.requestMatchers(HttpMethod.POST, "/api/v1/video/**").hasAuthority(Role.INSTRUCTOR.name());
					registry.requestMatchers(HttpMethod.PUT, "/api/v1/video/**").hasAuthority(Role.INSTRUCTOR.name());
					registry.anyRequest().authenticated();
				}).userDetailsService(userDetailsService)
				.exceptionHandling(configurer -> configurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider()).build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
			throws Exception {
		return configuration.getAuthenticationManager();
	}
}
