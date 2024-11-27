package org.example.c10_laboration2.category.dto;

import org.example.c10_laboration2.category.entity.Category;

public record CategoryDto(String name, String description, String symbol) {
    public static CategoryDto fromCategory(Category category) {
        return new CategoryDto(category.getName(),category.getDescription(),category.getSymbol());
    }
}
