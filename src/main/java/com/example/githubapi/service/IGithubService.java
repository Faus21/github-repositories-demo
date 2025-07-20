package com.example.githubapi.service;

import com.example.githubapi.model.response.GithubRepoResponse;

import java.util.List;

public interface IGithubService {
    List<GithubRepoResponse> getRepos(String username);
}
