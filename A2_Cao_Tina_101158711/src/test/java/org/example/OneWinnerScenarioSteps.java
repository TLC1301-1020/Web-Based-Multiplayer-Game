package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OneWinnerScenarioSteps {
    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @Given("one winner game is created")
    public void one_winner_game_created() {
        mockDeck = Mockito.mock(Deck.class);
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

    @Given("all players hands in one winner game are removed")
    public void player_hands_removed(){
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }

    }
    @Given("all player hands are defined to specific cards in the one winner game")
    public void one_winner_game_player_hands_defined(DataTable dataTable) {
        List<Map<String, String>> playerCards = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : playerCards) {
            int playerIndex = Integer.parseInt(row.get("playerIndex"));
            List<String> hand = Arrays.asList(row.get("cards").split(","));
            game.getPlayers().get(playerIndex).addCards(hand);
        }
    }

    @Given("the adventure cards that the players will draw from the deck is defined in the one winner game")
    public void defined_adventure_deck(DataTable dataTable){
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

    @Given("the cards the players use in the quest in one winner game is defined")
    public void defined_cards_used_in_quest(DataTable dataTable){
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

    @Given("the one winner game starts")
    public void one_winner_game_starts(){
        menu.updateRound();
    }

    @When("player1 draws first {string} quest in one winner game")
    public void player1_draws_quest_Q4(String quest){
        menu.findingSponsor(quest);
    }

    @When("player1 sponsors the first quest")
    public void player1_sponsors_quest_Q4(){
        when(mockScanner.nextInt()).thenReturn(1);
    }
    @When("player2 player3 player4 choose to participate in the first quest of one winner game")
    public void three_participants_found_for_the_quest_Q4(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds the first quest {string} in one winner game")
    public void player1_builds_first_quest(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} passed by all participants then sponsor update hands")
    public void quest_Q4_completed_with_three_winners(String event){
        menu.quest(event);
        menu.updateRound();
    }

    @When("player2 draws event card plague")
    public void player_2_draws_plague(){
        menu.plagueCard();
        menu.updateRound();
    }

    @When("player3 draws event card prosperity")
    public void player3_draws_prosperity(){
        menu.Prosperity();
        menu.updateRound();
    }

    @When("player4 draws Queens favor")
    public void player4_draws_Queens_favor(){
        menu.QueensFavor();
        menu.updateRound();
    }

    @When("player1 draws second quest {string}")
    public void player1_draws_quest_Q3(String quest){
        menu.findingSponsor(quest);
    }

    @When("player1 sponsors second quest")
    public void player1_sponsors_quest_Q3(){
        when(mockScanner.nextInt()).thenReturn(1);
    }

    @When("player2 player3 player4 choose to participate in the second quest of one winner game")
    public void three_participants_found_for_quest_Q3(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds the second quest {string} in one winner game")
    public void player1_builds_second_quest(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        menu.buildQuest(quest,sponsor,participants);

    }

    @When("quest {string} passed by player2 player3 then sponsor update hands")
    public void quest_Q3_completed_with_two_winners(String event){
        menu.quest(event);
        menu.updateRound();
    }

    @Then("players shields should be correct")
    public void players_shields_should_be_correct(){
        assertEquals(game.getPlayers().get(0).getShields(),0);
        assertEquals(game.getPlayers().get(1).getShields(),5);
        assertEquals(game.getPlayers().get(2).getShields(),7);
        assertEquals(game.getPlayers().get(3).getShields(),4);
    }


    @Then("the hand cards of each player should be correct")
    public void player_hand_cards_should_be_correct(){
        assertTrue(game.getPlayers().get(0).getHand().containsAll(Arrays.asList("F10","F10","F10","F10","F10","F10","F10","F15","F70","H10","S10","L20")));
        assertTrue(game.getPlayers().get(1).getHand().containsAll(Arrays.asList("F5", "F10", "F20", "D5", "H10", "S10", "S10", "B15", "B15", "E30")));
        assertTrue(game.getPlayers().get(2).getHand().containsAll(Arrays.asList("F5", "F5", "F10", "F10", "F10", "F20", "D5", "S10", "H10")));
        assertTrue(game.getPlayers().get(3).getHand().containsAll(Arrays.asList("F5", "F10", "F10", "F20", "D5", "S10", "H10", "B15", "B15", "L20", "E30")));
    }

    @Then("there should be only one winner of the game")
    public void one_winner_of_the_game(){
        assertEquals(1, game.checkWinners().size());
    }

    @Then("player3 should be detected as winner")
    public void player3_should_be_detected_as_winner(){
        menu.printWinner();
        assertTrue(game.checkWinners().contains(game.getPlayers().get(2)));
    }


}
