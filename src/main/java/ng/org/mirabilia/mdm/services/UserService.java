package ng.org.mirabilia.mdm.services;


import ng.org.mirabilia.mdm.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    List<User> getAllUsers();
    User addUser(User user);
    User findByUsername(String username);
    void updateUserWithPassword(User user);
    void deleteUser(Long id);
}
