package mosecom.dao.inspections;

import mosecom.model.inspections.dictionaries.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {
    List<Employees> findAllByOrderByNameAsc();

    List<Employees> findAllByIsRegistratorTrueOrderByNameAsc();
}

