package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import io.cucumber.java.Before;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class QuestFailedScenarioSteps {

    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @Before
    public void setUp() {
        mockDeck = mock(Deck.class);
        mockScanner = mock(Scanner.class);

        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };
        menu = new Menu(game);
        menu.setScanner(mockScanner);
    }
    @Given("no winner game is created with players hand removed")
    public void playersHandRemoved_noWinnerGame(){
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }
    }

    @Given("all player hands are defined to specific cards in the no winner game")
    public void no_winner_game_player_hands_defined(DataTable dataTable){
        List<Map<String, String>> playerCards = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : playerCards) {
            int playerIndex = Integer.parseInt(row.get("playerIndex"));
            List<String> hand = Arrays.asList(row.get("cards").split(","));
            game.getPlayers().get(playerIndex).addCards(hand);
        }
    }

    @Given("the adventure cards that the players will draw from the deck in no winner game is defined")
    public void defined_adventure_deck(){
        when(mockDeck.drawAdventureCard())
                .thenReturn("F5") //p2s1
                .thenReturn("F5") // p3s1
                .thenReturn("F5")   //p4s1
                .thenReturn("S10"); //P1 draw after the quest
    }

    @Given("the cards the players use in the quest in no winner game is defined")
    public void defined_sponsorBuildCards(){
        when(mockScanner.nextLine()).thenReturn("F20","quit")
                                    .thenReturn("F15","D5","B15","quit","\n").thenReturn("F5","no","D5","quit","\n")     //P2 S1
                                    .thenReturn("F5","no","D5","quit","\n")     //P3 S1
                                    .thenReturn("F5","no","D5","quit","\n")
                                    .thenReturn("S10"); //sponsor trim cards

    }

    @Given("no winner game starts")
    public void no_winner_game_starts(){
        menu.updateRound();
    }
    @When("player1 draws a {string} event and chooses to sponsor the quest of the no winner game")
    public void playerOne_draws_then_sponsor_quest(String quest){
        when(mockScanner.nextInt()).thenReturn(1);
        menu.findingSponsor(quest);
    }

    @When("player2 player3 player4 choose to participate in the quest of the no winner game")
    public void players_chose_to_participate(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds the quest {string} in no winner game")
    public void playerOne_builds_the_quest(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("player2 player3 player4 play cards in the quest and failed to complete the quest {string}")
    public void players_failed_to_complete_the_quest(String event){
        menu.quest(event);
    }

    @Then("players hand size should be correctly updated")
    public void players_hand_size_should_be_correctly_updated(){
        //player draws S10 after the quest, # of cards draw = Q2(2) + usedcards(4) = 6 -> add 6 S10
        //original hand remaining = 8, as 4 played in the quest
        //6+8 need to trim 2 out, remaining S10 should be 4
        int count = 0;
        for(int i = 0; i < menu.getSponsorplayer().getHand().size(); i++){
            if(menu.getSponsorplayer().getHand().get(i).equalsIgnoreCase("S10")){
                count++;
            }
        }
        assertEquals(4,count);
        assertEquals(game.getPlayers().get(0).getHand().size(),12);
        assertEquals(game.getPlayers().get(1).getHand().size(),11);
        assertEquals(game.getPlayers().get(2).getHand().size(),11);
        assertEquals(game.getPlayers().get(3).getHand().size(),11);
    }

    @Then("players hand cards should be correct")
    public void players_hand_cards_should_be_correct(){
        assertTrue(game.getPlayers().get(0).getHand().containsAll(Arrays.asList("F5","F5","F10","F10","F15","F20","D5","S10","S10","S10","S10","B15")));
        assertTrue(game.getPlayers().get(1).getHand().containsAll(Arrays.asList("F5", "F5", "F20", "F70", "D5", "S10", "S10", "H10", "H10", "B15", "E30")));
        assertTrue(game.getPlayers().get(2).getHand().containsAll(Arrays.asList("F5", "F5", "F10", "F20", "D5", "S10", "S10", "H10", "H10", "L20", "E30")));
        assertTrue(game.getPlayers().get(3).getHand().containsAll(Arrays.asList("F5", "F5", "F10", "D5", "S10", "S10", "S10", "H10", "H10", "L20", "E30")));
    }

    @Then("all players should have no shields")
    public void all_players_should_have_not_gained_any_shields(){
        assertEquals(0,game.getPlayers().get(0).getShields());
        assertEquals(0,game.getPlayers().get(1).getShields());
        assertEquals(0,game.getPlayers().get(2).getShields());
        assertEquals(0,game.getPlayers().get(3).getShields());
    }

    @Then("the game should not detect any winner")
    public void check_winners(){
        assertTrue(game.checkWinners().isEmpty());
    }
}
