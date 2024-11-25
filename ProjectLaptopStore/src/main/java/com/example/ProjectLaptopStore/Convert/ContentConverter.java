package com.example.ProjectLaptopStore.Convert;

import com.example.ProjectLaptopStore.DTO.ProductDetailDTO;
import com.example.ProjectLaptopStore.Entity.ContentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.ContentModel;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
public class ContentConverter {
    @Autowired
    private ModelMapper modelMapper;
    public ContentEntity toContentEntity(ProductDetailDTO productNew, ContentEntity contentEntity) {
        StringBuilder contentBuilder = new StringBuilder();

        // Danh sách các trường cần loại trừ
        List<String> excludedFields = Arrays.asList("productId", "supplierId", "productDescriptionId");
        //Sử dụng Reflection
        // Lặp qua các trường trong ProductDetailDTO
        Field[] fields = ProductDetailDTO.class.getDeclaredFields();
        for (Field field : fields) {
            if (excludedFields.contains(field.getName())) {
                continue; // Bỏ qua các trường trong danh sách loại trừ
            }
            field.setAccessible(true); // Đảm bảo có thể truy cập
            try {
                Object value = field.get(productNew);
                if (value != null) {
                    contentBuilder.append(value.toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Xử lý lỗi nếu không truy cập được trường
            }
        }

        contentEntity.setContent(contentBuilder.toString());
        return contentEntity;
    }
}
