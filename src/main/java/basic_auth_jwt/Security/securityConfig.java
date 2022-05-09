package basic_auth_jwt.Security;

import basic_auth_jwt.Security.filter.JWTauthorizationFilter;
import basic_auth_jwt.Security.filter.JwtAuthFilter;
import basic_auth_jwt.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bcrypte = new BCryptPasswordEncoder();

    @Autowired
    private IUserService iuserService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iuserService).passwordEncoder(bcrypte);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/").permitAll().
                antMatchers("/login/**").permitAll().
                antMatchers("/utils/**").permitAll().
                antMatchers("/inscription/**").permitAll().
                antMatchers("/Administration/**").hasAuthority("ADMIN").
                anyRequest().authenticated();

        http.addFilter(new JwtAuthFilter(authenticationManager()));
        http.addFilterBefore(new JWTauthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
