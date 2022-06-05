package org.saikrishna.rao.learning.twitterdemo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class TwitterResponseDTO {
    @JsonAlias({"data"})
    List<TweetDTO> tweets;
}
