package executor_primer;

import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        BlockingQueue<Future<Boolean>> taskQueue = new LinkedBlockingQueue<>();
        Random random = new Random();
        boolean stop = false;

        while (!stop){
            try{
                Thread.sleep(2000); // waitt 2 sec;
                int randomNum = random.nextInt(50) +1;
                Future<Boolean> task = executorService.submit(new SquareTask(randomNum, "SquareThread-" + (taskQueue.size() + 1)));
                taskQueue.put(task);

                Future<Boolean> completedTask = taskQueue.take();
                if(completedTask.get()){
                    stop = true;
                    executorService.shutdown();
                    System.out.println("Task generation stopped..");

                }

            }catch (InterruptedException| ExecutionException e){
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
