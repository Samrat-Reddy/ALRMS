# 📚 Automated Library Resource Management System (ALRMS)

A structured academic project demonstrating the design of a **Library Resource Management System** with a **frontend dashboard** and a **Java-based logic layer using Data Structures and Algorithms (DSA)**.

This project is developed as part of **BTech Trimester 2 coursework** to demonstrate algorithmic implementation and system architecture.

---

# 🧩 Project Architecture

The project is divided into two independent layers.

```
ALRMS
│
├── frontend/   → HTML, CSS, JavaScript UI
│
├── logic/      → Java implementation of system logic using DSA
│
└── README.md
```

### Frontend

A fully designed UI simulating a real-world library dashboard.

Technologies used:

* HTML
* CSS
* JavaScript
* Chart.js

Features:

* Public landing dashboard
* Student dashboard
* Admin dashboard
* Book catalogue
* Issued books
* Issue requests
* Fines page

---

### Logic Layer (Java DSA)

The backend logic is implemented using **core data structures and algorithms** as required by the syllabus.

Implemented concepts:

| Feature        | Data Structure / Algorithm     |
| -------------- | ------------------------------ |
| Catalogue      | Singly Linked List             |
| Issued Books   | Doubly Linked List             |
| Issue Requests | Queue                          |
| Book Search    | Linear Search                  |
| Book Sorting   | Merge Sort                     |
| Book Lookup    | Hash Table (Separate Chaining) |

The system is implemented as a **console-based menu-driven program**.

---

# 📂 Logic Folder Structure

```
logic/src
│
├── algorithms
│   ├── SearchAlgorithms.java
│   └── SortAlgorithms.java
│
├── models
│   ├── Book.java
│   ├── Student.java
│   └── IssueRecord.java
│
├── structures
│   ├── BookLinkedList.java
│   ├── HashTable.java
│   └── IssueQueue.java
│
├── services
│   └── LibrarySystem.java
│
└── Main.java
```

---

# ⚙️ How to Run the Logic Layer

1. Navigate to the `logic/src` directory.
2. Compile the program.

```
javac Main.java
```

3. Run the program.

```
java Main
```

The system will launch a **menu-driven console interface**.

---

# 🖥 Example Console Menu

```
===== ALRMS LIBRARY SYSTEM =====

1. Add Book
2. View Catalogue
3. Search Book
4. Sort Catalogue
5. Add Issue Request
6. Process Issue Request
7. Return Book
8. View Issued Books
9. Calculate Fine
0. Exit
```

---

# 📊 Learning Objectives

This project demonstrates:

* Implementation of core data structures
* Algorithmic problem solving
* Modular program architecture
* Separation of UI and system logic

---

# 🚀 Future Work

Planned extensions:

* Backend API layer
* Database integration
* Authentication system
* Real-time analytics

---

# 👨‍💻 Author

Samrat Reddy
BTech – Computer Science

---
