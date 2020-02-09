package com.example;

public class Game {
  private int currentFrameIndex;
  private int currentScore;
  private int strikeBonusRollCounter;
  private int spareBonusRollCounter;
  private Frame[] frames;

  public Game(int numberOfFrames) {
    currentScore = 0;
    currentFrameIndex = 0;
    strikeBonusRollCounter = 0;
    spareBonusRollCounter = 0;
    initFrames(numberOfFrames);
  }

  public void roll(int nbOfPins) throws Exception {
    System.out.println(String.format("rolling %d", nbOfPins));

    Frame currentFrame = frames[currentFrameIndex];
    setRollScore(nbOfPins, currentFrame);

    if (!isOnLastFrame()) {
      setRollBonuses(currentFrame);
      setFrameIndex(currentFrame);
    }
  }

  public int score() {
    return currentScore;
  }

  public int getRollsRemainingOnLastFrame() {
    if (!isOnLastFrame()) {
      return 0;
    }

    Frame lastFrame = frames[frames.length - 1];
    if (lastFrame.isStrikeFrame()) {
      return 2;
    } else if (lastFrame.isSpareFrame()) {
      return 1;
    }
    return 0;
  }

  //region private methods

  private boolean isOnLastFrame() {
    return currentFrameIndex == frames.length - 1;
  }

  private void setRollScore(int nbOfPins, Frame currentFrame) throws Exception {
    if (isOnLastFrame()) {
      currentFrame.rollLastFrame(nbOfPins);
    } else {
      currentFrame.roll(nbOfPins);
    }
    currentScore += currentFrame.getRollScore();
  }

  private void setRollBonuses(Frame currentFrame) {
    if (spareBonusRollCounter > 0) {
      currentScore += currentFrame.getRollScore();
      spareBonusRollCounter--;
    } else if (strikeBonusRollCounter > 0) {
      currentScore += currentFrame.getRollScore();
      strikeBonusRollCounter--;
    }
  }

  private void setFrameIndex(Frame currentFrame) {
    if (currentFrame.isStrikeFrame()) {
      currentFrameIndex++;
      strikeBonusRollCounter += 2;
    } else if (currentFrame.isSpareFrame()) {
      spareBonusRollCounter += 1;
    }

    if (currentFrame.getCurrentRollNumber() == 2) {
      currentFrameIndex++;
    }
  }

  private void initFrames(int numberOfFrames) {
    frames = new Frame[numberOfFrames];
    for (int i = 0; i < numberOfFrames; i++) {
      frames[i] = new Frame();
    }
  }

  //endregion
}
