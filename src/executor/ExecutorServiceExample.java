package executor;

import mythread.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
    private SimpleDateFormat sdf = null;

    private ExecutorServiceExample() {
        sdf = new SimpleDateFormat("HH:mm:ss.S");

        ExecutorService executor;
        executor = Executors.newFixedThreadPool(300);

        System.out.println("Threads are run");
        int COUNT = new mythread.Config().getNumber();
        for (int i = 0; i < COUNT; i++) {
            executor.execute(new MyThread(new CountDownLatch(COUNT), "Thread." + i));

        }
        //System.out.println("Потоки запущені.");

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Threads were complete");
    }

    //-------------------------------------------------
    private void printMessage(final String templ) {
        String text = sdf.format(new Date()) + " : " + templ;
        System.out.println(text);
    }

    //-------------------------------------------------
    class MyThread implements Runnable {
        String name;
        CountDownLatch latch;
        String web_url = new Config().getWebUrl();

        MyThread(CountDownLatch count, String n) {
            latch = count;
            name = n;
            new Thread(this);
        }

        public void run() {
            printMessage(name);
            latch.countDown();
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(web_url).openConnection();
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                //connection.setConnectTimeout(250);
                //connection.setReadTimeout(250);
                try {
                    connection.connect();
                } catch (ConnectException e) {
                    e.printStackTrace();
                }


                StringBuilder sb = new StringBuilder();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;
                    try {
                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                            sb.append("\n");
                        }
                    } catch (IOException e) {
                        System.out.println("Fail");
                    }

                    //System.out.println(sb.toString());
                    System.out.println("Successful!");
                } else {
                    System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                }
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }


            printMessage(name + " completed");

        }
    }

    //-------------------------------------------------
    public static void main(String args[]) {
        new ExecutorServiceExample();
    }
}
