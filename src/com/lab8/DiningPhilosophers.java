package com.lab8;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс, в котором производится обед философов
 */
public class DiningPhilosophers {


  /**
   * Количество философов, которые могут приступить к пище
   */
  public static int philosophersCount = 5;

  /**
   * Философы
   */
  ArrayList<Philosopher> philosophers;


  /**
   * Конструктор класса
   *
   * @param eatTime время приема пищи
   */
  DiningPhilosophers(int eatTime){
    philosophers = IntStream.range(0, philosophersCount).
        mapToObj((x) -> (new Philosopher(x, eatTime))).collect(
            Collectors.toCollection(ArrayList<Philosopher>::new));

  }

  /**
   * Метод запуска всех философов
   */
  public void start(){
    philosophers.forEach(Thread::start);
  }

}