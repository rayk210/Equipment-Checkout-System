package com.ecs.ecs_api;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

//Marks this class as a service component in Spring Boot
//This class contains the business logic for handling transactions
@Service
public class TransactionService {

 // This method processes the checkout operation
 // It updates the equipment status and inserts a new transaction record into the database
 public boolean checkoutEquipment(int empID, int equipmentID) {
     
     // Try-with-resources ensures the database connection is automatically closed
     try (Connection conn = DBConnect.getInstance().getConnection()) {
         
         // Disable auto-commit to manually control the transaction
         conn.setAutoCommit(false);

         // -------------------------------
         // 1. Update equipment status
         // -------------------------------
         
         // SQL query to mark the equipment as "Loaned"
         String updateSQL = "UPDATE equipment SET equipStatus = 'Loaned' WHERE equipmentID = ?";
         
         // Prepare and execute the update statement
         try (PreparedStatement stmtUpdate = conn.prepareStatement(updateSQL)) {
             stmtUpdate.setInt(1, equipmentID);
             stmtUpdate.executeUpdate();
         }

         // -------------------------------
         // 2. Insert new transaction record
         // -------------------------------
         
         // SQL query to insert a new transaction into the transaction table
         String insertSQL = "INSERT INTO transaction " +
                 "(empID, equipmentID, borrowDate, expectedReturnDate, transactionStatus, checkoutCondition) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
         
         // Prepare and execute the insert statement
         try (PreparedStatement stmtInsert = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
             
             // Set values for each column in the transaction table
             stmtInsert.setInt(1, empID);                                              // Employee ID
             stmtInsert.setInt(2, equipmentID);                                        // Equipment ID
             stmtInsert.setDate(3, java.sql.Date.valueOf(LocalDate.now()));            // Borrow date (today)
             stmtInsert.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(7)));// Expected return date (+7 days)
             stmtInsert.setString(5, "Borrowed");                                      // Transaction status
             stmtInsert.setString(6, "Good");                                          // Equipment condition at checkout

             // Execute the insert operation
             stmtInsert.executeUpdate();
         }

         // Commit both operations (update + insert) as a single transaction
         conn.commit();

         // Return true to indicate success
         return true;

     } catch (Exception e) {
         
         // Print error details for debugging
         e.printStackTrace();

         // Return false to indicate failure
         return false;
     }
 }
}