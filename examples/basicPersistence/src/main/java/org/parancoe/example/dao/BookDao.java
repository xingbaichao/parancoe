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
package org.parancoe.example.dao;


import java.util.List;

import org.parancoe.example.po.Book;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * Represents Book DAO
  * @author <a href="mailto:enricogiurin@gmail.com">Enrico Giurin</a>
 *
 */
@Dao(entity=Book.class)
public interface BookDao extends GenericDao<Book, Long>{
		List<Book> findByAuthor(String author);
	    List<Book> findByTitle(String title);
	   

}