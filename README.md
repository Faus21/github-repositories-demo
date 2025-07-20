# GitHub Repositories API

A Spring Boot application that exposes an endpoint to fetch all **non-forked** GitHub repositories of a given user along with the list of branches and their latest commit SHA.

## Technologies Used

- Spring Boot 3.5
- Java 21
- RestClient (Spring)

## How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/githubapi.git
   cd githubapi
   ```

2. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run 
   ```

## API Docs

1. Endpoint: 
   - /api/github/repositories/{username}
   - Status 200(OK) - response json: 
   ```json
   [
      {
        "name": "repoName",
        "login": "loginOfUser",
        "branches": [
          {
            "name": "branch-name",
            "sha": "lastSha"
          }      
        ]
      }
   ]
    ```
    - Status 404(Not Found) - response json:
   ```json
   {
     "status": 404,
     "message": "User not found"
   }
    ```
   - Status 505(Not Found) - response json:
   ```json
   {
     "status": 500,
     "message": "Iternal server error"
   }
    ```