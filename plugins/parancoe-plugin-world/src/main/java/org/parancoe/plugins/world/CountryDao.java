package org.parancoe.plugins.world;

import java.util.List;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

@Dao(entity = Country.class)
public interface CountryDao extends GenericDao<Country, String> {
  List<Country> findByPartialLocalName(String partialLocalName);
  List<Country> findByPartialLocalNameAndContinent(String partialLocalName, String continent);
  List<Country> findByPartialEnglishName(String partialEnglishName);
  List<Country> findByPartialEnglishNameAndContinent(String partialLocalName, String continent);
  /**
   * Search Country by isoCode
   * @author Enrico Giurin
   * @param isocode
   * @return
   */
  List<Country> findByIsoCode(String isocode);
  
  Country findByEnglishName(String englishName);
  
  List<Country> findAllOrderedByEnglishNameAsc();
}
