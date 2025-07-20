package com.example.githubapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepository(String name, GithubRepoOwner owner, boolean fork) {}
