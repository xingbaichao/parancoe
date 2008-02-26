/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.parancoe.basicWebApp.dao;

import org.parancoe.basicWebApp.po.Person;
import org.parancoe.persistence.dao.generic.BusinessDao;
import org.parancoe.persistence.dao.generic.HibernateGenericBusinessDao;


/**
 *
 * @author jacopo
 */
@BusinessDao
public class PersonBusinessDao extends HibernateGenericBusinessDao<Person, Long> {
    
}
