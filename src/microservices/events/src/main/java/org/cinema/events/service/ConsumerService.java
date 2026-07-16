package org.cinema.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.cinema.events.model.MovieEventRequest;
import org.cinema.events.model.PaymentEventRequest;
import org.cinema.events.model.UserEventRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = {
            "movie-events-topic",
            "user-events-topic",
            "payment-events-topic"
    })
    public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {

        switch (record.topic()) {
            case "movie-events" -> {
                MovieEventRequest event =
                        objectMapper.readValue(record.value(), MovieEventRequest.class);
                log.info("Movie event: {}", event);
            }

            case "user-events" -> {
                UserEventRequest event =
                        objectMapper.readValue(record.value(), UserEventRequest.class);
                log.info("User event: {}", event);
            }

            case "payment-events" -> {
                PaymentEventRequest event =
                        objectMapper.readValue(record.value(), PaymentEventRequest.class);
                log.info("Payment event: {}", event);
            }

            default -> log.warn("Unknown topic {}", record.topic());
        }
    }

}
