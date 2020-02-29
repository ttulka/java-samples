package com.ttulka.samples.ddd.ecommerce.sales.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
final class CategoriesJdbc implements Categories {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> all() {
        return jdbcTemplate.query("SELECT * FROM categories",
                                  BeanPropertyRowMapper.newInstance(CategoryEntry.class)).stream()
                .map(this::toCategory)
                .collect(Collectors.toList());
    }

    private Category toCategory(CategoryEntry entry) {
        return new Category(
                new CategoryId(entry.id),
                new Title(entry.title)
        );
    }

    @NoArgsConstructor
    @Setter
    private static class CategoryEntry {

        public String id;
        public String title;
    }
}
