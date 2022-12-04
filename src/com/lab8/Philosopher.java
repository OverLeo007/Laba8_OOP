package com.lab8;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class Philosopher extends Thread {

  public static final PrintStream out = new PrintStream(System.out,
      true, StandardCharsets.UTF_8);

  private static final boolean[] forks = new boolean[DiningPhilosophers.philosophersCount];

  private final int num;
  private final HashMap<String, Integer> philoForks = new HashMap<>();

  private final int eatTime;


  Philosopher(int num, int eatTime) {
    this.num = num;
    philoForks.put("left", num - 1 < 0 ? forks.length - 1 : num - 1);
    philoForks.put("right", num);
    this.eatTime = eatTime;
    Arrays.fill(forks, true);
  }


  public static int randint(int min, int max) {
    max -= min;
    return (int) (Math.random() * ++max) + min;
  }

  private synchronized boolean isForksAvailable() {
    return forks[philoForks.get("left")] && forks[philoForks.get("right")];
  }

  private synchronized void pickUpForks() {
    forks[philoForks.get("left")] = false;
    forks[philoForks.get("right")] = false;
  }

  private synchronized void putDownForks() {
    forks[philoForks.get("left")] = true;
    forks[philoForks.get("right")] = true;
  }

  @Override
  public void run() {
    int thinkingTime = randint(1000, 10000);
    boolean isWaiting = false;
    int eatCount = 0;

    out.printf("\033[0;3" + (num + 1) + "mФилософ номер %d гуляет и размышляет\n", num + 1);
    try {
      Thread.sleep(thinkingTime);
    } catch (InterruptedException ignored) {}

    while (eatCount != 5) {
      if (!isWaiting) {
        out.printf("\033[0;3" + (num + 1) + "mФилософ номер %d проголодался и сел на свое место\n", num + 1);
      }
      if (isForksAvailable()) {
        isWaiting = false;
        out.printf("\033[0;3" + (num + 1) + "mФилософ номер %d приступает к пище, взял вилки: %d %d\n",
            num + 1,
            philoForks.get("left") + 1,
            philoForks.get("right") + 1);
        pickUpForks();
        eatCount ++;
        try {
          Thread.sleep(eatTime);

          out.printf("\033[0;3" + (num + 1) + "mФилософ номер %d наелся и отправляется на подумать\n", num + 1);
          putDownForks();
          Thread.sleep(thinkingTime);
        } catch (InterruptedException ignored) {
        }
      } else if (!isWaiting) {
        out.printf("\033[0;3" + (num + 1) + "mФилософ номер %d ждет своих вилок\n", num + 1);
        isWaiting = true;
      }
    }

  }

  @Override
  public String toString() {
    return String.format("Философ номер %d, его вилки: %d %d",
        num + 1,
        philoForks.get("left") + 1,
        philoForks.get("right") + 1);
  }
}
