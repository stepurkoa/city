package com.city.list.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class CustomPage<T> {
    private List<T> content;
    private CustomPageable pageable;

    public CustomPage(Page<T> page) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

    @Setter
    @Getter
    class CustomPageable {
        private int pageNumber;
        private int pageSize;
        private long totalElements;
        private long totalPages;

        public CustomPageable(int pageNumber, int pageSize, long totalElements, long totalPages) {

            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }
    }
}

