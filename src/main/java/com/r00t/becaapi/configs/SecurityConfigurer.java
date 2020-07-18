package com.r00t.becaapi.configs;

import com.r00t.becaapi.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SecurityRequestFilter securityRequestFilter;

    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                //.antMatchers("/admin/**").hasRole("ADMIN")
                //.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                //.antMatchers("/auth/**").permitAll()
                //.antMatchers("/cart/**").permitAll()
                //.antMatchers("/checkout/**").permitAll()
                //.antMatchers("/extras/**").permitAll()
                //.antMatchers("/kit/**").permitAll()
                //.antMatchers("/location/**").permitAll()
                //.antMatchers("/payment/**").permitAll()
                //.antMatchers("/product/**").permitAll()

                .antMatchers("/actuator/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(securityRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors();
    }
}
