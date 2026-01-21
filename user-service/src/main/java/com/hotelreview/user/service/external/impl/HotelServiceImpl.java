package com.hotelreview.user.service.external.impl;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.dto.request.HotelBatchRequest;
import com.hotelreview.user.entity.Hotel;
import com.hotelreview.user.service.external.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public HotelServiceImpl(RestTemplate restTemplate, @Value("${hotel.service.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Hotel> getBatchHotels(List<Integer> hotelIds) {
        LOGGER.info("Processing hotel batch request for hotel ids: {}", hotelIds);
        try {

            String url = baseUrl + "/api/v1/hotels/batch";
            HotelBatchRequest payload = new HotelBatchRequest(hotelIds);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.ALL));

            HttpEntity<HotelBatchRequest> entity = new HttpEntity<>(payload, headers);

            LOGGER.info("apiRequest: {}, {}", url, entity);
            ApiResponse apiResponse = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    ApiResponse.class
            ).getBody();

            LOGGER.info("apiResponse: {}", apiResponse);

            if ("SUCCESS".equals(apiResponse.getStatus())) {
                return objectMapper.convertValue(
                        apiResponse.getData(),
                        new TypeReference<List<Hotel>>() {
                        }
                );
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("failed to fetch batch hodel details");

        }
        return new ArrayList<>();

    }
}
