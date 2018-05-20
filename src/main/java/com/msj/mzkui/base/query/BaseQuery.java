package com.msj.mzkui.base.query;

/**
 * 基础查询参数实体封装
 */
public class BaseQuery {

    // 当前页
    private Integer pageNumber = 1;
    // 当前页面条数
    private Integer pageSize = 10;

    /**
     * 状态
     */
    private Integer status;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
