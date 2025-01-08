Feature: Quest Game

  Scenario: A1_Compulsory_Quest
    Given first game is created with players hands set to values
    When player 1 declines to sponsor the quest "Q4", player2 sponsors
    And three players participate the quest
    And player2 sets the stage for "Q4"
    And quest "Q4" completed with sponsor card updated and participant shields updated
    Then sponsor player should have exact 12 cards
    And shields should be updated correctly
    And cards in each player hand should be correct


  Scenario: 2winner_game_2winner_quest
    Given second game is created with players hands set to values
    When player1 draws then sponsor "Q4"
    And participants found for Q4 quest
    And player1 builds the stage for "Q4"
    And quest "Q4" completed with player1 hand updated
    And player2 draws then player 3 sponsors "Q3"
    And 2 participants found for Q3 quest
    And player3 builds the stage for "Q3"
    And quest "Q3" completed with player3 hand updated
    Then players should have correct shields
    And two winners should be detected


  Scenario: 1winner_game_with_events
    Given third game is created with players hands set to values
    When player 1 draws then sponsor "Q4"
    And 3 participants found for Q4 quest
    And player1 builds game stage "Q4"
    And quest "Q4" completed with three winners
    And player 2 draws plague
    And player 3 draws prosperity
    And player 4 draws Queens favor
    And player 1 draws sponsor second quest "Q3"
    And 3 participants found for Q3 quest
    And player1 builds the stage "Q3"
    And quest "Q3" completed with two winners
    Then players shields should be updated correctly
    And player3 should be detected as winner


  Scenario: As the game starts, I want a quest to be drawn and built by one player,
          with three participants failing in stage one, so the results are verified.
    Given no winner game is created
    And all players hands are removed
    And all player hands are defined to specific cards in the no winner game
      | playerIndex | cards                                           |
      | 0           | F5,F5,F10,F10,F15,F15,F20,F20,D5,D5,B15,B15     |
      | 1           | F5,F5,F20,F70,D5,D5,S10,S10,H10,H10,B15,E30     |
      | 2           | F5,F5,F10,F20,D5,D5,S10,S10,H10,H10,L20,E30     |
      | 3           | F5,F5,F10,D5,D5,S10,S10,S10,H10,H10,L20,E30     |
    And the adventure cards that the players will draw from the deck is defined in the no winner game
    And the cards the players use in the quest in no winner game is defined
    And no winner game starts
    When player1 draws a "Q2" event then chooses to sponsor the quest of the no winner game
    And player2 player3 player4 choose to participate in the quest of the no winner game
    And player1 builds the quest "Q2" in no winner game
    And player2 player3 player4 play cards in the quest and failed to complete the quest "Q2"
    Then players hand size should be correct
    And players hand cards should be correct
    And all players should have no shields
    And the game should not detect any winner

