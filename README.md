# SauceDemo Test Automation Framework

This project contains automated UI tests for the SauceDemo application using Selenium WebDriver, Java, Cucumber BDD and JUnit.

## Tech Stack

- Java
- Selenium WebDriver
- Cucumber BDD
- JUnit
- Maven
- IntelliJ IDEA

## Application Under Test

SauceDemo  
https://www.saucedemo.com/

## Test Scenarios Covered

The automation suite covers:

- Successful login
- Invalid login scenarios
- Different SauceDemo users
- Product page validation
- Add product to cart
- Add all products to cart
- Remove products from cart
- Shopping cart badge validation
- Product sorting
- Product price validation
- Cart page validation
- Checkout flow
- Checkout customer information
- Checkout overview validation
- Complete order
- Order confirmation message
- Generate PDF order
- Hamburger menu validation
- Footer validation
- Social media link validation

## Dynamic Automation

The framework uses reusable and dynamic step definitions for:

- Selecting products by product name
- Adding and removing products
- Clicking different buttons
- Validating different pages
- Validating menu options
- Validating social media links

Example:

```gherkin
When I add "Sauce Labs Backpack" to the cart
