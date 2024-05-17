package com.javaweb.converter;

import com.javaweb.model.dto.RestaurantDto;
import com.javaweb.model.dto.RestaurantSignUpDto;
import com.javaweb.model.dto.RestaurantUpdateDto;
import com.javaweb.model.entity.FeedbackEntity;
import com.javaweb.model.entity.RestaurantEntity;
import com.javaweb.model.request.RestaurantSearchRequest;
import org.apache.commons.collections4.MapUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RestaurantConverter {

    @Autowired
    ModelMapper modelMapper;

    public RestaurantDto entityToDto(RestaurantEntity restaurantEntity, Integer customerPostcode) {
        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);
        List<FeedbackEntity> feedbackEntityList = restaurantEntity.getFeedbackEntityList();

        if (feedbackEntityList != null && !feedbackEntityList.isEmpty()) {
            double avgStars = feedbackEntityList.stream()
                    .mapToInt(FeedbackEntity::getStars)
                    .average()
                    .orElse(0);
            restaurantDto.setStars(avgStars);
        } else {
            restaurantDto.setStars(0.0);
        }

        Integer distance = Math.abs(restaurantEntity.getPostCode() - customerPostcode);
        restaurantDto.setDistance(distance);
        restaurantDto.setRestaurantId(restaurantEntity.getId());
        return restaurantDto;
    }

    public RestaurantSearchRequest paramsToRequest(Map<String, Object> params) {
        return RestaurantSearchRequest.builder()
                .keyword(MapUtils.getString(params, "keyword", ""))
                .category(MapUtils.getString(params, "category", ""))
                .distanceFrom(MapUtils.getInteger(params, "distanceFrom", 0))
                .distanceTo(MapUtils.getInteger(params, "distanceTo", Integer.MAX_VALUE))
                .ratingFrom(MapUtils.getDouble(params, "ratingFrom", 0.0))
                .ratingTo(MapUtils.getDouble(params, "ratingTo", Double.MAX_VALUE))
                .build();
    }

    public RestaurantEntity signUpDtoToEntity(RestaurantSignUpDto signUpDto) {
        return modelMapper.map(signUpDto, RestaurantEntity.class);
    }

    public RestaurantEntity updateDtoToEntity(RestaurantUpdateDto restaurantUpdateDto) {
        return modelMapper.map(restaurantUpdateDto, RestaurantEntity.class);
    }
}