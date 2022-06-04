package org.saikrishna.rao.learning.twitterdemo.client;

import lombok.AllArgsConstructor;
import org.saikrishna.rao.learning.twitterdemo.dto.TweetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class TwitterClient {

    @Autowired
    WebClient webClient;

    @Value("#{environment.tweet_token}")
    String token;

    public void getTweets() {
        Flux<TweetDTO> san_antonio = webClient.get().uri("/2/tweets/search/recent?query={query}&&tweet.fields=author_id,source,created_at", "San Antonio")
                .retrieve().bodyToFlux(TweetDTO.class);

        san_antonio.doOnNext(tweetDTO -> System.out.println(tweetDTO));
    }
}
