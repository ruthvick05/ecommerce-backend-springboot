package com.scaler.productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    @NotBlank(message = "Search term must not be blank")
    private String searchTerm;

    public SearchRequest(String searchTerm) {
        this.searchTerm = searchTerm.trim();
    }
}
