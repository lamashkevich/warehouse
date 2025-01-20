# Warehouse Management System

## Description
This is a lightweight version of the [Auto Parts Store](https://github.com/lamashkevich/auto-parts-store).

The Warehouse Management System is an application designed for:
- Manage products and their prices.
- Sales of products
- Retrieve statistics.

---
## Technologies

- **Java 21**
- **Spring Boot 3.4**
- **Hibernate**
- **H2 Database**
- **Flyway**
- **Maven**
- **WebClient** (for interacting with external APIs)
- **JUnit 5** (for testing)
- **Angular** (for frontend)

---
## Installation and Running

### 1. **Clone the repository:**

```bash
git clone https://github.com/lamashkevich/warehouse.git
cd warehouse
```

### 2. **Set environment variables:**
Before running the application, set the following environment variables:

- #### For Linux/MacOS:

```bash
export SHATEM_LOGIN=login
export SHATEM_PASSWORD=password
```

- #### **For Windows (Command Prompt):**

```bash
set SHATEM_LOGIN=login
set SHATEM_PASSWORD=password
```

- #### **For Windows (PowerShell):**
```bash
$env:SHATEM_LOGIN="login"
$env:SHATEM_PASSWORD="password"
```

### 3. **Build the project using Maven:**
```bash
mvn clean install
This will create a .jar file in the target directory.
```


### 4. **Run the application:**
You can run the application in one of the following ways:

- #### Option 1: Using Maven

```bash
mvn spring-boot:run
```


- #### Option 2: Using the generated JAR file

After building the project, navigate to the target directory and run the .jar file:

```bash
cd target
java -jar warehouse-<version>.jar
```

Replace <version> with the actual version of your application (e.g., warehouse-0.0.1.jar).

The application will be available at: http://localhost:8081.
