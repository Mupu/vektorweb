package me.mupu.vektorweb.config;

import me.mupu.vektorweb.HashPasswordEncoder;
import me.mupu.vektorweb.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
//        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    HashPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // tells spring boot to take my password encode ander my userService as default
        auth
                .userDetailsService(customUserService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("**").permitAll()
                .antMatchers("/", "/home").permitAll()

                .antMatchers("/favicon.ico").permitAll()

                .antMatchers("/registration").permitAll()
                .antMatchers("/confirmation").permitAll()
                .antMatchers("/resendConfirmationEmail").permitAll()

                .antMatchers("/login").permitAll()

                .antMatchers("/forgotCredentials/resetPassword").permitAll()
                .antMatchers("/forgotCredentials/username").permitAll()
                .antMatchers("/forgotCredentials/password").permitAll()

                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .defaultSuccessUrl("/home")

                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")

                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }

}
