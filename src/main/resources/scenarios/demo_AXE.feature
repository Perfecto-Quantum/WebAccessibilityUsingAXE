@AXE
Feature: Check Perfecto PM site
  #Sample Test Scenario Description

  @AXE
  Scenario: send details
    Given I open browser to webpage "http://www.google.com"
    Then I open browser to webpage "http://ec2-54-175-223-15.compute-1.amazonaws.com/perfecto-rwd/contact.php"
    And I enter "Uzi" to "name"
    And I enter "Uzi@perfecto.com" to "email"
    And I enter "Perfecto!!!" to "msg"
    Then axe scans the web page
    Then I click on "send"
    Then I click on "main"
    Then I go to services menu
  Then axe scans the web page

        #Then I must see text "PERFECTO SVS"
