# DDD Example Project: e-commerce

The purpose of this project is to provide a sample implementation of an e-commerce product following Domain-Driven Design (DDD) and Service-Oriented Architecture (SAO) principles.

Programming language is Java 11 with heavy use of Spring framework.

## The Domain

Several primary [business capabilities][1] have been identified:

- **Sales**
  - add a product to sale
  - categorize a product
  - do product pricing
  - validate an order
  - place an order
  
- **Warehouse**
  - store goods
  - receive items for shipping
  
- **Billing**
  - charge a customer

- **Shipping**
  - ship an order

There are also possible secondary (supporting) business capabilities:

- **Marketing**
  - discount a product
  - promote a product
  
- **User Reviews**
  - add a review
  
- **Customer Support**
  - solve an issue
  
The e-commerce system is a web application using a **Catalogue** service implementing the [Backends For Frontends (BFF)][2] pattern.

![Service overview](https://raw.githubusercontent.com/ttulka/java-samples/master/ddd-ecommerce-example/services-overview.png)

[1]: http://bill-poole.blogspot.com/2008/07/value-chain-analysis.html
[2]: https://samnewman.io/patterns/architectural/bff/
