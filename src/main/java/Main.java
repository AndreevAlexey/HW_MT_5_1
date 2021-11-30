import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int port = 30000;
        String host = "127.0.0.1";
        Server server = new Server(port);
        Client client = new Client(host, port);
        // поток сервера
        Thread serverThread = new Thread(null, server);
        serverThread.start();
        // выполнение клиента
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in))
        {
            String msg;
            while (true) {
                System.out.println("Введите номер числа последовательности Фибоначчи:");
                msg = scanner.nextLine();
                out.println(msg);
                if ("end".equals(msg)) break;
                System.out.println("Результат: " + in.readLine());
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        serverThread.interrupt();
    }
}
