package com.leaguetrack.controller;

import com.leaguetrack.model.fixture;
import com.leaguetrack.service.fixtureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixtures")
public class fixtureController {

    private final fixtureService fixtureService;

    public fixtureController(fixtureService fixtureService) {
        this.fixtureService = fixtureService;
    }

    @PostMapping("/generate")
    public String generateFixtures() {
        return fixtureService.generateFixtures();
    }

    @GetMapping
    public List<fixture> getAllFixtures() {
        return fixtureService.getAllFixtures();
    }
}