package com.example.EcommerceApp.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoryMetaDataFieldValueDto implements Serializable {
    private Long categoryId;
    private Long fieldId;

    @NotEmpty
    private String values;
}
