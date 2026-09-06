# 🏥 Hospital Management System

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![License](https://img.shields.io/badge/License-MIT-green)
![Version](https://img.shields.io/badge/Version-1.0.0-blue)

A complete Java console-based Hospital Management System that demonstrates OOP principles, Collections Framework, Exception Handling, and File I/O operations. Built as a comprehensive mini-project covering all core Java concepts.

## 📋 Table of Contents
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Project Structure](#-project-structure)
- [How to Run](#-how-to-run)
- [Key OOP Concepts](#-key-oop-concepts-demonstrated)
- [Sample Data](#-sample-data)
- [Test Scenarios](#-test-scenarios)
- [Generated Files](#-generated-files)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### 👤 Patient Management
- Register patients with complete medical history
- Track allergies and emergency contacts
- View detailed patient summaries
- Search patients by name

### 👨‍⚕️ Doctor Management
- Register doctors with specializations
- Track consultation fees and experience
- View available doctors
- Filter doctors by specialization

### 📅 Appointment Management
- Schedule appointments with conflict checking
- Cancel appointments with automatic refunds
- Track appointment status (SCHEDULED, COMPLETED, CANCELLED, NO_SHOW)
- View all appointments

### 💰 Financial Management
- Track medical bills per patient
- Calculate total revenue
- View doctor appointment statistics

### 📊 Data Persistence
- Save data using Object Serialization (.ser files)
- Load data automatically on startup
- Generate human-readable hospital report (.txt)

## 🛠️ Technologies Used

### Core Java Concepts
- **OOP Principles**: Encapsulation, Inheritance, Polymorphism
- **Abstract Classes**: Person as base abstract class
- **Collections Framework**: ArrayList, HashMap
- **Exception Handling**: Custom exceptions with try-catch-finally
- **File I/O**: Object Serialization, BufferedWriter, FileWriter
- **Java Date/Time**: LocalDateTime for appointment scheduling

### Tools & Environment
- Java 8 or higher
- VS Code / IntelliJ IDEA / Eclipse
- Git for version control

## 📁 Project Structure
Hospital-Management-System/
├── src/
│ ├── exceptions/ # Custom exceptions
│ │ ├── AppointmentConflictException.java
│ │ ├── DoctorNotFoundException.java
│ │ ├── InvalidAgeException.java
│ │ └── PatientNotFoundException.java
│ │
│ ├── model/ # Entity classes
│ │ ├── Person.java # Abstract base class
│ │ ├── Patient.java # Inherits Person
│ │ ├── Doctor.java # Inherits Person
│ │ └── Staff.java # Inherits Person
│ │
│ ├── hospital/ # Core business logic
│ │ ├── Appointment.java
│ │ └── HospitalManager.java
│ │
│ ├── util/ # Utilities
│ │ └── FileHandler.java
│ │
│ └── MainApp.java # Entry point
│
├── .gitignore
├── README.md
└── hospital_report.txt # Generated on save


## 🚀 How to Run

### Prerequisites
- Java 8 or higher installed
- Git (optional, for cloning)

```bash
# Clone repository
git clone https://github.com/Shahani2001/Hospital-Management-System-Java.git
cd Hospital-Management-System-Java

# Navigate to src directory
cd src

# Compile all Java files
javac **/*.java

# Run the application
java MainApp


THANK_YOU HAPPY CODING...