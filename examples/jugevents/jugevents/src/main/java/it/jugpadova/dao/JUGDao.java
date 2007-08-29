/**
 * 
 */
package it.jugpadova.dao;

import javax.persistence.Entity;

import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * @author Admin
 *
 */
@Dao(entity=JUG.class)
public interface JUGDao extends GenericDao<JUG, Long> {

}
