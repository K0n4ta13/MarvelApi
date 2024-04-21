package com.api.marvel.service.impl;

import com.api.marvel.exception.ApiErrorException;
import com.api.marvel.service.HttpClientService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@Service
public class RestTemplateService implements HttpClientService {

    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T doGet(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(getHeaders());
        System.out.println("URL: " + finalUrl);
        ResponseEntity<T> response = this.restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String message = String.format("Error consuming endpoint [ {} - {} ], response code is: {}",
                    HttpMethod.GET, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

    private static String buildFinalUrl(String endpoint, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);

        if (Objects.nonNull(queryParams)) {
            //queryParams.forEach(builder::queryParam);
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        String finalUrl = builder.build().toUriString();
        return finalUrl;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public <T, R> T doPost(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, getHeaders());
        ResponseEntity<T> response = this.restTemplate.exchange(finalUrl, HttpMethod.POST, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getStatusCode().value() != HttpStatus.CREATED.value()) {
            String message = String.format("Error consuming endpoint [ {} - {} ], response code is: {}",
                    HttpMethod.POST, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

    @Override
    public <T, R> T doPut(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, getHeaders());
        ResponseEntity<T> response = this.restTemplate.exchange(finalUrl, HttpMethod.PUT, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String message = String.format("Error consuming endpoint [ {} - {} ], response code is: {}",
                    HttpMethod.PUT, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

    @Override
    public <T> T doDelete(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(getHeaders());
        ResponseEntity<T> response = this.restTemplate.exchange(finalUrl, HttpMethod.DELETE, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String message = String.format("Error consuming endpoint [ {} - {} ], response code is: {}",
                    HttpMethod.DELETE, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }
}
