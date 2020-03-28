package com.ttulka.ecommerce.sales;

import java.util.List;

import com.ttulka.ecommerce.sales.category.Uri;
import com.ttulka.ecommerce.sales.product.Code;
import com.ttulka.ecommerce.sales.product.Product;

/**
 * Find Products use-case.
 */
public interface FindProducts {

    /**
     * Lists all products.
     *
     * @return all products
     */
    List<Product> all();

    /**
     * Lists products from the category
     *
     * @param categoryUri the URI of the category
     * @return the products from the category
     */
    List<Product> fromCategory(Uri categoryUri);

    /**
     * Finds a product by the code.
     *
     * @param code the code
     * @return the product
     */
    Product byCode(Code code);
}
