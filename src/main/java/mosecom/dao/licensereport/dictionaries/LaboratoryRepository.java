package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.dictionary.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Integer> {
}
