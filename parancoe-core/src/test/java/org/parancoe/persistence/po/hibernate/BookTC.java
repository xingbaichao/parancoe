/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Core.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.persistence.po.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import org.lambico.po.hibernate.EntityBase;

/**
 * An persistent object to be used for the tests M:N relationships.
 * 
 * @author michele franzin <michele at franzin.net>
 * @version $Revision$
 */
@javax.persistence.Entity
@NamedQuery(name = "BookTC.findByTitle", query = "from BookTC where title like ?")
public class BookTC extends EntityBase {

    private static final long serialVersionUID = 1L;

    private String title = null;

    private List<AuthorTC> authors = new ArrayList<AuthorTC>();

    public BookTC() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToMany(targetEntity = AuthorTC.class, cascade = { CascadeType.ALL }, mappedBy = "books", fetch = FetchType.EAGER)
    public List<AuthorTC> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorTC> authors) {
        this.authors = authors;
    }

}
