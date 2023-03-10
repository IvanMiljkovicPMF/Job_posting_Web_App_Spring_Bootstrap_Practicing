package com.job_adds.JobAdds.configuration;

import com.job_adds.JobAdds.service.AdminDetailsService;
import com.job_adds.JobAdds.service.CompanyDetailsServices;
import com.job_adds.JobAdds.service.WorkerDetailsServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService workerDetailsService() {
        return new WorkerDetailsServices();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(workerDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    @Order(1)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider1());
        http.authorizeHttpRequests().requestMatchers("/").permitAll();

        http
                .securityMatcher("/worker/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                ).formLogin().loginPage("/worker/worker_login")
                .usernameParameter("email")
                .loginProcessingUrl("/worker/worker_login")
                .defaultSuccessUrl("/worker/home_worker")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/worker/logout")
                .logoutSuccessUrl("/");

        return http.build();
    }
    @Bean
    public UserDetailsService companyDetailsService() {return new CompanyDetailsServices();}
    @Bean
    public DaoAuthenticationProvider authenticationProvider2() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(companyDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    @Order(2)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider2());
        http.authorizeHttpRequests().requestMatchers("/").permitAll();

        http
                .securityMatcher("/company/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                ).formLogin().loginPage("/company/company_login")
                .usernameParameter("email")
                .loginProcessingUrl("/company/company_login")
                .defaultSuccessUrl("/company/home_company")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/company/logout")
                .logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public UserDetailsService adminDetailsServices() {return new AdminDetailsService();}
    @Bean
    public DaoAuthenticationProvider authenticationProvider3() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailsServices());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain3(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider3());

        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                ).formLogin().loginPage("/admin/admin_login")
                .usernameParameter("email")
                .loginProcessingUrl("/admin/admin_login")
                .defaultSuccessUrl("/admin/home_admin")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/");
        return http.build();
    }
}
