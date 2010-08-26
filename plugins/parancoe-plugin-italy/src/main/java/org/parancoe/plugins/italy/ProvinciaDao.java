package org.parancoe.plugins.italy;

import java.util.List;
import org.lambico.dao.generic.Dao;
import org.lambico.dao.generic.GenericDao;

@Dao(entity = Provincia.class)
public interface ProvinciaDao extends GenericDao<Provincia, String> {
  List<Provincia> findByPartialNome(String partialNome);

  List<Provincia> findByPartialTarga(String partialTarga);
}
