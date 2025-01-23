package ng.org.mirabilia.mdm.config.startup;

import ng.org.mirabilia.mdm.domain.entities.User;
import ng.org.mirabilia.mdm.domain.enums.Role;
import ng.org.mirabilia.mdm.domain.enums.UserStoreDomain;
import ng.org.mirabilia.mdm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (!userRepository.existsByUserrole(Role.ADMIN)) {
                User admin = new User();
                admin.setFirstName("System");
                admin.setLastName("Administrator");
                admin.setEmail("admin@egmail.com");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setUserrole(Role.ADMIN);
                admin.setUserStoreDomain(UserStoreDomain.PRIMARY);

                // Save the user in the repository
                userRepository.save(admin);
            }
        };
    }
}
