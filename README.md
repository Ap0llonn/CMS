**Cégep Management System**

This project aims to streamline academic operations at a cégep with a comprehensive management system. It includes a JavaFX desktop application for administrative tasks and a React.ts web application for student and teacher access. The system facilitates roles for students, teachers, and administrators, providing features such as schedules, messaging, assignments, exams, notifications, and statistics. The technologies used are Java, Spring Boot, React.ts, and PostgreSQL.

**Requirements**
- Java 11 or higher
- Maven
- Spring Boot
- React.ts
- PostgreSQL

**Setup**
1. Clone the repository: `git clone https://github.com/T1WiLLi/CMS.git`
2. Set up the backend:
   - Navigate to the `backend` directory.
   - Run Spring Boot application: `mvn spring-boot:run`
3. Set up the frontend:
   - Navigate to the `frontend` directory.
   - Install dependencies: `npm install`
   - Start React app: `npm start`
4. Access the desktop application and web app via their respective URLs.

**Usage**
- Desktop Application (JavaFX):
  - Execute the JAR file or run the application from an IDE.
- Web Application (React.ts):
  - Access the web app via the provided URL.
- Follow the provided user documentation for detailed instructions on using each feature.

**Contributing**
- Fork the repository, create a new branch, make your changes, and submit a pull request.
- For major changes, please open an issue first to discuss the proposed changes.

**License**
- This project is licensed under the [MIT License](LICENSE).

**Acknowledgements**
- Include any acknowledgments or credits for libraries, resources, or individuals that contributed to the project.

**Contact**
- For inquiries or support, please use the issues tab.

**Note**
- This project was developed for self-development and is intended for educational purposes.
- To Install the netconex.jar library, navigate to the folder of the project where you want to use it, then using ***git bash*** enter the following command : 
``` mvn install:install-file -Dfile=lib/netconex.jar -DgroupId=qc.netconex -DartifactId=netconex -Dversion=1.0.0 -Dpackaging=jar```
