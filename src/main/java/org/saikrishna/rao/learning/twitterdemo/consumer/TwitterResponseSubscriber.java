package org.saikrishna.rao.learning.twitterdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.saikrishna.rao.learning.twitterdemo.dto.TweetDTO;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitterResponseSubscriber implements Subscriber<TweetDTO> {


    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(TweetDTO tweetDTO) {
        log.info("-> {}", tweetDTO.getText());
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error Occurred when consuming Twitter Data " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("Finished consuming Twitter response");
    }
}
