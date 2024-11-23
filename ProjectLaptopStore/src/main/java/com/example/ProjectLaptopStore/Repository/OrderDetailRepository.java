package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.OrderDetailEntity;
import com.example.ProjectLaptopStore.Repository.Custom.OrderDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity,Integer>, OrderDetailRepositoryCustom {
    //lấy số lượng sản phẩm bán được trong tháng hiện tại
    @Query(value =
            "SELECT SUM(od.quantity) as totalquantity " +
                    "FROM orderdetails od " +
                    "JOIN orders o ON o.OrderID = od.OrderID " +
                    "WHERE MONTH(o.OrderDate) = MONTH(CURDATE()) " +
                    "  AND YEAR(o.OrderDate) = YEAR(CURDATE())", nativeQuery = true)
    Integer getTotalQuantityProductCurrentMonth();

    // lay danh sach cac don hang
    @Query(value = "SELECT o.OrderID,o.ProductID, p.ProductName, o.LineTotal, p.ImageURL FROM OrderDetails o\n" +
            "            JOIN Products p ON p.ProductID = o.ProductID\n" +
            "            WHERE o.OrderID = :orderID ",nativeQuery = true)
    List<Object[]> getOrderDetail(@Param("orderID")int orderID);

    @Query(value = "SELECT od.OrderID,od.ProductID, p.ProductName, od.LineTotal, p.ImageURL FROM OrderDetails od\n" +
            "            JOIN Products p ON p.ProductID = od.ProductID\n" +
            "            JOIN Orders o ON o.OrderID = od.OrderID\n" +
            "            WHERE o.OrderStatus like :orderStatus",nativeQuery = true)
    List<Object[]> SearchOrderDetailByStatus(@Param("orderStatus")String orderStatus);
}
