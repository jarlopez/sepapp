package com.sep;

import com.sep.common.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SepApplication extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/404").setViewName("404");
    }

        // Main entry point
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SepApplication.class).run(args);
    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/static/css/");
//    }
    /*
        Set up main security configurations
     */
    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/resources/**", "/registration").permitAll()
//                    .antMatchers("*.css**").permitAll()
                    .antMatchers("/epr/edit/**")
                        .access("hasRole('VICE_PRESIDENT')")
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
                .inMemoryAuthentication()
                    .withUser("admin@sep.se")
                        .password("admin")
                        .roles(UserRole.ADMIN.name())
                    .and()
                    .withUser("cs@sep.se")
                        .password("cs")
                        .roles(UserRole.CUSTOMER_SERVICE.name())
                    .and()
                    .withUser("cs-manager@sep.se")
                        .password("cs-manager")
                        .roles( UserRole.CUSTOMER_SERVICE.name(),
                                UserRole.CUSTOMER_SERVICE_MANAGER.name());
        }

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        }

    }
}
