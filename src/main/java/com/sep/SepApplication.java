package com.sep;

import com.sep.common.UserRole;
import com.sep.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableJpaRepositories(value="com.sep.repositories")
public class SepApplication extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    // Main entry point
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SepApplication.class).run(args);
    }

    /*
        Set up main security configurations
     */
    @Configuration
    @EnableWebSecurity
    @ComponentScan(basePackageClasses = UserDetailsServiceImpl.class)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    // Not the most secure, but works fine for local projects
                    .csrf().disable()
                    .headers().disable()
                    .authorizeRequests()
                    .antMatchers("/console/**").permitAll()
                    .antMatchers("/epr/create/**")
                    .hasAnyRole(
                            UserRole.CUSTOMER_SERVICE.name(),
                            UserRole.CUSTOMER_SERVICE_MANAGER.name()
                    )
                    .antMatchers("/client/list")
                    .hasAnyRole(
                            UserRole.CUSTOMER_SERVICE_MANAGER.name(),
                            UserRole.FINANCIAL_MANAGER.name()
                    )
                    .antMatchers("/client/**")
                    .hasAnyRole(
                            UserRole.CUSTOMER_SERVICE_MANAGER.name()
                    )
                    .antMatchers("/task/mgmt/**")
                    .hasAnyRole(
                            UserRole.TEAM_MANAGER.name()
                    )
                    .antMatchers("/task/**")
                    .hasAnyRole(
                            UserRole.TEAM_MANAGER.name(),
                            UserRole.TEAM_MEMBER.name()
                    )
                    .anyRequest()
                    .fullyAuthenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?loginError").permitAll()
                    .and()
                    .logout().permitAll();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        }
    }
}
