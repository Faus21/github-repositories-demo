package com.example.githubapi.integration;

import com.example.githubapi.model.response.GithubBranchWithSha;
import com.example.githubapi.model.response.GithubRepoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.githubapi.TestDataFactory.OWNER_LOGIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void shouldReturnNonForkedRepositoriesWithBranchesAndShas() {
        String url = "http://localhost:" + port + "/api/github/repositories/" + OWNER_LOGIN;

        ResponseEntity<GithubRepoResponse[]> response = restTemplate.getForEntity(url, GithubRepoResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        for (GithubRepoResponse repo : response.getBody()) {
            assertThat(repo.name()).isNotEmpty();
            assertThat(repo.login()).isEqualTo(OWNER_LOGIN);
            List<GithubBranchWithSha> branches = repo.branches();
            assertThat(branches).isNotNull();
            for (GithubBranchWithSha branch : branches) {
                assertThat(branch.name()).isNotEmpty();
                assertThat(branch.sha()).isNotEmpty();
            }
        }
    }
}
