package com.fangcloud.noah.common.api.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 带分页信息的List
 * 
 * @author chenke Dec 16, 2015
 */
public class PagedList<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_ITEMS_PER_PAGE = 20;

    private int page;                       // current page number, 1-based
    private int items;                      // total items number
    private int itemsPerPage;               // items number per page
    private List<T> data;                   // list data

    public PagedList(int items) {
        this(DEFAULT_ITEMS_PER_PAGE, items);
    }

    public PagedList(int itemsPerPage, int items) {
        this(itemsPerPage, items, 0);
    }

    public PagedList(int itemsPerPage, int items, int page) {
        this.items = items >= 0 ? items : 0;
        this.itemsPerPage = itemsPerPage > 0 ? itemsPerPage : DEFAULT_ITEMS_PER_PAGE;
        this.page = calcPage(page);
    }

    /**
     * get total page number.
     * 
     * @return
     */
    public int getPages() {
        return (int) Math.ceil((double) items / itemsPerPage);
    }

    /**
     * get current page number.
     * 
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * set current page number.
     * 
     * @param page
     */
    public void setPage(int page) {
        this.page = calcPage(page);
    }

    /**
     * get total item number.
     * 
     * @return
     */
    public int getItems() {
        return items;
    }

    /**
     * get item number per page.
     * 
     * @return
     */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * get offset, 0-based.
     * 
     * @return
     */
    public int getOffset() {
        return page > 0 ? itemsPerPage * (page - 1) : 0;
    }

    /**
     * get length of current page.
     * 
     * @return
     */
    public int getLength() {
        if (page > 0) {
            return Math.min(itemsPerPage * page, items) - itemsPerPage * (page - 1);
        }
        return 0;
    }

    /**
     * get begin index, 1-based.
     * 
     * @return
     */
    public int getBeginIndex() {
        if (page > 0) {
            return itemsPerPage * (page - 1) + 1;
        }
        return 0;
    }

    /**
     * get end index, 1-based.
     * 
     * @return
     */
    public int getEndIndex() {
        if (page > 0) {
            return Math.min(itemsPerPage * page, items);
        }
        return 0;
    }

    /**
     * get previous page number.
     * 
     * @return
     */
    public int getPreviousPage() {
        return calcPage(page - 1);
    }

    /**
     * get next page number.
     * 
     * @return
     */
    public int getNextPage() {
        return calcPage(page + 1);
    }

    /**
     * get data
     * 
     * @return
     */
    public List<T> getData() {
        return data;
    }

    /**
     * set data
     * 
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data != null ? data : new ArrayList<T>(0);
    }

    // calc current page
    protected int calcPage(int page) {
        int pages = getPages();
        if (pages > 0) {
            return page < 1 ? 1 : page > pages ? pages : page;
        }
        return 0;
    }

}
