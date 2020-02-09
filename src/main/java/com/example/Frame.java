package com.example;

import java.security.InvalidParameterException;

public class Frame {
  private static final int NUMBER_OF_PINS = 10;
  private int currentRollNumber;
  private int frameScore;
  private int rollScore;

  public Frame() {
    currentRollNumber = 0;
    frameScore = 0;
  }

  public int getCurrentRollNumber() {
    return currentRollNumber;
  }

  public boolean isLastRollDone() {
    return currentRollNumber == 2;
  }

  public int getFrameScore() {
    return frameScore;
  }

  public int getRollScore() {
    return rollScore;
  }

  public void roll(int nbOfPins) throws Exception {
    checkRollValidity(nbOfPins);

    rollInternal(nbOfPins);
  }

  public void rollLastFrame(int nbOfPins) {
    if (nbOfPins > 10) {
      throw new InvalidParameterException("More than the 10 possible pins");
    }

    rollInternal(nbOfPins);
  }

  public boolean isSpareFrame() {
    return currentRollNumber == 2 && frameScore == 10;
  }

  public boolean isStrikeFrame() {
    return currentRollNumber == 1 && frameScore == 10;
  }

  //region private methods

  private void checkRollValidity(int nbOfPins) {
    if ((currentRollNumber == 2 || frameScore == 10)) {
      throw new IndexOutOfBoundsException("Max number of roll reached for frame");
    }

    if ((nbOfPins + frameScore) > NUMBER_OF_PINS) {
      throw new InvalidParameterException("Not enough pins remaining");
    }
  }

  private void rollInternal(int nbOfPins) {
    rollScore = nbOfPins;
    frameScore += nbOfPins;
    currentRollNumber++;
  }

  //endregion
}
