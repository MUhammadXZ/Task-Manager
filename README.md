# Simple Task Management App

## Overview

The **Simple Task Management App** is a backend service that allows users to manage their tasks organized within workspaces. The application supports user account creation (via phone number, email, and optionally Gmail sign-in), login, and full CRUD operations for both workspaces and tasks. Tasks are designed to be valid for 4 days from their received date. This project is intended to run locally without deployment and is built with a partitioned PostgreSQL database schema to efficiently manage workspace-related data.

## Features

- **User Management:**
  - **Register User:** Create an account using phone number, email, or Gmail sign-in.
  - **Login:** Authenticate with credentials.

- **Workspace Management:**
  - **CRUD Operations:** Create, read, update, and delete workspaces.
  - **Retrieve Workspaces:** Get a list of all workspaces associated with a user.

- **Task Management:**
  - **CRUD Operations:** Create, read, update, and delete tasks within a workspace.
  - **Change Task Status:** Update task status from pending to done.
  - **Task Validity:** Tasks are valid for 4 days from the date they are created.
  - **Retrieve All Tasks:** Fetch tasks across workspaces.

- **Database Architecture:**
  - Partitioned PostgreSQL schema based on workspaces to optimize performance and manage data relationships.

- **Robust Error Handling:**
  - Comprehensive error and exception handling with clear error messages and logging.

## Getting Started

### Prerequisites

- **Java 11+**
- **Maven or Gradle** (depending on your build tool preference)
- **PostgreSQL** (ensure that your PostgreSQL instance supports partitioning)
- **Git** for version control
- **Postman** for API testing

### Installation & Setup

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/MUhammadXZ/Task-Manager.git
