package com.lab8;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс философа, наследованный от класса Thread
 */
public class Philosopher extends Thread {

  /**
   * Поток вывода
   */
  public static final PrintStream out = new PrintStream(System.out,
      true, StandardCharsets.UTF_8);

  /**
   * Общие вилки, для всех философов
   */
  private static final boolean[] forks = new boolean[DiningPhilosophers.philosophersCount];

  /**
   * Номер текущего философа
   */
  private final int num;
  /**
   * Вилки, которыми пользуется текущий философ
   */
  private final HashMap<String, Integer> philoForks = new HashMap<>();

  /**
   * Время, которое ест философ
   */
  private final int eatTime;


  /**
   * Конструктор экземпляра философа
   *
   * @param num номер философа
   * @param eatTime сколько ест философ
   */
  Philosopher(int num, int eatTime) {
    this.num = num;
    philoForks.put("left", num - 1 < 0 ? forks.length - 1 : num - 1);
    philoForks.put("right", num);
    this.eatTime = eatTime;
    Arrays.fill(forks, true);
  }


  /**
   * Метод получения случайного числа в заданном диапазоне
   *
   * @param min минимальное значение
   * @param max максимальное значение
   * @return случайное число, находящиеся между min и max
   */
  public static int randint(int min, int max) {
    max -= min;
    return (int) (Math.random() * ++max) + min;
  }

  /**
   * Синхронный метод проверки доступности вилок с заданными номерами
   *
   * @return true если вилки можно взять, false если хотя бы одна из них занята
   */
  private synchronized boolean isForksAvailable() {
    return forks[philoForks.get("left")] && forks[philoForks.get("right")];
  }

  /**
   * Метод взятия вилок определенным философом
   */
  private synchronized void pickUpForks() {
    forks[philoForks.get("left")] = false;
    forks[philoForks.get("right")] = false;
  }

  /**
   * Метод откладывания вилок определенными философом
   */
  private synchronized void putDownForks() {
    forks[philoForks.get("left")] = true;
    forks[philoForks.get("right")] = true;
  }

  /**
   * Метод, вызываемый тредом при начале его работы
   */
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

  /**
   * @return строковое представления философа, с его номером и присвоенными ему вилками
   */
  @Override
  public String toString() {
    return String.format("Философ номер %d, его вилки: %d %d",
        num + 1,
        philoForks.get("left") + 1,
        philoForks.get("right") + 1);
  }
}
