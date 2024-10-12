package com.example.ProjectLaptopStore.Entity;

import com.example.ProjectLaptopStore.DTO.Product_FindTopPurchasedProductsDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//lớp map dữ liệu với bảng Products trong db
@Entity
@Table(name = "products")
public class ProductsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productID;
//    @Column(name = "SupplierID") khóa ngoài
//    private Integer suppierId;
    @Column(name = "ProductName", nullable = false)
    private String productName;
    @Column(name = "Brand")
    private String brand;
    @Column(name = "Model")
    private String model;
    @Column(name = "Price",nullable = false)
    private Float price;
    @Column(name = "StockQuantity",nullable = false)
    private Integer stockQuantity;
    @Column(name = "ReleaseDate")
    private Date releaseDate;
    @Column(name = "WarrantyPeriod")
    private Integer warrantyPeriod;
    @Column(name = "ImageURL")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private SuppliersEntity supplier;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CartDetailsEntity> cartDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ProductReviewsEntity> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ProductDescriptionEntity> productDescriptions = new ArrayList<>();
//    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private  ProductDescriptionEntity productDescription;


    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public SuppliersEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SuppliersEntity supplier) {
        this.supplier = supplier;
    }

    public List<OrderDetailEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<CartDetailsEntity> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetailsEntity> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public List<ProductReviewsEntity> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<ProductReviewsEntity> productReviews) {
        this.productReviews = productReviews;
    }

    public List<ProductDescriptionEntity> getProductDescriptions() {
        return productDescriptions;
    }

    public void setProductDescriptions(List<ProductDescriptionEntity> productDescriptions) {
        this.productDescriptions = productDescriptions;
    }
}
