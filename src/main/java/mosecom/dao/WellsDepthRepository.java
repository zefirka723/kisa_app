package mosecom.dao;

import mosecom.model.WellsDepth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellsDepthRepository extends JpaRepository<WellsDepth, Integer> {

    WellsDepth findOneByWellId(int wellId);

}
