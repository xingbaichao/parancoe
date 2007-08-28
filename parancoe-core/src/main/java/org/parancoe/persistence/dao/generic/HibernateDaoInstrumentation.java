// Copyright 2006-2007 The Parancoe Team
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
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.StringUtils;


/**
 * An aspect for instrumenting a class with dao interfaces and methods.
 *
 * @author <a href="mailto:lucio.benfante@jugpadova.it">Lucio Benfante</a>
 * @version $Revision$
 */
@Aspect
public class HibernateDaoInstrumentation {

    private static final Logger logger = Logger.getLogger(HibernateDaoInstrumentation.class);

    @Around(value = "execution(* *(..)) && target(org.parancoe.persistence.dao.generic.GenericDaoHibernateSupport)")
    public Object executeFinder(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        final GenericDaoHibernateSupport target = (GenericDaoHibernateSupport) pjp.getTarget();
        final Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        final Object[] args = pjp.getArgs();
        final StringBuffer errorMessages = new StringBuffer();
        logger.debug("target: " + target);
        logger.debug("method: " + method);
        logger.debug("args: " + args);

        // query using a named query from the method name
        result = target.getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                String queryName = queryNameFromMethod(target, method);
                Query namedQuery = null;
                try {
                    namedQuery = session.getNamedQuery(queryName);
                } catch (MappingException e) {
                    // No such named query
                }
                if (namedQuery != null) {
                    for (int i = 0; i < args.length; i++) {
                        Object arg = args[i];
                        namedQuery.setParameter(i, arg);
                    }
                    return namedQuery.list();
                } else {
                    errorMessages.append("Named query not found: ").append(queryName).append(". ");
                }
                return null;
            }
        });
        if (result == null) {
            // No named query found
            if (method.getName().startsWith("findBy")) {
                // Query evicting condition from the method name
                result = target.getHibernateTemplate().executeFind(new HibernateCallback() {

                    public Object doInHibernate(Session session) throws HibernateException {
                        DetachedCriteria criteria = criteriaFromMethod(target, method, args);
                        return criteria.getExecutableCriteria(session).list();
                    }
                });
            } else {
                // Call an instance method
                try {
                    result = pjp.proceed(args);
                } catch (Throwable throwable) {
                    errorMessages.append("Method not starting with \"findBy\".").append("Trying to call ").append(method.getName()).append(" method, but the method doesn't exist in the object (").append(target.getClass().getName()).append(").");
                    logger.error(errorMessages.toString(), throwable);
                }
            }
        }
        if (result != null && List.class.isAssignableFrom(result.getClass()) && !List.class.isAssignableFrom(method.getReturnType())) {
            // The return type is not a List, so I return the first result
            // of the list, or null if the list is empty
            List listResult = (List) result;
            if (!listResult.isEmpty()) {
                result = listResult.get(0);
            } else {
                result = null;
            }
        }
        return result;
    }

    private String queryNameFromMethod(GenericDaoHibernateSupport target, Method finderMethod) {
        return target.getType().getSimpleName() + "." + finderMethod.getName();
    }

    private DetachedCriteria criteriaFromMethod(GenericDaoHibernateSupport target, Method finderMethod, Object[] args) {
        DetachedCriteria criteria = DetachedCriteria.forClass(target.getType());
        int orderByIdx = finderMethod.getName().indexOf("OrderBy");
        String[] parameters = null;
        String[] orderParameters = null;
        if (orderByIdx == -1) {
            // no orderBy
            parameters = finderMethod.getName().substring(6).split("And");
        } else {
            if (orderByIdx - 1 > 6) {
                parameters = finderMethod.getName().substring(6, orderByIdx).split("And");
            }
            orderParameters = finderMethod.getName().substring(orderByIdx + 7).split("And");
        }
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                criteria.add(Restrictions.eq(StringUtils.uncapitalize(parameters[i]), args[i]));
            }
        }
        if (orderParameters != null) {
            for (String oPar : orderParameters) {
                criteria.addOrder(Order.asc(StringUtils.uncapitalize(oPar)));
            }
        }
        return criteria;
    }
}
