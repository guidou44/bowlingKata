package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTests {
  private static final int NUMBER_OF_PINS = 10;
  private static final int NUMBER_OF_FRAMES = 10;
  private Game game;
  private Random rand;

  @BeforeEach
  public void init() {
    game = new Game(NUMBER_OF_FRAMES);
    rand = new Random();
  }

  @Test
  public void whenTakingDownRandomNumberOfPinsOnFirstRoll_scoreShouldReturnSameValue()
      throws Exception {
    int pinsDown = rand.nextInt(NUMBER_OF_PINS + 1);
    game.roll(pinsDown);
    assertEquals(pinsDown, game.score());
  }

  @Test
  public void whenTakingDownPinsOnSecondFrameNoBonus_gameScoreIsAccurate() throws Exception {
    int firstRoll = 1, secondRoll = 3, thirdRoll = 5, fourthRoll = 4;
    int expectedScore = firstRoll + secondRoll + thirdRoll + fourthRoll;
    game.roll(firstRoll);
    game.roll(secondRoll);
    game.roll(thirdRoll);
    game.roll(fourthRoll);

    assertEquals(expectedScore, game.score());
  }

  @Test
  public void whenMakingASpare_scoreShouldReturn10AndBonusOfNextRoll() throws Exception {
    game.roll(5);
    game.roll(5);
    int pinsDownSecondFrame = rand.nextInt(8);
    game.roll(pinsDownSecondFrame);
    assertEquals((10 + 2*pinsDownSecondFrame), game.score());
  }

  @Test
  public void whenMakingAStrike_scoreShouldReturn10AndBonusOfNextTwoRolls() throws Exception {
    game.roll(10);
    int secondRoll = 2, thirdRoll = 7;
    game.roll(secondRoll);
    game.roll(thirdRoll);
    assertEquals((2*secondRoll + 2*thirdRoll + 10), game.score());
  }

  @Test
  public void whenMakingStrikeOnLastFrame_rollsRemainingShouldBeTwo() throws Exception {
    rollTillLastFrameWithEachFrameScore3();

    game.roll(10);
    assertEquals(2, game.getRollsRemainingOnLastFrame());
  }

  @Test
  public void whenMakingSpareOnLastFrame_rollsRemainingShouldBeOne() throws Exception {
    rollTillLastFrameWithEachFrameScore3();

    game.roll(2);
    game.roll(8);

    assertEquals(1, game.getRollsRemainingOnLastFrame());
  }

  @Test
  public void whenNotOnLastFrame_rollsRemainingOnLastFrameShouldBeZerp() throws Exception {
    game.roll(1);
    assertEquals(0, game.getRollsRemainingOnLastFrame());
  }

  @Test
  public void afterACompleteGame_with2StrikeAnd1Spare_scoreIsValid() throws Exception {
    List<Integer> rolls =
        Arrays.asList(8, 2, 2, 1, 3, 4, 10, 10, 2, 2, 3, 3, 5, 4, 6, 1, 1, 1);
    int sum = 0;
    for (Integer roll : rolls)
    {
      game.roll(roll);
      sum += roll;
    }

    assertEquals(12 + 2 + 2 + 3 + sum, game.score());
  }

  @Test
  public void afterACompleteGame_withStrikeOnLastFrame_scoreIsValid() throws Exception {
    rollTillLastFrameWithEachFrameScore3();
    game.roll(10);

    int lastRolls = 2;
    int rollsRemaining = game.getRollsRemainingOnLastFrame();
    for (int i = 0; i < rollsRemaining; i++) {
      game.roll(lastRolls);
    }

    assertEquals((9*3 + 2*lastRolls + 10), game.score());
  }

  @Test
  public void afterACompleteGame_withSpareOnLastFrame_scoreIsValid() throws Exception {
    rollTillLastFrameWithEachFrameScore3();
    game.roll(8);
    game.roll(2);

    int lastRolls = 2;
    int rollsRemaining = game.getRollsRemainingOnLastFrame();
    for (int i = 0; i < rollsRemaining; i++) {
      game.roll(lastRolls);
    }

    assertEquals((9*3 + lastRolls + 8 + 2), game.score());
  }

  //region private methods

  private void rollTillLastFrameWithEachFrameScore3() throws Exception {
    for (int i = 0; i < NUMBER_OF_FRAMES - 1; i++) {
      game.roll(1);
      game.roll(2);
    }
  }

  //endregion
}
