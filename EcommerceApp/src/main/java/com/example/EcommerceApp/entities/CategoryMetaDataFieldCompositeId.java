package com.example.EcommerceApp.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoryMetaDataFieldCompositeId implements Serializable {
    @Column(name = "category_metadata_field_id")
    private Long categoryMetaDataFieldId;
    @Column(name = "category_id")
    private Long categoryId;

    public Long getCategoryMetaDataFieldId() {
        return categoryMetaDataFieldId;
    }

    public void setCategoryMetaDataFieldId(Long categoryMetaDataFieldId) {
        this.categoryMetaDataFieldId = categoryMetaDataFieldId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryMetaDataFieldCompositeId that = (CategoryMetaDataFieldCompositeId) o;
        return Objects.equals(categoryMetaDataFieldId, that.categoryMetaDataFieldId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryMetaDataFieldId, categoryId);
    }
}
