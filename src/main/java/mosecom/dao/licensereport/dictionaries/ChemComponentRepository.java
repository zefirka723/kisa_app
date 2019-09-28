package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.dictionary.ChemComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChemComponentRepository extends JpaRepository<ChemComponent, Integer> {
}
