package org.saikrishna.rao.learning.twitterdemo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TweetDTO {

    private Long id;
    private String text;
    @JsonAlias({"created_at"})
    private ZonedDateTime createdAt;
    private String source;
}
