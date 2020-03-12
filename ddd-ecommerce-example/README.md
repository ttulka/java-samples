# DDD Example Project: e-commerce

The purpose of this project is to provide a sample implementation of an e-commerce product following Domain-Driven Design (DDD) and Service-Oriented Architecture (SAO) principles.

Programming language is Java 11 with heavy use of Spring framework.

## Domain Services

Several primary [business capabilities][1] have been identified:

- **Sales**
  - add a product to sale
  - categorize a product
  - do product pricing
  - validate an order
  - place an order
  
- **Warehouse**
  - store goods
  - fetch goods for shipping
  
- **Billing**
  - collect a payment

- **Shipping**
  - dispatch a delivery

There are also possible secondary (supporting) business capabilities:

- **Marketing**
  - discount a product
  - promote a product
  
- **User Reviews**
  - add a product review
  
- **Customer Support**
  - resolve a complain
  - answer a question
  - provide help
  
The e-commerce system is a web application using a **Catalogue** service implementing the [Backends For Frontends (BFF)][2] pattern.

### Services Dependencies

Services cooperate together to work out a bussiness capability: sell and deliver products.

![Service Dependencies](services-dependencies.png)

### Services Event Workflow

The communication among services is implemented via events:

![Service Dependencies](services-dependencies.png)

When the customer places an order the following process starts up (happy path):

1. Sales service creates a new order and publishes the OrderPlaced event.
2. Billing service collects payment for the order and publishes the PaymentReceived event.
2. Shipping service prepares a delivery.
2. Warehouse service fetches goods from the stock and publishes the GoodsFetched event.
3. Shipping service dispatches the delivery and publishes the DeliveryDispatched event.
4. Warehouse service updates the stock.

[1]: http://bill-poole.blogspot.com/2008/07/value-chain-analysis.html
[2]: https://samnewman.io/patterns/architectural/bff/
