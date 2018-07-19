package mythread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {
    private int number;
    private String webUrl;
    private Scanner scanner;

    {
        try {
            scanner = new Scanner(new File("config.txt"));
            number = scanner.nextInt();
            webUrl = scanner.next();
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getNumber() {
        return number;
    }

    public String getWebUrl() {
        return webUrl;
    }
}


