package basic_auth_jwt.Security.filter;

import basic_auth_jwt.Security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTauthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //pr accepter tous les domains
        response.addHeader("Access-Control-Allow-Origin", "*");
        //pr autoriser les entetes
        response.addHeader("Access-Control-Allow-Headers",
                "Origin,Accept,X-Requested-With,Content-Type,"
                        + "Acess-Control-Request-Method,"
                        + "Acces-Control-Request-Headers,"
                        + "Authorization");
        //pr exposer les entetes
        response.addHeader("Access-Control-Expose-Headers",
                "Access-Control-Allow-Origin,"
                        + "Acess-Control-Allow-Credentials,Authorization");
        //si la request est envoiye avec OPTIONS on  retourne ok
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            String JWtToken = request.getHeader(SecurityConstants.HEADER_STRING);
            if (JWtToken == null || !JWtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;

            }

            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(JWtToken.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody();

            String username = claims.getSubject();

            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            roles.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
                System.out.println(r.get("authority"));

            });

            AnonymousAuthenticationToken authWithToken = new AnonymousAuthenticationToken(username, "null", authorities);

            SecurityContextHolder.getContext().setAuthentication(authWithToken);
            System.out.println(response.getStatus());
            filterChain.doFilter(request, response);
            System.out.println(response.getStatus());

        }
    }

}
