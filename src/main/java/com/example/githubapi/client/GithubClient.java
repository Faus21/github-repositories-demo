package com.example.githubapi.client;

import com.example.githubapi.model.dto.GithubBranch;
import com.example.githubapi.model.dto.GithubRepository;
import com.example.githubapi.model.exception.GithubUserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Arrays;
import java.util.List;

import static com.example.githubapi.config.GithubUrls.BASE_URL;
import static com.example.githubapi.config.GithubUrls.REPO_BRANCHES_WITH_LAST_COMMIT;
import static com.example.githubapi.config.GithubUrls.USER_REPOS;

@Component
public class GithubClient {

    private final RestClient restClient;

    public GithubClient() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .build();
    }

    public List<GithubRepository> getNoForkedRepositories(String username) {
        try {
            GithubRepository[] response = restClient.get()
                    .uri(USER_REPOS, username)
                    .retrieve()
                    .body(GithubRepository[].class);

            if (response == null) {
                return List.of();
            }

            return Arrays.stream(response)
                    .filter(repo -> !repo.fork())
                    .toList();

        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new GithubUserNotFoundException("User not found: " + username);
            }
            throw new RuntimeException("Failed to fetch repositories for user: " + username, e);
        }
    }

    public List<GithubBranch> getBranches(String owner, String repoName) {
        try {
            GithubBranch[] response = restClient.get()
                    .uri(REPO_BRANCHES_WITH_LAST_COMMIT, owner, repoName)
                    .retrieve()
                    .body(GithubBranch[].class);

            if (response == null) {
                return List.of();
            }

            return List.of(response);

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch branches for repo: " + repoName, e);
        }
    }
}
