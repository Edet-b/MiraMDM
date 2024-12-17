package ng.org.mirabilia.mdm.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ng.org.mirabilia.mdm.domain.enums.Role;
import ng.org.mirabilia.mdm.domain.enums.UserStoreDomain;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue()
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "userStoreDomain", nullable = false)
    private UserStoreDomain userStoreDomain;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false)
    private Role userrole;

    @Column(nullable = false)
    private String password;

    private LocalDateTime dateAndTimeCreated;
}
