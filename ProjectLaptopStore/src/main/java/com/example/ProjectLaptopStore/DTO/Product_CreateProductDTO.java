package com.example.ProjectLaptopStore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class Product_CreateProductDTO {
<<<<<<< Updated upstream
    private Integer supplierId;
    private String productName;
    private String productBrand;
    private String model;
    private Float price;
    private Integer stockQuantity;
//    private String description;
    private Date releaseDate;
    private Integer warrantyPeriod;
    private String imageUrl;

    public Product_CreateProductDTO(Integer supplierId, String productName, String productBrand, String model, Float price, Integer stockQuantity, Date releaseDate, Integer warrantyPeriod, String imageUrl) {
        this.supplierId = supplierId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.model = model;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.releaseDate = releaseDate;
        this.warrantyPeriod = warrantyPeriod;
        this.imageUrl = imageUrl;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Integer warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
=======
        private Integer supplierId;
        private String productName;
        private String productBrand;
        private String model;
        private Float price;
        private Integer stockQuantity;
        private Date releaseDate;
        private Integer warrantyPeriod;
        private String imageUrl;
        private String cpuCompany;
        private String cpuTechnology;
        private String cpuType;
        private String minimumCPUspeed;
        private BigDecimal maximunSpeed;
        private long multiplier;
        private String processorCache;
        private String brandCardOboard;
        private String modelCardOboard;
        private String fullNameCardOboard;
        private String vgaBrand;
        private String vgaFullName;
        private long ramCapacity;
        private String ramType;
        private String ramSpeed;
        private long numberOfRemovableSlots;
        private long numberOfOnboardRAM;
        private long maximumRAMSupport;
        private String hardDriveType;
        private long totalSSDHDDSlots;
        private long numberOfSSDHDDSlotsRemaining;
        private long maximumHardDriveUpgradeCapacity;
        private String ssdType;
        private long capacity;
        private BigDecimal screenSize;
        private String displayTechnology;
        private String resolution;
        private String screenType;
        private BigDecimal scanningFrequency;
        private String basePlate;
        private long brightness;
        private String colorCoverage;
        private BigDecimal screenRatio;
        private String communicationPort;
        private String wifi;
        private String bluetooth;
        private String webcam;
        private String os;
        private String version;
        private String security;
        private String keyboardType;
        private boolean numericKeypad;
        private String keyboardLight;
        private String touchPad;
        private String batteryType;
        private long batteryCapacity;
        private String powerSupply;
        private String accessoriesInTheBox;
        private String size;
        private BigDecimal productWeight;
        private String material;
        private String pn;
        private String origin;
        //    private Date releaseDate;
        private long warrantyPeriodMonths;
        private String storageInstructions;
        private String userManual;
        private String color;

        public Product_CreateProductDTO(Integer supplierId, String productName, String productBrand, String model, Float price, Integer stockQuantity, Date releaseDate, Integer warrantyPeriod, String imageUrl, String cpuCompany, String cpuTechnology, String cpuType, String minimumCPUspeed, BigDecimal maximunSpeed, long multiplier, String processorCache, String brandCardOboard, String modelCardOboard, String fullNameCardOboard, String vgaBrand, String vgaFullName, long ramCapacity, String ramType, String ramSpeed, long numberOfRemovableSlots, long numberOfOnboardRAM, long maximumRAMSupport, String hardDriveType, long totalSSDHDDSlots, long numberOfSSDHDDSlotsRemaining, long maximumHardDriveUpgradeCapacity, String ssdType, long capacity, BigDecimal screenSize, String displayTechnology, String resolution, String screenType, BigDecimal scanningFrequency, String basePlate, long brightness, String colorCoverage, BigDecimal screenRatio, String communicationPort, String wifi, String bluetooth, String webcam, String os, String version, String security, String keyboardType, boolean numericKeypad, String keyboardLight, String touchPad, String batteryType, long batteryCapacity, String powerSupply, String accessoriesInTheBox, String size, BigDecimal productWeight, String material, String pn, String origin, long warrantyPeriodMonths, String storageInstructions, String userManual, String color) {
            this.supplierId = supplierId;
            this.productName = productName;
            this.productBrand = productBrand;
            this.model = model;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.releaseDate = releaseDate;
            this.warrantyPeriod = warrantyPeriod;
            this.imageUrl = imageUrl;
            this.cpuCompany = cpuCompany;
            this.cpuTechnology = cpuTechnology;
            this.cpuType = cpuType;
            this.minimumCPUspeed = minimumCPUspeed;
            this.maximunSpeed = maximunSpeed;
            this.multiplier = multiplier;
            this.processorCache = processorCache;
            this.brandCardOboard = brandCardOboard;
            this.modelCardOboard = modelCardOboard;
            this.fullNameCardOboard = fullNameCardOboard;
            this.vgaBrand = vgaBrand;
            this.vgaFullName = vgaFullName;
            this.ramCapacity = ramCapacity;
            this.ramType = ramType;
            this.ramSpeed = ramSpeed;
            this.numberOfRemovableSlots = numberOfRemovableSlots;
            this.numberOfOnboardRAM = numberOfOnboardRAM;
            this.maximumRAMSupport = maximumRAMSupport;
            this.hardDriveType = hardDriveType;
            this.totalSSDHDDSlots = totalSSDHDDSlots;
            this.numberOfSSDHDDSlotsRemaining = numberOfSSDHDDSlotsRemaining;
            this.maximumHardDriveUpgradeCapacity = maximumHardDriveUpgradeCapacity;
            this.ssdType = ssdType;
            this.capacity = capacity;
            this.screenSize = screenSize;
            this.displayTechnology = displayTechnology;
            this.resolution = resolution;
            this.screenType = screenType;
            this.scanningFrequency = scanningFrequency;
            this.basePlate = basePlate;
            this.brightness = brightness;
            this.colorCoverage = colorCoverage;
            this.screenRatio = screenRatio;
            this.communicationPort = communicationPort;
            this.wifi = wifi;
            this.bluetooth = bluetooth;
            this.webcam = webcam;
            this.os = os;
            this.version = version;
            this.security = security;
            this.keyboardType = keyboardType;
            this.numericKeypad = numericKeypad;
            this.keyboardLight = keyboardLight;
            this.touchPad = touchPad;
            this.batteryType = batteryType;
            this.batteryCapacity = batteryCapacity;
            this.powerSupply = powerSupply;
            this.accessoriesInTheBox = accessoriesInTheBox;
            this.size = size;
            this.productWeight = productWeight;
            this.material = material;
            this.pn = pn;
            this.origin = origin;
            this.warrantyPeriodMonths = warrantyPeriodMonths;
            this.storageInstructions = storageInstructions;
            this.userManual = userManual;
            this.color = color;
        }
>>>>>>> Stashed changes
}
