/*
 *  Copyright 2008 jacopo.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.parancoe.web.persistance;

import org.apache.log4j.Logger;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Validator;

/**
 *
 * @author jacopo
 */
public abstract class WebEntityBase extends EntityBase {
    
    public abstract void programaticalValidate(Errors errors);
    
}
