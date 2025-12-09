# 🍔 Food Delivery App – Backend  
Backend for a Food Delivery Application built using **Spring Boot + MySQL**, featuring **JWT authentication**, **role-based access (Admin/User)**, **Food Management APIs**, and **Order Processing**.

---------------------------------------------------

## 🚀 Features

### 🔐 **Authentication & Authorization**
- User Registration & Login  
- JWT-based authentication  
- Role-based access (Admin / User)  
- Secure endpoints with Spring Security  

---------------------------------------------------

## 👤 **User Features**
- Register a new user  
- Login and receive JWT token  
- View own profile  
- Place orders  
- View own orders  
- Update order status (User can update only their own order)

--------------------------------------------------------

## 🛠️ **Admin Features**
- Add new food items  
- Update food items  
- Delete food items  
- View all orders placed by users  

----------------------------------------------------------

## 🍽️ **Food Management**
- Add / Update / Delete food (Admin only)  
- Get all foods  
- Get food by ID  
- Search food by name  
- Filter food by category  

---------------------------------------------------------

## 📦 **Order Management**
- Place new order  
- Get logged-in user's orders  
- Get all orders (Admin)  
- Delete order  
- Update order status  

-----------------------------------------------------------

## 🧱 Project Structure

src/main/java/com/example/food_demo/
├── controller/ → REST APIs
├── entity/ → JPA Entities
├── service/ → Business Logic
├── repository/ → JPA Repositories
├── jwt/ → JWT Token Utilities
├── exception/ → Custom Exceptions
└── config/ → Security Configuration

------------------------------------------------------------

## 🛢️ **Database (MySQL)**

Tables created:
- `User`
- `Food`
- `Orders`


