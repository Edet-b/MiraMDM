package ng.org.mirabilia.mdm.repositories;

import ng.org.mirabilia.mdm.domain.entities.User;
import ng.org.mirabilia.mdm.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUserrole(Role role);
}
