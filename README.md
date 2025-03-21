# CME Coding Challenger
## **Objective**
- Test automation framework using Java 17, Maven, Selenium Grid, Docker, TestNG/JUnit,
  and any suitable tool, library, or API to interact with web services as RestAssured.

### **Automate the following test cases:**
- **Login Failure Verification:** Verify that attempting to log in with the username "00000"
  and password "00000" results in a red error message containing the text "we are
  having trouble logging you in."

- **Petroleum Index Data Verification:** Navigate to the "Petroleum Index" page by
  clicking "Data" and then "Petroleum Index" in the header navigation bar. There, verify
  that the proper service data matches with the values displayed in the 2 tables (End of
  Day Index Value and Intraday Index Value 5 Minute Intervals) - Important: You need to
  find what’s the service to be verified.

### **Requirements:**
- Tag the "Login Failure Verification" test case as "regression" and the "Petroleum Index
  Data Verification" test case as "smoke".
- Create separate TestNG/JUnit test suites to enable the independent execution of each
  group (smoke and regression).
- Develop a simple Jenkinsfile with the necessary stages to run both test suites on a
  Jenkins server. Important: You don’t need to set up any Jenkins cluster, just the jenkins
  file.

### **Important Considerations:**
1) **Object-Oriented Programming (OOP) and Test Automation Patterns:** Implement
any OOP principle and/or any test automation pattern that you think might apply.
2) **Comprehensive Reporting and Error Messages:** Ensure clear and informative test
   reporting and error messages. Also, it is a plus if you implement a reporting tool.
3) **Effective Wait Strategies:** Utilize appropriate wait strategies to handle dynamic web
   elements/components.
4) **Automated ChromeDriver Management:** Implement a mechanism to automatically
   download the correct ChromeDriver version based on the browser version,
   eliminating the need for manual downloads.

## **Technologies Used**
- Java 17
- Maven
- Selenium Grid
- Web Driver Manager
- TestNG
- Docker
- Jenkins
- Surefire Plugins
- JSON
- RestAssured
- Extent Reports
- Maven
- Log4J Logger.
- XML

## **Environment to run the project:**
- Install Java 17 and configure the ```JAVA_HOME``` in Environments variables.
- On Linux for Intellij IDE, is necessary open the Intellij Terminal and execute: `export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64`
- Should be installed ```Apache Maven 3.9.9``` in your machine, and have configured the ```.m2 folder``` with ```M2_Home``` configured in Environments variables for Windows.
- This project was developed with ```IntelliJ IDEA 2024.3.4.1 (Ultimate Edition)``` and on Linux as OS.

## **Installation**
**1)** Clone or download the project.

**2)** Import the project from ```poxm.xml``` file from your IDE.

**3)** Accept ```import dependencies``` or download dependencies from IDE, or a better option is through CMD running: ```mvn clean install -DskipTests=true``` from ```pom.xml``` folder location.

## **How to Run Test-Cases**
### **Option Suites XML files:**
- Go to ```project``` tab in your IDE, find the file called: `LoginFailureVerificationTNGTest.xml`, select it and right-click to select the option `Run '...LoginFailureVerificationTNGTest.xml` and the Test will start to run.
The same process to execute the `PetroleumIndexDataVerificationTNGTest.xml` Test Case.


https://github.com/user-attachments/assets/709b1a0b-769b-4231-9937-afc4279bf774


### **Option Class files:**
- Go to ```project``` tab in your IDE, find the file called: `LoginFailureVerificationTNGTest.java` (located at: `src/test/java/LoginFailureVerificationTNGTest.java`), select it and right-click to select the option `Run 'LoginFailureVerificationTNGTest` and the Test will start to run.
The same process to execute the `PetroleumIndexDataVerificationTNGTest.java` Test Case.

https://github.com/user-attachments/assets/a658fb47-9ed3-47b0-a6f3-3b7ec323a5eb


### **Jenkins Server files:**
- On your Jenkins server you should install Docker.
- Create a pipeline with 4 jobs.
- Execute the `docker-composes/docker-compose-up.yml` in which I set up all necessary to run Selenium Grid Hub Docker images with the ChromeService Docker image.
- On the file `docker-composes/docker-compose-up.yml` are the instructions for local runs on Docker Desktop and how to scale ChromeService nodes.
### **Jenkins files:**
- **For Jenkins server, I created 4 Jenkins files.** 
- The file called `Jenkins/selenium-hub-down/Jenkinsfile` should be used from a Jenkins job and is responsible for down all the image containers related to the Selenium Grid to start fresh again.
- The file called `Jenkins/selenium-hub-up/Jenkinsfile` should be used from a Jenkins job and is responsible for starting all docker image containers related to Selenium Grid and Nodes. 
- The file called `Jenkins/regression/Jenkinsfile` should be used from a Jenkins job and is responsible to execute the LoginFailureVerificationTNGTest Test Case. 
- The file called `Jenkins/smoke/Jenkinsfile` should be used from a Jenkins job and is responsible for executing the PetroleumIndexDataVerificationTNGTest Test Case. 

## **How to See the Extend Reports**
- After executing whatever Test Case, go to ```project``` tab in your IDE, find the folder called `ExtentReports`, open the folder, select the `.html` file Extend Report (`ExtentReportsResults-yyyy-MM-dd-hh-mm-ss.html`), right-click -> Open In -> Browser -> Chrome.
  ![extentreport-dashboard](https://github.com/nicolaslopez82/cme-codingchallenge-testautomationengineer/blob/master/ReadmeImages/extentreports-dashboard.png)
  ![extentreport-testcases](https://github.com/nicolaslopez82/cme-codingchallenge-testautomationengineer/blob/master/ReadmeImages/extentreports-testcases.png)
