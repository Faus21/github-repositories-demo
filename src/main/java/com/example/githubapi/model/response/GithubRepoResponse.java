package com.example.githubapi.model.response;

import java.util.List;

public record GithubRepoResponse(String name, String login, List<GithubBranchWithSha> branches) {
}
