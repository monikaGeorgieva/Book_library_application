Book Library Application – Technical Documentation

##  Overview

The Book Library Application is a modular system designed to manage books, reviews, and related metadata.  
Its architecture follows a service-oriented structure, where each component (e.g., review handling, book management) may operate independently or as part of a larger platform.

This document provides a detailed overview of the application, its modules, architecture, technologies, API endpoints, and installation process.

##  System Modules

The application typically consists of the following modules:

##  Book Service  
Responsible for managing book entities, including:
- Book registration  
- Book metadata (title, author, ISBN, category)  
- Book catalog retrieval

#  Review Service Internal Structure
  src/main/java/com/example/reviewservice
│
├── review/
│ ├── model/ → Review entity
│ ├── repository/ → ReviewRepository
│ └── service/ → ReviewService
│
├── web/
│ ├── controller/ → ReviewRestController
│ └── dto/ → ReviewRequest DTO
│exception/ → ReviewNotFoundException, handlers



└── exception/ → ReviewNotFoundException, handlers
