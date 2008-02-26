package org.parancoe.plugins.italy;

import java.util.List;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

@Dao(entity= Procura.class)
public interface ProcuraDao extends GenericDao<Procura, Long> {
    List<Procura> findByComune(String comune);
    List<Procura> findByPartialComune(String partialComune);
}
