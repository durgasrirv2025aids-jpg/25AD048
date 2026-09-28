package com.leaguetrack.controller;

import com.leaguetrack.model.standing;
import com.leaguetrack.service.standingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/standings")
public class standingController {

    private final standingService standingService;

    public standingController(standingService standingService) {
        this.standingService = standingService;
    }

    @PostMapping("/{teamId}")
    public standing addTeamToStandings(
            @PathVariable Long teamId) {

        return standingService.addTeamToStandings(teamId);
    }

    @GetMapping
    public List<standing> getStandings() {

        return standingService.getStandings();
    }

    @GetMapping("/{teamId}")
    public standing getByTeamId(
            @PathVariable Long teamId) {

        return standingService.getByTeamId(teamId);
    }

    @PutMapping("/{teamId}/{result}")
    public standing updatePoints(
            @PathVariable Long teamId,
            @PathVariable String result) {

        return standingService.updatePoints(teamId, result);
    }
}