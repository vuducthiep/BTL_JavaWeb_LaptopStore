package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.ProductDetailDTO;
import com.example.ProjectLaptopStore.DTO.Product_FindTopPurchasedProductsDTO;
import com.example.ProjectLaptopStore.DTO.Product_DisplayForHomePageDTO;
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
//import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//sử dụng JDBC để lấy dữ liệu
@Repository
@Transactional
public class IProductRepositoryCustomImpl implements IProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    //hàm lấy list sản phẩm theo số lượng được đặt hàng
    @Override
    public List<Product_FindTopPurchasedProductsDTO> findAllProductsWithTotalQuantityOrdered() {
        String query = "SELECT p.ProductID ,p.ProductName, p.Brand, p.Model, p.Price, p.StockQuantity, " +
                "p.WarrantyPeriod, p.ImageURL, COALESCE(SUM(od.Quantity), 0) AS quantityOrdered ,COALESCE(SUM(od.LineTotal), 0) AS lineTotal " +
                "FROM Products p " +
                "JOIN OrderDetails od ON p.ProductID = od.ProductID " +
                "JOIN Suppliers s on s.SupplierID = p.SupplierID " +
                "WHERE s.Status = 'active'  " +
                "GROUP BY p.ProductID " +
                "ORDER BY lineTotal DESC";
        Query nativeQuery = entityManager.createNativeQuery(query);// ko cần truyền DTO.class
        List<Object[]> results = nativeQuery.getResultList();

        //set thủ công
        List<Product_FindTopPurchasedProductsDTO> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            Product_FindTopPurchasedProductsDTO dto = new Product_FindTopPurchasedProductsDTO(
                    (Integer) result[0],
                    (String) result[1],  // productName
                    (String) result[2],  // brand
                    (String) result[3],  // model
                    (Float) result[4],   // price
                    (Integer) result[5], // stockQuantity
                    (Integer) result[6], // warrantyPeriod
                    (String) result[7],  // imageURL
                    ((Number) result[8]).longValue(),  // quantityOrdered
                    (BigDecimal) result[9] //lineTotal
            );
            dtoList.add(dto);
        }

        return dtoList;


    }
    //hàm tạo sản phẩm
    @Override
    public void createProduct(ProductDetailDTO productNew, ProductsEntity productsEntity,ProductDescriptionEntity productDescriptionEntity) {
        setDataProduct(productNew,productsEntity,productDescriptionEntity,1);
    }
    //hàm cập nhật sản phẩm
    @Override
    public void updateProduct(ProductDetailDTO productUpdate, ProductsEntity productsEntityById,ProductDescriptionEntity productDescriptionEntity) {
       setDataProduct(productUpdate,productsEntityById,productDescriptionEntity,2);
    }


    //CẦN CẢI TIẾN LOGIC VÀ TỐI ƯU HÓA CODE, GIẢM LƯỢNG CODE TRONG TƯƠNG LAI
    //không đảm bảo khách hàng sẽ tìm kiếm theo các key chuẩn bị sẵn
    //Hàm tìm kiếm sản phẩm bằng key trên searchbar
    @Override
    public List<Product_DisplayForHomePageDTO> findAllProductsByKey(Object key) {
        // Chuyển đổi key thành chuỗi và tách giá trị cuối cùng
        String[] parts = key.toString().trim().split(" ");
        String columnValue = parts[parts.length - 1]; // Giá trị là phần cuối cùng
        String columnName = String.join(" ", Arrays.copyOf(parts, parts.length - 1)).toLowerCase(); // Phần còn lại là tên cột
        // Xây dựng truy vấn
        String query = "SELECT p.ProductID , p.ProductName, p.Price, p.ImageURL " +
                "FROM Products p " +
                "JOIN ProductDescription pd ON p.ProductID = pd.ProductID " +
                "JOIN Suppliers s ON p.SupplierID = s.SupplierID " +
                "WHERE ";

        // Thêm điều kiện tìm kiếm tương ứng với tên cột
        switch (columnName) {
            case "brand":
                query += "LOWER(p.Brand) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "hãng":
                query += "LOWER(p.Brand) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "model":
                query += "LOWER(p.Model) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "mẫu":
                query += "LOWER(p.Model) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "cpu technology":
                query += "LOWER(pd.CPUtechnology) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "công nghệ cpu":
                query += "LOWER(pd.CPUtechnology) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "cpu company":
                query += "LOWER(pd.CPUcompany) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "công ty cung cấp cpu":
                query += "LOWER(pd.CPUcompany) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "ram":
                query += "LOWER(pd.RAMcapacity) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "dung lượng ram":
                query += "LOWER(pd.RAMcapacity) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "color":
                query += "LOWER(pd.Color) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "màu":
                query += "LOWER(pd.Color) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "màu sắc":
                query += "LOWER(pd.Color) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "material":
                query += "LOWER(pd.Material) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "chất liệu":
                query += "LOWER(pd.Material) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "làm bằng":
                query += "LOWER(pd.Material) LIKE LOWER(CONCAT('%', :value, '%')) ";
                break;
            case "cpu type":
                query += "LOWER(pd.CPUtype) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "kiểu cpu":
                query += "LOWER(pd.CPUtype) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "Processor Cache":
                query += "LOWER(pd.ProcessorCache) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "xử lý bộ nhớ tạm":
                query += "LOWER(pd.ProcessorCache) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "vga brand":
                query += "LOWER(pd.VGABrand) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "hãng card đồ họa":
                query += "LOWER(pd.VGABrand) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "card đồ họa":
                query += "LOWER(pd.VGABrand) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "vga":
                query += "LOWER(pd.VGABrand) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "ram speed":
                query += "LOWER(pd.RAMspeed) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "ramspeed":
                query += "LOWER(pd.RAMspeed) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "tốc độ ram":
                query += "LOWER(pd.RAMspeed) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "number of onboard rAM":
                query += "LOWER(pd.NumberOfOnboardRAM) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "số lượng khe ram tích hợp":
                query += "LOWER(pd.NumberOfOnboardRAM) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            case "số khe ram":
                query += "LOWER(pd.NumberOfOnboardRAM) LIKE LOWER(CONCAT('%',:value,'%')) ";
                break;
            default:
                //nếu tên cột không hợp lệ, trả về danh sách rỗng hoặc thông báo lỗi
                //nên trả về danh sách sản phẩm như ban đầu của trang chủ
                return new ArrayList<>();
        }
        Query queryNative = entityManager.createNativeQuery(query);
        queryNative.setParameter("value", columnValue);
        List<Object[]> resultQuery = queryNative.getResultList();
        List<Product_DisplayForHomePageDTO> productsEntityByNativeQuery = new ArrayList<>();
        for (Object[] rowOfResult : resultQuery) {
            Product_DisplayForHomePageDTO dto = new Product_DisplayForHomePageDTO(
                    (Integer) rowOfResult[0],
                    (String) rowOfResult[1],
                    (Float) rowOfResult[2],
                    (String) rowOfResult[3]
            );
            productsEntityByNativeQuery.add(dto);
        }
        return productsEntityByNativeQuery;
    }

    //thấy thông tin chi tiết danh sách các sản phẩm
    @Override
    public List<ProductDetailDTO> listProductDetail() {
        StringBuilder query = setQuery();
        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        List<Object[]> resultQuery = nativeQuery.getResultList();
        List<ProductDetailDTO> listProductDetailDTO = new ArrayList<>();

        for (Object[] rowOfResult : resultQuery) {
            ProductDetailDTO dto = setConstructor(rowOfResult);
            listProductDetailDTO.add(dto);
        }
        return listProductDetailDTO;
    }

    @Override
    public ProductDetailDTO getOneProductDetail(Integer idProduct) {
        StringBuilder query = setQuery();
        query.append(" AND p.productId = :idProduct ");
        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("idProduct", idProduct);
        List<Object[]> resultQuery = nativeQuery.getResultList();
        ProductDetailDTO productDetai = new ProductDetailDTO();
        for (Object[] rowOfResult : resultQuery) {
            productDetai = setConstructor(rowOfResult);

        }
        return productDetai;
    }


    //hàm set dữ liệu cho các biến
    public void setDataProduct(ProductDetailDTO productNew, ProductsEntity productsEntity,ProductDescriptionEntity productDescriptionEntity,Integer task){
        // Kiểm tra nếu nhà cung cấp (Supplier) đã tồn tại trong cơ sở dữ liệu
        SuppliersEntity suppliersEntity = entityManager.find(SuppliersEntity.class, productNew.getSupplierId());
        // Nếu Supplier không tồn tại, có thể ném ra ngoại lệ hoặc xử lý khác
        if (suppliersEntity == null) {
            throw new EntityNotFoundException("Supplier with ID " + productNew.getSupplierId() + " not found");
        }
//        SuppliersEntity suppliersEntity = new SuppliersEntity();
        // Tạo đối tượng sản phẩm
        productsEntity.setSupplier(suppliersEntity); // Liên kết sản phẩm với nhà cung cấp đã tồn tại
        productsEntity.setProductName(productNew.getProductName());
        productsEntity.setBrand(productNew.getProductBrand());
        productsEntity.setModel(productNew.getModel());
        productsEntity.setPrice(productNew.getPrice());
        productsEntity.setStockQuantity(productNew.getStockQuantity());
        productsEntity.setWarrantyPeriod(productNew.getWarrantyPeriod());
        productsEntity.setReleaseDate(productNew.getReleaseDate());
        productsEntity.setImageURL(productNew.getImageUrl());
        // Tạo đối tượng mô tả sản phẩm
        productDescriptionEntity.setProduct(productsEntity); // Liên kết mô tả sản phẩm với sản phẩm đã tồn tại
        productDescriptionEntity.setCpuCompany(productNew.getCpuCompany());
        productDescriptionEntity.setCpuTechnology(productNew.getCpuTechnology());
        productDescriptionEntity.setCpuType(productNew.getCpuType());
        productDescriptionEntity.setMinimumCPUspeed(productNew.getMinimumCPUspeed());
        productDescriptionEntity.setMaximunSpeed(productNew.getMaximunSpeed());
        productDescriptionEntity.setMultiplier(productNew.getMultiplier());
        productDescriptionEntity.setProcessorCache(productNew.getProcessorCache());
        productDescriptionEntity.setBrandCardOboard(productNew.getBrandCardOboard());
        productDescriptionEntity.setModelCardOboard(productNew.getModelCardOboard());
        productDescriptionEntity.setFullNameCardOboard(productNew.getFullNameCardOboard());
        productDescriptionEntity.setVgaBrand(productNew.getVgaBrand());
        productDescriptionEntity.setVgaFullName(productNew.getVgaFullName());
        productDescriptionEntity.setRamCapacity(productNew.getRamCapacity());
        productDescriptionEntity.setRamType(productNew.getRamType());
        productDescriptionEntity.setRamSpeed(productNew.getRamSpeed());
        productDescriptionEntity.setNumberOfRemovableSlots(productNew.getNumberOfRemovableSlots());
        productDescriptionEntity.setNumberOfOnboardRAM(productNew.getNumberOfOnboardRAM());
        productDescriptionEntity.setMaximumRAMSupport(productNew.getMaximumRAMSupport());
        productDescriptionEntity.setHardDriveType(productNew.getHardDriveType());
        productDescriptionEntity.setTotalSSDHDDSlots(productNew.getTotalSSDHDDSlots());
        productDescriptionEntity.setNumberOfSSDHDDSlotsRemaining(productNew.getNumberOfSSDHDDSlotsRemaining());
        productDescriptionEntity.setMaximumHardDriveUpgradeCapacity(productNew.getMaximumHardDriveUpgradeCapacity());
        productDescriptionEntity.setSsdType(productNew.getSsdType());
        productDescriptionEntity.setCapacity(productNew.getCapacity());
        productDescriptionEntity.setScreenSize(productNew.getScreenSize());
        productDescriptionEntity.setDisplayTechnology(productNew.getDisplayTechnology());
        productDescriptionEntity.setResolution(productNew.getResolution());
        productDescriptionEntity.setScreenType(productNew.getScreenType());
//        productDescriptionEntity.setScanningFrequency(productNew.getScanningFrequency());
        productDescriptionEntity.setBasePlate(productNew.getBasePlate());
        productDescriptionEntity.setBrightness(productNew.getBrightness());
        productDescriptionEntity.setColorCoverage(productNew.getColorCoverage());
        productDescriptionEntity.setScreenRatio(productNew.getScreenRatio());
        productDescriptionEntity.setCommunicationPort(productNew.getCommunicationPort());
        productDescriptionEntity.setWifi(productNew.getWifi());
        productDescriptionEntity.setBluetooth(productNew.getBluetooth());
        productDescriptionEntity.setWebcam(productNew.getWebcam());
        productDescriptionEntity.setOs(productNew.getOs());
        productDescriptionEntity.setVersion(productNew.getVersion());
        productDescriptionEntity.setSecurity(productNew.getSecurity());
        productDescriptionEntity.setKeyboardType(productNew.getKeyboardType());
        productDescriptionEntity.setNumericKeypad(productNew.getNumericKeypad());
        productDescriptionEntity.setKeyboardLight(productNew.getKeyboardLight());
        productDescriptionEntity.setTouchPad(productNew.getTouchPad());
        productDescriptionEntity.setBatteryType(productNew.getBatteryType());
        productDescriptionEntity.setBatteryCapacity(productNew.getBatteryCapacity());
        productDescriptionEntity.setPowerSupply(productNew.getPowerSupply());
        productDescriptionEntity.setAccessoriesInTheBox(productNew.getAccessoriesInTheBox());
        productDescriptionEntity.setSize(productNew.getSize());
        productDescriptionEntity.setProductWeight(productNew.getProductWeight());
        productDescriptionEntity.setMaterial(productNew.getMaterial());
        productDescriptionEntity.setPn(productNew.getPnProductCode());
        productDescriptionEntity.setOrigin(productNew.getOrigin());
        productDescriptionEntity.setWarrantyPeriodMonths(productNew.getWarrantyPeriodMonths());
        productDescriptionEntity.setStorageInstructions(productNew.getStorageInstructions());
        productDescriptionEntity.setUserManual(productNew.getUserManual());
        productDescriptionEntity.setColor(productNew.getColor());
        // Lưu đối tượng mô tả sản phẩm vào cơ sở dữ liệu
        //thêm mới
        if(task == 1){
            // Lưu cả sản phẩm và mô tả sản phẩm
            entityManager.persist(productsEntity);
            entityManager.persist(productDescriptionEntity);
            entityManager.flush();
        }
        //cập nhật
        else {
            // Lưu cả sản phẩm và mô tả sản phẩm
            entityManager.merge(productsEntity);
            entityManager.merge(productDescriptionEntity);
            // Flush để đảm bảo các thay đổi được đẩy vào cơ sở dữ liệu ngay lập tức
            entityManager.flush();
        }
    }
    //hàm tạo query lấy thông tin bảng product productdes
    public StringBuilder setQuery(){
        StringBuilder query = new StringBuilder("SELECT \n" +
                "    p.supplierId,\n" +
                "    p.productId,\n" +
                "    p.productName,\n" +
                "    p.brand AS productBrand,\n" +
                "    p.model,\n" +
                "    p.price,\n" +
                "    p.stockQuantity,\n" +
                "    p.releaseDate,\n" +
                "    p.warrantyPeriod,\n" +
                "    p.imageUrl,\n" +
                "    pd.productDescriptionId,\n" +
                "    pd.cpuCompany,\n" +
                "    pd.cpuTechnology,\n" +
                "    pd.cpuType,\n" +
                "    pd.minimumCPUspeed,\n" +
                "    pd.maximunSpeed,\n" +
                "    pd.multiplier,\n" +
                "    pd.processorCache,\n" +
                "    pd.brandCardOboard,\n" +
                "    pd.modelCardOboard,\n" +
                "    pd.fullNameCardOboard,\n" +
                "    pd.vgaBrand,\n" +
                "    pd.vgaFullName,\n" +
                "    pd.ramCapacity,\n" +
                "    pd.ramType,\n" +
                "    pd.ramSpeed,\n" +
                "    pd.numberOfRemovableSlots,\n" +
                "    pd.numberOfOnboardRAM,\n" +
                "    pd.maximumRAMSupport,\n" +
                "    pd.hardDriveType,\n" +
                "    pd.totalSSDHDDSlots,\n" +
                "    pd.numberOfSSDHDDSlotsRemaining,\n" +
                "    pd.maximumHardDriveUpgradeCapacity,\n" +
                "    pd.ssdType,\n" +
                "    pd.capacity,\n" +
                "    pd.screenSize,\n" +
                "    pd.displayTechnology,\n" +
                "    pd.resolution,\n" +
                "    pd.screenType,\n" +
                "    pd.scanningFrequency,\n" +
                "    pd.basePlate,\n" +
                "    pd.brightness,\n" +
                "    pd.colorCoverage,\n" +
                "    pd.screenRatio,\n" +
                "    pd.communicationPort,\n" +
                "    pd.wifi,\n" +
                "    pd.bluetooth,\n" +
                "    pd.webcam,\n" +
                "    pd.os,\n" +
                "    pd.version,\n" +
                "    pd.security,\n" +
                "    pd.keyboardType,\n" +
                "    pd.numericKeypad,\n" +
                "    pd.keyboardLight,\n" +
                "    pd.touchPad,\n" +
                "    pd.batteryType,\n" +
                "    pd.batteryCapacity,\n" +
                "    pd.powerSupply,\n" +
                "    pd.accessoriesInTheBox,\n" +
                "    pd.size,\n" +
                "    pd.productWeight,\n" +
                "    pd.material,\n" +
                "    pd.pn AS pnProductCode,\n" +
                "    pd.origin,\n" +
                "    pd.warrantyPeriodMonths,\n" +
                "    pd.storageInstructions,\n" +
                "    pd.userManual,\n" +
                "    pd.color\n" +
                "FROM Products p\n" +
                "JOIN ProductDescription pd ON p.productId = pd.productId " +
                "JOIN Suppliers s on s.SupplierID = p.SupplierID " +
                "WHERE s.Status = 'active'" +
                "ORDER BY p.productId desc ");
        return query;
    }
    //hàm set dữ liệu cho constructor
    public ProductDetailDTO setConstructor(Object[] rowOfResult){
        ProductDetailDTO dto = new ProductDetailDTO(
        (Integer) rowOfResult[1], // productId
                (Integer) rowOfResult[0], // supplierId
                (String) rowOfResult[2],  // productName
                (String) rowOfResult[3],  // productBrand
                (String) rowOfResult[4],  // model
                (Float) rowOfResult[5],   // price
                (Integer) rowOfResult[6], // stockQuantity
                (Date) rowOfResult[7],    // releaseDate
                (Integer) rowOfResult[8], // warrantyPeriod
                (String) rowOfResult[9],  // imageUrl
                (Integer) rowOfResult[10],// productDescriptionId
                (String) rowOfResult[11], // cpuCompany
                (String) rowOfResult[12], // cpuTechnology
                (String) rowOfResult[13], // cpuType
                (BigDecimal) rowOfResult[14], // minimumCPUspeed
                (BigDecimal) rowOfResult[15], // maximunSpeed
                (Integer) rowOfResult[16],  // multiplier
                (String) rowOfResult[17], // processorCache
                (String) rowOfResult[18], // brandCardOboard
                (String) rowOfResult[19], // modelCardOboard
                (String) rowOfResult[20], // fullNameCardOboard
                (String) rowOfResult[21], // vgaBrand
                (String) rowOfResult[22], // vgaFullName
                (Integer) rowOfResult[23],  // ramCapacity
                (String) rowOfResult[24], // ramType
                (String) rowOfResult[25], // ramSpeed
                (Integer) rowOfResult[26],  // numberOfRemovableSlots
                (Integer) rowOfResult[27],  // numberOfOnboardRAM
                (Integer) rowOfResult[28],  // maximumRAMSupport
                (String) rowOfResult[29], // hardDriveType
                (Integer) rowOfResult[30],  // totalSSDHDDSlots
                (Integer) rowOfResult[31],  // numberOfSSDHDDSlotsRemaining
                (Integer) rowOfResult[32],  // maximumHardDriveUpgradeCapacity
                (String) rowOfResult[33], // ssdType
                (Integer) rowOfResult[34],  // capacity
                (String) rowOfResult[35], // screenSize
                (String) rowOfResult[36], // displayTechnology
                (String) rowOfResult[37], // resolution
                (String) rowOfResult[38], // screenType
                (String) rowOfResult[39], // scanningFrequency
                (String) rowOfResult[40], // basePlate
                (Integer) rowOfResult[41],  // brightness
                (String) rowOfResult[42], // colorCoverage
                (BigDecimal) rowOfResult[43], // screenRatio
                (String) rowOfResult[44], // communicationPort
                (String) rowOfResult[45], // wifi
                (String) rowOfResult[46], // bluetooth
                (String) rowOfResult[47], // webcam
                (String) rowOfResult[48], // os
                (String) rowOfResult[49], // version
                (String) rowOfResult[50], // security
                (String) rowOfResult[51], // keyboardType
                (Boolean) rowOfResult[52], // numericKeypad =============================
                (String) rowOfResult[53], // keyboardLight
                (String) rowOfResult[54], // touchPad
                (String) rowOfResult[55], // batteryType
                (Integer) rowOfResult[56],  // batteryCapacity
                (String) rowOfResult[57], // powerSupply
                (String) rowOfResult[58], // accessoriesInTheBox
                (Float)  rowOfResult[59], // size
                (BigDecimal) rowOfResult[60], // productWeight
                (String) rowOfResult[61], // material
                (String) rowOfResult[62], // pnProductCode
                (String) rowOfResult[63], // origin
                (Integer) rowOfResult[64],  // warrantyPeriodMonths
                (String) rowOfResult[65], // storageInstructions
                (String) rowOfResult[66], // userManual
                (String) rowOfResult[67] // color
        );
        return dto;
    }


// phan trang product
//    @Override
//    public Page<Product_DisplayForHomePageDTO> findAllProductsByPage(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo,pageSize);
//        List<ProductsEntity> productsEntities = productRepository.getAllProducts(pageable);
//        List<Product_DisplayForHomePageDTO> rs = new ArrayList<>();
//        for (ProductsEntity productsEntity : productsEntities) {
//            Product_DisplayForHomePageDTO dto = modelMapper.map(productsEntity, Product_DisplayForHomePageDTO.class);
//            rs.add(dto);
//        }
//        Page<Product_DisplayForHomePageDTO> page = new PageImpl<>(rs);
//        return page;
//    }


}
