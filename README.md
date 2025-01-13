# Grocery-Booking-Application

Database Design
Tables:
Users:

id (Primary Key)
username (Unique)
password (Encrypted)
role (ENUM: ADMIN, USER)
Groceries:

id (Primary Key)
name
price
stock
Orders:

id (Primary Key)
user_id (Foreign Key to Users)
total_price
created_at
Order_Items:

id (Primary Key)
order_id (Foreign Key to Orders)
grocery_id (Foreign Key to Groceries)
quantity
price


