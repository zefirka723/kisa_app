package mosecom.dao;

import mosecom.model.Well;
import mosecom.model.WellsDepth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WellsDepthRepository extends JpaRepository<WellsDepth, Integer> {

    WellsDepth findOneByWellId(int wellId);

    WellsDepth findOneByWellIdAndDate(int wellId, Date date);

    //List<WellsDepth> findAllByWellId(int wellId);

}
