package com.hackerrank.sample.clients;

import com.hackerrank.sample.models.hackerrank.Data;
import com.hackerrank.sample.models.hackerrank.Response;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HackerrankAPIClient {

    private final RestTemplate restTemplate;

    public HackerrankAPIClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Stream<Data> invokeAPI(String url) {
        Response resp = invoke(String.format(url, 1));
        return Stream.concat(Stream.of(resp), IntStream.rangeClosed(2, resp.getTotalPages())
                        .parallel()
                        .mapToObj(i -> String.format(url, i))
                        .map(this::invoke))
                .map(r -> r.getData())
                .flatMap(List::stream);
    }

    private Response invoke(String url) {
        return this.restTemplate.getForEntity(url, Response.class).getBody();
    }

}
