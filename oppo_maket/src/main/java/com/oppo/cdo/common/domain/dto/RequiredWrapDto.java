package com.oppo.cdo.common.domain.dto;

import com.oppo.cdo.common.domain.dto.ResourceDto;
import io.protostuff.Tag;
import java.util.List;

public class RequiredWrapDto {
    @Tag(101)
    private List<ResourceDto> requires;

    public RequiredWrapDto(List<ResourceDto> list) {
        this.requires = list;
    }
    public  RequiredWrapDto(){}

    public List<ResourceDto> getRequires() {
        return this.requires;
    }

    public void setRequires(List<ResourceDto> list) {
        this.requires = list;
    }
}