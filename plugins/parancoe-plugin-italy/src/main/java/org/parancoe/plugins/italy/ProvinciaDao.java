package org.parancoe.plugins.italy;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

import java.util.List;

@Dao(entity = Provincia.class)
public interface ProvinciaDao extends GenericDao<Provincia, String> {
  List<Provincia> findByPartialNome(String partialNome);

  List<Provincia> findByPartialTarga(String partialTarga);
}
