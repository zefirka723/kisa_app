package mosecom.model.auth;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Dictionary_Employees")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Emploe_ID")
    private int id;

    private String login;

    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "work.Employees_Roles", joinColumns = @JoinColumn(name = "Emploe_ID"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
