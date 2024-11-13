package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.Builder.CustomerSearchBuilder;
import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Entity.CustomerEntity;
import com.example.ProjectLaptopStore.Entity.Enum.Customer_Enum;
import com.example.ProjectLaptopStore.Entity.Enum.User_Enum;
import com.example.ProjectLaptopStore.Entity.ShippingAddressEntity;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.example.ProjectLaptopStore.Repository.Custom.ICustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.catalina.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
//@Primary
@Transactional
public class ICustomerRepositoryCustomImpl implements ICustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    // Hàm xây dựng câu truy vấn điều kiện
    public static void queryNomal(CustomerSearchBuilder customerSearchBuilder, StringBuilder where) {
        // Nếu có ngày bắt đầu
        if (customerSearchBuilder.getDateRegisterFrom() != null) {
            where.append(" and c.RegistrationDate >= :dateRegisterFrom ");
        }
        // Nếu có ngày kết thúc
        if (customerSearchBuilder.getDateRegisterTo() != null) {
            where.append(" and c.RegistrationDate <= :dateRegisterTo ");
        }
    }

//    @Override
//    public Integer countNewCustomer(CustomerSearchBuilder customerSearchBuilder) {
//        StringBuilder sql = new StringBuilder("select count(distinct c.CustomerID) from customers c ");
//        StringBuilder where = new StringBuilder(" where 1=1 ");
//        queryNomal(customerSearchBuilder, where);
//        sql.append(where);
//        // Hiển thị câu lệnh trong console (nếu cần kiểm tra)
//        System.out.println(sql.toString());
//
//        Query query = entityManager.createNativeQuery(sql.toString());
//        // Set các tham số cho câu truy vấn
//        if (customerSearchBuilder.getDateRegisterFrom() != null) {
//            query.setParameter("dateRegisterFrom", customerSearchBuilder.getDateRegisterFrom());
//        }
//        if (customerSearchBuilder.getDateRegisterTo() != null) {
//            query.setParameter("dateRegisterTo", customerSearchBuilder.getDateRegisterTo());
//        }
//        // Trả về kết quả duy nhất
//        try {
//            return ((Number) query.getSingleResult()).intValue();
//        } catch (NoResultException e) {
//            return 0;
//        }
//    }

    //lấy ra số khách hàng mới trong các tháng,trong năm hiện tại
    @Override
    public List<Customer_CountNewCustomerPerMonthDTO> listNewCustomerPerMonth() {
        String query = "SELECT " +
                "MONTH(RegistrationDate) AS month, " +
                "COUNT(DISTINCT CustomerID) AS NewCustomerCount " +
                "FROM " +
                "Customers " +
                "WHERE YEAR(RegistrationDate) = YEAR(CURDATE()) " +
                "GROUP BY Month " +
                "ORDER BY Month ";
        Query nativeQuery = entityManager.createNativeQuery(query) ;
        List<Object[]> resultQuery = nativeQuery.getResultList();
        List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomer = new ArrayList<>();
        for(Object[] rowOfResult : resultQuery) {
            Customer_CountNewCustomerPerMonthDTO dto = new Customer_CountNewCustomerPerMonthDTO(
                    (Long) rowOfResult[0],
                    (Long) rowOfResult[1]
            );
            listCountNewCustomer.add(dto);
        }
        return listCountNewCustomer;
    }

    @Override
    public void deleteCustomer(List<CustomerEntity> listCustomerDelete) {

        for(CustomerEntity cusOfListDel : listCustomerDelete) {
            cusOfListDel.setStatus(Customer_Enum.locked);
            entityManager.merge(cusOfListDel);
        }
        entityManager.flush();
    }

    //sử dụng JPA tạo khách hàng mới
    @Override
    public void createCustomer(CustomerDTO customerNew) {
        CustomerEntity customerEntity = new CustomerEntity();
        UserEntity userEntity = new UserEntity();
        ShippingAddressEntity shippingAddressEntity = new ShippingAddressEntity();

        userEntity.setFullName(customerNew.getFullName());
        userEntity.setEmail(customerNew.getEmail());
        userEntity.setPassword(customerNew.getPassWord());
        userEntity.setPhoneNumber(customerNew.getPhoneNumber());
        userEntity.setUserType(User_Enum.customer);
        userEntity.setRegistrationDate(customerNew.getRegistrationDate());

        customerEntity.setUser(userEntity);
        customerEntity.setRegistrationDate(customerNew.getRegistrationDate());
        customerEntity.setStatus(Customer_Enum.active);

        shippingAddressEntity.setCustomer(customerEntity);
        shippingAddressEntity.setAddress(customerNew.getAddress());
        shippingAddressEntity.setCity(customerNew.getCity());
        shippingAddressEntity.setDistrict(customerNew.getDistrict());
        shippingAddressEntity.setWard(customerNew.getWard());
        shippingAddressEntity.setStreetAddress(customerNew.getStreetAddress());
        entityManager.persist(userEntity);
        entityManager.persist(customerEntity);
        entityManager.persist(shippingAddressEntity);
    }

    @Override
    public void updateCustomer(CustomerDTO customerUpdate,ShippingAddressEntity shippingAddressEntity, CustomerEntity customerEntity, UserEntity userEntity) {
        userEntity.setFullName(customerUpdate.getFullName());
        userEntity.setEmail(customerUpdate.getEmail());
        userEntity.setPassword(customerUpdate.getPassWord());
        userEntity.setPhoneNumber(customerUpdate.getPhoneNumber());
        userEntity.setUserType(User_Enum.customer);
        userEntity.setRegistrationDate(customerUpdate.getRegistrationDate());

        customerEntity.setUser(userEntity);
        customerEntity.setRegistrationDate(customerUpdate.getRegistrationDate());
        customerEntity.setStatus(Customer_Enum.active);

        shippingAddressEntity.setCustomer(customerEntity);
        shippingAddressEntity.setAddress(customerUpdate.getAddress());
        shippingAddressEntity.setCity(customerUpdate.getCity());
        shippingAddressEntity.setDistrict(customerUpdate.getDistrict());
        shippingAddressEntity.setWard(customerUpdate.getWard());
        shippingAddressEntity.setStreetAddress(customerUpdate.getStreetAddress());
        entityManager.merge(userEntity);
        entityManager.merge(customerEntity);
        entityManager.merge(shippingAddressEntity);
        entityManager.flush();
    }

    @Override
    public List<Customer_FindTopCustomerInMonthDTO> listTopCustomerInMonth() {
        String query =
                "SELECT " +
                        "c.CustomerID, " +
                        "u.FullName, " +
                        "u.Email, " +
                        "u.PhoneNumber, " +
                        "u.RegistrationDate, " +
                        "sd.Address, " +
                        "sd.City, " +
                        "sd.District, " +
                        "sd.Ward, " +
                        "sd.StreetAddress, " +
                        "SUM(o.TotalAmount) AS totalamount, " +
                        "SUM(od.Quantity) AS quantity " +
                        "FROM " +
                        "Customers c " +
                        "JOIN " +
                        "Users u ON u.UserID = c.UserID " +
                        "JOIN " +
                        "Orders o ON c.CustomerID = o.CustomerID " +
                        "JOIN " +
                        "OrderDetails od ON o.OrderID = od.OrderID " +
                        "JOIN " +
                        "ShippingAddresses sd ON c.CustomerID = sd.CustomerID " +
                        "WHERE " +
                        "MONTH(o.OrderDate) = MONTH(CURDATE()) " +
                        "AND YEAR(o.OrderDate) = YEAR(CURDATE()) " +
                        "AND c.Status = 'active' "+
                        "GROUP BY " +
                        "c.CustomerID, " +
                        "u.FullName, " +
                        "u.Email, " +
                        "u.PhoneNumber, " +
                        "u.RegistrationDate, " +
                        "sd.Address, " +
                        "sd.City, " +
                        "sd.District, " +
                        "sd.Ward, " +
                        "sd.StreetAddress " +
                        "ORDER BY " +
                        "totalamount DESC ;";
        Query queryNative = entityManager.createNativeQuery(query);
        List<Object[]> resultQuery = queryNative.getResultList();
        List<Customer_FindTopCustomerInMonthDTO> listTopCustomerInMonth = new ArrayList<>();
        for(Object[] rowOfResult : resultQuery) {
            Customer_FindTopCustomerInMonthDTO dto = new Customer_FindTopCustomerInMonthDTO(
                    (Integer) rowOfResult[0],
                    (String) rowOfResult[1],
                    (String) rowOfResult[2],
                    (String) rowOfResult[3],
                    (Date) rowOfResult[4],
                    (String) rowOfResult[5],
                    (String) rowOfResult[6],
                    (String) rowOfResult[7],
                    (String) rowOfResult[8],
                    (String) rowOfResult[9],
                    (BigDecimal) rowOfResult[10],
                    (Integer) rowOfResult[11]
            );
            listTopCustomerInMonth.add(dto);
        }
        return listTopCustomerInMonth;
    }

    @Override
    public List<CustomerDTO> getListCustomer() {
        String query = "SELECT \n" +
                "u.UserID AS userID,\n" +
                "c.CustomerID AS customerID,\n" +
                "sa.AddressID AS addressID,\n" +
                "u.FullName AS fullName,\n" +
                "u.Email AS email,\n" +
                "u.Password AS passWord,\n" +
                "u.PhoneNumber AS phoneNumber,\n" +
                "u.RegistrationDate AS registrationDate,\n" +
                "sa.Address AS address,\n" +
                "sa.City AS city,\n" +
                "sa.District AS district,\n" +
                "sa.Ward AS ward,\n" +
                "sa.StreetAddress AS streetAddress\n" +
                "FROM Users u\n" +
                "JOIN Customers c ON u.UserID = c.UserID\n" +
                "LEFT JOIN \n" +
                "ShippingAddresses sa ON c.CustomerID = sa.CustomerID " +
                "Where c.Status = 'active' ";
        Query queryNative = entityManager.createNativeQuery(query);
        List<Object[]> resultQuery = queryNative.getResultList();
        List<CustomerDTO> listCustomerDTO = new ArrayList<>();
        for(Object[] rowOfResult : resultQuery) {
            CustomerDTO dto = new CustomerDTO(
                    (Integer) rowOfResult[0],
                    (Integer) rowOfResult[1],
                    (Integer) rowOfResult[2],
                    (String) rowOfResult[3],
                    (String) rowOfResult[4],
                    (String) rowOfResult[5],
                    (String) rowOfResult[6],
                    (Date) rowOfResult[7],
                    (String) rowOfResult[8],
                    (String) rowOfResult[9],
                    (String) rowOfResult[10],
                    (String) rowOfResult[11],
                    (String) rowOfResult[12]
            );
            listCustomerDTO.add(dto);
        }
        return listCustomerDTO;
    }


}
