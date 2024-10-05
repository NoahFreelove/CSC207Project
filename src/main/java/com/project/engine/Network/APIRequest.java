package com.project.engine.Network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class APIRequest {

    private final APIResponse responseCallback;

    private final JSONObject response;

    /**
     * Constructor for the APIRequest class. This is asynchronous and will call the onResponse method
     * of the responseCallback.
     * Note: If main exits before the response is received, the response will not be handled.
     * Use synchronous requests (the other constructor) if you need to ensure the response is received in a
     * specific order.
     * @param URL The URL of the API endpoint.
     * @param method The HTTP method to use for the request. Only supports GET, POST, and DELETE
     * @param queries A hashmap of query parameters to include in the request: ?query=value&query2=value2 etc.
     */
    public APIRequest(APIResponse responseCallback, String URL, APIMethod method, HashMap<String, String> queries) {
        this.responseCallback = responseCallback;
        this.response = null;

        HttpClient client = HttpClient.newHttpClient();

        // Append queries to the URL
        URL += genQueries(queries);

        // Create an HTTP request to the API
        HttpRequest request = getHttpRequest(URL, method);

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::handleResponse);
    }

    /**
     * Constructor for the APIRequest class. This is synchronous and will return the response as a JSONObject
     * @param URL The URL of the API endpoint.
     * @param method The HTTP method to use for the request. Only supports GET, POST, and DELETE
     * @param queries A hashmap of query parameters to include in the request: ?query=value&query2=value2 etc.
     */
    public APIRequest(String URL, APIMethod method, HashMap<String, String> queries) {
        this.responseCallback = null;
        HttpClient client = HttpClient.newHttpClient();

        // Append queries to the URL
        URL += genQueries(queries);

        // Create an HTTP request to the API
        HttpRequest request = getHttpRequest(URL, method);

        JSONObject response = null;
        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            response = new JSONObject(res.body());
        }
        catch (Exception e){
            System.err.println("Failed to send request: " + e.getMessage() + "\nFor URL: " + URL);
        }
        this.response = response;
    }

    private String genQueries(HashMap<String, String> queries) {
        StringBuilder query = new StringBuilder();
        if (queries.isEmpty()) {
            return "";
        }
        query.append("?");
        for (String key : queries.keySet()) {
            query.append(key).append("=").append(queries.get(key)).append("&");
        }
        return query.toString();
    }

    private HttpRequest getHttpRequest(String URL, @NotNull APIMethod method) {
        HttpRequest req = null;
        switch (method) {
            case GET:
                req = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();
                break;
            case POST:
                req = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
                break;
            case DELETE:
                req = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .DELETE()
                    .build();
                break;
        }
        return req;
    }

    public JSONObject getResponseDefaultIfNull() {
        return (response == null) ? new JSONObject() : response;
    }

    private void handleResponse(String responseBody) {
        responseCallback.onResponse(new JSONObject(responseBody));
    }

}
