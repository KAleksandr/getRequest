package mythread;

public class MainMyThread {

    public static void main(String[] args) {
        Config config = new Config();
        Thread[] threads = new Thread[config.getNumber()];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyThread());
            System.out.println("Start thread " + i);
        }
        startThreads(threads);
        System.out.println("Ok");
    }

    private static void startThreads(Thread[] threads) {
        for (Thread t : threads)
            t.start();
    }

}



