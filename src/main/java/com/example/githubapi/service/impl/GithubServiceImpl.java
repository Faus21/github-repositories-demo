package com.example.githubapi.service.impl;

import com.example.githubapi.client.GithubClient;
import com.example.githubapi.model.dto.GithubBranch;
import com.example.githubapi.model.dto.GithubRepository;
import com.example.githubapi.model.response.GithubBranchWithSha;
import com.example.githubapi.model.response.GithubRepoResponse;
import com.example.githubapi.service.IGithubService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubServiceImpl implements IGithubService {


    private final GithubClient client;

    public GithubServiceImpl(GithubClient client) {
        this.client = client;
    }

    @Override
    public List<GithubRepoResponse> getRepos(String username) {
        List<GithubRepository> repositories = client.getNoForkedRepositories(username);
        List<GithubRepoResponse> responses = new ArrayList<>();

        for (GithubRepository repo : repositories) {
            List<GithubBranch> branches = client.getBranches(repo.owner().login(), repo.name());

            List<GithubBranchWithSha> branchesWithSha = new ArrayList<>();
            for (GithubBranch branch : branches) {
                branchesWithSha.add(new GithubBranchWithSha(branch.name(), branch.commit().sha()));
            }

            GithubRepoResponse response = new GithubRepoResponse(repo.name(), repo.owner().login(), branchesWithSha);
            responses.add(response);
        }

        return responses;
    }
}
