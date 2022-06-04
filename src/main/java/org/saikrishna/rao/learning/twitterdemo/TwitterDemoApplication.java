package org.saikrishna.rao.learning.twitterdemo;

import org.saikrishna.rao.learning.twitterdemo.client.TwitterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterDemoApplication implements CommandLineRunner {
	@Autowired
	TwitterClient twitterClient;

	public static void main(String[] args) {
		SpringApplication.run(TwitterDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		twitterClient.getTweets();
	}
}
