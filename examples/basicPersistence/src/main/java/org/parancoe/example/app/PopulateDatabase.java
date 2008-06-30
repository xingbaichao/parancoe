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
package org.parancoe.example.app;

import org.parancoe.example.bo.PersonBO;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * A simple example of an application using Parancoe persistence module.
 *
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
public class PopulateDatabase {
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance("beanRefFactory.xml");
        BeanFactoryReference bf = bfl.useBeanFactory("org.parancoe.example");
        PersonBO personBO = (PersonBO)bf.getFactory().getBean("personBO");
        // Popoulating the database
        personBO.populateArchive();
        // Print the person with id=1
        personBO.printPerson(1L);
    }
    
}
