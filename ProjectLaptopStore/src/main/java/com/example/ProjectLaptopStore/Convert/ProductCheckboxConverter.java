package com.example.ProjectLaptopStore.Convert;

import com.example.ProjectLaptopStore.DTO.Product_ProductSearchCheckBoxDTO;
import com.example.ProjectLaptopStore.Util.MapUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductCheckboxConverter {
    public Product_ProductSearchCheckBoxDTO toProductCheckboxBuilder(Map<String,Object> params){
        Product_ProductSearchCheckBoxDTO productProductSearchCheckBoxDTO = Product_ProductSearchCheckBoxDTO.builder()
                .idBrand(MapUtil.getObject(params,"idBrand",Integer.class))
                .price(MapUtil.getObject(params,"price",String.class))
                .cpu(MapUtil.getObject(params,"cpu", String.class))
                .ram(MapUtil.getObject(params,"ram",Integer.class))
                .hardDrive(MapUtil.getObject(params,"hardDrive", String.class))
                .demand(MapUtil.getObject(params,"demand", String.class))
                .screenSize(MapUtil.getObject(params,"screenSize",String.class))
                .build();
        return productProductSearchCheckBoxDTO;
    }
}
