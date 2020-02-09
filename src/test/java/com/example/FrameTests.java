package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FrameTests {
  private Frame frame;

  @BeforeEach
  public void init() {
    frame = new Frame();
  }

  @Test
  public void whenMakingMoreThanTwoRolls_frameThrowsException() throws Exception {
    frame.roll(3);
    frame.roll(5);
    assertThrows(IndexOutOfBoundsException.class, () -> frame.roll(2));
  }

  @Test
  public void whenMakingTwoRolls_frameIsLastRollDoneReturnsTrue() throws Exception {
    frame.roll(1);
    assertFalse(frame.isLastRollDone());
    frame.roll(2);
    assertTrue(frame.isLastRollDone());
  }

  @Test
  public void whenMakingStrike_frameThrowsExceptionOnMoreRoll() throws Exception {
    frame.roll(10);
    assertThrows(IndexOutOfBoundsException.class, () -> frame.roll(2));
  }

  @Test
  public void whenTakingDownMoreThanMaxPins_frameThrowsException() {
    assertThrows(InvalidParameterException.class, () -> frame.roll(50));
  }

  @Test
  public void whenMakingTwoValidRolls_frameKeepsFrameCount() throws Exception {
    int firstRoll = 3;
    int secondRoll = 5;

    frame.roll(firstRoll);
    frame.roll(secondRoll);

    assertEquals(firstRoll + secondRoll, frame.getFrameScore());
  }

  @Test
  public void whenMakingOneRoll_frameRollCountIsOne() throws Exception {
    frame.roll(2);
    assertEquals(1, frame.getCurrentRollNumber());
  }

  @Test
  public void whenMakingTwoRolls_frameRollCountIsTwo() throws Exception {
    frame.roll(0);
    frame.roll(0);
    assertEquals(2, frame.getCurrentRollNumber());
  }

  @Test
  public void whenMakingStrike_frameIsStrikeReturnsTrueAndIsSpareReturnsFalse() throws Exception {
    frame.roll(10);
    assertFalse(frame.isSpareFrame());
    assertTrue(frame.isStrikeFrame());
  }

  @Test
  public void whenNotMakingStrike_frameIsStrikeAndIsSpareReturnFalse() throws Exception {
    frame.roll(9);
    assertFalse(frame.isSpareFrame());
    assertFalse(frame.isStrikeFrame());
  }

  @Test
  public void whenMakingSpare_frameIsSpareReturnsTrue() throws Exception {
    frame.roll(8);
    frame.roll(2);
    assertTrue(frame.isSpareFrame());
  }

  @Test
  public void whenNotMakingSpareInTwoRolls_frameIsSpareReturnsFalse() throws Exception {
    frame.roll(1);
    frame.roll(3);
    assertFalse(frame.isSpareFrame());
  }

  @Test
  public void whenMakingSecondRoll_frameRollScoreIsSameValue() throws Exception {
    frame.roll(8);
    frame.roll(1);
    assertEquals(1, frame.getRollScore());
  }

  @Test
  public void onLastFrame_whenMakingInvalidRoll_invalidParameterExceptionIsThrown() throws Exception {
    assertThrows(InvalidParameterException.class, () -> frame.rollLastFrame(20));
  }
}
