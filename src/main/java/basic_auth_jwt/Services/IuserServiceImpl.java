package basic_auth_jwt.Services;

import basic_auth_jwt.doa.UserRepo;
import basic_auth_jwt.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class IuserServiceImpl implements IUserService {

    @Autowired
    UserRepo utilisateurRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users AppUser =	utilisateurRepo.findOneByEmail(username);
        if(AppUser == null ) throw new UsernameNotFoundException("utilisateur "+username+"n'est pas troué");

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        AppUser.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(AppUser.getEmail(), AppUser.getPassword(), AppUser.isActivated(), true, true, true, authorities);
    }

}
