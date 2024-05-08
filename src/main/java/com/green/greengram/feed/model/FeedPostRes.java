package com.green.greengram.feed.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostRes {
    private long feedId;
    private List<String> pics;
}
