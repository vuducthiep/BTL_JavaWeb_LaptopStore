package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.ImportExport_ReceiptDTO;
import com.example.ProjectLaptopStore.Repository.Custom.ImportReceiptDetailsRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
@Transactional
public class ImportReceiptDetailsRepositoryCustomImpl implements ImportReceiptDetailsRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ImportExport_ReceiptDTO> listImportReceipt() {
        String query = "SELECT \n" +
                "    p.ProductName AS productName, \n" +
                "    p.Brand as brand,\n" +
                "    p.Model as model,\n" +
                "    p.Price as price,\n" +
                "    ir.ImportDate AS importDate,          \n" +
                "    ir.Importer AS importer,             \n" +
                "    ird.Quantity AS quantity              \n" +
                "FROM \n" +
                "    ImportReceiptDetails ird\n" +
                "JOIN \n" +
                "    Products p ON ird.ProductID = p.ProductID\n" +
                "JOIN \n" +
                "    ImportReceipts ir ON ird.ImportReceiptID = ir.ImportReceiptID;\n";
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object[]> result = nativeQuery.getResultList();
        List<ImportExport_ReceiptDTO> listImportReceipt = new ArrayList<>();
        for(Object[] rowOfResult : result) {
            ImportExport_ReceiptDTO item = new ImportExport_ReceiptDTO(
                    (String) rowOfResult[0],
                    (String) rowOfResult[1],
                    (String) rowOfResult[2],
                    (Float) rowOfResult[3],
                    (Date) rowOfResult[4],
                    (String) rowOfResult[5],
                    (Integer) rowOfResult[6]
            );
            listImportReceipt.add(item);
        }
        return listImportReceipt;
    }
}
