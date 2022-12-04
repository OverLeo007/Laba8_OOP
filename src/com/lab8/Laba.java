package com.lab8;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Класс для запуска программы
 */
public class Laba {

  public static void main(String[] args) {
    new UI().menu();
  }
}


class UI {


  /**
   * Поток вывода, поддерживающий русские символы
   */
  private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

  /**
   * Экземпляр класса содержащего методы ввода разных типов
   */
  private final Inputer inp = new Inputer();

  /**
   * Время обеда философа
   */
  int time;

  /**
   * Метод основного меню работы
   */
  public void menu() {

    int choice;
    do {

      out.println("""
          1. Запустить философов
          2. Выйти
          """);

      choice = inp.getInt();
      if (choice == 1) {
        try {
          time = getTime();
        } catch (NumberFormatException e) {
          continue;
        }
        new DiningPhilosophers(time).start();
      } else {
        if (choice != 2) {
          out.println("Некорректный ввод");
        }
      }
    }while (choice != 2);
  }


  /**
   * Метод получения времени обеда философа от пользователя,
   * принимает секунды
   *
   * @return время обеда философа в мс
   */
  private int getTime(){
    out.println("Введите время обеда философа в секундах");
    int philoTime = inp.getInt();
    if (philoTime < 1) {
      out.println("Некорректный ввод");
      throw new NumberFormatException();
    } else {
      return philoTime * 1000;
    }
  }
}