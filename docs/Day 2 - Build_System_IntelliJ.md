# Day 2 — Building a Simple Web Application (Java Backend + HTML Frontend)

Welcome to **Day 2**!  
Today students will learn how to create a **real web application** that:

- Uses **Java** as the backend (API)
- Uses **HTML, CSS, and JavaScript** as the frontend (UI)
- Runs in a browser
- Uses **Node.js + npx serve** to host the frontend
- Uses **Java HTTP Server** to serve backend API

This is their first **full-stack Java project**.

---

# 1. Overview of Today’s Project

Students will build:

### ✔ Backend (Java)
- Runs on: `http://localhost:8080`
- Endpoint: `/grade`
- Returns JSON: `{ "name": "...", "grade": "A/B/C/Fail" }`
- Includes CORS support
- Runs using IntelliJ

### ✔ Frontend (HTML + CSS + JS)
- Runs on: `http://localhost:3000`
- UI form for name + score
- Fetches result from backend API
- Displays grade on screen
- Runs using `npx serve`

---

# 2. Project Folder Structure

Students should create the following structure:

```
JavaTraining/
├── backend/
│ └── Main.java
└── web/
├── index.html
├── style.css
└── script.js
```


The backend and frontend run separately.

Backend = Java  
Frontend = Node.js static server

---

# 3. Setting Up Node.js (Frontend Requirement)

Check if Node.js is installed:

```
node -v
```
If version appears → Node.js installed.

If not, download from:

👉 https://nodejs.org/

During installation:

- Leave default options
- Ensure PATH is enabled

# 4. Writing the Backend (Java)

Open IntelliJ, create a new Java project.
Inside backend/, create Main.java and paste this:

```
// Import Library Section

public class Main {
    public static void main(String[] args) throws IOException {
        // Main method
    }

    static class GradeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // CORS Headers

            // Handle pre-flight OPTIONS request (sent automatically by browser)
            // If browser sends OPTIONS, respond with empty 204 and return


            // ==============================================================
            // PROCESS GET REQUEST (Browser calls this when fetching grade)
            // ==============================================================


        }
    }
}
```
# 5. Creating the Frontend

Inside /web/, create 3 files:

## 5.1 index.html
```
<!DOCTYPE html>
<html>
<head>
    <title>Student Grade Checker</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    <div class="container">
        <h2>Student Grade Checker</h2>

        <label>Student Name:</label>
        <input id="name" type="text">

        <label>Score (0 - 100):</label>
        <input id="score" type="number">

        <button onclick="checkGrade()">Check Grade</button>

        <div id="result"></div>
    </div>

    <script src="script.js"></script>
</body>
</html>
```
## 5.2 style.css
```
body {
    font-family: Arial;
    background: #f0f0f0;
}

.container {
    width: 350px;
    margin: 70px auto;
    padding: 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 0 10px #ccc;
}

label {
    margin-top: 10px;
    display: block;
}

input {
    width: 100%;
    padding: 8px;
}

button {
    width: 100%;
    padding: 10px;
    margin-top: 10px;
    background: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}
```

## 5.3 script.js
```
function checkGrade() {
    let name = document.getElementById("name").value;
    let score = document.getElementById("score").value;

    fetch(`http://localhost:8080/grade?name=${name}&score=${score}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById("result").innerHTML =
                `${data.name} got grade: <b>${data.grade}</b>`;
        })
        .catch(err => {
            document.getElementById("result").innerText = "Error contacting server.";
        });
}
```

# 6. Running the Project

## Step 1 — Run Backend (Java)

In IntelliJ:

Run Main.java

Terminal output should say:
```
Server running at http://localhost:8080
```

## Step 2 — Run Frontend using Node.js

In the /web folder:
```
npx serve -p 3000
```

This starts a static web server.

Open browser:
```
http://localhost:3000
```

### Step 3 — Test the Application

- Enter student name
- Enter score
- Press Check Grade

Expected output:
```
ALIAH got grade: B
```

The UI communicates with Java backend using fetch().

# 7. What Students Learned Today

By end of Day 2, students understand:

✔ How Java can be used as backend
✔ How to build and call a backend API
✔ How to create a frontend UI using HTML/CSS/JS
✔ How to use Node.js to host frontend
✔ Cross-Origin Resource Sharing (CORS)
✔ Full-stack development basics
✔ How a browser communicates with a backend

# 8. Homework

Extend today’s project:

- Add “Overall Result” (Pass / Fail)
- Display timestamp in result
- Save history of checks in a table (frontend)
- Validate input: score must be 0–100
- Add multiple grade ranges (A+, B+, etc.)
