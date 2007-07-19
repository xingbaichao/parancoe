package org.parancoe.plugins.world;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

import java.util.List;

@Dao(entity = Continent.class)
public interface ContinentDao extends GenericDao<Continent, String> {
  List<Continent> findByPartialName(String partialName);
}
