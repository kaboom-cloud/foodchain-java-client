# FoodchainClient Java

This is a Java client for interacting with the Foodchain API, which provides endpoints for managing lots, orders, procedures, and other resources.

## Prerequisites

- Java Development Kit (JDK) installed
- JSON library for Java (e.g., org.json:json)

## Usage

To use this client, initialize the `FoodchainClient` class with the API base URL and set your API key. Then, call the available methods to interact with the API.

### Example Code

```java
import org.json.JSONObject;

public class FoodchainClientExample {
    public static void main(String[] args) {
        try {
            // Initialize client with the base URL
            FoodchainClient client = new FoodchainClient("https://yourapiurl.com");

            // Set the API key
            client.setApiKey("your-api-key");

            // Create a new lot
            JSONObject lotData = new JSONObject();
            lotData.put("name", "Lot1");
            lotData.put("description", "Description for Lot1");
            System.out.println("Create Lot Response: " + client.createLot(lotData));

            // Create a new order
            JSONObject orderData = new JSONObject();
            orderData.put("orderName", "Order1");
            orderData.put("orderDetails", "Details for Order1");
            System.out.println("Create Order Response: " + client.createOrder(orderData));

            // Get procedure by ID
            System.out.println("Get Procedure Response: " + client.getProcedure("123", null));

            // List orders with status "active"
            System.out.println("List Orders Response: " + client.listOrders("active", null));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

