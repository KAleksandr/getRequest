package mythread;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


class MyThread implements Runnable {
    Config config = new Config();

    public void run() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(config.getWebUrl()).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            //connection.setConnectTimeout(250);
            //connection.setReadTimeout(250);

            connection.connect();
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
                    System.out.println("fail");
                }

                //System.out.println(sb.toString());
                System.out.println("Successful");
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

    }
}
