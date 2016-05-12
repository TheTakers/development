package com.sophia.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Kim on 2015/10/12.
 */
public class UserParam {

    @NotNull
    private Long productId;
    @NotNull
    private Long dateId;
    @NotNull
    private Integer index;
    @NotNull
    private Integer size;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
