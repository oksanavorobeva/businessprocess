package com.example.sales_metrics_worker.persistence.repository;

import com.example.sales_metrics_worker.persistence.model.SalesReportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SalesReportRepository extends JpaRepository<SalesReportData, Long> {

    @Query("SELECT s FROM SalesReportData s WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate")
    List<SalesReportData> findSalesDataBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM SalesReportData s WHERE s.productName = :productName")
    List<SalesReportData> findSalesDataByProductName(@Param("productName") String productName);
}