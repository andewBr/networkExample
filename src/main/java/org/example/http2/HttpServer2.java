package org.example.http2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer2 {
    private final int port;
    private final ExecutorService executorService;

    public HttpServer2(int port, int poolSize) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                var socket = serverSocket.accept();
                System.out.println("Socket accepted");
                executorService.submit(() -> processSocket(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processSocket(Socket socket) {
        try (socket;
             var inputStream = new DataInputStream(socket.getInputStream());
             var outputStream = new DataOutputStream(socket.getOutputStream())) {
//            Thread.sleep(1000);
            byte[] requestData = inputStream.readNBytes(600);
            System.out.println("===>>> this is string: " + new String(requestData));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestData);

            // Получаем значения из JSON
            int totalIncome = jsonNode.get("total_income").asInt();
            int totalTax = jsonNode.get("total_tax").asInt();
            int totalProfit = jsonNode.get("total_profit").asInt();

            // Формируем HTML-страницу с данными
            String htmlResponse = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                   <title>Salary</title>
                </head>
                <body>
                <table>
                   <tr>
                       <th>Total income</th>
                       <th>Total tax</th>
                       <th>Total profit</th>
                   </tr>
                   <tr>
                       <td>%d</td>
                       <td>%d</td>
                       <td>%d</td>
                   </tr>
                </table>
                </body>
                </html>
                """.formatted(totalIncome, totalTax, totalProfit);

            outputStream.write("""
                HTTP/1.1 200 OK
                content-type: text/html
                content-length: %s
                """.formatted(htmlResponse.getBytes().length).getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(htmlResponse.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}