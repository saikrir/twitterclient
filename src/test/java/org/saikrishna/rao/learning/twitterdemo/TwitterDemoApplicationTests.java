package org.saikrishna.rao.learning.twitterdemo;

import org.junit.jupiter.api.Test;
import org.saikrishna.rao.learning.twitterdemo.client.TwitterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class TwitterDemoApplicationTests {


    @Autowired
    TwitterClient twitterClient;

    @Test
    void contextLoads() throws InterruptedException {
        twitterClient.getTweets();
        //Thread.sleep(10000);
    }

}
