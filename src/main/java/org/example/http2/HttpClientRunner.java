package org.example.http2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import static java.net.http.HttpRequest.BodyPublishers.ofFile;

public class HttpClientRunner {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082"))
                .header("content-type", "application/json")
                .POST(ofFile(Path.of("src/main/resources/example.json")))
                .build();


        var response3 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String body = response3.get().body();
        System.out.println(body);



    }


}
