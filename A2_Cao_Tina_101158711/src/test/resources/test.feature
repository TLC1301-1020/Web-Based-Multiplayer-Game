Feature: Quest Game

  Scenario: As the game starts, I want one quest to be created,
  with three participants in the quest, one failing in second stage, one failing in fourth stage
  and only one winner of the quest, so the results can be verified.
    Given one quest one winner game is created
    And all players hands in one quest one winner game are removed
    And all players hands are defined to specific cards in the one quest one winner game
      | playerIndex | cards                                                   |
      | 0           | F5,F5,F15,F15,D5,S10,S10,H10,H10,B15,B15,L20 |
      | 1           | F5,F5,F5,F10,F10,F10,D5,H10,B15,B15,L20,E30  |
      | 2           | F5,F5,F5,F15,D5,S10,H10,S10,H10,B15,L20,S10  |
      | 3           | F5,F15,F15,F40,D5,D5,S10,H10,H10,B15,L20,E30 |
    And the adventure cards that the players will draw from the deck is defined in the one quest one winner game
      | scenario          | cards                                               |
      | Quest 1 stage 1   | F30, S10, B15                                       |
      | Quest 1 stage 2   | F10, L20, L20                                       |
      | Quest 1 stage 3   | B15, S10                                            |
      | Quest 1 stage 4   | F30, L20                                            |
      | Quest 1 Sponsor   | S10,S10,S10,S10,S10,S10,S10,S10,S10,S10,S10,S10,S10 |
    And the cards the players use in the quest in one quest one winner game is defined
      | scenario        | cards                               |
      | Quest 1 stage 1 | F10, quit                           |
      | Quest 1 stage 2 | F10, B15, quit                      |
      | Quest 1 stage 3 | F10, B15, H10, quit                 |
      | Quest 1 stage 4 | F5, L20, E30, quit                  |
      | player1 stage 1 | F5, no, D5, S10, quit               |
      | player3 stage 1 | \n, F5, no, S10, D5, quit, \n       |
      | player4 stage 1 | \n, F5, no, D5, H10, quit, \n       |
      | player1 stage 2 | \n, no, H10, S10, quit, \n          |
      | player3 stage 2 | \n, no, B15, S10, quit, \n          |
      | player4 stage 2 | \n, no, H10, B15, quit, \n          |
      | player3 stage 3 | \n, no, L20, H10, S10, quit, \n     |
      | player4 stage 3 | \n, no, D5, S10, L20, quit, \n      |
      | player3 stage 4 | \n, no, B15, H10, L20, quit, \n     |
      | player4 stage 4 | \n, no, D5, S10, L20, E30, quit, \n |
      | Sponsor trim    | S10, S10, S10, S10, S10             |
    And the one quest one winner game starts
    When player1 draws a Q4 in one quest one winner game
    And player1 chooses to decline and player2 chooses to sponsor the quest "Q4"
    And player1 player3 player4 choose to participate in quest Q4 in one quest one winner game
    And player2 builds the quest "Q4" in one quest one winner game
    And quest "Q4" passed by player4 with sponsor hand updated where player1 fails in first stage and player3 fails in fourth stage
    Then players should have correct cards
    And players should have correct shields in one quest one winner game
    And players should have correct hand cards
    And only one winner of the one quest one winner game is detected



  Scenario: As the game starts, I want two quests to be created,
  with three participants in first quest and two participants in second quest
  and only two winners of the game, so the results can be verified.
    Given two winner game is created
    And all players hands in two winner game are removed
    And all player hands are defined to specific cards in the two winner game
      | playerIndex | cards                                                     |
      | 0           | F5,F10,F10,F10,F15,D5,S10,S10,H10,H10,B15,L20  |
      | 1           | F5,F10,F20,D5,D5,S10,S10,H10,H10,B15,L20,E30   |
      | 2           | F5,F5,F10,F15,F20,F20,D5,S10,S10,H10,B15,E30   |
      | 3           | F5,F10,D5,S10,S10,H10,B15,B15,L20,L20,E30,E30  |
    And the adventure cards that the players will draw from the deck is defined in the two winner game
      | scenario          | cards                                                 |
      | Quest 1 stage 1   | L20, D5, F20                                          |
      | Quest 1 stage 2   | B15, F20                                              |
      | Quest 1 stage 3   | F5, D5                                                |
      | Quest 1 stage 4   | H10, E30                                              |
      | Quest 1 Sponsor   | S10, S10, S10, S10, S10, S10, S10, S10, S10, S10, S10 |
      | Quest 2 stage 1   | S10, F5                                               |
      | Quest 2 stage 2   | L20, S10                                              |
      | Quest 2 stage 3   | F10, F5                                               |
      | Quest 2 Sponsor   | F10, F10, F10, F10, F10, F10                          |
    And the cards the players use in the quests in two winner game is defined
      | scenario        | cards                                   |
      | Quest 1 stage 1 | F10, quit                               |
      | Quest 1 stage 2 | F15, quit                               |
      | Quest 1 stage 3 | F10, S10, quit                          |
      | Quest 1 stage 4 | F10, S10, D5, quit                      |
      | Player2 stage 1 | F20, no, B15, quit                      |
      | Player3 stage 1 | \n, S10, no, D5, quit, \n               |
      | Player4 stage 1 | \n, F20, no, B15, quit, \n              |
      | Player2 stage 2 | \n, no, L20, quit, \n                   |
      | Player4 stage 2 | \n, no, D5, S10, quit, \n               |
      | Player2 stage 3 | \n, no, S10, H10, quit, \n              |
      | Player4 stage 3 | \n, no, E30, quit, \n                   |
      | Player2 stage 4 | \n, no, L20, D5, quit, \n               |
      | Player4 stage 4 | \n, no, S10, H10, B15, quit, \n         |
      | Sponsor trim    | \n, S10, S10, S10, S10, quit, \n        |
      | Quest 2 stage 1 | F5, quit                                |
      | Quest 2 stage 2 | F10, quit                               |
      | Quest 2 stage 3 | F15, quit                               |
      | Player2 stage 1 | \n, no, S10, quit, \n                   |
      | Player4 stage 1 | \n, no, L20, quit, \n                   |
      | Player2 stage 2 | \n, no, B15, quit, \n                   |
      | Player4 stage 2 | \n, no, E30, quit, \n                   |
      | Player2 stage 3 | \n, no, E30, quit, \n                   |
      | Player4 stage 3 | \n, no, L20, quit, \n                   |
      | Sponsor trim    | \n, F10, F10, F10, F10, F10, F10, quit  |
    And the two winner game starts
    When player1 draws first quest in two winner game
    And player1 sponsors the first quest "Q4" in two winner game
    And player2 player3 player4 choose to participate in the first quest of two winner game
    And player1 builds the first quest "Q4" in two winner game
    And quest "Q4" passed by all participants then sponsor update hands in first quest of two winner game
    And player2 draws second quest in two winner game
    And player2 declines to sponsor the quest
    And player2 declines and player3 sponsors the quest "Q3" in two winner game
    And player2 player4 choose to participate in the second quest of two winner game
    And player3 builds the second quest "Q3" in two winner game
    And quest "Q3" passed by player2 player4 then sponsor update hands in second quest of two winner game
    Then players should have correct shields in two winner game
    And there should be two winners of the game
    And player2 player4 should be detected as winners


  Scenario: As the game starts, I want event cards to be drawn and two quests to be created,
            with three participants and only one winner of the game, so the results can be verified.
    Given one winner game is created
    And all players hands in one winner game are removed
    And all player hands are defined to specific cards in the one winner game
      | playerIndex | cards                                             |
      | 0           | F5,F5,F10,F10,F20,F70,D5,H10,S10,H10,S10,L20      |
      | 1           | F5,F5,F10,D5,D5,S10,H10,H10,S10,L20,L20,E30       |
      | 2           | F10,F20,F70,D5,D5,S10,H10,S10,H10,B15,E30,E30     |
      | 3           | F5,F10,F10,F10,F70,D5,D5,S10,L20,L20,E30,E30      |
    And the adventure cards that the players will draw from the deck is defined in the one winner game
      | scenario          | cards                                       |
      | Quest 1 stage 1   | E30, D5, B15                                |
      | Quest 1 stage 2   | D5, F5, D5                                  |
      | Quest 1 stage 3   | B15, E30, S10                               |
      | Quest 1 stage 4   | B15, S10, H10                               |
      | Quest 1 Sponsor   | F10, F10, F10, F10, F10, F10, F10, F10, F10 |
      | Prosperity p1     | F10, F15                                    |
      | Prosperity p2     | E30, S10                                    |
      | Prosperity p3     | D5, F10                                     |
      | Prosperity p4     | F10, B15                                    |
      | Queens favor      | F10, F20                                    |
      | Quest 2 stage 1   | D5, H10, E30                                |
      | Quest 2 stage 2   | F20, F10                                    |
      | Quest 2 stage 3   | D5, F5                                      |
      | Quest 2 Sponsor   | F10, F10, F10, F10, F10, F10, F10, F10      |
    And the cards the players use in the quests in one winner game is defined
      | scenario          | cards                           |
      | Quest 1 stage 1   | F5, quit                        |
      | Quest 1 stage 2   | F10, quit                       |
      | Quest 1 stage 3   | F5, H10, quit                   |
      | Quest 1 stage 4   | F20, quit                       |
      | Player2 stage 1   | F5, no, D5, quit                |
      | Player3 stage 1   | \n, F70, no, B15, quit, \n      |
      | Player4 stage 1   | \n, F10, no, S10, quit, \n      |
      | Player2 stage 2   | \n, no, H10, quit, \n           |
      | Player3 stage 2   | \n, no, D5, H10, quit, \n       |
      | Player4 stage 2   | \n, no, L20, quit, \n           |
      | Player2 stage 3   | \n, no, L20, quit, \n           |
      | Player3 stage 3   | \n, no, E30, quit, \n           |
      | Player4 stage 3   | \n, no, E30, quit, \n           |
      | Player2 stage 4   | \n, no, L20, D5, quit, \n       |
      | Player3 stage 4   | \n, no, S10, H10, D5, quit, \n  |
      | Player4 stage 4   | \n, no, E30, D5, quit, \n       |
      | Sponsor trim      | \n, F10, F10, F10, F10, \n      |
      | Queens favor      | F10, F10                        |
      | Quest 2 stage 1   | F10, quit                       |
      | Quest 2 stage 2   | F10, D5, quit                   |
      | Quest 2 stage 3   | F10, S10, quit                  |
      | player2 stage 1   | D5, no, S10, D5, quit, \n       |
      | player3 stage 1   | \n, no, S10, D5, quit, \n       |
      | player4 stage 1   | \n, F70, no, D5, quit, \n       |
      | player2 stage 2   | \n, no, E30, quit, \n           |
      | player3 stage 2   | \n, no, E30, quit, \n           |
      | player2 stage 3   | \n, no, E30, quit, \n           |
      | player3 stage 3   | \n, no, E30, quit, \n           |
      | Sponsor trim      | \n, F10, F10, F10, F10, F10     |
    And the one winner game starts
    When player1 draws first "Q4" quest in one winner game
    And player1 sponsors the first quest in one winner game
    And player2 player3 player4 choose to participate in the first quest of one winner game
    And player1 builds the first quest "Q4" in one winner game
    And quest "Q4" passed by all participants then sponsor update hands in first quest of one winner game
    And player2 draws event card plague
    And player3 draws event card prosperity
    And player4 draws Queens favor
    And player1 draws second quest "Q3" in one winner game
    And player1 sponsors second quest in one winner game
    And player2 player3 player4 choose to participate in the second quest of one winner game
    And player1 builds the second quest "Q3" in one winner game
    And quest "Q3" passed by player2 player3 then sponsor update hands in second quest of one winner game
    Then players shields should be correct
    And the hand cards of each player should be correct
    And there should be only one winner of the game
    And player3 should be detected as winner


  Scenario: As the game starts, I want a quest to be drawn and built by one player,
          with three participants failing in stage one, so the results are verified.
    Given no winner game is created
    And all players hands in no winner game are removed
    And all player hands are defined to specific cards in the no winner game
      | playerIndex | cards                                           |
      | 0           | F5,F5,F10,F10,F15,F15,F20,F20,D5,D5,B15,B15     |
      | 1           | F5,F5,F20,F70,D5,D5,S10,S10,H10,H10,B15,E30     |
      | 2           | F5,F5,F10,F20,D5,D5,S10,S10,H10,H10,L20,E30     |
      | 3           | F5,F5,F10,D5,D5,S10,S10,S10,H10,H10,L20,E30     |
    And the adventure cards that the players will draw from the deck is defined in the no winner game
    And the cards the players use in the quest in no winner game is defined
    And no winner game starts
    When player1 draws a "Q2" quest in no winner game
    And player1 sponsors the quest in no winner game
    And player2 player3 player4 choose to participate in the quest of the no winner game
    And player1 builds the quest "Q2" in no winner game
    And player2 player3 player4 play cards in the quest and failed to complete the quest "Q2"
    Then players hand size should be correct
    And players hand cards should be correct
    And all players should have no shields
    And the game should not detect any winner

