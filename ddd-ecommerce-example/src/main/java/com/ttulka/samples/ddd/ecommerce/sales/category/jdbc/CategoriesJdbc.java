package com.ttulka.samples.ddd.ecommerce.sales.category.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;
import com.ttulka.samples.ddd.ecommerce.sales.category.Title;
import com.ttulka.samples.ddd.ecommerce.sales.category.UnknownCategory;
import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class CategoriesJdbc implements FindCategories {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> all() {
        return jdbcTemplate.queryForList(
                "SELECT id, uri, title FROM categories").stream()
                .map(this::toCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Category byId(CategoryId id) {
        try {
            Map<String, Object> entry = jdbcTemplate.queryForMap(
                    "SELECT id, uri, title FROM categories " +
                    "WHERE id = ?",
                    new Object[]{id.value()});
            if (entry != null) {
                return toCategory(entry);
            }
        } catch (DataAccessException ignore) {
            log.warn("Category by id {} was not found: {}", id, ignore.getMessage());
        }
        return new UnknownCategory();
    }

    private Category toCategory(Map<String, Object> entry) {
        return new CategoryJdbc(
                new CategoryId(entry.get("id")),
                new Uri((String) entry.get("uri")),
                new Title((String) entry.get("title")),
                jdbcTemplate
        );
    }
}
