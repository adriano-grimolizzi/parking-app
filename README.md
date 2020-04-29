# Parking Toll App
Parking Toll App is an application for managing car parkings lots.
It supports different types of Parking Spot types (Gasoline, Electric) and Billing Policies (Hourly, Hourly plus Fixed Amount).

## Installation
This is a Maven project. It can be used in your own project by following these steps:

Do a:
```bash
mvn clean install
```
in the Parking Toll App main directory to install the project into your local .m2 folder.

Then include the following dependency in your own project's pom.xml:
```xml
<dependency>
	<groupId>com.grimolizzi</groupId>
	<artifactId>ParkingApp</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
## Usage

A **TollParking** object represents a single toll parking.
It's comprised of a billing policy and a list of parking spot.

The billing policy is represented by an object that extends the abstract class **BillingPolicy**. 
It's method **computeBill** will be used by the TollParking object to calculate the checkout bill.

You can initialize a TollParking object by passing it a BillingPolicy like this:
```java
HourlyBillingPolicy policy = new HourlyBillingPolicy(2); 
TollParking tollParking = new TollParking(policy);
```
The BillingPolicy constructor takes a single **int** as the hourly rate.

You can then add Parking Spots to the parking like this:
```java
tollParking.getParkingSpotList().add(new ParkingSpot("Code01", PossibleCarType.GASOLINE));
```
The TollParking object has two methods: handleArrival and handleDeparture.
**handleArrival** will return the code of an available spot, or throw a NoAvailableSpotException if all the spot of the same type as the requesting car are used.
```java
ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "AA229AA", timeOfArrival);  
String spotCode = tollParking.handleArrival(request);
```
**handleDeparture** will free the spot and return the bill due, or a CarIsNotPresentException if the car is not present in the parking.
```java
DepartureRequest request = new DepartureRequest("AA229AA", timeOfDeparture);
long bill = tollParking.handleDeparture(request);
```
"AA229AA" is the car's license plate. The times are java.util.Date.

This project uses Lombok to automatically generate getters/setters, among other things.
If you are using IntelliJ you might have to install Lombok IntelliJ plugin.
