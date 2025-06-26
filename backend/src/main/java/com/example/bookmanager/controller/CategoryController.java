package com.example.bookmanager.controller;

import com.example.bookmanager.dto.CategoryDTO;
import com.example.bookmanager.entity.Category;
import com.example.bookmanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDTO addCategory(@RequestBody Category category) {
        return toDTO(categoryService.addCategory(category));
    }

    @PutMapping
    public CategoryDTO updateCategory(@RequestBody Category category) {
        return toDTO(categoryService.updateCategory(category));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return toDTO(categoryService.getCategoryById(id));
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
} 