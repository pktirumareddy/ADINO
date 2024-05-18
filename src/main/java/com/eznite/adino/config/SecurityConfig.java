package com.eznite.adino.config;

import java.time.Instant;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class SecurityConfig {

    private static final String[] SWAGGERLIST = { "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-resources" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers(SWAGGERLIST).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .sessionRegistry(sessionRegistry())
                    .and()
                .sessionFixation().migrateSession();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user@123"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin@123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Scheduled(fixedRate = 60000)
    public void invalidateExpiredSessions() {
        List<Object> principals = sessionRegistry().getAllPrincipals();
        for (Object principal : principals) {
            List<SessionInformation> sessions = sessionRegistry().getAllSessions(principal, false);
            for (SessionInformation sessionInfo : sessions) {
                if (isSessionExpired(sessionInfo)) {
                    System.out.println("Removing expired session: " + sessionInfo.getSessionId());
                    sessionRegistry().removeSessionInformation(sessionInfo.getSessionId());
                }
            }
        }
    }

    private boolean isSessionExpired(SessionInformation sessionInfo) {
        long sessionTimeoutInMillis = 1 * 60 * 1000; // 5 minutes
        long lastRequestTimeInMillis = sessionInfo.getLastRequest().getTime();
        long currentTimeInMillis = Instant.now().toEpochMilli();
        return (currentTimeInMillis - lastRequestTimeInMillis) > sessionTimeoutInMillis;
    }
}

