package mosecom.dao.auth;

import mosecom.dto.auth.DbRoleProjection;
import mosecom.model.auth.DbRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DbRoleRepository extends JpaRepository<DbRole, Integer> {

}
