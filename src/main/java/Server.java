import java.io.*;
import java.net.*;

public class Server implements Runnable {
    private int port;
    // конструткор
    public Server(int port) {
        this.port = port;
    }
    // получить порт
    public int getPort() {
        return port;
    }
    // получить N элемент последовательности
    private static Long getFibonachi(int num) {
        Long rez = 0L;
        switch (num) {
            case 0:
                rez = 0L;
                break;
            case 1:
            case 2:
                rez = 1L;
                break;
            default:
                Long n_1 = 1L;Long n_2 = 0L;
                for(int i = 3; i <= num; i++) {
                    rez = n_1 + n_2;
                    n_2 = n_1;
                    n_1 = rez;
                }
                break;
        }
        return rez;
    }

    @Override
    public void run() {
        while(true) {
            if(Thread.interrupted()) break;
            try(ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String line, answer = "";
                int num;
                while ((line = in.readLine()) != null) {
                    // Выход если от клиента получили end
                    if (line.equals("end")) {
                        break;
                    }
                    try {
                        num = Integer.parseInt(line);
                        if(num < 0) answer = "Номер в числовой последовательности не может быть отрицательным!";
                        else answer = getFibonachi(num).toString();
                    } catch (Exception exp) {
                        answer = "Неверно указан номер в числовой последовательности Фибоначчи!";
                    }
                    // Пишем ответ
                    out.println(answer);
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }
    }
}
