package org.saikrishna.rao.learning.twitterdemo;

import lombok.SneakyThrows;
import org.saikrishna.rao.learning.twitterdemo.client.TwitterClient;
import org.saikrishna.rao.learning.twitterdemo.consumer.TwitterResponseSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class TwitterDemoApplication implements CommandLineRunner {
    @Autowired
    TwitterClient twitterClient;

    @Autowired
    TwitterResponseSubscriber twitterResponseSubscriber;

    public static void main(String[] args) {
        SpringApplication.run(TwitterDemoApplication.class, args);
    }

    @Override
    @SneakyThrows
    public void run(String... args) throws Exception {
        twitterClient.getTweets("Johnny Depp")
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(twitterResponseSubscriber);

        Thread.sleep(10000);
    }
}
