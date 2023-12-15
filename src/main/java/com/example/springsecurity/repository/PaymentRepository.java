package com.example.springsecurity.repository;

import com.example.springsecurity.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

   @Query
   List<Payment> findByEmail(String email);

   @Query("SELECT MONTH(p.date) as month, SUM(p.totalSales) as totalSales " +
           "FROM Payment p " +
           "WHERE p.status = 'successful' " +
           "AND YEAR(p.date) = YEAR(CURRENT_DATE) " +
           "GROUP BY MONTH(p.date) " +
           "ORDER BY MONTH(p.date)")
   List<Object[]> getMonthlySales();


   @Query("SELECT YEAR(p.date) as year, SUM(p.totalSales) as totalSales " +
           "FROM Payment p " +
           "WHERE p.status = 'successful' " +
           "GROUP BY YEAR(p.date) " +
           "ORDER BY YEAR(p.date)")
   List<Object[]> getYearlySales();

   @Query("SELECT SUM(p.totalSales) FROM Payment p " +
           "WHERE p.status = 'successful' " +
           "AND DATE(p.date) = CURRENT_DATE")
   Double getTodaySales();

   @Query("SELECT SUM(p.totalSales) FROM Payment p WHERE p.status = 'successful'")
   Double getTotalSales();

}