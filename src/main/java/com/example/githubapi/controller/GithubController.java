package com.example.githubapi.controller;

import com.example.githubapi.service.IGithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final IGithubService githubService;

    public GithubController(IGithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getAllRepos(@PathVariable String username){
        return ResponseEntity.ok().body(githubService.getRepos(username));
    }
}
