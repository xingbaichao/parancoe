package org.parancoe.plugins.italy;

import java.util.List;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

@Dao(entity = Comune.class)
public interface ComuneDao extends GenericDao<Comune, String> {
  List<Comune> findByPartialNomeAndProvincia(String partialComune, String partialProvincia);
}