package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
final class CategoryJdbc implements Category {

    private final CategoryId id;
    private final Uri uri;
    private Title title;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CategoryId id() {
        return id;
    }

    @Override
    public Uri uri() {
        return uri;
    }

    @Override
    public Title title() {
        return title;
    }

    @Override
    public void changeTitle(Title title) {
        this.title = title;
        jdbcTemplate.update("UPDATE categories SET title = ? WHERE id = ?",
                            new Object[]{title.value(), id.value()});
    }
}
