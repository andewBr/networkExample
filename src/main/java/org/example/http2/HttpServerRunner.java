package org.example.http2;


public class HttpServerRunner {

    public static void main(String[] args) {
        HttpServer2 httpServer = new HttpServer2(8082, 100);
        httpServer.run();
    }

}
