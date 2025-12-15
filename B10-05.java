// -------- Client.java --------
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 5000;

        // файл з рядками
        String filename = "input.txt";

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
             Scanner sc = new Scanner(System.in)) {

            // 1) вводимо regex
            System.out.print("Enter regex pattern: ");
            String regex = sc.nextLine();
            out.println(regex);

            // 2) читаємо рядки з файлу і надсилаємо на сервер
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                out.println(line);
                String answer = in.readLine(); // YES/NO
                System.out.println("Line: " + line);
                System.out.println("Server ответ: " + answer);
                System.out.println();
            }

            // 3) сигнал завершення
            out.println("END");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
