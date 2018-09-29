package cps.fs.APImanagerSys.common.util;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 
 * @author fs
 * @date 2018年9月11日
 * @description 分页
 */
public class PageUtil<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 默认一页显示数量
	 */
	private final int defaultPageSize = 10;
	
	 /**
     * 数据库分页查询时的begin值，第几页
     */
    private int begin;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 总条数
     */
    private int totalSize;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 一页显示的内容
     */
    private List<T> content;
    
    /**
     * 当前页的数量<pagesize
     */
    private int size;
    
    /**
     * Instantiates a new Page.
     */
    public PageUtil(List<T> content) {
    	if(content instanceof Page){
    		Page<T> page = (Page<T>)content;
    		this.currentPage = page.getPageNum();
    		this.pageSize = page.getPageNum();
    		totalPage = (int) page.getTotal();
    		this.content = page;
    		this.size = page.size();
    	}
    	
    }  

    /**
     * 初始化分页信息
     * @param currentPage
     * @param pageSize
     * @param totalSize
     */
    /*public PageUtil(int currentPage, int pageSize,
    		int totalSize) {
    	pageSize = pageSize > 0? pageSize:defaultPageSize;
    	this.totalPage = totalSize % pageSize > 0 ? totalSize / pageSize + 1 : (totalSize / pageSize >= 0 ? totalSize / pageSize: 1);
    	int tmpTotalPage = totalPage > 0 ? totalPage : 1;
        this.begin = currentPage > 1 ? (currentPage > tmpTotalPage ? tmpTotalPage - 1 : currentPage - 1) * pageSize : 0;
        this.currentPage = currentPage > 0 ? (currentPage > totalPage ? tmpTotalPage : currentPage) : 1;
        this.pageSize = pageSize;
    }*/


    /**
     * 生成一个有内容的分页
     *
     * @param currentPage 当前页
     * @param pageSize    the page size
     * @param content     the content
     * @param totalSize   the total size
     */
    /*public PageUtil(int currentPage, int pageSize, List<T> content, int totalSize) {
        pageSize = pageSize > 0 ? pageSize : defaultPageSize;
        this.totalSize = totalSize > 0 ? totalSize : 0;
        this.totalPage = totalSize % pageSize > 0 ? totalSize / pageSize + 1 : (totalSize / pageSize >= 0 ? totalSize / pageSize: 1);
        int tmpTotalPage = totalPage > 0 ? totalPage : 1;
        this.begin = currentPage > 1 ? (currentPage > tmpTotalPage ? tmpTotalPage - 1 : currentPage - 1) * pageSize : 0;
        this.content = content;
        this.currentPage = currentPage > 0 ? (currentPage > totalPage ? tmpTotalPage : currentPage) : 1;
        this.pageSize = pageSize;
    }*/
    
    /**
     * Get begin int.
     *
     * @return the int
     */
    public int getBegin() {
        return begin;
    }

    /**
     * Gets current page.
     *
     * @return the current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets current page.
     *
     * @param currentPage the currentPage
     */
    public void setCurrentPage(long currentPage) {
        this.currentPage = (int) (currentPage > 0 ? (currentPage > totalPage ? totalPage : currentPage) : 1);
    }

    /**
     * Sets total page.
     *
     * @param totalPage the total page
     */
    public void setTotalPage(final int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Gets current page.
     *
     * @param begin the begin
     * @return the current page
     */
    public int getCurrentPage(int begin) {
        return currentPage;
    }

    /**
     * Gets total size.
     *
     * @return the total size
     */
    public int getTotalSize() {
        //select count(*) from f_T where a=x ...
        return totalSize;
    }

    /**
     * Sets total size.
     */
    public void setTotalSize() {
        this.totalSize = totalSize % pageSize > 0 ? totalSize / pageSize + 1 : totalSize / pageSize;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets total page.
     *
     * @return the total page
     */
    public int getTotalPage() {
        return totalSize % pageSize > 0 ? totalSize / pageSize + 1 : (totalSize / pageSize > 0 ? totalSize / pageSize: 1);
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * 是否有前一页
     *
     * @return the boolean
     */
    public boolean hasPrev() {
        return currentPage > 1 && currentPage <= totalPage;
    }

    /**
     * 是否有后一页
     * @return the boolean
     */
    public boolean hasNext() {
        return currentPage > 0 && currentPage < totalPage;
    }

    /**
     * 当前页是否合法
     * @return
     */
    public boolean isCurrentPageIllegal() {
        return currentPage > 0 && currentPage <= totalPage;
    }

    @Override
    public String toString() {
        return "Page:{" + "\"begin\":" + begin + "," + "\"currentPage\":" + currentPage + "," + "\"totalSize\":" +
                totalSize + "," + "\"pageSize\":" + pageSize + "," + "\"totalPage\":" + totalPage + "," +
                "\"content\":" + content + "," + "\"prev\":" + hasPrev() + "," + "\"next\":" + hasNext() + "}";
    }

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
