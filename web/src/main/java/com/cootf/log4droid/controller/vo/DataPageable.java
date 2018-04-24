package com.cootf.log4droid.controller.vo;

import com.cootf.log4droid.base.entity.BaseEntity;
import com.cootf.log4droid.entity.ExtractLog;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class DataPageable<T extends BaseEntity> implements Serializable, Pageable {

    // 当前页
    private Integer pgNumber = 1;
    // 当前页面条数
    private Integer pgSize = 10;
    // 排序条件
    private Sort sort;

    /**
     * 总记录
     */
    private long total;

    /**
     * 数据记录
     */
    private List<T> data;


    // 当前页面
    @Override
    public int getPageNumber() {
        return getPgNumber();
    }

    // 每一页显示的条数
    @Override
    public int getPageSize() {
        return getPgSize();
    }

    // 第二页所需要增加的数量
    @Override
    public int getOffset() {
        return (getPgNumber() - 1) * getPgSize();
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public Integer getPgNumber() {
        return pgNumber;
    }

    public void setPgNumber(Integer pgNumber) {
        this.pgNumber = pgNumber;
    }

    public Integer getPgSize() {
        return pgSize;
    }

    public void setPgSize(Integer pgSize) {
        this.pgSize = pgSize;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public Pageable first() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasPrevious() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Pageable next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Class<T> getGenericType(T t) {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public Class getGenericType(int index) {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static void main(String[] args) {
        DataPageable<ExtractLog> pageable = new DataPageable<ExtractLog>();
        System.out.println(pageable.getGenericType(0));
    }
}
