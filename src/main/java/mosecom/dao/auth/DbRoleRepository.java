package mosecom.dao.auth;

import mosecom.dto.auth.DbRoleProjection;
import mosecom.dto.auth.DbUserProjection;
import mosecom.model.auth.DbRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DbRoleRepository extends JpaRepository<DbRole, Integer> {

//    @Query("select new mosecom.dto.auth.DbRoleProjection"
//            + "(u.id as id, u.username as username, u.password as password, u.name as name) "
//            + "from work.Employees_Roles where id:id u order by u.id")
//    List<DbUserProjection> findUsersList(int userId);
}
