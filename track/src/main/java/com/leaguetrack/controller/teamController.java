package com.leaguetrack.controller;

import com.leaguetrack.model.team;
import com.leaguetrack.service.teamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class teamController {

    private final teamService teamService;

    public teamController(teamService teamService) {
        this.teamService = teamService;
    }


    @PostMapping
    public team addTeam(@RequestBody team data) {
        return teamService.addTeam(data);
    }


    @GetMapping
    public List<team> getAllTeams() {
        return teamService.getAllTeams();
    }


    @GetMapping("/{id}")
    public team getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }


    @PutMapping("/{id}")
    public team updateTeam(
            @PathVariable Long id,
            @RequestBody team data) {

        return teamService.updateTeam(id, data);
    }


    @DeleteMapping("/{id}")
    public String deleteTeam(@PathVariable Long id) {

        teamService.deleteTeam(id);

        return "Team deleted successfully";
    }
}