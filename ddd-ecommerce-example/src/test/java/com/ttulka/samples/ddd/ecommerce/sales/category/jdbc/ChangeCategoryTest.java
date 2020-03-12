package com.ttulka.samples.ddd.ecommerce.sales.category.jdbc;

import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;
import com.ttulka.samples.ddd.ecommerce.sales.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.category.Title;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = CategoriesJdbcConfig.class)
@Sql(statements = "INSERT INTO categories VALUES (1, 'test', 'Test');")
@Transactional
class ChangeCategoryTest {

    @Autowired
    private FindCategories findCategories;

    @Test
    void product_title_is_changed() {
        Category category = findCategories.byId(new CategoryId(1L));
        category.changeTitle(new Title("Updated title"));

        Category productUpdated = findCategories.byId(new CategoryId(1L));

        assertThat(productUpdated.title()).isEqualTo(new Title("Updated title"));
    }
}
