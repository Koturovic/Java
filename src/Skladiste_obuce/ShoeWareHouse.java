package Skladiste_obuce;

import Skladiste_obuce.Order;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWareHouse {
    private List<Order> shippingItems;
    private final ExecutorService fulfillmentService;
    public final static String[] PRODUCT_LIST = {"Running Shoes", "Sandals", "Boots", "Slipers","High Tops"};
    public ShoeWareHouse(){
        this.shippingItems = new ArrayList<>();
        fulfillmentService = Executors.newFixedThreadPool(3);
    }
    public void shutDown(){
        fulfillmentService.shutdown();
    }
    public synchronized void reciveOrder(Order item){
        while(shippingItems.size()>20){
            try{
                wait();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        shippingItems.add(item);
        System.out.println(Thread.currentThread().getName() + "Incoming:" + item);
        fulfillmentService.submit(this::fulfillOrder);
        notifyAll(); // make sure to update other threads..

    }

    public synchronized Order fulfillOrder(){
        while (shippingItems.isEmpty()){
            try{
                wait();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        Order item = shippingItems.remove(0);
        System.out.println(Thread.currentThread().getName() + "FulFilled" + item);
        notifyAll();
        return item;
    }
}
