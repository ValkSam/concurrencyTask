package com.concurrency.task6.complition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.allOf;

public class Main {
  private static int QUEUE_SIZE = 10;
  private static ExecutorService executorService = Executors.newFixedThreadPool(QUEUE_SIZE);


  public static void main(String[] args) throws Exception {
    List<Callable<Integer>> taskList = new ArrayList<>();

    for (int i = 0; i < QUEUE_SIZE; i++) {
      final int taskNumber = i;
      taskList.add(() -> {
        Thread.sleep(200 + new Random().nextInt(1000));
        System.out.println(taskNumber);
        return taskNumber;
      });
    }

    System.out.println("============= CompletionService ==");
    CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
    for (Callable<Integer> task : taskList) {
      completionService.submit(task);
    }
    for (int i = 0; i < QUEUE_SIZE; i++) {
      Future<Integer> future = completionService.take(); //получаем по одному завершенный future. Ждем пока не завершится какой-либо future
      int r = future.get(); //берем из него результат. на этом get не ждем так future уже завершен - ждали на take()
      System.out.println(">> " + r);
    }

    System.out.println("============= CompletableFuture.allOf ==");
//для каждой таски создаем CompletableFuture и все их кладем в один CompletableFuture
    CompletableFuture<Void> allResults = allOf(
        taskList.stream()
            .map(e -> CompletableFuture.supplyAsync(() -> {
              try {
                return e.call();
              } catch (Exception ex) {
                return -1;
              }
            }, executorService))
            .toArray(CompletableFuture[]::new));

    allResults.get(); //тут ждем завершения всех. В отличие от предыдущего варианта , не надо знать сколько всего задач,
    // но зато не имеем доступ к результату каждой из задач

    System.out.println("============= CompletionStage ==");
//Создаем CompletableFuture , запуская асинхронную таску
//Таска вернет value или пробросит ошибку
    CompletableFuture completableFuture = CompletableFuture.supplyAsync(
        (Supplier<Integer>) () -> {
          int value = 1000;
          try {
            Thread.sleep(value);
            int t = 1 / 0;
          } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
          }
          return value;
        }, executorService);
//создаем хендлер , который должен перехватить отработку таски.
//представляет из себя BiFunction
//на входе он получает результат из таски и ошибку (null, если ее не будет)
//на выходе вернет Строку для случая ошибки и для случая успеха
    BiFunction<Integer, Throwable, String> executionHandler = (result, exception) -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (exception == null) {
        return "OK: " + result;
      } else {
        return "BAD: " + exception.getMessage();
      }
    };
    //передаем хендлер completableFuture и получаем CompletionStage для асинхронной задачи, которая выполняет код хендлера
    CompletionStage stageOne = completableFuture.handleAsync(executionHandler);
    //Не ждем выполнения хендлера, а вешаем его в асинхронное выполнение (thenApply)
    CompletionStage stageTwo = stageOne.thenApply((Function<String, String>) (message) -> {
      System.out.println(message);
      return "DONE";
    });
    System.out.println("============= END ==");
    stageTwo.thenAccept((finalMessage) -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(finalMessage);
    });
    System.out.println("============= ALL DONE ==");
  }

}
