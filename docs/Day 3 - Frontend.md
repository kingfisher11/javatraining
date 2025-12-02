# DAY 3 — Part 2: Frontend Web App (HTML / CSS / JavaScript)

Now that the backend is complete, we will build the full **frontend** that interacts with the Day 3 API.

The frontend will allow the user to:

- Add students
- View student list
- Add subject results for a student
- View the subject results table
- Calculate GPA (CPA) for a student
- Delete results
- Delete students

Everything will run in the browser using:
- HTML (structure)
- CSS (design)
- JavaScript (logic + API communication)
- Node.js (static file hosting via `npx serve`)

---

## 1. File Structure

Create these files in the `web/` folder:
```
web/
├── index.html
├── style.css
└── script.js
```

## 2. Basic HTML Layout (index.html)

Start with a clean layout containing two main sections:

### ✔ Student Management  
### ✔ Result Management (per student)

Paste this into `index.html`:

```
<!DOCTYPE html>
<html>

<head>
    <title>Student Result System</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

    <h1>Student Result System</h1>

    <!-- ========== ADD STUDENT SECTION ========== -->
    <section class="box">
        <h2>Add Student</h2>

        <label>Name:</label>
        <input id="studentName" type="text">

        <label>Matric No:</label>
        <input id="studentMatric" type="text">

        <button onclick="addStudent()">Add Student</button>

        <h3>Student List</h3>
        <table id="studentTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Matric</th>
                    <th>Select</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </section>

    <!-- ========== ADD RESULT SECTION ========== -->
    <section class="box">
        <h2>Add Subject Result</h2>

        <p><b>Selected Student:</b> <span id="selectedStudentName">None</span></p>

        <label>Subject:</label>
        <input id="subjectName" type="text">

        <label>Credit Hour:</label>
        <input id="creditHour" type="number">

        <label>Mark (0-100):</label>
        <input id="subjectMark" type="number">

        <button onclick="addResult()">Add Subject Result</button>

        <h3>Subject Results</h3>
        <table id="resultTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Subject</th>
                    <th>Credit</th>
                    <th>Mark</th>
                    <th>Grade</th>
                    <th>Point</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>

        <button onclick="calculateGPA()" class="gpa-btn">Process GPA</button>

        <h2 id="gpaDisplay">GPA: -</h2>

    </section>

    <script src="script.js"></script>
</body>

</html>
```

## 3. Styling (style.css)

Use simple styling for clarity:
```
body {
    font-family: Arial, sans-serif;
    background: #f2f2f2;
    margin: 20px;
}

h1 {
    text-align: center;
}

.box {
    width: 700px;
    background: white;
    padding: 20px;
    margin: 20px auto;
    border-radius: 8px;
    box-shadow: 0 0 10px gray;
}

label {
    display: block;
    margin-top: 10px;
}

input {
    width: 100%;
    padding: 8px;
}

button {
    margin-top: 10px;
    padding: 10px;
    background: #007bff;
    color: white;
    border: none;
}

table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
}

table th, table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
}

.gpa-btn {
    background: green;
    width: 200px;
    display: block;
    margin-top: 15px;
}
```

## 4. JavaScript Logic (script.js)

This file connects the frontend UI to the backend API.

Start with global variables:
```
let selectedStudentId = null;
let selectedStudentName = "";
let API = "http://localhost:8080";
```

## 5. Load Students on Page Start

At the bottom of script.js:
```
window.onload = () => {
    loadStudents();
};
```
Function to load students:
```
function loadStudents() {
    fetch(`${API}/student/list`)
        .then(res => res.json())
        .then(data => {
            let tbody = document.querySelector("#studentTable tbody");
            tbody.innerHTML = "";

            data.forEach(stu => {
                let row = `
                <tr>
                    <td>${stu.id}</td>
                    <td>${stu.name}</td>
                    <td>${stu.matric}</td>
                    <td><button onclick="selectStudent(${stu.id}, '${stu.name}')">Select</button></td>
                    <td><button onclick="deleteStudent(${stu.id})">Delete</button></td>
                </tr>`;
                tbody.innerHTML += row;
            });
        });
}
```

## 6. Adding a Student
```
function addStudent() {
    let name = document.getElementById("studentName").value;
    let matric = document.getElementById("studentMatric").value;

    fetch(`${API}/student/add`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ name, matric })
    })
    .then(() => {
        alert("Student added!");
        loadStudents();
    });
}
```

### 7. Delete Student
```
function deleteStudent(id) {
    fetch(`${API}/student/delete?id=${id}`, { method: "DELETE" })
    .then(() => loadStudents());
}
```

### 8. Selecting a Student
```
function selectStudent(id, name) {
    selectedStudentId = id;
    selectedStudentName = name;
    document.getElementById("selectedStudentName").innerText = name;
    loadResults();
}
```

### 9. Load Results for Selected Student
```
function loadResults() {
    fetch(`${API}/result/list?student_id=${selectedStudentId}`)
        .then(res => res.json())
        .then(data => {
            let tbody = document.querySelector("#resultTable tbody");
            tbody.innerHTML = "";

            data.forEach(r => {
                let row = `
                <tr>
                    <td>${r.id}</td>
                    <td>${r.subject}</td>
                    <td>${r.credit_hour}</td>
                    <td>${r.mark}</td>
                    <td>${r.grade}</td>
                    <td>${r.point}</td>
                    <td><button onclick="deleteResult(${r.id})">Delete</button></td>
                </tr>`;
                tbody.innerHTML += row;
            });
        });
}
```

### 10. Add Subject Result
```
function addResult() {
    let subject = document.getElementById("subjectName").value;
    let credit_hour = parseInt(document.getElementById("creditHour").value);
    let mark = parseInt(document.getElementById("subjectMark").value);

    fetch(`${API}/result/add`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            student_id: selectedStudentId,
            subject,
            credit_hour,
            mark
        })
    })
    .then(() => {
        alert("Subject result added!");
        loadResults();
    });
}
```

### 11. Delete Result
```
function deleteResult(id) {
    fetch(`${API}/result/delete?id=${id}`, { method: "DELETE" })
        .then(() => loadResults());
}
```

### 12. Calculate GPA
```
function calculateGPA() {
    fetch(`${API}/result/gpa?student_id=${selectedStudentId}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById("gpaDisplay").innerText =
                "GPA: " + data.gpa.toFixed(2);
        });
}
```

### 13. Running the Frontend

In terminal:
```
cd web
npx serve -p 3000
```

### 14. What the Students Have Built

By completing Part 2, students now have:

✔ Full Student CRUD
✔ Full Subject Result CRUD
✔ Automatic grade & point conversion
✔ GPA calculator with real formula
✔ Working API calls using fetch()
✔ Real database storage using SQLite
✔ Fully functional mini academic system

This is now a complete beginner-friendly full-stack Java project.
