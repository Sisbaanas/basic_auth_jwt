package basic_auth_jwt;

import basic_auth_jwt.doa.RolesRepo;
import basic_auth_jwt.doa.UserRepo;
import basic_auth_jwt.entities.Roles;
import basic_auth_jwt.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BasicAuthJwtApplication implements CommandLineRunner {

	@Autowired
	UserRepo utilisateurRepo;

	@Autowired
	RolesRepo rolesRepo;

	private BCryptPasswordEncoder bcrypte = new BCryptPasswordEncoder();


	public static void main(String[] args) {
		SpringApplication.run(BasicAuthJwtApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Roles role = new Roles();
		role.setRoleName("ADMIN");
		rolesRepo.save(role);

		List<Roles> rolesPersist1 =  new ArrayList<>();
		rolesPersist1.add(role);
		Set<Roles> rolesP = new HashSet<Roles>(rolesPersist1);

		Users user1 = new Users();
		user1.setEmail("test1");
		user1.setPassword(bcrypte.encode("password"));
		user1.setRoles(rolesP);
		user1.setActivated(true);

		utilisateurRepo.save(user1);
	}
}
