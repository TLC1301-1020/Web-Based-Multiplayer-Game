
package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class A1ScenarioSteps {
    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;
    @Given("one quest one winner game is created")
    public void one_quest_one_winner_game_created(){
        mockDeck = Mockito.mock(Deck.class);
        mockScanner = Mockito.mock(Scanner.class);
        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };
        menu = new Menu(game);
        menu.setScanner(mockScanner);

    }

    @Given("all players hands in one quest one winner game are removed")
    public void all_players_hands_in_one_quest_one_winner_removed(){
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }
    }

    @Given("all players hands are defined to specific cards in the one quest one winner game")
    public void all_players_hands_defined(DataTable dataTable){
        List<Map<String, String>> playerCards = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : playerCards) {
            int playerIndex = Integer.parseInt(row.get("playerIndex"));
            List<String> hand = Arrays.asList(row.get("cards").split(","));
            game.getPlayers().get(playerIndex).addCards(hand);
        }
    }

    @Given("the adventure cards that the players will draw from the deck is defined in the one quest one winner game")
    public void adventure_cards_players_draw_defined(DataTable dataTable) {
        List<Map<String, String>> drawnCards = dataTable.asMaps(String.class, String.class);

        Iterator<Map<String, String>> cardIterator = drawnCards.iterator();
        List<String> adventureCards = new ArrayList<>();

        while (cardIterator.hasNext()) {
            Map<String, String> row = cardIterator.next();
            String cards = row.get("cards");
            String[] cardArray = cards.split(",\\s*");

            adventureCards.addAll(Arrays.asList(cardArray));
        }

        when(mockDeck.drawAdventureCard())
                .thenAnswer(invocation -> {
                    if (!adventureCards.isEmpty()) {
                        String draw = adventureCards.remove(0);
                        return draw;
                    } else {
                        return null;
                    }
                });
    }

    @Given("the cards the players use in the quest in one quest one winner game is defined")
    public void players_use_in_quest_defined(DataTable dataTable){
        List<Map<String, String>> playCards = dataTable.asMaps(String.class, String.class);
        Iterator<Map<String, String>> cardIterator = playCards.iterator();
        List<String> usedCards = new ArrayList<>();

        while (cardIterator.hasNext()) {
            Map<String, String> row = cardIterator.next();
            String cards = row.get("cards");
            String[] cardArray = cards.split(",\\s*");

            usedCards.addAll(Arrays.asList(cardArray));
        }

        when(mockScanner.nextLine())
                .thenAnswer(invocation -> {
                    if (!usedCards.isEmpty()) {
                        String draw = usedCards.remove(0);
                        return draw;
                    } else {
                        return null;
                    }
                });

    }

    @Given("the one quest one winner game starts")
    public void one_quest_one_winner_starts(){
        menu.updateRound();
    }

    @When("player1 draws a Q4 in one quest one winner game")
    public void player1_draws_quest_Q4(){
        Mockito.when(mockScanner.nextInt()).thenReturn(2).thenReturn(1);
    }

    @When("player1 chooses to decline and player2 chooses to sponsor the quest {string}")
    public void player1_declines_sponsor_Q4(String quest){
        menu.findingSponsor(quest);
    }

    @When("player1 player3 player4 choose to participate in quest Q4 in one quest one winner game")
    public void players_participate_in_quest(){
        Mockito.when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player2 builds the quest {string} in one quest one winner game")
    public void player2_builds_quest_in_one_quest_one_winner(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        menu.buildQuest(quest,sponsor,participants);
    }
    @When("quest {string} passed by player4 with sponsor hand updated where player1 fails in first stage and player3 fails in fourth stage")
    public void quest_passed_by_one_player(String event){
        menu.quest(event);
    }

    @When("players should have correct cards")
    public void sponsor_should_have_exact_cards(){
        assertEquals(menu.getSponsorplayer().getHand().size(),12);
    }

    @When("players should have correct shields in one quest one winner game")
    public void players_should_have_correct_shields_one_quest_one_winner(){
        assertEquals(game.getPlayers().get(0).getShields(),0);              //player 1 shield 0
        assertEquals(game.getPlayers().get(1).getShields(),0);              //player 2 shield 0
        assertEquals(game.getPlayers().get(2).getShields(),0);              //player 3 shield 0
        assertEquals(game.getPlayers().get(3).getShields(),4);
    }

    @When("players should have correct hand cards")
    public void players_should_have_correct_hand_size(){
        assertTrue(game.getPlayers().get(0).getHand().containsAll(Arrays.asList("F5", "F10", "F15", "F15", "F30", "H10", "B15", "B15", "L20")));
        assertTrue(menu.getSponsorplayer().getHand().containsAll(List.of("F5","F5","D5","S10","S10","S10","S10","S10","S10","S10","S10","S10")));
        assertTrue(game.getPlayers().get(2).getHand().containsAll(Arrays.asList("F5", "F5", "F15", "F30", "S10")));
        assertTrue(game.getPlayers().get(3).getHand().containsAll(Arrays.asList("F15", "F15", "F40", "L20")));
    }
    @When("only one winner of the one quest one winner game is detected")
    public void one_winner_detected_one_quest_one_winner(){
        assertTrue(menu.getParticipants().contains(game.getPlayers().get(3)));     //only player 4 is in the participant list
        assertEquals(menu.getParticipants().size(),1);                       //only one participant left at the last stage

    }

}



