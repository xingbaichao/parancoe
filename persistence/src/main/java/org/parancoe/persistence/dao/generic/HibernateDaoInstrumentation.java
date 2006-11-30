// Copyright 2006 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.persistence.dao.generic;

import java.lang.reflect.Method;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.StringUtils;


/**
 * An aspect for instrumenting a class with dao interfaces and methods.
 *
 * @author <a href="mailto:lucio.benfante@jugpadova.it">Lucio Benfante</a>
 * @version $Revision$
 */
@Aspect()
public class HibernateDaoInstrumentation<T> {
    
    @Around("execution(* *(..)) && target(org.parancoe.persistence.dao.generic.GenericDaoHibernateSupport)")
    public Object executeFinder(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        final GenericDaoHibernateSupport target = (GenericDaoHibernateSupport)pjp.getTarget();
        final Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        final Object[] args = pjp.getArgs();
        
        if (target instanceof HibernateGenericDao) { // no decoration on the base DAO
            return pjp.proceed(args);
        }
        
        // query using a named query from the method name
        result = target.getHibernateTemplate().executeFind(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                String queryName = queryNameFromMethod(target, method);
                Query namedQuery = null;
                try {
                    namedQuery = session.getNamedQuery(queryName);
                } catch (MappingException e) {
                    // No such named query
                }
                if (namedQuery != null) {
                    for(int i = 0; i < args.length; i++) {
                        Object arg = args[i];
                        namedQuery.setParameter(i, arg);
                    }
                    return namedQuery.list();
                }
                return null;
            }
        });
        if (result == null) { // No named query found
            if (method.getName().startsWith("findBy")) {
                // Query evicting condition from the method name
                result = target.getHibernateTemplate().executeFind(
                        new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
                        String queryString = queryStringFromMethod(target, method);
                        Query query = null;
                        query = session.createQuery(queryString);
                        for(int i = 0; i < args.length; i++) {
                            Object arg = args[i];
                            query.setParameter(i, arg);
                        }
                        return query.list();
                    }
                });
            } else {
                // Call an instance method
                Class daoEntity = getDaoEntityType(target);
                target.setType(daoEntity);
                result = pjp.proceed(args);
            }
        }
        return result;
    }
    
    private String queryNameFromMethod(GenericDaoHibernateSupport target, Method finderMethod) {
        return getDaoEntityType(target).getSimpleName() + "." + finderMethod.getName();
    }
    
    private String queryStringFromMethod(GenericDaoHibernateSupport target, Method finderMethod) {
        StringBuffer result = new StringBuffer();
        result.append("from ").append(getDaoEntityType(target).getSimpleName());
        String[] parameters = finderMethod.getName().substring(6).split("And");
        result.append(" where ").append(StringUtils.uncapitalize(parameters[0])).append(" = ?");
        for (int i=1; i < parameters.length; i++) {
            result.append(" and ").append(StringUtils.uncapitalize(parameters[i])).append(" = ?");
        }
        return result.toString();
    }
    
    private Class getDaoEntityType(GenericDaoHibernateSupport target) {
        Class result = null;
        Class[] interfaces = target.getClass().getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Dao daoAnnotation = (Dao) interfaces[i].getAnnotation(Dao.class);
            if (daoAnnotation != null) {
                result = daoAnnotation.entity();
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("target Dao interface not annotated with Dao annotation");
        }
        return result;
    }
}
