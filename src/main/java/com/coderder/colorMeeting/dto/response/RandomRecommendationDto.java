package com.coderder.colorMeeting.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RandomRecommendationDto {
    private String start;
    private String end;
    private List<String> memberNickNames;
}
