package ${package};

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

@Dao(entity=Sample.class)
public interface SampleDao extends GenericDao<Sample, Long> {
}
