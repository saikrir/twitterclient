package org.saikrishna.rao.learning.twitterdemo.client;

import lombok.extern.slf4j.Slf4j;
import org.saikrishna.rao.learning.twitterdemo.dto.TweetDTO;
import org.saikrishna.rao.learning.twitterdemo.dto.TwitterResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class TwitterClient {

    @Autowired
    WebClient webClient;

    @Value("#{environment.twitter_token}")
    String token;

    public Mono<TwitterResponseDTO> getTweetResponse(String topic) {
        log.info("Getting Tweets for {}", topic);
        return webClient
                .get()
                .uri("/2/tweets/search/recent?query={query}&tweet.fields=author_id,source,created_at", topic)
                .headers(httpHeaders -> httpHeaders.add("Authorization", String.format("Bearer %s", token)))
                .retrieve()
                .bodyToMono(TwitterResponseDTO.class)
                .timeout(Duration.ofSeconds(30), Mono.just(new TwitterResponseDTO()))
                .doFirst(() -> log.info("Got Response"));
    }


    public Flux<TweetDTO> getTweets(String topic) {
        log.info("Getting Tweets for {}", topic);

        return webClient
                .get()
                .uri("/2/tweets/search/recent?query={query}&tweet.fields=author_id,source,created_at", topic)
                .headers(httpHeaders -> httpHeaders.add("Authorization", String.format("Bearer %s", token)))
                .retrieve()
                .bodyToMono(TwitterResponseDTO.class)
                .timeout(Duration.ofSeconds(30), Mono.just(new TwitterResponseDTO()))
                .doFirst(() -> log.info("Got Response"))
                .map(twitterResponseDTO -> twitterResponseDTO.getTweets())
                .flatMapIterable(tweetDTOS -> tweetDTOS)
                .limitRate(10)
                .delayElements(Duration.ofSeconds(1));

    }
}
