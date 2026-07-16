package org.cinema.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.cinema.events.model.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    public RecordMetadata send(
            String topic,
            Object event
    ) throws ExecutionException, InterruptedException {

        return kafkaTemplate.send(
                topic,
                event
        )
                .get()
                .getRecordMetadata();
    }

    public EventResponse createPaymentEvent(PaymentEventRequest request) {

        try {
            RecordMetadata metadata = send("cinema-event-topic", request);
            Event payment = new Event(request.getPaymentId(), "payment", Instant.now(), mapper.writeValueAsString(request));
            return new EventResponse("success", metadata.partition(), metadata.offset(), payment);
        } catch (JsonProcessingException | ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public EventResponse createUserEvent(UserEventRequest request) {
        try {
            RecordMetadata metadata = send("cinema-event-topic", request);
            Event user = new Event(request.getUserId(), "user", Instant.now(), mapper.writeValueAsString(request));
            return new EventResponse("success", metadata.partition(), metadata.offset(), user);
        } catch (JsonProcessingException | ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public EventResponse createMovieEvent(MovieEventRequest request) {
        try {
            RecordMetadata metadata = send("cinema-event-topic", request);
            Event movie = new Event(request.getMovieId(), "movie", Instant.now(), mapper.writeValueAsString(request));
            return new EventResponse("success", metadata.partition(), metadata.offset(), movie);
        } catch (JsonProcessingException | ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
