package mosecom.dao.auth;

import mosecom.dto.auth.DbRoleProjection;
import mosecom.dto.auth.DbUserProjection;
import mosecom.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbUserRepository extends JpaRepository<User, Integer> {
    @Query("select new mosecom.dto.auth.DbUserProjection"
            + "(u.id as id, u.username as username, u.password as password, u.name as name) "
            + "from User u order by u.id")
    List<DbUserProjection> findUsersList();

    User findUserByUsername(String username);

    User findUserById(int id);

}

