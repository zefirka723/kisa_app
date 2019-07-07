package mosecom.model.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "work", name = "Employees_Roles")
public class DbRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Pkey_id")
    private int id;

    @Column(name = "Role_ID")
    private String roleId;

    @ManyToOne
    @JoinColumn(name = "Emploe_ID", referencedColumnName = "Emploe_ID")
    protected User user;


}

