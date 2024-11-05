package com.example.ProjectLaptopStore.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Promotions_DisplayPromotionsDTO {
    String promotionName;
    BigDecimal discountPercentage;
    String promotionDetails;
}
