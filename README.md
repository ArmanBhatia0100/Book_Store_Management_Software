# Library Management System 📚

![Java](https://img.shields.io/badge/Java-17-blue)
![Swing](https://img.shields.io/badge/Swing-GUI-orange)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![License](https://img.shields.io/badge/License-MIT-green)

Welcome to the **Library Management System**, a desktop application built by **Arman Bhatia** using **Java Swing** for an intuitive user interface and **JDBC** with **MySQL Connector/J** for robust database connectivity. This project simulates a real-world library, enabling efficient management of books, members, and borrowing/returning transactions, all structured with the **MVC architecture**.

---

## ✨ Features

### Core Features
- **Book Management** 📖
  - Add, edit, delete, or archive books
  - View the complete book catalog
- **Member Management** 👤
  - Register new members
  - Update or delete member records
  - Track borrowing history
- **Book Issuing & Returning** 🔄
  - Issue books to members
  - Return books with automatic fine calculation for overdue returns
  - Prevent multiple issuances of the same book
- **Search Functionality** 🔍
  - Search books by title, author, or ISBN
  - Filter books by status (available/issued)
- **User Roles** (Optional for MVP)
  - **Admin**: Full access to manage books, members, and system settings
  - **Librarian**: Limited access to issue and return books

### Future Features
- Export transaction reports as PDF
- Auto-generated book and member IDs
- Overdue reminders via pop-up notifications
- Dark/light theme support for the GUI
- Daily transaction and overdue book reports

---

## 🛠️ Tech Stack

| Component          | Tool/Library         |
|--------------------|----------------------|
| **GUI**            | Java Swing          |
| **Database**       | MySQL               |
| **Database Access**| JDBC (MySQL Connector/J) |
| **Build Tool**     | IntelliJ / Eclipse  |
| **Version Control**| Git + GitHub        |

---

## 🏗️ Architecture

The application follows the **MVC (Model-View-Controller)** architecture for clean code organization:

- **Model**: Data classes (`Book`, `Member`, `Transaction`, `Authentication`) for business logic
- **View**: Java Swing classes for the user interface
- **Controller**: Handles user interactions and application logic
- **Database**: Manages MySQL connections and queries
- **Utils**: Helper classes for input validation and utilities

### Package Structure
```
com.library
├── model         // Data classes (Book, Member, etc.)
├── view          // Swing GUI classes
├── controller    // Event handling and logic
├── database      // MySQL connection and operations
├── utils         // Helper classes (e.g., validators)
```

---

## 🗄️ Database Schema

The system uses **MySQL** with the following tables to store data persistently:

### 1. `authentication`
Stores user credentials and roles.
```sql
CREATE TABLE authentication (
    auth_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'librarian') DEFAULT 'librarian' NULL
);
```

### 2. `books`
Manages book inventory.
```sql
CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NULL,
    isbn VARCHAR(20) UNIQUE NULL,
    status VARCHAR(15) NULL,
    added_date VARCHAR(30) NULL
);
```

### 3. `members`
Stores member information.
```sql
CREATE TABLE members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NULL,
    phone VARCHAR(20) NULL,
    registration_date VARCHAR(20) NULL
);
```

### 4. `transactions`
Tracks book borrowing and returning.
```sql
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NULL,
    member_id INT NULL,
    issue_date DATE NOT NULL,
    return_date DATE NULL,
    fine DECIMAL(5,2) DEFAULT 0.00 NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);
CREATE INDEX book_id ON transactions(book_id);
CREATE INDEX member_id ON transactions(member_id);
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher
- **MySQL** (version 8.0 or later recommended)
- **MySQL Connector/J** (JDBC driver)
- **IntelliJ IDEA** or **Eclipse** (recommended)
- **Git** for version control
- MySQL Workbench (optional for database management)

### Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/ArmanBhatia0100/Book_Store_Management_Software.git
   cd Book_Store_Management_Software
   ```

2. **Set up MySQL**:
   - Ensure MySQL is installed and running.
   - Create a database:
     ```sql
     CREATE DATABASE library_db;
     ```
   - Use the database:
     ```sql
     USE library_db;
     ```
   - Run the SQL schema provided above to create the tables.
   - Example command in MySQL terminal or Workbench:
     ```bash
     mysql -u yourusername -p library_db < schema.sql
     ```
     (Save the schema in a `schema.sql` file or execute it directly in MySQL Workbench.)

3. **Configure the database connection**:
   - Update the database configuration in `database/DatabaseConnection.java` with your MySQL credentials:
     ```java
     String url = "jdbc:mysql://localhost:3306/library_db";
     String username = "your_mysql_username";
     String password = "your_mysql_password";
     ```

4. **Add MySQL Connector/J**:
   - Download the [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) JAR file (e.g., `mysql-connector-j-8.x.x.jar`).
   - Add it to your project’s classpath in IntelliJ or Eclipse.

5. **Build and run**:
   - Open the project in IntelliJ or Eclipse.
   - Ensure the MySQL Connector/J JAR is included in the project dependencies.
   - Build the project and run the `Main` class to launch the application.

---

## 📖 Usage

1. **Launch the Application**:
   - Start the app to access the login screen or main dashboard.
2. **Admin Features**:
   - Add, edit, or delete books and members.
   - Manage user accounts and view reports (if implemented).
3. **Librarian Features**:
   - Issue or return books with real-time status updates.
4. **Search & Filter**:
   - Search for books by title, author, or ISBN.
   - Filter by availability status.
5. **Optional Features** (if implemented):
   - Toggle dark/light themes.
   - Export transaction reports or view overdue reminders.

---

## 🤝 Contributing

Contributions are welcome to improve the Library Management System! To contribute:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`.
3. Commit your changes: `git commit -m "Add your message"`.
4. Push to the branch: `git push origin feature/your-feature-name`.
5. Open a Pull Request.

Please follow the [Code of Conduct](CODE_OF_CONDUCT.md) and adhere to the project’s coding standards.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 🙏 Acknowledgments

- Designed and developed by **Arman Bhatia**
- Inspired by real-world library management workflows
- Built with Java, MySQL, and the support of the open-source community

---

## 📬 Contact

For questions, suggestions, or feedback, reach out to Arman at [arman.bhatia.1407gmail.com](mailto:arman.bhatia.1407@gmail.com) or open an issue on GitHub.

Happy coding! 🚀
```
