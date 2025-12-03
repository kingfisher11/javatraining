# DAY 3 — Student Result System (Backend + SQLite)

In Day 3, you will upgrade your Day 2 Java HTTP server into a complete backend for a **Student Result System**, using a real database (SQLite) to store:

- Students
- Subjects & marks
- Grade & point calculation
- GPA computation

This guide explains step-by-step how to set up the database, create the necessary tables, and build CRUD API endpoints.

---

# 1. Overview of Today’s System

By the end of Day 3 - Part 1, you will have:

### ✔ SQLite database (`result.db`)  
### ✔ CRUD for Students  
### ✔ CRUD for Subject Results  
### ✔ Automatic Grade + Point calculation from marks  
### ✔ GPA (CPA) calculation for each student  
### ✔ A full REST-like API for the frontend (Day 4)

This replaces yesterday’s score demo and transforms it into a complete academic mini-system.

---

# 2. Project Structure

We continue using the same structure from Day 2:
```
JavaTraining/
├── src/
│ ├── Main.java
│ ├── DB.java
│ ├── result.db
└── web/
├── index.html
├── script.js
└── style.css
```


---

# 3. SQLite Database Setup

We will use **SQLite** because:

- No installation required
- Works on any student laptop
- Creates a simple `.db` file
- Easy to use with Java (JDBC)

## 3.1 Download SQLite JDBC Driver

Download the latest `sqlite-jdbc-x.x.x.jar` from:

https://github.com/xerial/sqlite-jdbc/releases

Create a `lib/` folder and place the JAR inside it.

### Add the JAR to IntelliJ:

1. Go to **File → Project Structure**
2. Select **Modules → Dependencies**
3. Click **+ (Add)** → *JARs or Directories*
4. Select the downloaded SQLite JAR
5. Set Scope → **Compile**
6. Click **Apply → OK**

The backend can now use SQLite.

---

# 4. Database Tables for Result System

We will have two tables:

## 4.1 Student Table

```
students (
id INTEGER PRIMARY KEY AUTOINCREMENT,
name TEXT NOT NULL,
matric TEXT NOT NULL
)
```

## 4.2 Result Table

```
results (
id INTEGER PRIMARY KEY AUTOINCREMENT,
student_id INTEGER NOT NULL,
subject TEXT NOT NULL,
credit_hour INTEGER NOT NULL,
mark INTEGER NOT NULL,
grade TEXT NOT NULL,
point REAL NOT NULL,
FOREIGN KEY(student_id) REFERENCES students(id)
)
```

# 5. Create the `DB.java` Helper Class

Create a new Java file `DB.java` in your backend folder.

```
import java.sql.*;

public class DB {

    private static final String URL = "jdbc:sqlite:result.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void initDatabase() throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            String studentTable = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    matric TEXT NOT NULL
                );
            """;

            String resultTable = """
                CREATE TABLE IF NOT EXISTS results (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER NOT NULL,
                    subject TEXT NOT NULL,
                    credit_hour INTEGER NOT NULL,
                    mark INTEGER NOT NULL,
                    grade TEXT NOT NULL,
                    point REAL NOT NULL,
                    FOREIGN KEY(student_id) REFERENCES students(id)
                );
            """;

            stmt.executeUpdate(studentTable);
            stmt.executeUpdate(resultTable);

            System.out.println("Database ready with tables: students, results");
        }
    }
}
```

# 6. Updating Main.java

We will keep Day 2’s HTTP server and add more API endpoints.

Add these imports at the top:
```
import org.json.JSONObject;
import java.sql.*;
```

Add the new API contexts:
```
server.createContext("/student/add", new StudentAddHandler());
server.createContext("/student/list", new StudentListHandler());
server.createContext("/student/delete", new StudentDeleteHandler());

server.createContext("/result/add", new ResultAddHandler());
server.createContext("/result/list", new ResultListHandler());
server.createContext("/result/delete", new ResultDeleteHandler());

server.createContext("/result/gpa", new ResultGPAHandler());
```

CORS headers from Day 2 can remain unchanged.

# 7. Grade & Point Conversion Helpers

Add these static helper methods inside Main class:
```
public static double convertMarkToPoint(int mark) {
    if (mark >= 90) return 4.0;
    if (mark >= 80) return 4.0;
    if (mark >= 75) return 3.7;
    if (mark >= 70) return 3.3;
    if (mark >= 65) return 3.0;
    if (mark >= 60) return 2.7;
    if (mark >= 55) return 2.3;
    if (mark >= 45) return 2.0;
    return 0.0;
}

public static String convertMarkToGrade(int mark) {
    if (mark >= 90) return "A+";
    if (mark >= 80) return "A";
    if (mark >= 75) return "A-";
    if (mark >= 70) return "B+";
    if (mark >= 65) return "B";
    if (mark >= 60) return "B-";
    if (mark >= 55) return "C+";
    if (mark >= 45) return "C";
    return "F";
}
```

# 8. Student CRUD Endpoints

## 8.1 Add Student — POST /student/add
```
static class StudentAddHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("POST".equals(exchange.getRequestMethod())) {

            String body = new String(exchange.getRequestBody().readAllBytes());
            JSONObject json = new JSONObject(body);

            String name = json.getString("name");
            String matric = json.getString("matric");

            try (Connection conn = DB.getConnection()) {
                String sql = "INSERT INTO students (name, matric) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, matric);
                stmt.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }

            String response = "{ \"status\": \"success\" }";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
        }
    }
}
```
## 8.2 List Students — GET /student/list
```
static class StudentListHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        JSONArray arr = new JSONArray();

        try (Connection conn = DB.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students");

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("name", rs.getString("name"));
                obj.put("matric", rs.getString("matric"));
                arr.put(obj);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        String response = arr.toString();
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
    }
}
```
# 9. Result CRUD Endpoints

## 9.1 Add Result — POST /result/add
```
static class ResultAddHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("POST".equals(exchange.getRequestMethod())) {

            String body = new String(exchange.getRequestBody().readAllBytes());
            JSONObject json = new JSONObject(body);

            int studentId = json.getInt("student_id");
            String subject = json.getString("subject");
            int credit = json.getInt("credit_hour");
            int mark = json.getInt("mark");

            String grade = convertMarkToGrade(mark);
            double point = convertMarkToPoint(mark);

            try (Connection conn = DB.getConnection()) {
                String sql = "INSERT INTO results (student_id, subject, credit_hour, mark, grade, point) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, studentId);
                stmt.setString(2, subject);
                stmt.setInt(3, credit);
                stmt.setInt(4, mark);
                stmt.setString(5, grade);
                stmt.setDouble(6, point);
                stmt.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }

            String response = "{ \"status\": \"success\" }";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
        }
    }
}
```

# 10. GPA Calculation — GET /result/gpa?student_id=1
```
static class ResultGPAHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();
        int studentId = Integer.parseInt(query.split("=")[1]);

        double totalCredits = 0;
        double totalPoints = 0;

        try (Connection conn = DB.getConnection()) {
            String sql = "SELECT credit_hour, point FROM results WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int credit = rs.getInt("credit_hour");
                double point = rs.getDouble("point");

                totalCredits += credit;
                totalPoints += (credit * point);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        double gpa = (totalCredits == 0) ? 0 : (totalPoints / totalCredits);

        String response = "{ \"gpa\": " + gpa + " }";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
    }
}
```

# 11. What’s Next?

This completes Day 3 Backend - Part 1.

Next part, we will build the frontend web app:

✔ Add Student form
✔ Add Subject form
✔ List Results
✔ Display GPA
✔ Buttons for delete/edit
✔ Fetch requests to call the API

# 🟦 Download

Download the official json.jar:

📥 Direct download (safe & official):
https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar

Download the official json.jar:
👉 https://repo1.maven.org/maven2/org/json/json/20210307/json-20210307.jar
