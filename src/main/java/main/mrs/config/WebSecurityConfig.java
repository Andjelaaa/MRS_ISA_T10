package main.mrs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import main.mrs.security.TokenUtils;
import main.mrs.security.auth.RestAuthenticationEntryPoint;
import main.mrs.security.auth.TokenAuthenticationFilter;
import main.mrs.service.KorisnikService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private KorisnikService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint).and().authorizeRequests()
				.antMatchers("/auth/**", "/api/**").permitAll().anyRequest().authenticated().and().cors().and()
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/**");
		//web.ignoring().antMatchers(HttpMethod.POST, "api/**"); // skloniti kad stavimo svuda @preauthorize
		web.ignoring().antMatchers(HttpMethod.GET, "api/verification/potvrdiRegistraciju/**");
		web.ignoring().antMatchers(HttpMethod.POST, "api/zahtevreg");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg");
	}

}
