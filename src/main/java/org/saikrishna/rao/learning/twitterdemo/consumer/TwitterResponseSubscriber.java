package org.saikrishna.rao.learning.twitterdemo.consumer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.saikrishna.rao.learning.twitterdemo.dto.TweetDTO;
import org.saikrishna.rao.social.avro.TwitterMessage;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Component
@Slf4j
public class TwitterResponseSubscriber implements Subscriber<TweetDTO> {


    private final String TOPIC_NAME = "twitter.messages";

    private Properties properties = new Properties();
    private final KafkaProducer<String, TwitterMessage> kafkaProducer;

    TwitterResponseSubscriber() {
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "skrao-oracle-server:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://skrao-oracle-server:8081");
        kafkaProducer = new KafkaProducer<>(properties);
    }


    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(TweetDTO tweetDTO) {
        Future<RecordMetadata> send = kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, String.valueOf(tweetDTO.getId()), toMessage(tweetDTO)));
        log.info("Sent -> {}", tweetDTO.getText());

    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error Occurred when consuming Twitter Data " + throwable.getMessage());
    }


    protected TwitterMessage toMessage(TweetDTO tweetDTO) {
        return TwitterMessage.newBuilder().setCreatedAt(tweetDTO.getCreatedAt().toEpochSecond())
                .setId(tweetDTO.getId())
                .setText(tweetDTO.getText())
                .setSource(tweetDTO.getSource()).build();
    }

    @Override
    public void onComplete() {
        log.info("Finished consuming Twitter response");
    }
}
