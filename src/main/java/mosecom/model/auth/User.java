package mosecom.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.WellsConstruction;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "dictionaries", name = "Dictionary_Employees")
public class User implements UserDetails {
//    private String username;
//    private String password;

//
//    // дефолтные свойства
//    private boolean accountNonExpired;
//    private boolean accountNonLocked;
//    private boolean credentialsNonExpired;
//    private boolean enabled;
//


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Emploe_ID")
    private int id;

    @Column(name = "Login")
    private String username;

    @Column(name = "Password")
    private String password;

    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private boolean credentialsNonExpired;
    @Transient
    private boolean enabled;
    @Transient
    private List<Role> authorities;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<DbRole> dbRoles;

    @Override
    public List<Role> getAuthorities(){
        authorities.clear();
        for (DbRole dbRole: dbRoles) {
            authorities.add(Role.getRoleById(dbRole.getId()));
        }
        return authorities;
    }

}
