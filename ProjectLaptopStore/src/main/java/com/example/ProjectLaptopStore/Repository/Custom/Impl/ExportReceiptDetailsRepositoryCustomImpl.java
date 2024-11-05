package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.DTO.ImportExport_ReceiptDTO;
import com.example.ProjectLaptopStore.Repository.Custom.ExportReceiptDetailsRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class ExportReceiptDetailsRepositoryCustomImpl implements ExportReceiptDetailsRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ImportExport_ReceiptDTO> listExportReceipt() {
        String query = "SELECT \n" +
                "    p.ProductName AS productName, \n" +
                "    p.Brand as brand,\n" +
                "    p.Model as model,\n" +
                "    p.Price as price,\n" +
                "    er.ExportDate AS exportDate,          \n" +
                "    er.Exporter AS exporter,             \n" +
                "    erd.Quantity AS quantity              \n" +
                "FROM \n" +
                "    ExportReceiptDetails erd\n" +
                "JOIN \n" +
                "    Products p ON erd.ProductID = p.ProductID\n" +
                "JOIN \n" +
                "    ExportReceipts er ON erd.ExportReceiptID = er.ExportReceiptID;\n";
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
