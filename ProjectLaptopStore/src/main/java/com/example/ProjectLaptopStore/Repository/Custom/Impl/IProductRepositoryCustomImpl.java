package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.Product_CreateProductDTO;
import com.example.ProjectLaptopStore.DTO.Product_FindTopPurchasedProductsDTO;
import com.example.ProjectLaptopStore.DTO.Product_UpdateProductDTO;
import com.example.ProjectLaptopStore.Entity.ProductDescriptionEntity;
import com.example.ProjectLaptopStore.Entity.ProductsEntity;
import com.example.ProjectLaptopStore.Entity.SuppliersEntity;
import com.example.ProjectLaptopStore.Repository.Custom.IProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//sử dụng JDBC để lấy dữ liệu
@Repository
@Transactional
public class IProductRepositoryCustomImpl implements IProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Product_FindTopPurchasedProductsDTO> findAllProductsWithTotalQuantityOrdered() {
//        String query = "SELECT p.ProductName, p.Brand, p.Model, p.Price, p.StockQuantity, p.Description, " +
//                "p.WarrantyPeriod, p.ImageURL, COALESCE(SUM(od.Quantity), 0) AS quantityOrdered " +
//                "FROM Products p " +
//                "LEFT JOIN OrderDetails od ON p.ProductID = od.ProductID " +
//                "GROUP BY p.ProductID " +
//                "ORDER BY quantityOrdered DESC";
//                Query nativeQuery = entityManager.createNativeQuery(query, Product_FindTopPurchasedProductsDTO.class);
//        return nativeQuery.getResultList();

        //set tay cho toàn bộ trường của lớp DTO vì gặp lỗi 500, ko map dữ liệu được
        String query = "SELECT p.ProductName, p.Brand, p.Model, p.Price, p.StockQuantity, " +
                "p.WarrantyPeriod, p.ImageURL, COALESCE(SUM(od.Quantity), 0) AS quantityOrdered " +
                "FROM Products p " +
                "LEFT JOIN OrderDetails od ON p.ProductID = od.ProductID " +
                "GROUP BY p.ProductID " +
                "ORDER BY quantityOrdered DESC";
        Query nativeQuery = entityManager.createNativeQuery(query);// ko cần truyền DTO.class
        List<Object[]> results = nativeQuery.getResultList();

        //set thủ công
        List<Product_FindTopPurchasedProductsDTO> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            Product_FindTopPurchasedProductsDTO dto = new Product_FindTopPurchasedProductsDTO(
                    (String) result[0],  // productName
                    (String) result[1],  // brand
                    (String) result[2],  // model
                    (Float) result[3],   // price
                    (Integer) result[4], // stockQuantity
                    (Integer) result[5], // warrantyPeriod
                    (String) result[6],  // imageURL
                    ((Number) result[7]).longValue()  // quantityOrdered (SUM result)
            );
            dtoList.add(dto);
        }

        return dtoList;


    }
//
//    @Override
//    public void createProduct(Product_CreateProductDTO createProductDTO) {
//        ProductsEntity productsEntity = new ProductsEntity();
//        SuppliersEntity suppliersEntity = new SuppliersEntity();
//        suppliersEntity.setSupplierID(createProductDTO.getSupplierId());
//        productsEntity.setSupplier(suppliersEntity);
//        productsEntity.setProductName(createProductDTO.getProductName());
//        productsEntity.setBrand(createProductDTO.getProductBrand());
//        productsEntity.setModel(createProductDTO.getModel());
//        productsEntity.setPrice(createProductDTO.getPrice());
//        productsEntity.setStockQuantity(createProductDTO.getStockQuantity());
//        productsEntity.setWarrantyPeriod(createProductDTO.getWarrantyPeriod());
//        productsEntity.setReleaseDate(createProductDTO.getReleaseDate());
//        productsEntity.setImageURL(createProductDTO.getImageUrl());
//        entityManager.persist(productsEntity);
//        ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
//        productDescriptionEntity.setProduct(productsEntity);
//        productDescriptionEntity.setCpuCompany(createProductDTO.getCpuCompany());
//        productDescriptionEntity.setCpuTechnology(createProductDTO.getCpuTechnology());
//        productDescriptionEntity.setCpuType(createProductDTO.getCpuType());
//        productDescriptionEntity.setMinimumCPUspeed(createProductDTO.getMinimumCPUspeed());
//        productDescriptionEntity.setMaximunSpeed(createProductDTO.getMaximunSpeed());
//        productDescriptionEntity.setMultiplier(createProductDTO.getMultiplier());
//        productDescriptionEntity.setProcessorCache(createProductDTO.getProcessorCache());
//        productDescriptionEntity.setBrandCardOboard(createProductDTO.getBrandCardOboard());
//        productDescriptionEntity.setModelCardOboard(createProductDTO.getModelCardOboard());
//        productDescriptionEntity.setFullNameCardOboard(createProductDTO.getFullNameCardOboard());
//        productDescriptionEntity.setVgaBrand(createProductDTO.getVgaBrand());
//        productDescriptionEntity.setVgaFullName(createProductDTO.getVgaFullName());
//        productDescriptionEntity.setRamCapacity(createProductDTO.getRamCapacity());
//        productDescriptionEntity.setRamType(createProductDTO.getRamType());
//        productDescriptionEntity.setRamSpeed(createProductDTO.getRamSpeed());
//        productDescriptionEntity.setNumberOfRemovableSlots(createProductDTO.getNumberOfRemovableSlots());
//        productDescriptionEntity.setNumberOfOnboardRAM(createProductDTO.getNumberOfOnboardRAM());
//        productDescriptionEntity.setMaximumRAMSupport(createProductDTO.getMaximumRAMSupport());
//        productDescriptionEntity.setHardDriveType(createProductDTO.getHardDriveType());
//        productDescriptionEntity.setTotalSSDHDDSlots(createProductDTO.getTotalSSDHDDSlots());
//        productDescriptionEntity.setNumberOfSSDHDDSlotsRemaining(createProductDTO.getNumberOfSSDHDDSlotsRemaining());
//        productDescriptionEntity.setMaximumHardDriveUpgradeCapacity(createProductDTO.getMaximumHardDriveUpgradeCapacity());
//        productDescriptionEntity.setSsdType(createProductDTO.getSsdType());
//        productDescriptionEntity.setCapacity(createProductDTO.getCapacity());
//        productDescriptionEntity.setScreenSize(createProductDTO.getScreenSize());
//        productDescriptionEntity.setDisplayTechnology(createProductDTO.getDisplayTechnology());
//        productDescriptionEntity.setResolution(createProductDTO.getResolution());
//        productDescriptionEntity.setScreenType(createProductDTO.getScreenType());
//        productDescriptionEntity.setScanningFrequency(createProductDTO.getScanningFrequency());
//        productDescriptionEntity.setBasePlate(createProductDTO.getBasePlate());
//        productDescriptionEntity.setBrightness(createProductDTO.getBrightness());
//        productDescriptionEntity.setColorCoverage(createProductDTO.getColorCoverage());
//        productDescriptionEntity.setScreenRatio(createProductDTO.getScreenRatio());
//        productDescriptionEntity.setCommunicationPort(createProductDTO.getCommunicationPort());
//        productDescriptionEntity.setWifi(createProductDTO.getWifi());
//        productDescriptionEntity.setBluetooth(createProductDTO.getBluetooth());
//        productDescriptionEntity.setWebcam(createProductDTO.getWebcam());
//        productDescriptionEntity.setOs(createProductDTO.getOs());
//        productDescriptionEntity.setVersion(createProductDTO.getVersion());
//        productDescriptionEntity.setSecurity(createProductDTO.getSecurity());
//        productDescriptionEntity.setKeyboardType(createProductDTO.getKeyboardType());
//        productDescriptionEntity.setNumericKeypad(createProductDTO.isNumericKeypad());
//        productDescriptionEntity.setKeyboardLight(createProductDTO.getKeyboardLight());
//        productDescriptionEntity.setTouchPad(createProductDTO.getTouchPad());
//        productDescriptionEntity.setBatteryType(createProductDTO.getBatteryType());
//        productDescriptionEntity.setBatteryCapacity(createProductDTO.getBatteryCapacity());
//        productDescriptionEntity.setPowerSupply(createProductDTO.getPowerSupply());
//        productDescriptionEntity.setAccessoriesInTheBox(createProductDTO.getAccessoriesInTheBox());
//        productDescriptionEntity.setSize(createProductDTO.getSize());
//        productDescriptionEntity.setProductWeight(createProductDTO.getProductWeight());
//        productDescriptionEntity.setMaterial(createProductDTO.getMaterial());
//        productDescriptionEntity.setPn(createProductDTO.getPn());
//        productDescriptionEntity.setOrigin(createProductDTO.getOrigin());
//        productDescriptionEntity.setWarrantyPeriodMonths(createProductDTO.getWarrantyPeriodMonths());
//        productDescriptionEntity.setStorageInstructions(createProductDTO.getStorageInstructions());
//        productDescriptionEntity.setUserManual(createProductDTO.getUserManual());
//        productDescriptionEntity.setColor(createProductDTO.getColor());
//        entityManager.persist(productDescriptionEntity);
//    }
@Override
public void createProduct(Product_CreateProductDTO createProductDTO) {
    // Kiểm tra nếu nhà cung cấp (Supplier) đã tồn tại trong cơ sở dữ liệu
    SuppliersEntity suppliersEntity = entityManager.find(SuppliersEntity.class, createProductDTO.getSupplierId());

    // Nếu Supplier không tồn tại, có thể ném ra ngoại lệ hoặc xử lý khác
    if (suppliersEntity == null) {
        throw new EntityNotFoundException("Supplier with ID " + createProductDTO.getSupplierId() + " not found");
    }

    // Tạo đối tượng sản phẩm
    ProductsEntity productsEntity = new ProductsEntity();
    productsEntity.setSupplier(suppliersEntity); // Liên kết sản phẩm với nhà cung cấp đã tồn tại
    productsEntity.setProductName(createProductDTO.getProductName());
    productsEntity.setBrand(createProductDTO.getProductBrand());
    productsEntity.setModel(createProductDTO.getModel());
    productsEntity.setPrice(createProductDTO.getPrice());
    productsEntity.setStockQuantity(createProductDTO.getStockQuantity());
    productsEntity.setWarrantyPeriod(createProductDTO.getWarrantyPeriod());
    productsEntity.setReleaseDate(createProductDTO.getReleaseDate());
    productsEntity.setImageURL(createProductDTO.getImageUrl());

    // Lưu đối tượng sản phẩm vào cơ sở dữ liệu
    entityManager.persist(productsEntity);

    // Tạo đối tượng mô tả sản phẩm
    ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
    productDescriptionEntity.setProduct(productsEntity); // Liên kết mô tả sản phẩm với sản phẩm đã tồn tại
    productDescriptionEntity.setCpuCompany(createProductDTO.getCpuCompany());
    productDescriptionEntity.setCpuTechnology(createProductDTO.getCpuTechnology());
    productDescriptionEntity.setCpuType(createProductDTO.getCpuType());
    productDescriptionEntity.setMinimumCPUspeed(createProductDTO.getMinimumCPUspeed());
    productDescriptionEntity.setMaximunSpeed(createProductDTO.getMaximunSpeed());
    productDescriptionEntity.setMultiplier(createProductDTO.getMultiplier());
    productDescriptionEntity.setProcessorCache(createProductDTO.getProcessorCache());
    productDescriptionEntity.setBrandCardOboard(createProductDTO.getBrandCardOboard());
    productDescriptionEntity.setModelCardOboard(createProductDTO.getModelCardOboard());
    productDescriptionEntity.setFullNameCardOboard(createProductDTO.getFullNameCardOboard());
    productDescriptionEntity.setVgaBrand(createProductDTO.getVgaBrand());
    productDescriptionEntity.setVgaFullName(createProductDTO.getVgaFullName());
    productDescriptionEntity.setRamCapacity(createProductDTO.getRamCapacity());
    productDescriptionEntity.setRamType(createProductDTO.getRamType());
    productDescriptionEntity.setRamSpeed(createProductDTO.getRamSpeed());
    productDescriptionEntity.setNumberOfRemovableSlots(createProductDTO.getNumberOfRemovableSlots());
    productDescriptionEntity.setNumberOfOnboardRAM(createProductDTO.getNumberOfOnboardRAM());
    productDescriptionEntity.setMaximumRAMSupport(createProductDTO.getMaximumRAMSupport());
    productDescriptionEntity.setHardDriveType(createProductDTO.getHardDriveType());
    productDescriptionEntity.setTotalSSDHDDSlots(createProductDTO.getTotalSSDHDDSlots());
    productDescriptionEntity.setNumberOfSSDHDDSlotsRemaining(createProductDTO.getNumberOfSSDHDDSlotsRemaining());
    productDescriptionEntity.setMaximumHardDriveUpgradeCapacity(createProductDTO.getMaximumHardDriveUpgradeCapacity());
    productDescriptionEntity.setSsdType(createProductDTO.getSsdType());
    productDescriptionEntity.setCapacity(createProductDTO.getCapacity());
    productDescriptionEntity.setScreenSize(createProductDTO.getScreenSize());
    productDescriptionEntity.setDisplayTechnology(createProductDTO.getDisplayTechnology());
    productDescriptionEntity.setResolution(createProductDTO.getResolution());
    productDescriptionEntity.setScreenType(createProductDTO.getScreenType());
    productDescriptionEntity.setScanningFrequency(createProductDTO.getScanningFrequency());
    productDescriptionEntity.setBasePlate(createProductDTO.getBasePlate());
    productDescriptionEntity.setBrightness(createProductDTO.getBrightness());
    productDescriptionEntity.setColorCoverage(createProductDTO.getColorCoverage());
    productDescriptionEntity.setScreenRatio(createProductDTO.getScreenRatio());
    productDescriptionEntity.setCommunicationPort(createProductDTO.getCommunicationPort());
    productDescriptionEntity.setWifi(createProductDTO.getWifi());
    productDescriptionEntity.setBluetooth(createProductDTO.getBluetooth());
    productDescriptionEntity.setWebcam(createProductDTO.getWebcam());
    productDescriptionEntity.setOs(createProductDTO.getOs());
    productDescriptionEntity.setVersion(createProductDTO.getVersion());
    productDescriptionEntity.setSecurity(createProductDTO.getSecurity());
    productDescriptionEntity.setKeyboardType(createProductDTO.getKeyboardType());
    productDescriptionEntity.setNumericKeypad(createProductDTO.isNumericKeypad());
    productDescriptionEntity.setKeyboardLight(createProductDTO.getKeyboardLight());
    productDescriptionEntity.setTouchPad(createProductDTO.getTouchPad());
    productDescriptionEntity.setBatteryType(createProductDTO.getBatteryType());
    productDescriptionEntity.setBatteryCapacity(createProductDTO.getBatteryCapacity());
    productDescriptionEntity.setPowerSupply(createProductDTO.getPowerSupply());
    productDescriptionEntity.setAccessoriesInTheBox(createProductDTO.getAccessoriesInTheBox());
    productDescriptionEntity.setSize(createProductDTO.getSize());
    productDescriptionEntity.setProductWeight(createProductDTO.getProductWeight());
    productDescriptionEntity.setMaterial(createProductDTO.getMaterial());
    productDescriptionEntity.setPn(createProductDTO.getPn());
    productDescriptionEntity.setOrigin(createProductDTO.getOrigin());
    productDescriptionEntity.setWarrantyPeriodMonths(createProductDTO.getWarrantyPeriodMonths());
    productDescriptionEntity.setStorageInstructions(createProductDTO.getStorageInstructions());
    productDescriptionEntity.setUserManual(createProductDTO.getUserManual());
    productDescriptionEntity.setColor(createProductDTO.getColor());

    // Lưu đối tượng mô tả sản phẩm vào cơ sở dữ liệu
    entityManager.persist(productDescriptionEntity);
}


//    @Override
//    public void updateProduct(Product_UpdateProductDTO updateProductDTO, ProductsEntity productsEntityById) {
//        SuppliersEntity suppliersEntity = new SuppliersEntity();
//        suppliersEntity.setSupplierID(updateProductDTO.getSupplierId());
//        productsEntityById.setSupplier(suppliersEntity);
//        productsEntityById.setProductName(updateProductDTO.getProductName());
//        productsEntityById.setBrand(updateProductDTO.getProductBrand());
//        productsEntityById.setModel(updateProductDTO.getModel());
//        productsEntityById.setPrice(updateProductDTO.getPrice());
//        productsEntityById.setStockQuantity(updateProductDTO.getStockQuantity());
//        productsEntityById.setWarrantyPeriod(updateProductDTO.getWarrantyPeriod());
//        productsEntityById.setReleaseDate(updateProductDTO.getReleaseDate());
//        productsEntityById.setImageURL(updateProductDTO.getImageUrl());
//        entityManager.merge(productsEntityById);
//        ProductDescriptionEntity productDescriptionEntity = new ProductDescriptionEntity();
//        productDescriptionEntity.setProduct(productsEntityById);
//        productDescriptionEntity.setCpuCompany(updateProductDTO.getCpuCompany());
//        productDescriptionEntity.setCpuTechnology(updateProductDTO.getCpuTechnology());
//        productDescriptionEntity.setCpuType(updateProductDTO.getCpuType());
//        productDescriptionEntity.setMinimumCPUspeed(updateProductDTO.getMinimumCPUspeed());
//        productDescriptionEntity.setMaximunSpeed(updateProductDTO.getMaximunSpeed());
//        productDescriptionEntity.setMultiplier(updateProductDTO.getMultiplier());
//        productDescriptionEntity.setProcessorCache(updateProductDTO.getProcessorCache());
//        productDescriptionEntity.setBrandCardOboard(updateProductDTO.getBrandCardOboard());
//        productDescriptionEntity.setModelCardOboard(updateProductDTO.getModelCardOboard());
//        productDescriptionEntity.setFullNameCardOboard(updateProductDTO.getFullNameCardOboard());
//        productDescriptionEntity.setVgaBrand(updateProductDTO.getVgaBrand());
//        productDescriptionEntity.setVgaFullName(updateProductDTO.getVgaFullName());
//        productDescriptionEntity.setRamCapacity(updateProductDTO.getRamCapacity());
//        productDescriptionEntity.setRamType(updateProductDTO.getRamType());
//        productDescriptionEntity.setRamSpeed(updateProductDTO.getRamSpeed());
//        productDescriptionEntity.setNumberOfRemovableSlots(updateProductDTO.getNumberOfRemovableSlots());
//        productDescriptionEntity.setNumberOfOnboardRAM(updateProductDTO.getNumberOfOnboardRAM());
//        productDescriptionEntity.setMaximumRAMSupport(updateProductDTO.getMaximumRAMSupport());
//        productDescriptionEntity.setHardDriveType(updateProductDTO.getHardDriveType());
//        productDescriptionEntity.setTotalSSDHDDSlots(updateProductDTO.getTotalSSDHDDSlots());
//        productDescriptionEntity.setNumberOfSSDHDDSlotsRemaining(updateProductDTO.getNumberOfSSDHDDSlotsRemaining());
//        productDescriptionEntity.setMaximumHardDriveUpgradeCapacity(updateProductDTO.getMaximumHardDriveUpgradeCapacity());
//        productDescriptionEntity.setSsdType(updateProductDTO.getSsdType());
//        productDescriptionEntity.setCapacity(updateProductDTO.getCapacity());
//        productDescriptionEntity.setScreenSize(updateProductDTO.getScreenSize());
//        productDescriptionEntity.setDisplayTechnology(updateProductDTO.getDisplayTechnology());
//        productDescriptionEntity.setResolution(updateProductDTO.getResolution());
//        productDescriptionEntity.setScreenType(updateProductDTO.getScreenType());
//        productDescriptionEntity.setScanningFrequency(updateProductDTO.getScanningFrequency());
//        productDescriptionEntity.setBasePlate(updateProductDTO.getBasePlate());
//        productDescriptionEntity.setBrightness(updateProductDTO.getBrightness());
//        productDescriptionEntity.setColorCoverage(updateProductDTO.getColorCoverage());
//        productDescriptionEntity.setScreenRatio(updateProductDTO.getScreenRatio());
//        productDescriptionEntity.setCommunicationPort(updateProductDTO.getCommunicationPort());
//        productDescriptionEntity.setWifi(updateProductDTO.getWifi());
//        productDescriptionEntity.setBluetooth(updateProductDTO.getBluetooth());
//        productDescriptionEntity.setWebcam(updateProductDTO.getWebcam());
//        productDescriptionEntity.setOs(updateProductDTO.getOs());
//        productDescriptionEntity.setVersion(updateProductDTO.getVersion());
//        productDescriptionEntity.setSecurity(updateProductDTO.getSecurity());
//        productDescriptionEntity.setKeyboardType(updateProductDTO.getKeyboardType());
//        productDescriptionEntity.setNumericKeypad(updateProductDTO.isNumericKeypad());
//        productDescriptionEntity.setKeyboardLight(updateProductDTO.getKeyboardLight());
//        productDescriptionEntity.setTouchPad(updateProductDTO.getTouchPad());
//        productDescriptionEntity.setBatteryType(updateProductDTO.getBatteryType());
//        productDescriptionEntity.setBatteryCapacity(updateProductDTO.getBatteryCapacity());
//        productDescriptionEntity.setPowerSupply(updateProductDTO.getPowerSupply());
//        productDescriptionEntity.setAccessoriesInTheBox(updateProductDTO.getAccessoriesInTheBox());
//        productDescriptionEntity.setSize(updateProductDTO.getSize());
//        productDescriptionEntity.setProductWeight(updateProductDTO.getProductWeight());
//        productDescriptionEntity.setMaterial(updateProductDTO.getMaterial());
//        productDescriptionEntity.setPn(updateProductDTO.getPn());
//        productDescriptionEntity.setOrigin(updateProductDTO.getOrigin());
//        productDescriptionEntity.setWarrantyPeriodMonths(updateProductDTO.getWarrantyPeriodMonths());
//        productDescriptionEntity.setStorageInstructions(updateProductDTO.getStorageInstructions());
//        productDescriptionEntity.setUserManual(updateProductDTO.getUserManual());
//        productDescriptionEntity.setColor(updateProductDTO.getColor());
//        entityManager.merge(productDescriptionEntity);
//        entityManager.flush();
//    }
@Override
public void updateProduct(Product_UpdateProductDTO updateProductDTO, ProductsEntity productsEntityById) {
    // Kiểm tra nếu Supplier đã tồn tại trong cơ sở dữ liệu
    SuppliersEntity suppliersEntity = entityManager.find(SuppliersEntity.class, updateProductDTO.getSupplierId());

    if (suppliersEntity == null) {
        throw new EntityNotFoundException("Supplier with ID " + updateProductDTO.getSupplierId() + " not found");
    }

    // Cập nhật thông tin sản phẩm
    productsEntityById.setSupplier(suppliersEntity); // Liên kết với supplier đã tồn tại
    productsEntityById.setProductName(updateProductDTO.getProductName());
    productsEntityById.setBrand(updateProductDTO.getProductBrand());
    productsEntityById.setModel(updateProductDTO.getModel());
    productsEntityById.setPrice(updateProductDTO.getPrice());
    productsEntityById.setStockQuantity(updateProductDTO.getStockQuantity());
    productsEntityById.setWarrantyPeriod(updateProductDTO.getWarrantyPeriod());
    productsEntityById.setReleaseDate(updateProductDTO.getReleaseDate());
    productsEntityById.setImageURL(updateProductDTO.getImageUrl());

    // Cập nhật thông tin mô tả sản phẩm
    ProductDescriptionEntity productDescriptionEntity = entityManager.find(ProductDescriptionEntity.class, productsEntityById.getProductID());

    if (productDescriptionEntity == null) {
        // Nếu không tìm thấy, tạo mới ProductDescriptionEntity
        productDescriptionEntity = new ProductDescriptionEntity();
        productDescriptionEntity.setProduct(productsEntityById); // Liên kết mô tả sản phẩm với sản phẩm
    }

    productDescriptionEntity.setCpuCompany(updateProductDTO.getCpuCompany());
    productDescriptionEntity.setCpuTechnology(updateProductDTO.getCpuTechnology());
    productDescriptionEntity.setCpuType(updateProductDTO.getCpuType());
    productDescriptionEntity.setMinimumCPUspeed(updateProductDTO.getMinimumCPUspeed());
    productDescriptionEntity.setMaximunSpeed(updateProductDTO.getMaximunSpeed());
    productDescriptionEntity.setMultiplier(updateProductDTO.getMultiplier());
    productDescriptionEntity.setProcessorCache(updateProductDTO.getProcessorCache());
    productDescriptionEntity.setBrandCardOboard(updateProductDTO.getBrandCardOboard());
    productDescriptionEntity.setModelCardOboard(updateProductDTO.getModelCardOboard());
    productDescriptionEntity.setFullNameCardOboard(updateProductDTO.getFullNameCardOboard());
    productDescriptionEntity.setVgaBrand(updateProductDTO.getVgaBrand());
    productDescriptionEntity.setVgaFullName(updateProductDTO.getVgaFullName());
    productDescriptionEntity.setRamCapacity(updateProductDTO.getRamCapacity());
    productDescriptionEntity.setRamType(updateProductDTO.getRamType());
    productDescriptionEntity.setRamSpeed(updateProductDTO.getRamSpeed());
    productDescriptionEntity.setNumberOfRemovableSlots(updateProductDTO.getNumberOfRemovableSlots());
    productDescriptionEntity.setNumberOfOnboardRAM(updateProductDTO.getNumberOfOnboardRAM());
    productDescriptionEntity.setMaximumRAMSupport(updateProductDTO.getMaximumRAMSupport());
    productDescriptionEntity.setHardDriveType(updateProductDTO.getHardDriveType());
    productDescriptionEntity.setTotalSSDHDDSlots(updateProductDTO.getTotalSSDHDDSlots());
    productDescriptionEntity.setNumberOfSSDHDDSlotsRemaining(updateProductDTO.getNumberOfSSDHDDSlotsRemaining());
    productDescriptionEntity.setMaximumHardDriveUpgradeCapacity(updateProductDTO.getMaximumHardDriveUpgradeCapacity());
    productDescriptionEntity.setSsdType(updateProductDTO.getSsdType());
    productDescriptionEntity.setCapacity(updateProductDTO.getCapacity());
    productDescriptionEntity.setScreenSize(updateProductDTO.getScreenSize());
    productDescriptionEntity.setDisplayTechnology(updateProductDTO.getDisplayTechnology());
    productDescriptionEntity.setResolution(updateProductDTO.getResolution());
    productDescriptionEntity.setScreenType(updateProductDTO.getScreenType());
    productDescriptionEntity.setScanningFrequency(updateProductDTO.getScanningFrequency());
    productDescriptionEntity.setBasePlate(updateProductDTO.getBasePlate());
    productDescriptionEntity.setBrightness(updateProductDTO.getBrightness());
    productDescriptionEntity.setColorCoverage(updateProductDTO.getColorCoverage());
    productDescriptionEntity.setScreenRatio(updateProductDTO.getScreenRatio());
    productDescriptionEntity.setCommunicationPort(updateProductDTO.getCommunicationPort());
    productDescriptionEntity.setWifi(updateProductDTO.getWifi());
    productDescriptionEntity.setBluetooth(updateProductDTO.getBluetooth());
    productDescriptionEntity.setWebcam(updateProductDTO.getWebcam());
    productDescriptionEntity.setOs(updateProductDTO.getOs());
    productDescriptionEntity.setVersion(updateProductDTO.getVersion());
    productDescriptionEntity.setSecurity(updateProductDTO.getSecurity());
    productDescriptionEntity.setKeyboardType(updateProductDTO.getKeyboardType());
    productDescriptionEntity.setNumericKeypad(updateProductDTO.isNumericKeypad());
    productDescriptionEntity.setKeyboardLight(updateProductDTO.getKeyboardLight());
    productDescriptionEntity.setTouchPad(updateProductDTO.getTouchPad());
    productDescriptionEntity.setBatteryType(updateProductDTO.getBatteryType());
    productDescriptionEntity.setBatteryCapacity(updateProductDTO.getBatteryCapacity());
    productDescriptionEntity.setPowerSupply(updateProductDTO.getPowerSupply());
    productDescriptionEntity.setAccessoriesInTheBox(updateProductDTO.getAccessoriesInTheBox());
    productDescriptionEntity.setSize(updateProductDTO.getSize());
    productDescriptionEntity.setProductWeight(updateProductDTO.getProductWeight());
    productDescriptionEntity.setMaterial(updateProductDTO.getMaterial());
    productDescriptionEntity.setPn(updateProductDTO.getPn());
    productDescriptionEntity.setOrigin(updateProductDTO.getOrigin());
    productDescriptionEntity.setWarrantyPeriodMonths(updateProductDTO.getWarrantyPeriodMonths());
    productDescriptionEntity.setStorageInstructions(updateProductDTO.getStorageInstructions());
    productDescriptionEntity.setUserManual(updateProductDTO.getUserManual());
    productDescriptionEntity.setColor(updateProductDTO.getColor());

    // Lưu cả sản phẩm và mô tả sản phẩm
    entityManager.merge(productsEntityById);
    entityManager.merge(productDescriptionEntity);

    // Flush để đảm bảo các thay đổi được đẩy vào cơ sở dữ liệu ngay lập tức
    entityManager.flush();
}

}
