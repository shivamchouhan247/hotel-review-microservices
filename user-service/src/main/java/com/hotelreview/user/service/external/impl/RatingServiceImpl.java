package com.hotelreview.user.service.external.impl;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.entity.Rating;
import com.hotelreview.user.service.external.RatingService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.JsonParser;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(RatingServiceImpl.class);


    private final String baseUrl;

    public RatingServiceImpl(RestTemplate restTemplate, @Value("${rating.service.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Rating> getUserRatings(String userId) throws Exception {
        LOGGER.info("Processing user rating for user id: {}", userId);
        try {
            String url = baseUrl + "/api/v1/ratings/users/" + userId;

            LOGGER.info("Rating request: {}", url);
            ResponseEntity<ApiResponse> apiResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    ApiResponse.class
            );
            ApiResponse response = apiResponse.getBody();
            LOGGER.info("Rating api Response: {}", response);

            if (response.getStatus().equals("SUCCESS")) {
                return objectMapper.convertValue(
                        response.getData(),
                        new TypeReference<List<Rating>>() {
                        }
                );
            }
            return List.of();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("Failed to fetch rating for userId= {}", userId);
            throw new Exception("unable to fetch user ratings");
        }

    }
}
