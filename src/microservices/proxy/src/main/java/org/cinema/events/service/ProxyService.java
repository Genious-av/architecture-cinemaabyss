package org.cinema.events.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Enumeration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyService {

    private final RestClient monolithRestClient;

    public ResponseEntity<byte[]> forward(
            HttpServletRequest request,
            byte[] body) {

        String uri = buildUri(request);
        log.info("Proxy request: {} {}", request.getMethod(), uri);

        RestClient.RequestBodySpec requestSpec = monolithRestClient
                .method(HttpMethod.valueOf(request.getMethod()))
                .uri(uri);

        copyHeaders(request, requestSpec);

        if (body != null && body.length > 0) {
            requestSpec.body(body);
        }

        return requestSpec
                .retrieve()
                .toEntity(byte[].class);
    }


    private String buildUri(HttpServletRequest request) {
        String query = request.getQueryString();

        return query == null
                ? request.getRequestURI()
                : request.getRequestURI() + "?" + query;
    }


    private void copyHeaders(
            HttpServletRequest request,
            RestClient.RequestBodySpec spec) {

        Enumeration<String> headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();

            // Эти заголовки нельзя прокидывать напрямую
            if (isHopByHopHeader(headerName)) {
                continue;
            }

            Enumeration<String> values = request.getHeaders(headerName);

            while (values.hasMoreElements()) {
                spec.header(headerName, values.nextElement());
            }
        }
    }


    private boolean isHopByHopHeader(String header) {
        return List.of(
                "host",
                "content-length",
                "transfer-encoding",
                "connection"
        ).contains(header.toLowerCase());
    }
}
