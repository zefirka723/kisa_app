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
    ADMIN (6,"Администратор"),
    ANALYST(4,	"Аналитик"),
    EGP(3,	"Сотрудник отдела ЭГП"),
    EGP_CONTROL(7,	"Нормоконтролёр отдела ЭГП"),
    FGI(2,	"Сотрудник ФГИ"),
    PV_CONTROL(5,"	Нормоконтролёр отдела ПВ"),
    PV_FIELD(1,	"Полевой сотрудник отдела подземных вод"),
    UNIDENTIFIED(-1,"Роль не определена");


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
