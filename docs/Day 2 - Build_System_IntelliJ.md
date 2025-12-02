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
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static java.lang.Float.isNaN;

public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/grade", new GradeHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server running at http://localhost:8080");
    }

    static class GradeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // ------------------------------------------------------------
            // CORS HEADERS (WAJIB)
            // ------------------------------------------------------------
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

            // Handle preflight CORS request
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // ------------------------------------------------------------

            if ("GET".equals(exchange.getRequestMethod())) {

                String query = exchange.getRequestURI().getQuery(); // name=ALIAH&score=70

                if (query == null) {
                    String error = "{ \"error\": \"Missing parameters\" }";
                    exchange.sendResponseHeaders(400, error.length());
                    exchange.getResponseBody().write(error.getBytes());
                    exchange.close();
                    return;
                }

                String[] parts = query.split("&");

                String name = parts[0].split("=")[1];
                int score = Integer.parseInt(parts[1].split("=")[1]);

                String grade;
                if (score >= 80) grade = "A";
                else if (score >= 60) grade = "B";
                else if (score >= 40) grade = "C";
                else grade = "Fail";

                String json = "{ \"name\": \"" + name + "\", \"grade\": \"" + grade + "\" }";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, json.length());

                OutputStream os = exchange.getResponseBody();
                os.write(json.getBytes());
                os.close();
            }
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

- How Java can be used as backend
- How to build and call a backend API
- How to create a frontend UI using HTML/CSS/JS
- How to use Node.js to host frontend
- Cross-Origin Resource Sharing (CORS)
- Full-stack development basics
- How a browser communicates with a backend

# 8. Homework

Extend today’s project:

- Add “Overall Result” (Pass / Fail)
- Display timestamp in result
- Save history of checks in a table (frontend)
- Validate input: score must be 0–100
- Add multiple grade ranges (A+, B+, etc.)

# STEP 1 – Add “Overall Result” (PASS / FAIL)

**Objective:**
Kalau markah ≥ 40 → PASS, kalau < 40 → FAIL.

### 1.1 – Kira PASS / FAIL di frontend

Dalam script.js, di dalam .then(data => { ... }), lepas kita ada score, kita boleh kira:
```
.then(data => {
    let numericScore = parseInt(score); // score dari input tadi

    let overall;
    if (numericScore >= 40) {
        overall = "PASS";
    } else {
        overall = "FAIL";
    }

    document.getElementById("result").innerHTML =
        `${data.name} got grade: <b>${data.grade}</b><br>` +
        `Overall Result: <b>${overall}</b>`;
})
```
Hands-on flow untuk student:

- Buka script.js
- Cari function checkGrade()
- Dalam .then(data => { ... }), tambah:
- variable numericScore
- if/else untuk overall
- update innerHTML untuk paparkan kedua-dua: grade + overall

# STEP 2 – Display Timestamp

**Objective:**
Tunjuk bila semakan dibuat, contohnya:
Checked at: 30/11/2025, 3:45:12 PM

### 2.1 – Guna new Date().toLocaleString()

Masih dalam blok .then(data => { ... }), sambung:
```
.then(data => {
    let numericScore = parseInt(score);

    let overall = (numericScore >= 40) ? "PASS" : "FAIL";

    let timestamp = new Date().toLocaleString(); // current date & time

    document.getElementById("result").innerHTML =
        `${data.name} got grade: <b>${data.grade}</b><br>` +
        `Overall Result: <b>${overall}</b><br>` +
        `Checked at: <i>${timestamp}</i>`;
})
```
Hands-on untuk student:
- Tambah let timestamp = new Date().toLocaleString();
- Update innerHTML supaya ada line “Checked at…”

# STEP 3 – Save History of Checks in a Table (Frontend)

**Objective:**
Setiap kali user tekan “Check Grade”, simpan ke history list dan papar dalam jadual di bawah form.

### 3.1 – Update HTML: tambah table untuk history

Dalam index.html, di bawah <div id="result"></div>, tambah:
```
<div class="history-container">
    <h3>History</h3>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Score</th>
                <th>Grade</th>
                <th>Result</th>
                <th>Time</th>
            </tr>
        </thead>
        <tbody id="history-body">
            <!-- rows will be added dynamically -->
        </tbody>
    </table>
</div>
```
### 3.2 – Add array untuk simpan history

Dalam script.js, di atas function checkGrade(), tambah:
```
let historyList = []; // to store all check records
```

### 3.3 – Simpan setiap record ke dalam array

Dalam .then(data => { ... }), selepas kita kira overall & timestamp, tambah:
```
.then(data => {
    let numericScore = parseInt(score);
    let overall = (numericScore >= 40) ? "PASS" : "FAIL";
    let timestamp = new Date().toLocaleString();

    document.getElementById("result").innerHTML =
        `${data.name} got grade: <b>${data.grade}</b><br>` +
        `Overall Result: <b>${overall}</b><br>` +
        `Checked at: <i>${timestamp}</i>`;

    // Save record into history
    let record = {
        name: data.name,
        score: numericScore,
        grade: data.grade,
        overall: overall,
        time: timestamp
    };

    historyList.push(record);
    displayHistory();
})
```

### 3.4 – Function untuk paparkan history dalam table

Masih dalam script.js, di luar checkGrade(), tambah:
```
function displayHistory() {
    let tbody = document.getElementById("history-body");
    tbody.innerHTML = ""; // clear existing rows

    historyList.forEach(record => {
        let row = `
            <tr>
                <td>${record.name}</td>
                <td>${record.score}</td>
                <td>${record.grade}</td>
                <td>${record.overall}</td>
                <td>${record.time}</td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}
```

Hands-on flow untuk student:

- Tambah <table> dalam HTML
- Tambah let historyList = [] dalam JS
- Dalam checkGrade, selepas result dipaparkan → simpan record ke historyList
- Tulis function displayHistory() dan panggil selepas historyList.push(record)

# STEP 4 – Validate Input: Score must be 0–100

**Objective:**
Kalau user masukkan markah kurang dari 0 atau lebih dari 100 → jangan hantar request, tunjuk error.

### 4.1 – Validation sebelum fetch

Dalam checkGrade(), selepas ambil score dan sebelum fetch(...), tambah:
```
function checkGrade() {
    let name = document.getElementById("name").value;
    let score = document.getElementById("score").value;

    let resultDiv = document.getElementById("result");

    // Basic validation
    if (!name || score === "") {
        alert("Please enter both name and score.");
        return;
    }

    let numericScore = parseInt(score);

    if (isNaN(numericScore)) {
        alert("Score must be a number.");
        return;
    }

    if (numericScore < 0 || numericScore > 100) {
        alert("Score must be between 0 and 100.");
        return;
    }

    // If valid, proceed to call backend
    fetch(`http://localhost:8080/grade?name=${name}&score=${numericScore}`)
        .then(res => res.json())
        .then(data => {
            let overall = (numericScore >= 40) ? "PASS" : "FAIL";
            let timestamp = new Date().toLocaleString();

            resultDiv.innerHTML =
                `${data.name} got grade: <b>${data.grade}</b><br>` +
                `Overall Result: <b>${overall}</b><br>` +
                `Checked at: <i>${timestamp}</i>`;

            let record = {
                name: data.name,
                score: numericScore,
                grade: data.grade,
                overall: overall,
                time: timestamp
            };

            historyList.push(record);
            displayHistory();
        })
        .catch(err => {
            console.error(err);
            resultDiv.innerText = "Error contacting server.";
        });
}
```
Hands-on untuk student:

- Tambah if (!name || score === "")
- Tambah check isNaN
- Tambah check < 0 atau > 100
- Pastikan fetch hanya jalan kalau semua validation pass

# STEP 5 – Add Detailed Grade Ranges (A+, A, A-, B+, …)

**Objective:**
Daripada hanya A, B, C, Fail, kita buat grade lebih detail.

Cara paling senang untuk training:
👉 Biarkan backend kekal bagi A/B/C/Fail
👉 Di frontend, kita kira sendiri “detailed grade” guna skor.

### 5.1 – Buat helper function getDetailedGrade(score)

Dalam script.js, di luar checkGrade() dan displayHistory(), tambah:
```
function getDetailedGrade(score) {
    if (score >= 90) return "A+";
    if (score >= 85) return "A";
    if (score >= 80) return "A-";
    if (score >= 75) return "B+";
    if (score >= 70) return "B";
    if (score >= 65) return "B-";
    if (score >= 55) return "C+";
    if (score >= 40) return "C";
    return "Fail";
}
```
### 5.2 – Guna detailed grade dalam UI & history

Dalam .then(data => { ... }), gantikan data.grade dengan:
```
.then(data => {
    let numericScore = numericScore = parseInt(score);
    let overall = (numericScore >= 40) ? "PASS" : "FAIL";
    let timestamp = new Date().toLocaleString();

    let detailedGrade = getDetailedGrade(numericScore);

    resultDiv.innerHTML =
        `${data.name} got grade: <b>${detailedGrade}</b><br>` +
        `Overall Result: <b>${overall}</b><br>` +
        `Checked at: <i>${timestamp}</i>`;

    let record = {
        name: data.name,
        score: numericScore,
        grade: detailedGrade,
        overall: overall,
        time: timestamp
    };

    historyList.push(record);
    displayHistory();
})
```

Hands-on untuk student:

- Tulis function getDetailedGrade(score) sendiri (boleh bagi range di whiteboard)
- Call function tersebut dalam checkGrade
- Pastikan:
    - result box guna detailed grade
    - history table juga guna detailed grade
