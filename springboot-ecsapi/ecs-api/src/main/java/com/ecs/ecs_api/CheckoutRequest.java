package com.ecs.ecs_api;

//This class represents the data structure of a checkout request
//It is used to transfer data from the client (ECS application) to the backend API
public class CheckoutRequest {

 // Employee ID of the person performing the checkout
 private int empID;

 // Equipment ID of the item being checked out
 private int equipmentID;

 // Getter method for employee ID
 public int getEmpID() { 
     return empID; 
 }

 // Setter method for employee ID
 public void setEmpID(int empID) { 
     this.empID = empID; 
 }

 // Getter method for equipment ID
 public int getEquipmentID() { 
     return equipmentID; 
 }

 // Setter method for equipment ID
 public void setEquipmentID(int equipmentID) { 
     this.equipmentID = equipmentID; 
 }
}