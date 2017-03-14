package com.fangcloud.noah.common.api.model;

import java.io.Serializable;

/**
 * 分页查询参数
 *
 * @author chenke Dec 16, 2015
 */
public class PagedQuery<T extends PagedQuery<T>> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_ITEMS_PER_PAGE = 20;

    private int page;
    private int itemsPerPage;

    public PagedQuery() {
        this(0, DEFAULT_ITEMS_PER_PAGE);
    }

    public PagedQuery(int page) {
        this(page, DEFAULT_ITEMS_PER_PAGE);
    }

    public PagedQuery(int page, int itemsPerPage) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    public int getPage() {
        return page;
    }

    @SuppressWarnings("unchecked")
    public T setPage(int page) {
        this.page = page;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return (T) this;
    }
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getOffset() {
        return page < 2 ? 0 : (page - 1) * itemsPerPage;
    }
}
