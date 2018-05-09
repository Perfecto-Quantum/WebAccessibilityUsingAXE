
@Web
Feature: Google Search

  @Desktop
  Scenario:  Desktop and Mobile
    Given I am on Google Search Page
    When I search for "quantum perfecto"
    Then it should have "Introducing Quantum Framework" in search results
    Then axe scans the web page
    #Then I am on Google Search Page
    #Then I select from search results "github"
    #Then I click on "projectQuantum"
    #Then I select "Quantum-Starter-Kit Wiki" from search results
    Then I select "Stack Overflow" from search results
    Then "nav-questions" must exist
    Then axe scans the web page
    #Then I wait for "5.1" seconds
