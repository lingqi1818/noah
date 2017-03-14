package com.fangcloud.noah.service.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenke on 16-8-20.
 */
public class PageObject<T> {
    /**
     * 默认每页的记录数量
     */
    public static final int PAGESIZE_DEFAULT = 20;
    public static final int PAGEVIEWNUM_DEFAULT = 5;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 当前页。第一页是1
     */
    private int curPage;

    /**
     * 总记录数
     */
    private int totalItem;
    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 分页后的记录开始的地方
     * 第一条记录是1
     */
    private int offSet;
    /**
     * 分页后的记录结束的地方
     */
    private int endRow;

    private List<T> datas;
    private List<String> viewPages = new ArrayList();


    public static final String startSqlFlag = "rowBegin";
    public static final String lineSqlFlag = "rowNum";

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<String> getViewPages() {
        return viewPages;
    }

    public void setViewPages(List<String> viewPages) {
        this.viewPages = viewPages;
    }


    /**
     * 默认构造方法
     */
    /*public PageToolbar() {
          repaginate();
    }*/

    /**
     * 带当前页和页大小的构造方法
     *
     * @param curPage  当前页
     * @param pageSize 页大小
     */
    public PageObject(int curPage, int pageSize, int totalItem) {
        this.curPage = curPage;
        if (curPage <= 0) {
            this.curPage = 1;
        }
        this.pageSize = pageSize;
        this.totalItem = totalItem;
        repaginate();
    }

    public PageObject(int curPage, int totalItem) {
        this(curPage, PAGESIZE_DEFAULT, totalItem);
    }

    /**
     * 表示是不是第一页
     *
     * @return true 是; false 不是
     */
    public boolean isFirstPage() {
        return curPage <= 1;
    }


    public boolean isMiddlePage() {
        return !(isFirstPage() || isLastPage());
    }


    public boolean isLastPage() {
        return curPage >= totalPage;
    }


    public boolean isNextPageAvailable() {
        return !isLastPage();
    }

    public boolean isPreviousPageAvailable() {
        return !isFirstPage();
    }

    /**
     * 下一页号
     *
     * @return 取得下一页号
     */
    public int getNextPage() {
        if (isLastPage()) {
            return totalItem;
        } else {
            return curPage + 1;
        }
    }

    public int getPreviousPage() {
        if (isFirstPage()) {
            return 1;
        } else {
            return curPage - 1;
        }
    }

    /**
     * Method getPageSize returns the pageSize of this PaginatedArrayList object.
     * <p/>
     * 每页大小
     *
     * @return the pageSize (type int) of this PaginatedArrayList object.
     */

    public int getPageSize() {
        return pageSize;
    }

    /**
     * Method setPageSize sets the pageSize of this PaginatedArrayList object.
     * <p/>
     * 每页大小
     *
     * @param pageSize the pageSize of this PaginatedArrayList object.
     */

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        repaginate();
    }


    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
        repaginate();
    }


    /**
     * Method getTotalItem returns the totalItem of this PaginatedArrayList object.
     * <p/>
     * 总记录数
     *
     * @return the totalItem (type int) of this PaginatedArrayList object.
     */

    public int getTotalItem() {
        return totalItem;
    }

    /**
     * Method setTotalItem sets the totalItem of this PaginatedArrayList object.
     * <p/>
     * 总记录数
     *
     * @param totalItem the totalItem of this PaginatedArrayList object.
     */

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
        repaginate();
    }

    /**
     * Method getTotalPage returns the totalPage of this PaginatedArrayList object.
     * <p/>
     * 总页数
     *
     * @return the totalPage (type int) of this PaginatedArrayList object.
     */

    public int getTotalPage() {
        return totalPage;
    }

    /**
     * Method getOffSet returns the offSet of this PaginatedArrayList object.
     * <p/>
     * 分页后的记录开始的地方
     *
     * @return the offSet (type int) of this PaginatedArrayList object.
     */

    public int getOffSet() {
        return offSet;
    }

    /**
     * Method getEndRow returns the endRow of this PaginatedArrayList object.
     * <p/>
     * 分页后的记录结束的地方
     *
     * @return the endRow (type int) of this PaginatedArrayList object.
     */

    public int getEndRow() {
        return endRow;
    }

    /**
     * Method repaginate ...
     */
    private void repaginate() {
        if (pageSize < 1) {
            pageSize = PAGESIZE_DEFAULT;
        }
        if (curPage < 1) {
            curPage = 1;//恢复到第一页
        }
        if (totalItem > 0) {
            totalPage = totalItem / pageSize + (totalItem % pageSize > 0 ? 1 : 0);
            if (curPage > totalPage) {
                curPage = totalPage; //最大页
            }
            endRow = curPage * pageSize;
            offSet = endRow - pageSize;
            if (endRow > totalItem) {
                endRow = totalItem;
            }
        }
    }

    /**
     * @param viewPageNum
     * @param datas
     */
    public void initViewData(int viewPageNum, List<T> datas) {

        this.setDatas(datas);

        initViewPage(viewPageNum);
    }

    public void initViewData(List<T> datas) {

        this.setDatas(datas);

        initViewPage(PAGEVIEWNUM_DEFAULT);
    }


    public void initViewPage(int viewNum) {

        int viewStartPage = this.getCurPage();

        int beforeNum = 0;
        while (viewStartPage > 1 && beforeNum++ < viewNum / 2 + 1) {
            viewStartPage--;
        }

        int end = viewStartPage;
        for (int j = 0; j < viewNum && end <= this.getTotalPage(); end++, j++) {
            viewPages.add(end + "");
        }
    }
}
