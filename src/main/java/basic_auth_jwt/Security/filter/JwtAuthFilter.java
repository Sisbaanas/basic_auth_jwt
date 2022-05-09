package basic_auth_jwt.Security.filter;

import basic_auth_jwt.Security.SecurityConstants;
import basic_auth_jwt.entities.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            System.out.println(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Users appUsers = null;
        try {

            appUsers = new ObjectMapper().readValue(request.getInputStream(), Users.class);

            System.out.println("login is ///.:"+ appUsers.getEmail());
            System.out.println("Password is ///.:"+ appUsers.getPassword());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUsers.getEmail(), appUsers.getPassword()));

        } catch (IOException e) {
            throw new RuntimeJsonMappingException(e.getMessage());

        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User springUsers = (User) authResult.getPrincipal();

        String Jwt = Jwts.builder()
                .setSubject(springUsers.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRITION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                .claim("roles", springUsers.getAuthorities())
                .compact();

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+Jwt);

        System.out.println(Jwt);
    }
}
