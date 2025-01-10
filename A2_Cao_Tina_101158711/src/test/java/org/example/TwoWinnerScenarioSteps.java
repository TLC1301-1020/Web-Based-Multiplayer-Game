package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TwoWinnerScenarioSteps {
    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @Given("two winner game is created")
    public void two_winner_game_created(){
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

    @Given("all players hands in two winner game are removed")
    public void two_winner_game_players_hands_removed(){
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }
    }

    @Given("all player hands are defined to specific cards in the two winner game")
    public void two_winner_game_player_hands_defined(DataTable dataTable){
        List<Map<String, String>> playerCards = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : playerCards) {
            int playerIndex = Integer.parseInt(row.get("playerIndex"));
            List<String> hand = Arrays.asList(row.get("cards").split(","));
            game.getPlayers().get(playerIndex).addCards(hand);
        }
    }

    @Given("the adventure cards that the players will draw from the deck is defined in the two winner game")
    public void adventure_cards_defined(DataTable dataTable){
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

    @Given("the cards the players use in the quests in two winner game is defined")
    public void cards_players_use_two_winner_game(DataTable dataTable){
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

    @Given("the two winner game starts")
    public void two_winner_game_starts(){
        menu.updateRound();
    }

    @When("player1 draws first quest in two winner game")
    public void player1_draws_first_quest_Q4(){
        when(mockScanner.nextInt()).thenReturn(1);
    }

    @When("player1 sponsors the first quest {string} in two winner game")
    public void player1_sponsors_first_quest(String quest){
        menu.findingSponsor(quest);
    }

    @When("player2 player3 player4 choose to participate in the first quest of two winner game")
    public void participants_in_first_quest(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds the first quest {string} in two winner game")
    public void sponsor_builds_first_quest(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} passed by all participants then sponsor update hands in first quest of two winner game")
    public void first_quest_two_winner_game_completed(String event){
        menu.quest(event);
        menu.updateRound();
    }

    @When("player2 draws second quest in two winner game")
    public void player2_draws_second_quest(){
        when(mockScanner.nextInt()).thenReturn(2,1);

    }
    @When("player2 declines to sponsor the quest")
    public void player2_declines_sponsor(){
        when(mockScanner.nextInt()).thenReturn(2,1);
    }
    @When("player2 declines and player3 sponsors the quest {string} in two winner game")
    public void player2_declines_sponsor(String quest){
        menu.findingSponsor(quest);

    }

    @When("player2 player4 choose to participate in the second quest of two winner game")
    public void participates_second_quest_two_winner_game(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(2)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player3 builds the second quest {string} in two winner game")
    public void sponsor_builds_second_quest(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} passed by player2 player4 then sponsor update hands in second quest of two winner game")
    public void second_quest_two_winner_game_completed(String event){
        menu.quest(event);
        menu.updateRound();
    }

    @Then("players should have correct shields in two winner game")
    public void players_should_have_correct_shields(){
        assertEquals(0,game.getPlayers().get(0).getShields());
        assertEquals(7,game.getPlayers().get(1).getShields());
        assertEquals(0,game.getPlayers().get(2).getShields());
        assertEquals(7,game.getPlayers().get(3).getShields());
    }

    @Then("there should be two winners of the game")
    public void two_winners_in_game(){
        assertEquals(2,game.checkWinners().size());
    }

    @Then("player2 player4 should be detected as winners")
    public void two_winners_should_be_detected() {
        assertTrue(game.checkWinners().contains(game.getPlayers().get(1)));
        assertTrue(game.checkWinners().contains(game.getPlayers().get(3)));
        assertFalse(game.checkWinners().contains(game.getPlayers().get(0)));
        assertFalse(game.checkWinners().contains(game.getPlayers().get(2)));
        menu.printWinner();
    }
}