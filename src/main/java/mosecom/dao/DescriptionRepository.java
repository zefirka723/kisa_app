package mosecom.dao;

import mosecom.model.Description;
import mosecom.model.Reccard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Integer> {
}
