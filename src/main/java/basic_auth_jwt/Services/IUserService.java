package basic_auth_jwt.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface IUserService  extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
