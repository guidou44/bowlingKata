package com.example;

import java.util.Random;

public class Player {
  private static final int NUMBER_OF_FRAMES = 10;
  private static final int NUMBER_OF_PINS = 10;

  public void play() throws Exception {
    System.out.println(String.format("Starting game for %d frames", NUMBER_OF_FRAMES));
    Game game = new Game(NUMBER_OF_FRAMES);


    for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
      int firstRoll = getRandomNumberOfPins();
      int secondRoll = getRandomNumberOfPins(NUMBER_OF_PINS - firstRoll);

      game.roll(firstRoll);
      if (firstRoll == 10) {
        continue;
      }
      game.roll(secondRoll);
    }

    int extraRolls = game.getRollsRemainingOnLastFrame();
    int lastRoll = 0;

    for (int i = 0; i < extraRolls; i++) {
      int currentRoll = getRandomNumberOfPins(NUMBER_OF_PINS - lastRoll);
      game.roll(currentRoll);
      lastRoll = (currentRoll == 10) ? 0 : currentRoll;
    }

    System.out.println(String.format("Game done. Score is %d", game.score()));
  }

  //region private methods

  private static int getRandomNumberOfPins() {
    return getRandomNumberOfPins(NUMBER_OF_PINS);
  }

  private static int getRandomNumberOfPins(final int pinsRemaining) {
    Random rand = new Random();
    return rand.nextInt(pinsRemaining + 1);
  }

  //endregion

}
