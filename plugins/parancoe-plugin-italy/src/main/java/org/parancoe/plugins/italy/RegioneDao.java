package org.parancoe.plugins.italy;

import org.lambico.dao.generic.Dao;
import org.lambico.dao.generic.GenericDao;

@Dao(entity = Regione.class)
public interface RegioneDao extends GenericDao<Regione, String> {
}
