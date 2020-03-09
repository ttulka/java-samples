package com.ttulka.samples.ddd.ecommerce.sales.category.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;
import com.ttulka.samples.ddd.ecommerce.sales.category.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.category.Title;
import com.ttulka.samples.ddd.ecommerce.sales.category.UnknownCategory;
import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
final class CategoriesJdbc implements FindCategories {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> all() {
        return jdbcTemplate.query("SELECT id, uri, title FROM categories",
                                  BeanPropertyRowMapper.newInstance(CategoryEntry.class)).stream()
                .map(this::toCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Category byId(CategoryId id) {
        try {
            CategoryEntry entry = jdbcTemplate.queryForObject(
                    "SELECT id, uri, title FROM categories " +
                    "WHERE id = ?",
                    new Object[]{id.value()},
                    BeanPropertyRowMapper.newInstance(CategoryEntry.class));
            if (entry != null) {
                return toCategory(entry);
            }
        } catch (DataAccessException ignore) {
        }
        return new UnknownCategory();
    }

    private Category toCategory(CategoryEntry entry) {
        return new CategoryJdbc(
                new CategoryId(entry.id),
                new Uri(entry.uri),
                new Title(entry.title),
                jdbcTemplate
        );
    }

    @NoArgsConstructor
    @Setter
    private static class CategoryEntry {

        public Long id;
        public String uri;
        public String title;
    }
}
