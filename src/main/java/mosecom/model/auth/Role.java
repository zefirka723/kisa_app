package mosecom.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role  implements GrantedAuthority {
    UNIDENTIFIED(0,"Роль не определена"),
    PV_FIELD(1,	"Полевой сотрудник отдела подземных вод"),
    FGI(2,	"Сотрудник ФГИ"),
    EGP(3,	"Сотрудник отдела ЭГП"),
    ANALYST(4,	"Аналитик"),
    PV_CONTROL(5,"	Нормоконтролёр отдела ПВ"),
    ADMIN (6,"Администратор"),
    EGP_CONTROL(7,	"Нормоконтролёр отдела ЭГП");

    private int id;
    private String description;

    Role(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public static Role getRoleById(int id) {
        for (Role r: Arrays.asList(Role.values())){
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }


    @Override
    public String getAuthority() {
        return name();
    }


}
