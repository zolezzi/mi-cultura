package unq.edu.li.pdes.micultura.configuration;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.security.CustomAccessDeniedHandler;
import unq.edu.li.pdes.micultura.security.JWTAuthorizationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final UserRepository repository;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    	
    	 http.csrf().disable();
         http.cors().configurationSource(corsConfigurationSource());
         return http
        		 .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                 .and()
                 .authorizeRequests()
                 .antMatchers("/api/create", "/api/login").permitAll()
                 .and()
                 .authorizeRequests()
                 .antMatchers("/api/micultura").permitAll()
                 .and()
                 .authorizeRequests()
                 .antMatchers("/api/**").authenticated()
                 .anyRequest()
                 .permitAll()
                 .and()
                 .exceptionHandling().and().sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
    	return new UserDetailsService() {
    		@Override
            public User loadUserByUsername(String username) throws UsernameNotFoundException {
                return repository.findOneByEmail(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User " + username + " not found"));
            }
    	};
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
     
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(Boolean.TRUE);
        configuration.setAllowedOrigins(Arrays.asList("https://mi-cultura-production.up.railway.app", "http://localhost:8080", "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));  
        configuration.setMaxAge(Duration.ZERO);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
