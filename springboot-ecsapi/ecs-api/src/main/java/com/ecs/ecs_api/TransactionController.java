package com.ecs.ecs_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Marks this class as a REST controller, meaning it handles HTTP requests
@RestController

//Base URL mapping for all endpoints in this controller
@RequestMapping("/transactions")
public class TransactionController {

 // Automatically injects the TransactionService dependency
 @Autowired
 private TransactionService txnService;

 // Maps HTTP POST requests to /transactions/checkout endpoint
 @PostMapping("/checkout")
 
 // This method handles checkout requests sent from the client (ECS application)
 // The request body is automatically converted from JSON into a CheckoutRequest object
 public ResponseEntity<String> checkout(@RequestBody CheckoutRequest request) {
     
     // Call the service layer to process the checkout logic
     boolean success = txnService.checkoutEquipment (
             request.getEmpID(),
             request.getEquipmentID()
     );

     // If the operation is successful, return HTTP 200 (OK) with a success message
     if(success) {
         return ResponseEntity.ok("Checkout Successful!");
     } 
     
     // If the operation fails, return HTTP 500 (Internal Server Error) with a failure message
     else {
         return ResponseEntity.status(500).body("Checkout Failed!");
     }
 }
}