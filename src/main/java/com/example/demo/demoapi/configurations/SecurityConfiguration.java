package com.example.demo.demoapi.configurations;

import com.example.demo.demoapi.services.implementation.UserDetailServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private UserDetailServiceImplementation userDetailServiceImplementation;

    @Autowired
    public SecurityConfiguration(UserDetailServiceImplementation userDetailServiceImplementation) {
        this.userDetailServiceImplementation = userDetailServiceImplementation;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .authorizeRequests(auth -> {
//                            auth.antMatchers("/users/").hasRole("USER");
//                            auth.antMatchers("/admin").hasRole("ADMIN");
//                }
//                )
//                .userDetailsService(userDetailServiceImplementation)
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .httpBasic(withDefaults())
//                .build();
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("/users/").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers("/news/").permitAll()

                )
                .userDetailsService(userDetailServiceImplementation)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
