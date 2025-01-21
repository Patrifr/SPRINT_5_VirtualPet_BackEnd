# Virtual Pet Application API
This project is a virtual pet application that allows users to care for and personalize their own virtual pets. 
It uses artificial intelligence (AI) to generate the frontend, while the backend is implemented in Java with a focus 
on security using JWT (JSON Web Tokens) for authentication and role management.
The API handles user registration, pet creation, interaction with pets and roles, ensuring that users and admins have appropriate access based on them.

## Technologies Used
* __Java 21__
* __Spring Boot__ for backend implementation.
* __JWT (JSON Web Token)__ for authentication and authorization.
* __MySQL__ for user and pet management.
* __React__ for the interactive frontend.
* __Artificial Intelligence (AI)__ for generating the frontend components.

## Installation and Setup

### Prerequisites

* __Java 21__
* __Maven 4.0__ or higher.
* __MySQL__ installed and running on default ports.
* __Visual Studio Code (VS Code)__ or any code editor of your choice for frontend development (React).

### Getting Started
**1. Clone the repository**
  Clone this repository on your computer and install all depencencies using: 
  `mvn clean install`

**2. Configute the databases**
  * MySQL
    1. Set up MySQL user credentials in `application.properties`.
    2. The default port is 3306, change it if needed in `application.properties`.
    3. Run the MySQL server and create the database `virtual_pet`.

  **3. Run the application**
  
      mvn spring-boot:run
      
   **4. Set up the frontend**
     To run the frontend, use Visual Studio Code and navigate to the frontend project directory.
     Install dependencies and start the React application with the following commands:
     `npm install`
     `npm start`
     
  The API will be accessible at `http://localhost:8080`.

## API Endpoints

### Authentication
  * **Sign Up**
      * **POST** `/api/v1/auth/signup`
      * **Description:** Registers a new user and returns a JWT token.
      * **Request Body (JSON):**
        ```
        
          {
            "username": "newUser",
            "email": "newUser@example.com",
            "password": "userPassword"
          }
        
        ```
        
   * **Sign In**
     * **POST** `/api/v1/auth/signin`
     * **Description:** Authenticates an existing user and returns a JWT token.
     * **Request Body (JSON):**
        ```
        
          {
            "username": "existingUser",
            "password": "userPassword"
          }
        
        ```

### Pet Management

  * **Create Pet**
    * **POST** `/api/pet/new`
    * **Description:** Creates a new pet for the authenticated user.
    * **Request Body (JSON):**
      ```
      {
        "userId": userId,
        "petName": "petName",
        "petType": "FAIRY",
        "petColor": "RED"
      }
      
      ```
    * **Response:** 201 (Created).

      
  * **Delete Pet**
    * **DELETE ** `/api/pet/delete`
    * **Description:** Deletes a pet by ID for the authenticated user.
    * **RequestParam:** (Integer)  `petId` --> ID of the pet to delete.
    * **Response:** 204 No Content.


  * **Get One Pet**
    * **GET** `/api/pet/get`
    * **Description:** Retrieves details of a specific pet for the authenticated user.
    * **RequestParam:** (Integer)  `petId` --> ID of the pet to retrieve the details.
    * **Response:** 200 (OK) and pet details.


   * **Get All Pets from User**
    * **GET** `/api/pet/getUserPets`
    * **Description:** Retrieves all pets for a specific user.
    * **RequestParam:** (Integer)  `userId` --> ID of the user.
    * **Response:** 200 (OK) and a list of that user's pets and their details.


  * **Get All Pets (Admin Only)**
    * **GET** `/api/pet/getAll`
    * **Description:** Retrieves all pets in the system. Accessible only to users with the `ADMIN` role.
    * **RequestParam:** (Integer)  `userId` --> ID of the user.
    * **Response:** 200 (OK) and a list of all pets in the system and their details.

   
  * **Update Pet**
    * **PUT** `/api/pet/update`
    * **Description:** Updates the attributes of an existing pet for the authenticated user.
    * **RequestParam:** (Integer)  `petId` --> ID of the pet to update.
    * * **Request Body (JSON):**
      ```
      {
        "location": "FOREST",
        "accessory": "NONE",
        "petInteraction": "FEED"
      }  
      ```
    * **Response:** 200 (OK).

## Role Management
  * __User Roles__
      * __Description:__ There are two roles implemented in the system:
          * __ROLE_USER:__ Can only access their own pets for viewing, updating, and deleting.
          * __ROLE_ADMIN:__ Can access and manage all pets in the system, regardless of ownership.

## Frontend Implementation with AI
This project uses artificial intelligence to generate the interactive frontend for the application, including user interfaces for managing virtual pets. 
ChatGPT was used to generate frontend components, and they were adjusted to fit the visual and functional requirements of the application.
The AI provided quick solutions for components like registration forms, pet visualization, and interaction screens.

