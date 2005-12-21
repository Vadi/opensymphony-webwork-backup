package org.hibernate.auction.web.actions;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import org.hibernate.auction.dao.ItemDAO;
import org.hibernate.auction.dao.ItemDAOAware;

import java.util.List;
import java.util.Map;

/**
 * User: plightbo
 * Date: Nov 5, 2004
 * Time: 4:58:24 PM
 */
public class Search extends ActionSupport implements ItemDAOAware, SessionAware {
    public static final String RESULTS = "__search_results";

    List items;
    String query;
    int page = 1;
    int pages;

    ItemDAO itemDAO;
    Map session;

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String execute() throws Exception {
        List results = itemDAO.search(query);
        session.put(RESULTS, results);
        cutPage(results);

        return SUCCESS;
    }

    public String moreResults() throws Exception {
        List results = (List) session.get(RESULTS);
        cutPage(results);

        return SUCCESS;
    }

    private void cutPage(List results) {
        int size = results.size();
        int start = Math.min((page - 1) * 10, size);
        int end = Math.min(page * 10, size);

        pages = Math.round((float) size / 10) + 1;

        items = results.subList(start, end);
    }

    public List getItems() {
        return items;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }
}
