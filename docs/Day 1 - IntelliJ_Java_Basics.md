# Day 1 — IntelliJ IDEA & Java Basics

Welcome to **Day 1** of the Java Beginner Training using **IntelliJ IDEA Community Edition (Windows)**.

By the end of this day, participants will be able to:

- Install and configure **JDK**
- Install and open **IntelliJ IDEA Community**
- Create and run a **Java console project**
- Understand the basic parts of the IntelliJ interface
- Use **variables, input, conditions, and loops** in Java
- Build a simple **menu-based console program**
- Make their **first Git commit** from inside IntelliJ

---

## 1. Installing JDK

Java requires a JDK to compile and run programs.

### 1.1 Download JDK

1. Open a browser and go to:  
   **https://www.oracle.com/asean/java/technologies/downloads/**
2. Choose:
   - Operating System: **Windows**
   - Packaging: **x64 Installer**  
3. Download the installer.

### 1.2 Install JDK 17

1. Run the downloaded `.exe` file.
2. Click **Next** through the steps.

### 1.3 Verify JDK Installation

1. Open **Command Prompt** (Win + R → type `cmd` → Enter).
2. Type:

   ```bash
   java -version

### 1.4 Set Java Path
1. Open System Properties -> Environment Variables
2. On User variable section:
   - Click New button
   - Set variable name: JAVA_HOME
   - Browser variable value: C:/Program Files/Java/jdk-25
3. On System variable section:
   - Click New button
   - Set variable name: JAVA_HOME
   - Browser variable value: C:/Program Files/Java/jdk-25
4. Double click on path, click New
5. Type
```
%JAVA_HOME%\bin
```

## 2. Installing IntelliJ IDEA Community

### 2.1 Download IntelliJ

- Go to: **https://www.jetbrains.com/idea/download**
- Under Community Edition, click Download.

### 2.2 Install IntelliJ

- Run the installer.
- Accept all default settings.

Optional:
- Check Create Desktop Shortcut
- Click Install → Finish.

### 2.3 First Launch

When IntelliJ opens:

- Select Do not import settings
- Choose Light / Darcula
- Skip plugins unless needed

You will see the Welcome Screen

## 3. Configuring JDK Inside IntelliJ

### 3.1 On New Project Screen

- From the Welcome Screen → click New Project
- In left sidebar select Java
- In JDK dropdown:
- Must show Version 17
- If empty → click Add JDK…
Browse to:
**C:\Program Files\Eclipse Adoptium\jdk-17.x.x**
- Select and proceed

### 4. Creating Your First Java Project

Steps:
- Click New Project
- Select Java
- Choose JDK 17
- Click Next/ Create
- Set project name
- Click Create

## 5. IntelliJ Interface Overview

### 5.1 Project Tool Window

Located on the left, showing folders like:
```
src/
.idea/
External Libraries/
```

### 5.2 Editor Area

Center area where you type code.

### 5.3 Run Tool Window

Bottom panel showing output/errors.

### 5.4 Toolbar

Top area with Run, Debug, and sometimes Git buttons.

## 6. Creating Your First Java Class

### 6.1 Create Class

- Right-click src
- Select New → Java Class
- Name class

### 6.2 Add Main Method

Replace code with:
```
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from IntelliJ and Java!");
    }
}
```

## 7. Running Your First Java Program

You can run in two ways:

### 7.1 Using Run Icon

Click the green ▶ icon next to the line:
```
public static void main(String[] args)
```

### 7.2 Right-Click Method

Right-click the editor → Run 'Main.main()'

### 7.3 Expected Output
```
Hello from IntelliJ and Java!
```

## 8. Java Basics

### 8.1 Variables
```
int age = 22;
double salary = 2500.75;
String name = "Aiman";
boolean isActive = true;
```

### 8.2 Input Using Scanner
```
import java.util.Scanner;

Scanner sc = new Scanner(System.in);

System.out.print("Enter name: ");
String name = sc.nextLine();

System.out.print("Enter age: ");
int age = Integer.parseInt(sc.nextLine());
```

### 8.3 Arithmetic
```
int a = 10;
int b = 5;

System.out.println(a + b);
System.out.println(a - b);
System.out.println(a * b);
System.out.println(a / b);
```

### 8.4 Conditions
```
int marks = 75;

if (marks >= 80) {
    System.out.println("A");
} else if (marks >= 60) {
    System.out.println("B");
} else {
    System.out.println("C");
}
```

### 8.5 Loops
For Loop
```
for (int i = 1; i <= 5; i++) {
    System.out.println("i = " + i);
}
```

While Loop
```
int i = 5;
while (i > 0) {
    System.out.println(i);
    i--;
}
```

## 9. Mini Project: Simple Console Menu

Replace Main.java with this exercise:
```
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== SIMPLE MENU ===");
            System.out.println("1. Say Hello");
            System.out.println("2. Add Two Numbers");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Hello! Welcome to Java training.");
                }

                case 2 -> {
                    System.out.print("Enter first number: ");
                    int a = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter second number: ");
                    int b = Integer.parseInt(sc.nextLine());
                    System.out.println("Total = " + (a + b));
                }

                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }

                default -> System.out.println("Please choose 1, 2, or 3.");
            }
        }
    }
}
```

## 10. Basic Git & GitHub in IntelliJ

### 10.1 Enable Git

Go to:
```
VCS → Enable Version Control Integration → Git
```

### 10.2 First Commit

- Press Ctrl + K
- Select all files
- Write message:
```
Day 1 - basic Java setup and menu program
```
- Click Commit

### 10.3 Connect GitHub (Optional)

Go to:
- File → Settings → Version Control → GitHub
- Login with GitHub

### 10.4 Push Code

Press:
```
Ctrl + Shift + K
```

## 11. Day 1 Deliverables

Participants should:

 - Install JDK 17

 - Install IntelliJ

 - Create Java project

 - Run a program

 - Understand variables, input, conditions, loops

 - Build the menu program

 - Make a Git commit