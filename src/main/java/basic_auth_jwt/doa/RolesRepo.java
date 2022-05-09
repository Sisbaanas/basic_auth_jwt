package basic_auth_jwt.doa;

import basic_auth_jwt.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RolesRepo extends JpaRepository<Roles, Long> {

    Roles findOneByRoleName(String roleName);

    Set<Roles> findByRoleName(String roleName);

    List<Roles> findAll();
}
