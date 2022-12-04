package com.lab8;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiningPhilosophers {


  public static int philosophersCount = 5;

  ArrayList<Philosopher> philosophers;


  DiningPhilosophers(int eatTime){
    philosophers = IntStream.range(0, philosophersCount).
        mapToObj((x) -> (new Philosopher(x, eatTime))).collect(
            Collectors.toCollection(ArrayList<Philosopher>::new));

  }

  public void menu(){
    philosophers.forEach(Thread::start);
  }

}