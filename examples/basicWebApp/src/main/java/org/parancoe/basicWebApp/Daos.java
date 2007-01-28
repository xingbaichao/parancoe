package org.parancoe.basicWebApp;

import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.basicWebApp.dao.SampleDao;
import org.parancoe.persistence.dao.DaoProvider;

/**
 * Interface for the DAO Provider. Doesn't require an implementation.
 * Simply add methods for the DAOs you need to use.
 * The convention for the methods is get<dao_bean_id>.
 */
public interface Daos extends DaoProvider {
    public PersonDao getPersonDao();    
}
