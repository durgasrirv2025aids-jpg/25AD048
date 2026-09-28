package com.leaguetrack.controller;

import com.leaguetrack.model.match;
import com.leaguetrack.service.matchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class matchController {

    private final matchService matchService;

    public matchController(matchService matchService) {
        this.matchService = matchService;
    }

    // Add Match
    @PostMapping
    public match addMatch(@RequestBody match data) {
        return matchService.addMatch(data);
    }

    // Get All Matches
    @GetMapping
    public List<match> getAllMatches() {
        return matchService.getAllMatches();
    }

    // Get Match By ID
    @GetMapping("/{id}")
    public match getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }

    // Update Match
    @PutMapping("/{id}")
    public match updateMatch(
            @PathVariable Long id,
            @RequestBody match data) {

        return matchService.updateMatch(id, data);
    }

    // Delete Match
    @DeleteMapping("/{id}")
    public String deleteMatch(@PathVariable Long id) {

        matchService.deleteMatch(id);

        return "Match deleted successfully";
    }
}