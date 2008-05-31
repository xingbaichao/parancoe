package org.parancoe.persistence.dao.generic;

import java.util.List;
import java.util.Collections;

/**
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class HibernatePage<T> {

    private List results;
    private int pageSize;
    private int page;
    private int rowCount;

    public static HibernatePage EMPTY = new HibernatePage(Collections.EMPTY_LIST, 1, 1, 0);  

    public HibernatePage(List<T> list, int page, int pageSize, int rowCount) {
        this.page = page;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        results = list;
    }
    
    public int getPage() {
        return page;
    }

    public boolean isNextPage() {
        return page < getLastPage();
    }

    public boolean isPreviousPage() {
        return page > 1;
    }

    public List<T> getList() {
        return results;
    }
    
    public int getLastPage() {
        int lastPage = rowCount/pageSize;
        if (rowCount%pageSize > 0) lastPage++;
        return lastPage;
    }

    public int getRowCount() {
        return rowCount;
    }

}
