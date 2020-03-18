package com.ttulka.samples.ddd.ecommerce;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.ttulka.samples.ddd.ecommerce.billing.PaymentCollected;
import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryDispatched;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.NESTED_CLASSES;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class CleanModulesArchTest {

    @Test
    void sales_service_has_no_dependency_on_other_services() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.sales");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.warehouse..",
                        "com.ttulka.samples.ddd.ecommerce.shipping..",
                        "com.ttulka.samples.ddd.ecommerce.billing..");
        rule.check(importedClasses);
    }

    @Test
    void sales_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.sales");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.sales.category.jdbc..",
                        "com.ttulka.samples.ddd.ecommerce.sales.product.jdbc..",
                        "com.ttulka.samples.ddd.ecommerce.sales.order.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.sales.category.jdbc..",
                        "com.ttulka.samples.ddd.ecommerce.sales.product.jdbc..",
                        "com.ttulka.samples.ddd.ecommerce.sales.order.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void sales_product_and_category_have_no_dependency_on_order() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.ttulka.samples.ddd.ecommerce.sales.product",
                "com.ttulka.samples.ddd.ecommerce.sales.category");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.sales.order..");
        rule.check(importedClasses);
    }

    @Test
    void warehouse_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.warehouse");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat(
                        resideOutsideOfPackages(
                                "com.ttulka.samples.ddd.ecommerce.sales..",
                                "com.ttulka.samples.ddd.ecommerce.shipping..",
                                "com.ttulka.samples.ddd.ecommerce.billing.."
                        ).or(type(OrderPlaced.class).or(NESTED_CLASSES)
                        ).or(type(DeliveryDispatched.class).or(NESTED_CLASSES)));
        rule.check(importedClasses);
    }

    @Test
    void warehouse_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.warehouse");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.warehouse.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.warehouse.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void shipping_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.shipping");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat(
                        resideOutsideOfPackages(
                                "com.ttulka.samples.ddd.ecommerce.sales..",
                                "com.ttulka.samples.ddd.ecommerce.warehouse..",
                                "com.ttulka.samples.ddd.ecommerce.billing.."
                        ).or(type(OrderPlaced.class).or(NESTED_CLASSES)
                        ).or(type(GoodsFetched.class).or(NESTED_CLASSES)
                        ).or(type(PaymentCollected.class).or(NESTED_CLASSES)));
        rule.check(importedClasses);
    }

    @Test
    void shipping_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.shipping");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void billing_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.billing");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat(
                        resideOutsideOfPackages(
                                "com.ttulka.samples.ddd.ecommerce.sales..",
                                "com.ttulka.samples.ddd.ecommerce.warehouse..",
                                "com.ttulka.samples.ddd.ecommerce.shipping.."
                        ).or(type(OrderPlaced.class).or(NESTED_CLASSES)));
        rule.check(importedClasses);
    }

    @Test
    void billing_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.billing");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void no_service_has_dependencies_on_catalogue() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.catalogue..")
                .and().areNotAnnotatedWith(WebMvcTest.class)
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.catalogue..");
        rule.check(importedClasses);
    }

    @Test
    void catalogue_service_has_no_dependencies_on_shipping_and_billing() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ttulka.samples.ddd.ecommerce.catalogue");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.ttulka.samples.ddd.ecommerce.shipping..",
                        "com.ttulka.samples.ddd.ecommerce.billing..");
        rule.check(importedClasses);
    }

    @Test
    void catalogue_web_uses_only_its_own_use_cases_and_no_direct_dependencies_on_other_services() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.ttulka.samples.ddd.ecommerce.sales",
                "com.ttulka.samples.ddd.ecommerce.warehouse",
                "com.ttulka.samples.ddd.ecommerce.shipping.",
                "com.ttulka.samples.ddd.ecommerce.billing");
        ArchRule rule = classes()
                .should().onlyHaveDependentClassesThat().resideOutsideOfPackage(
                        "com.ttulka.samples.ddd.ecommerce.catalogue.web..");
        rule.check(importedClasses);
    }
}
