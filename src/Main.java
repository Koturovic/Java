import javax.swing.*;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.*;
import java.util.*;

record Order(long orderId, String item, int qty){};

public class Main {
    private static final Random random = new Random();
    public static void main(String[] args) {
        ShoeWareHouse wareHouse = new ShoeWareHouse();
        ExecutorService orderingServis = Executors.newCachedThreadPool();
        Callable<Order> orderingTask = ()->{
            {
                Order newOrder = generateOrder();
                try{
                    Thread.sleep(random.nextInt(500,5000));
                    wareHouse.reciveOrder(newOrder);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                return newOrder;
            }
        };
        List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
        try {
            orderingServis.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        orderingServis.shutdown();
        try {
            orderingServis.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        wareHouse.shutDown();


    }
    private static Order generateOrder(){
        return new Order(
                random.nextLong(100000, 999999),
                ShoeWareHouse.PRODUCT_LIST[random.nextInt(0, 5)],
                random.nextInt(1, 4));
    }
}