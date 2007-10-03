/**
 * 
 */
package it.jugpadova.dao;

import it.jugpadova.po.ReliabilityRequest;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * Dao for reliabilityRequest
 * 
 * @author Enrico Giurin
 * 
 */
@Dao(entity = ReliabilityRequest.class)
public interface ReliabilityRequestDao extends
		GenericDao<ReliabilityRequest, Long> {

}
