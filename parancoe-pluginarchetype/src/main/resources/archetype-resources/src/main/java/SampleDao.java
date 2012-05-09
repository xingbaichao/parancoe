#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.lambico.dao.generic.Dao;
import org.lambico.dao.generic.GenericDao;

@Dao(entity=Sample.class)
public interface SampleDao extends GenericDao<Sample, Long> {
}
