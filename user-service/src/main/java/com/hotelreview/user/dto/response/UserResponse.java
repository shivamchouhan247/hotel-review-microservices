package com.hotelreview.user.dto.response;

import com.hotelreview.user.entity.Rating;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String about;
    private Date creationDate;
    private List<Rating> ratings=new ArrayList<>();
}
