Feature: Adding products to cart

  Background:
    Given I am on the Sauce Labs login page

  Scenario: Verify login page details
    Given  I log into the website as a regular user
    Then "Swag Labs" should be displayed on the login page
    And username , password and Login text fields should be displayed
    And accepted usernames should be displayed
    And password for all users should be displayed as "secret_sauce"


  Scenario: Adding the backpack and bike light to cart
    Given  I log into the website as a regular user
    When I add the "Sauce Labs Backpack" to my cart
    When I add the "Sauce Labs Bike Light" to my cart
    Then My cart shows "2" items added


  Scenario: Removing one item from the cart
    Given  I log into the website as a regular user
    When I add the "Sauce Labs Backpack" to my cart
    When I add the "Test.allTheThings() T-Shirt (Red)" to my cart
    And I remove the "Test.allTheThings() T-Shirt (Red)" from my cart
    Then My cart shows "1" items added

  Scenario Outline: Verify the product pricing
    Given  I log into the website as a regular user
    And Verify the "<product>" and "<price>"
    Examples:
      | product             | price  |
      | Sauce Labs Backpack | $29.99 |
      | Sauce Labs Onesie   | $7.99  |


  Scenario: Cart badge disappears when all products are removed
    Given  I log into the website as a regular user
    When I add the "Sauce Labs Backpack" to my cart
    And I remove the "Sauce Labs Backpack" from my cart
    Then My cart shows '0' items


  Scenario:Add to cart button to remove
    Given  I log into the website as a regular user
    When I add the "Sauce Labs Backpack" to my cart
    Then the button for the  "Sauce Labs Backpack" should display "Remove"
    Then My cart shows '1' items


  Scenario: Sort products by price from high to low
    Given  I log into the website as a regular user
    When I sort the products by "Price (high to low)"
    Then the products should be displayed in descending price order

  @TAG
  Scenario Outline: Verify login with different username and password combinations
    When I login with username "<username>" and password "<password>"
    Then login result should be "<expectedResult>" with error message "<errorMessage>"

    Examples:
      | username                | password       | expectedResult | errorMessage                                                              |
      | standard_user           | secret_sauce   | success        |                                                                           |
      | problem_user            | secret_sauce   | success        |                                                                           |
      | performance_glitch_user | secret_sauce   | success        |                                                                           |
      | error_user              | secret_sauce   | success        |                                                                           |
      | visual_user             | secret_sauce   | success        |                                                                           |
      | locked_out_user         | secret_sauce   | failure        | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user            | secret_sauce   | failure        | Epic sadface: Username and password do not match any user in this service |
      | standard_user           | wrong_password | failure        | Epic sadface: Username and password do not match any user in this service |
      | invalid_user            | wrong_password | failure        | Epic sadface: Username and password do not match any user in this service |
      |                         | secret_sauce   | failure        | Epic sadface: Username is required                                        |
      | standard_user           |                | failure        | Epic sadface: Password is required                                        |
      |                         |                | failure        | Epic sadface: Username is required                                        |

  @E2E
  Scenario Outline: Complete an order successfully with all products

    Given I login with username "<username>" and password "<password>"
    Then "Products" page should be displayed

    When I add all products to the cart
    Then My cart shows "6" items added

    When I click the shopping cart
    Then "Your Cart" page should be displayed
    And all added products should be displayed in the cart

    When I click the "Continue Shopping" button
    Then "Products" page should be displayed

    When I click the shopping cart
    And I click the "Checkout" button
    Then "Checkout: Your Information" page should be displayed

    When I enter first name "<firstName>" last name "<lastName>" and postal code "<postalCode>"
    And I click the "Continue" button
    Then "Checkout: Overview" page should be displayed

    When I click the "Finish" button
    Then "Checkout: Complete!" page should be displayed
    And order confirmation should be "Thank you for your order!"
    And order dispatch message should be displayed
    And "Generate PDF order" button should be displayed

    When I click the "Generate PDF order" button
    Then order PDF should be generated




    Examples:
      | username      | password     | firstName | lastName | postalCode |
      | standard_user | secret_sauce | Sonali    | Test     | G1 1AA     |


  Scenario: Verify hamburger menu options
    When I click the hamburger menu
@TAGS
  Scenario: Verify footer social media links are displayed
  Given  I log into the website as a regular user
  Then "Twitter" social media link should be displayed
  And "Facebook" social media link should be displayed