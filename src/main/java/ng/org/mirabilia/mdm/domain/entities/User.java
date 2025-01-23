package ng.org.mirabilia.mdm.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ng.org.mirabilia.mdm.domain.enums.Role;
import ng.org.mirabilia.mdm.domain.enums.UserStoreDomain;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

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


    public User() {
    }

    public User(Long id, String username, String firstName, String lastName, String email,
                UserStoreDomain userStoreDomain, Role userrole, String password, LocalDateTime dateAndTimeCreated) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userStoreDomain = userStoreDomain;
        this.userrole = userrole;
        this.dateAndTimeCreated = dateAndTimeCreated;
    }

}
