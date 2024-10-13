package com.example.ProjectLaptopStore.Entity.Enum;

import java.util.Map;
import java.util.TreeMap;

public enum FindProductsByPriceRange_Enum {
    BELOW_10(0, 10, "Dưới 10 triệu"),
    BETWEEN_10_AND_15(10, 15, "Từ 10 - 15 triệu"),
    BETWEEN_15_AND_20(15, 20, "Từ 15 - 20 triệu"),
    BETWEEN_20_AND_25(20, 25, "Từ 20 - 25 triệu"),
    BETWEEN_25_AND_30(25, 30, "Từ 25 - 30 triệu"),
    ABOVE_30(30, null, "Trên 30 triệu");  // null ở đây có nghĩa là không có giới hạn trên

    private final Integer minPrice;  // Giá trị tối thiểu
    private final Integer maxPrice;  // Giá trị tối đa (null nếu không có giới hạn)
    private final String description;  // Mô tả cho checkbox

    // Constructor
    FindProductsByPriceRange_Enum(Integer minPrice, Integer maxPrice, String description) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.description = description;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public String getDescription() {
        return description;
    }

    // Phương thức trả về Map<Integer[], String> (mảng Integer lưu giá trị min-max)
    public static Map<Integer[], String> getPriceRanges() {
        Map<Integer[], String> priceRanges = new TreeMap<>((a, b) -> a[0].compareTo(b[0]));
        for (FindProductsByPriceRange_Enum range : FindProductsByPriceRange_Enum.values()) {
            priceRanges.put(new Integer[]{range.getMinPrice(), range.getMaxPrice()}, range.getDescription());
        }
        return priceRanges;
    }
}
