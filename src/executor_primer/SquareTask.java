package executor_primer;

import java.util.concurrent.Callable;
/*
Napravi program koji koristi ExecutorService sa 3 niti. Glavni program treba da svakih 2
sekunde generiše slučajni broj između 1 i 50 i stavi ga u red zadataka.
Svaka nit treba da uzme broj iz reda,
izračuna kvadrat tog broja, i ispiše na ekranu ime niti i kvadrat broja.
Program treba da prestane sa radom kada se u redu nađe broj koji je veći od 40.
*/
public class SquareTask implements Callable<Boolean> {
    private final int number;
    private final String threadName;

    public SquareTask(int number, String threadName){
        this.number =number;
        this.threadName = threadName;
    }

    @Override
    public Boolean call() {
        int square = number* number;
        System.out.println("Thread" + threadName + " -Number: " + number + " - Square: "+ square);
        return number > 40;
    }


}
