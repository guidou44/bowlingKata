package com.example;

import java.util.Random;

public class Main {

  public static void main(String[] args) {
    Player player = new Player();
    try {
      player.play();
    } catch (Exception e) {
      System.out.println("Exception Thrown !");
      System.out.println(e.getMessage());
    }
  }
}
