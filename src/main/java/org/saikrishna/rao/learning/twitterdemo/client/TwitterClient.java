package org.saikrishna.rao.learning.twitterdemo.client;

import lombok.extern.slf4j.Slf4j;
import org.saikrishna.rao.learning.twitterdemo.dto.TweetDTO;
import org.saikrishna.rao.learning.twitterdemo.dto.TwitterResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class TwitterClient {

    @Autowired
    WebClient webClient;

    @Value("#{environment.tweet_token}")
    String token;

    public void getTweets() {
        log.info("Getting Tweets");
        Mono<TwitterResponseDTO> sanAntonioTweets = webClient
                .get()
                .uri("/2/tweets/search/recent?query={query}&&tweet.fields=author_id,source,created_at", "Java")
                .headers(httpHeaders -> httpHeaders.add("Authorization", String.format("Bearer %s", token)))
                .retrieve()
                .bodyToMono(TwitterResponseDTO.class)
                .doOnNext(twitterResponseDTO -> log.info(" On Next -> {} ", twitterResponseDTO))
                .subscribeOn(Schedulers.boundedElastic());


        sanAntonioTweets.subscribe(tweetDTO -> log.info("Got Data {}", tweetDTO));

    }
}
