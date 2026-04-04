package ecsapplication.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//This class acts as a client-side service responsible for communicating
//with the Spring Boot REST API
public class ApiService {

 // Base URL of the backend API (Spring Boot server)
 private final String BASE_URL = "http://localhost:8080";
 
 // Method to send a checkout request to the API
 // Returns true if the request is successful, otherwise false
 public boolean checkoutEquipment(int empID, int equipmentID) {
     
     try {
         // Create an HTTP client to send requests
         HttpClient client = HttpClient.newHttpClient();
         
         // Build JSON payload to match the structure expected by the backend (CheckoutRequest)
         String json = "{ \"empID\": " + empID +
                       ", \"equipmentID\": " + equipmentID + "}";
     
     // Build the HTTP POST request
     HttpRequest request = HttpRequest.newBuilder()
             
             // Set the API endpoint URL
             .uri(URI.create(BASE_URL + "/transactions/checkout"))
             
             // Specify that the request body is in JSON format
             .header("Content-Type", "application/json")
             
             // Attach the JSON payload to the request body using POST method
             .POST(HttpRequest.BodyPublishers.ofString(json))
             
             // Finalize the request
             .build();

     // Send the request and receive the response as a String
     HttpResponse<String> response = client.send(
             request,
             HttpResponse.BodyHandlers.ofString()
     );
     
     // Print the API response for debugging purposes
     System.out.print("Response: " + response.body());
     
     // Return true if HTTP status code is 200 (OK), indicating success
     return response.statusCode() == 200;
     
     } catch (Exception e) {
         
         // Print stack trace if any error occurs (e.g., connection failure)
         e.printStackTrace();
         
         // Return false to indicate failure
         return false;
     }
     
 }
}
