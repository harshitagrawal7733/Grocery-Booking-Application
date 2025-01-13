Project Setup and Running Guide
-------------------------------
-------------------------------

# Grocery Booking Application

## Project Setup and Running Guide

### Prerequisites

Before you start, make sure you have the following installed:

- **Docker**  
  Install Docker by following the instructions for your operating system: [Docker Installation](https://docs.docker.com/get-docker/)

- **Docker Compose**  
  Docker Compose is used to define and manage multi-container Docker applications. Install Docker Compose if you don't have it: [Docker Compose Installation](https://docs.docker.com/compose/install/)

- **Java 17 or Above**  
  Ensure you have JDK 17 or higher installed on your machine.

- **Maven**  
  Maven is used for building and managing dependencies in the project. You can check if Maven is installed by running the following command in your terminal:
  ```bash
  mvn -v
  

Clone the Repository
-------------------
Clone the repository to your local machine:

git clone https://github.com/harshitagrawal7733/qp-assesment
cd qp-assesment

Build the Project
-----------------
Run the following Maven command to build the project:


mvn clean package 

Start the Application
--------------------
Use Docker Compose to build and start the application:


docker-compose up --build


Grocery Booking API Documentation
-----------------------------------

User APIs
---------
This section outlines the user-related functionality, including user creation, checking the user balance, and verifying if a user exists.

1. Create User
    ---------
   This API creates a new user by providing user details such as name, email, password, phone number, and current balance.

Request
URL: /users/app/v1/save-users
Method: POST
Request Body:

{
"name": "John Doe",
"email": "john.doe@example.com",
"password": "securePassword123",
"phoneNumber": "1234567890",
"currentBalance": 500
}

Example curl Command:
bash
Copy code
curl -X POST "http://localhost:8080/users/app/v1/save-users" \
-H "Content-Type: application/json" \
-d '{
"name": "John Doe",
"email": "john.doe@example.com",
"password": "securePassword123",
"phoneNumber": "1234567890",
"currentBalance": 500
}'

Response
Status Code: 201 Created
Body:
json
Copy code
{
"id": 1,
"name": "John Doe",
"email": "john.doe@example.com",
"phoneNumber": "1234567890",
"currentBalance": 500
}



Here’s an updated README section with curl examples for the User APIs, based on the provided controller code:


2. Check User Balance
   -----------------
   This API retrieves the balance of a user identified by their userId.

Request
URL: /users/app/v1/users/get-user/{id}
Method: GET
Path Parameter:
id: The unique identifier for the user (e.g., 1).
Example curl Command:
bash
Copy code
curl -X GET "http://localhost:8080/users/app/v1/users/get-user/1" \
-H "Content-Type: application/json"

Response
Status Code: 200 OK
Body:
json
Copy code
{
"id": 1,
"name": "John Doe",
"email": "john.doe@example.com",
"phoneNumber": "1234567890",
"currentBalance": 500
}

Error Response (if the user is not found):

json
Copy code
{
"error": "User not found"
}

3. Check if User Exists
    -------------------
   This API checks if a user exists in the system by their userId.

Request
URL: /users/app/v1/users/{id}
Method: GET
Path Parameter:
id: The unique identifier for the user (e.g., 1).
Example curl Command:

curl -X GET "http://localhost:8080/users/app/v1/users/1" \
-H "Content-Type: application/json"
Response
Status Code: 200 OK
Body:

{
"message": "User exists"
}
{
"error": "User not found"
}


Grocery Booking Service - OrderService Documentation
-----------------------------------------------------
Overview
The OrderService class is responsible for managing the business logic related to placing orders in the grocery booking application. This includes verifying user balance, checking inventory levels, calculating total amounts for orders, and updating user balances and inventory levels upon successful order placement.

Service Methods:
1. getAllGroceryItems()
   This method retrieves all available grocery items from the inventory.

Request:
Method: GET
Endpoint: /grocery-items
Response:
Status: 200 OK
Body: A list of all grocery items available in the inventory.

2. placeOrder(OrderRequest orderRequest)
   This method processes a new order. It verifies the user's balance, checks the inventory levels, calculates the total order amount, and then places the order. If the order is successfully placed, the user's balance is updated, and the inventory is reduced accordingly.

Request:
Method: POST
Endpoint: /orders
Request Body: The OrderRequest object, which includes:
userId: The ID of the user placing the order.
items: A list of items included in the order. Each item contains:
groceryItemId: The ID of the grocery item.
quantity: The quantity of the item.
json
Copy code
{
"userId": 1,
"items": [
{
"groceryItemId": 1,
"quantity": 2
},
{
"groceryItemId": 2,
"quantity": 3
}
]
}
Response:
Status:
201 Created if the order is placed successfully.
400 Bad Request if there is an error like insufficient balance or insufficient inventory.
Response Body:
On success, a newly created Order object containing:
orderId: Unique identifier for the order.
userId: The user who placed the order.
totalAmount: The total amount of the order.
items: List of items with their details (quantity and price).
json
Copy code
{
"orderId": 123,
"userId": 1,
"totalAmount": 100.50,
"items": [
{
"groceryItemId": 1,
"quantity": 2
},
{
"groceryItemId": 2,
"quantity": 3
}
]
}
Error Responses:
InsufficientBalanceException:

Status: 400 Bad Request
Message: "Insufficient balance to place the order"
GroceryItemNotFoundException:

Status: 404 Not Found
Message: "Grocery item not found"
InsufficientInventoryException:

Status: 400 Bad Request
Message: "Insufficient inventory for item: [item name]"
Error Handling
The placeOrder method uses several custom exceptions to handle common errors that may occur during the order placement process:

GroceryItemNotFoundException: This exception is thrown if a grocery item in the order does not exist in the inventory. The message returned will indicate which item is missing.

InsufficientBalanceException: This exception is thrown if the user does not have enough balance to cover the total cost of the order. The message returned will indicate that there is insufficient balance.

InsufficientInventoryException: This exception is thrown if the requested quantity of any item exceeds the available inventory. The message will indicate the specific item and the issue.

Flow of placeOrder Method:
Fetch User Information:

The user placing the order is retrieved using the userId from the OrderRequest. If the user is not found, a RuntimeException is thrown.
Calculate Total Amount:

For each item in the order request, the system fetches the corresponding grocery item and checks if the requested quantity is available. It calculates the total amount by multiplying the item's price by the quantity requested.
Balance Check:

The user's balance is checked against the total amount. If the balance is insufficient, an InsufficientBalanceException is thrown.
Create and Save Order:

A new order is created with the user ID and the total amount. The order is saved to the database.
Deduct User Balance:

If the order is successfully placed, the user's balance is reduced by the total order amount, and the balance is updated in the database.
Process Each Order Item:

For each item in the order request, an OrderItem object is created. The inventory of each grocery item is updated by reducing the quantity based on the order.
Save Updated Order and Items:

The order with its items is saved to the database, and inventory levels are updated.
Example Curl Commands:
1. Create User:
   bash
   Copy code
   curl -X POST "http://localhost:8080/users/app/v1/save-users" \
   -H "Content-Type: application/json" \
   -d '{
   "name": "John Doe",
   "email": "john.doe@example.com",
   "password": "securePassword123",
   "phoneNumber": "1234567890",
   "currentBalance": 500
   }'
2. Place Order:
   bash
   Copy code
   curl -X POST "http://localhost:8080/orders" \
   -H "Content-Type: application/json" \
   -d '{
   "userId": 1,
   "items": [
   {
   "groceryItemId": 1,
   "quantity": 2
   },
   {
   "groceryItemId": 2,
   "quantity": 3
   }
   ]
   }'
3. Error Handling Example:
   If the user does not have enough balance:

bash
Copy code
curl -X POST "http://localhost:8080/orders" \
-H "Content-Type: application/json" \
-d '{
"userId": 1,
"items": [
{
"groceryItemId": 1,
"quantity": 10
}
]
}'
Response:

json
Copy code
{
"status": 400,
"message": "Insufficient balance to place the order"
}


Grocery Booking Service - GroceryItemController Documentation
Overview
The GroceryItemController class is responsible for handling HTTP requests related to grocery items and inventory management. It provides endpoints for adding new grocery items, retrieving all grocery items, updating existing items, deleting items, and updating the inventory levels for specific grocery items.

Controller Methods:
1. addGroceryItem(GroceryItem groceryItem)
   This method is responsible for adding a new grocery item to the system.

Request:
Method: POST
Endpoint: /grocery/app/v1/admin/grocery-items
Request Body: A GroceryItem object that contains:
name: The name of the grocery item.
price: The price of the grocery item.
inventoryLevel: The initial stock level for the grocery item.
json
Copy code
{
"name": "Apple",
"price": 1.50,
"inventoryLevel": 100
}
Response:
Status: 201 Created
Response Body: A success message confirming that the grocery item was added.
json
Copy code
{
"message": "Grocery item added successfully"
}
2. getAllGroceryItems()
   This method retrieves all the grocery items currently in the inventory.

Request:
Method: GET
Endpoint: /grocery/app/v1/admin/grocery-items
Response:
Status: 200 OK
Response Body: A list of all GroceryItem objects.
json
Copy code
[
{
"id": 1,
"name": "Apple",
"price": 1.50,
"inventoryLevel": 100
},
{
"id": 2,
"name": "Banana",
"price": 1.20,
"inventoryLevel": 200
}
]
3. deleteGroceryItem(Long id)
   This method deletes a grocery item from the inventory by its ID.

Request:
Method: DELETE
Endpoint: /grocery/app/v1/admin/grocery-items/{id}
Path Variable: id - The ID of the grocery item to delete.
Response:
Status: 200 OK
Response Body: A success message confirming that the grocery item was deleted.
json
Copy code
{
"message": "Grocery item deleted successfully"
}
4. updateGroceryItem(Long id, GroceryItem groceryItem)
   This method updates an existing grocery item based on its ID.

Request:
Method: PUT
Endpoint: /grocery/app/v1/admin/grocery-items/{id}
Path Variable: id - The ID of the grocery item to update.
Request Body: A GroceryItem object containing the updated details:
name: The new name of the grocery item (optional).
price: The new price of the grocery item (optional).
inventoryLevel: The new inventory level (optional).
json
Copy code
{
"name": "Green Apple",
"price": 1.80,
"inventoryLevel": 150
}
Response:
Status: 200 OK
Response Body: A success message confirming that the grocery item was updated.
json
Copy code
{
"message": "Grocery item updated successfully"
}
5. updateInventoryLevel(Long id, InventoryUpdateRequest request)
   This method updates the inventory level of a specific grocery item by its ID.

Request:
Method: PATCH
Endpoint: /grocery/app/v1/admin/grocery-items/{id}/inventory
Path Variable: id - The ID of the grocery item to update.
Request Body: An InventoryUpdateRequest object containing:
newInventoryLevel: The new inventory level to set for the item.
json
Copy code
{
"newInventoryLevel": 200
}
Response:
Status: 200 OK
Response Body: A success message confirming that the inventory level was updated.
json
Copy code
{
"message": "Inventory level updated successfully"
}
Error Handling
The controller uses standard HTTP status codes for error handling:

400 Bad Request: This status code may be returned if the request body is missing required fields or is invalid.
404 Not Found: This status code may be returned if the grocery item ID provided for update or delete operations does not exist in the system.
500 Internal Server Error: This status code may be returned if there is an unexpected server error while processing the request.
Example Curl Commands:
1. Add Grocery Item:
   bash
   Copy code
   curl -X POST "http://localhost:8080/grocery/app/v1/admin/grocery-items" \
   -H "Content-Type: application/json" \
   -d '{
   "name": "Apple",
   "price": 1.50,
   "inventoryLevel": 100
   }'
2. Get All Grocery Items:
   bash
   Copy code
   curl -X GET "http://localhost:8080/grocery/app/v1/admin/grocery-items"
3. Delete Grocery Item:
   bash
   Copy code
   curl -X DELETE "http://localhost:8080/grocery/app/v1/admin/grocery-items/1"
4. Update Grocery Item:
   bash
   Copy code
   curl -X PUT "http://localhost:8080/grocery/app/v1/admin/grocery-items/1" \
   -H "Content-Type: application/json" \
   -d '{
   "name": "Green Apple",
   "price": 1.80,
   "inventoryLevel": 150
   }'
5. Update Inventory Level:
   bash
   Copy code
   curl -X PATCH "http://localhost:8080/grocery/app/v1/admin/grocery-items/1/inventory" \
   -H "Content-Type: application/json" \
   -d '{
   "newInventoryLevel": 200
   }'
Clean Up
   To stop the application and remove containers, networks, and volumes, run:
   docker-compose down


# 1. GroceryItem (inventory_level)
   This table stores the details of each grocery item available for booking.


CREATE TABLE grocery_item (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price DECIMAL(10, 2) NOT NULL,
inventory_level INT NOT NULL
);

# 2. User (user_data)
   This table stores user details such as their name, email, password, phone number, and balance.


CREATE TABLE user_data (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
phone_number VARCHAR(15),
current_balance DECIMAL(10, 2) NOT NULL
);

# 3. Order (orders)
   This table stores the order information including the user ID (who placed the order) and the total amount for the order.


CREATE TABLE orders (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
total_amount DECIMAL(10, 2) NOT NULL,
FOREIGN KEY (user_id) REFERENCES user_data(id)
);

# 4. OrderItem (order_item)
   This table stores the relationship between orders and the grocery items. Each order can have multiple items.

CREATE TABLE order_item (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
order_id BIGINT NOT NULL,
grocery_item_id BIGINT NOT NULL,
quantity INT NOT NULL,
FOREIGN KEY (order_id) REFERENCES orders(id),
FOREIGN KEY (grocery_item_id) REFERENCES grocery_item(id)
);

Database Design Explanation
GroceryItem Table: Stores all the details related to grocery items like their name, price, and inventory level. The inventory level helps in tracking how much of the item is available for booking.

User Table: Stores all the details about users who make the orders. The current_balance field can be useful for handling order payments and user balances.

Order Table: Tracks individual orders placed by users. Each order has a reference to the user placing it (via user_id) and the total amount of the order.

OrderItem Table: A many-to-one relationship between orders and grocery items, with the quantity of each item specified in the order. An order can have multiple items, and an item can be part of many orders.