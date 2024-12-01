Feature: Client management

  Scenario: Create a client
    Given a client with email "test@example.com"
    When I create the client
    Then the client should be created successfully

