package mosecom.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbUserProjection {
    private int id;
    private String username;
    private String password;
    private String name;
}
