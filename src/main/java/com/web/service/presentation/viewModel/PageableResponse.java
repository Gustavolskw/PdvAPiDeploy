package com.web.service.presentation.viewModel;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageableResponse<T>(List<T> content, Long totalItems, Integer currentPage, Integer lastPage, Integer perPage ) {
    public PageableResponse(Page<T> p){
        this(p.getContent(), p.getTotalElements(), p.getNumber() +1, p.getTotalPages(), p.getSize());
    }

}
