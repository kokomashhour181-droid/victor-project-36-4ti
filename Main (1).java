import java.util.concurrent.Semaphore;

class Machine extends Thread {

    static int[] counts = new int[3];

    static int total = 0;

   
    static Semaphore mutex = new Semaphore(1);

    int id;

    public Machine(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        int start = (id - 1) * 25 + 1;
        int end = id * 25;

        
        for (int number = start; number <= end; number++) {

            int category;

            
            if (number <= 30) {
                category = 0;  
            } 
            else if (number <= 70) {
                category = 1;  
            } 
            else {
                category = 2;   
            }

            try {
                mutex.acquire();

                counts[category]++;
                total++;

              
                

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
                mutex.release();
            }
        }
    }
}


public class Main {

    public static void main(String[] args) {

        
        Machine[] threads = new Machine[4];

        for (int i = 0; i < 4; i++) {

            threads[i] = new Machine(i + 1);

            threads[i].start();
        }

        for (int i = 0; i < 4; i++) {

            try {
                threads[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nSmall: " + Machine.counts[0]);
        System.out.println("Medium: " + Machine.counts[1]);
        System.out.println("Large: " + Machine.counts[2]);
        System.out.println("Total: " + Machine.total);
    }
}
