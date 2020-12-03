Feature:

  Scenario Outline: Generate Number Sequence
    Given User gives "<goal>" and "<step>" in the request
    When User sends POST request to generate number sequence
    Then API should return status as 202
    And The response should have "task"
    Examples:
      | goal | step |
      | 10   | 2    |
      | 25   | 3    |

  Scenario: Get Status of task
    Given User gives uuid of task in the request
    When User sends GET request to get status of task
    Then API should return status as 200
    And The response should have "result"
    
  Scenario: Get Number Sequence List
    Given User gives uuid of task and get_numlist in the request
    When User sends GET request to get List of task
    Then API should return status as 200
    And The response should have a List of Numbers
    
      
    
