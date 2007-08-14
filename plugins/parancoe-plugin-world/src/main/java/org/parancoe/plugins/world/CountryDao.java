package org.parancoe.plugins.world;

import java.util.List;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

@Dao(entity = Country.class)
public interface CountryDao extends GenericDao<Country, String> {
  List<Country> findByPartialLocalName(String partialLocalName);
  /**
   * Search Country by isoCode
   * @author Enrico Giurin
   * @param isocode
   * @return
   */
  List<Country> findByIsoCode(String isocode);
  
  List<Country> findAllOrderedByEnglishNameAsc();
}
