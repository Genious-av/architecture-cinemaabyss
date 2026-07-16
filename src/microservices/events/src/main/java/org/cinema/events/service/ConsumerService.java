package org.cinema.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.cinema.events.model.MovieEventRequest;
import org.cinema.events.model.PaymentEventRequest;
import org.cinema.events.model.UserEventRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {
            "movie-events-topic",
            "user-events-topic",
            "payment-events-topic"
    })
    public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {

        switch (record.topic()) {
            case "movie-events-topic" -> {
                MovieEventRequest event =
                        objectMapper.readValue(record.value(), MovieEventRequest.class);
                log.info("Movie event: {}", event);
            }

            case "user-events-topic" -> {
                UserEventRequest event =
                        objectMapper.readValue(record.value(), UserEventRequest.class);
                log.info("User event: {}", event);
            }

            case "payment-events-topic" -> {
                PaymentEventRequest event =
                        objectMapper.readValue(record.value(), PaymentEventRequest.class);
                log.info("Payment event: {}", event);
            }

            default -> log.warn("Unknown topic {}", record.topic());
        }
    }

}
