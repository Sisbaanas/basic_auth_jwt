package basic_auth_jwt.doa;

import basic_auth_jwt.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {

    Users findOneByEmail(String l);

}
