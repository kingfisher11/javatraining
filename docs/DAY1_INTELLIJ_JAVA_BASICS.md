# Day 1 — IntelliJ IDEA & Java Basics

Welcome to **Day 1** of the Java Beginner Training using **IntelliJ IDEA Community Edition (Windows)**.

By the end of this day, participants will be able to:

- Install and configure **JDK 17**
- Install and open **IntelliJ IDEA Community**
- Create and run a **Java console project**
- Understand the basic parts of the IntelliJ interface
- Use **variables, input, conditions, and loops** in Java
- Build a simple **menu-based console program**
- Make their **first Git commit** from inside IntelliJ

---

## 1. Installing Java Development Kit (JDK 17)

Java requires a JDK to compile and run programs.

### 1.1 Download JDK 17

1. Open a browser and go to:  
   **https://adoptium.net**
2. Choose:
   - Version: **Temurin 17 (LTS)**
   - Operating System: **Windows x64**
   - Packaging: **Installer (.msi)**  
3. Download the installer.

### 1.2 Install JDK 17

1. Run the downloaded `.msi` file.
2. Click **Next** through the steps.
3. Keep default installation path (e.g. `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`).
4. Make sure **“Add to PATH”** is checked (if shown).
5. Click **Install**, then **Finish**.

### 1.3 Verify JDK Installation

1. Open **Command Prompt** (Win + R → type `cmd` → Enter).
2. Type:

   ```bash
   java -version

## 2. Installing IntelliJ IDEA Community

## 2.1 Download IntelliJ

- Go to: **https://www.jetbrains.com/idea/download**
- Under Community Edition, click Download.

## 2.2 Install IntelliJ

- Run the installer.
- Accept all default settings.

Optional:
- Check Create Desktop Shortcut
- Click Install → Finish.

## 2.3 First Launch

When IntelliJ opens:

- Select Do not import settings
- Choose Light / Darcula
- Skip plugins unless needed

You will see the Welcome Screen

## 3. Configuring JDK Inside IntelliJ

## 3.1 On New Project Screen

- From the Welcome Screen → click New Project
- In left sidebar select Java
- In JDK dropdown:
- Must show Version 17
- If empty → click Add JDK…
Browse to:
**C:\Program Files\Eclipse Adoptium\jdk-17.x.x**
- Select and proceed

## 4. Creating Your First Java Project

Steps:
- Click New Project
- Select Java
- Choose JDK 17
- Click Next/ Create
- Set project name
- Click Create

## 5. IntelliJ Interface Overview

## 5.1 Project Tool Window

Located on the left, showing folders like:
```
src/
.idea/
External Libraries/
```

## 5.2 Editor Area

Center area where you type code.

## 5.3 Run Tool Window

Bottom panel showing output/errors.

## 5.4 Toolbar

Top area with Run, Debug, and sometimes Git buttons.

## 6. Creating Your First Java Class

## 6.1 Create Class

- Right-click src
- Select New → Java Class
- Name class

## 6.2 Add Main Method

Replace code with:
```
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from IntelliJ and Java!");
    }
}
```

