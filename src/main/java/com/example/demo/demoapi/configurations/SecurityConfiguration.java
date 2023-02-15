package com.example.demo.demoapi.configurations;

import com.example.demo.demoapi.services.implementation.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailServiceImpl userDetailServiceImplementation;

    @Autowired
    public SecurityConfiguration(UserDetailServiceImpl userDetailServiceImplementation) {
        this.userDetailServiceImplementation = userDetailServiceImplementation;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> {
                            auth.antMatchers("/users/").hasRole("USER");
                            auth.antMatchers("/admin").hasRole("ADMIN");
                            auth.anyRequest().authenticated();
                }
                )
                .userDetailsService(userDetailServiceImplementation)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();
//       return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/users/").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers("/news/").permitAll()
//                        .anyRequest().authenticated()
//
//                )
//                .userDetailsService(userDetailServiceImplementation)
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .httpBasic(withDefaults())
//                .build();



   }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
