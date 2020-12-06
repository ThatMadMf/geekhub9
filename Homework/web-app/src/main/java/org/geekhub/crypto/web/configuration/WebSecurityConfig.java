package org.geekhub.crypto.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImp();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/update-password").hasAuthority("ROLE_USER")
                .antMatchers("/application").hasAuthority("ROLE_USER")
                .antMatchers("/application/encode").hasAuthority("ROLE_USER")
                .antMatchers("/application/decode").hasAuthority("ROLE_USER")
                .antMatchers("/api/**").hasAuthority("ROLE_USER")
                .antMatchers("/login").permitAll()
                .antMatchers("/application/analytics/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/application/history/show-history").hasAuthority("ROLE_USER")
                .antMatchers("/application/history/clear-history").hasAuthority("ROLE_ADMIN")
                .antMatchers("/application/history/remove-last").hasAuthority("ROLE_ADMIN");
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_GUEST");

        return roleHierarchy;
    }

}