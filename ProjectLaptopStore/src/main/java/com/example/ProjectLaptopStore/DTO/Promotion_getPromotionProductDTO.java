package com.example.ProjectLaptopStore.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Promotion_getPromotionProductDTO {
    String productName;
    String brand;
    int hasPromotion;
}
