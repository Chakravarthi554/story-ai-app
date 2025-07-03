# Story AI App

This is a web application that allows users to register, log in, and generate creative stories using an external AI model. It also provides additional related information for the generated stories.

## Features

* **User Authentication:** Secure user registration and login functionality.
* **AI Story Generation:** Generates stories based on user prompts by integrating with a third-party AI API.
* **Additional Information:** Provides supplementary details or related facts for the generated stories.
* **Web-based Interface:** User-friendly interface built with JavaServer Pages (JSP).

## Technology Stack

* **Backend:** Java Servlets
* **Frontend:** JSP (JavaServer Pages), HTML, CSS
* **Build Tool:** Maven
* **Web Server/Servlet Container:** Apache Tomcat 9
* **Database:** MySQL (for user authentication)
* **AI API:** ChatGPT AI Assistant (via RapidAPI)
* **JSON Parsing:** `org.json` library

## Setup Instructions

Follow these steps to get the Story AI App up and running on your local machine.

### Prerequisites

* JDK 8 or higher
* Apache Maven 3.6.0 or higher
* Apache Tomcat 9.x
* MySQL Server (with a user and password, e.g., `root`/`1234`)
* A RapidAPI account and an API key for the "ChatGPT AI Assistant" API.

### 1. Database Setup (MySQL)

1.  Connect to your MySQL server (e.g., using MySQL Workbench or the command line).
2.  Create a new database named `storyai`:
    ```sql
    CREATE DATABASE storyai;
    USE storyai;
    ```
3.  Create a `users` table for user authentication:
    ```sql
    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL
    );
    ```
4.  Verify the database connection details in `AuthServlet.java`:
    ```java
    final String JDBC_URL = "jdbc:mysql://localhost:3306/storyai";
    final String DB_USER = "root"; // Your MySQL username
    final String DB_PASS = "1234"; // Your MySQL password
    ```
    Adjust `DB_USER` and `DB_PASS` if your MySQL credentials are different.

### 2. RapidAPI Key Configuration

1.  Go to [RapidAPI.com](https://rapidapi.com/) and sign up or log in.
2.  Search for and subscribe to the "**ChatGPT AI Assistant**" API.
3.  Find your unique `x-rapidapi-key` on the API's endpoints page.
4.  Update the `apiKey` variable in `GenerateServlet.java` with your actual key:
    ```java
    String apiKey = "YOUR_NEW_RAPIDAPI_KEY_HERE"; // Replace with your RapidAPI Key
    ```
    **Note:** In a production environment, API keys should be stored more securely (e.g., environment variables) rather than hardcoded.

### 3. Build the Project

1.  Navigate to the `story-ai-app` root directory in your terminal (where `pom.xml` is located).
2.  Build the project using Maven:
    ```bash
    mvn clean install
    ```
    This command compiles the code, runs tests, and packages the application into a `.war` file in the `target/` directory (e.g., `story-ai-app.war`).

### 4. Deploy to Apache Tomcat

1.  Start your Apache Tomcat server.
2.  **Using VS Code (Recommended):**
    * Open the VS Code **SERVERS** view.
    * Right-click on your Tomcat server and select "Add Deployment...".
    * Choose the `story-ai-app.war` file from your `target/` directory.
    * Right-click on the deployed `story-ai-app` under Tomcat and select "Redeploy" or right-click the Tomcat server and "Restart".
3.  **Manually:**
    * Copy the generated `story-ai-app.war` file from your `target/` directory to the `webapps` folder of your Tomcat installation (e.g., `apache-tomcat-9.0.x/webapps/`).
    * Start or restart your Tomcat server. Tomcat will automatically deploy the `.war` file.

### 5. Access the Application

Once deployed and Tomcat is running, open your web browser and navigate to the application's login page:
