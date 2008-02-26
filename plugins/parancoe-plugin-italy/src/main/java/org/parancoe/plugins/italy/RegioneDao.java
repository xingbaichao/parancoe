package org.parancoe.plugins.italy;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

@Dao(entity = Regione.class)
public interface RegioneDao extends GenericDao<Regione, String> {
}
