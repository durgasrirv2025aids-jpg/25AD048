package com.leaguetrack.service;

import com.leaguetrack.model.standing;
import com.leaguetrack.repository.standingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class standingService {

    private final standingRepository standingRepository;

    public standingService(standingRepository standingRepository) {
        this.standingRepository = standingRepository;
    }

    public standing addTeamToStandings(Long teamId) {

        standing entry = new standing();

        entry.setTeamId(teamId);
        entry.setPlayed(0);
        entry.setWon(0);
        entry.setDrawn(0);
        entry.setLost(0);
        entry.setPoints(0);

        return standingRepository.save(entry);
    }

    public List<standing> getStandings() {
        return standingRepository.findAll();
    }

    public standing getByTeamId(Long teamId) {

        return standingRepository.findByTeamId(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found in standings"));
    }

    public standing updatePoints(Long teamId, String result) {

        standing entry = standingRepository.findByTeamId(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found in standings"));

        entry.setPlayed(entry.getPlayed() + 1);

        if (result.equalsIgnoreCase("WIN")) {

            entry.setWon(entry.getWon() + 1);
            entry.setPoints(entry.getPoints() + 3);

        } else if (result.equalsIgnoreCase("DRAW")) {

            entry.setDrawn(entry.getDrawn() + 1);
            entry.setPoints(entry.getPoints() + 1);

        } else if (result.equalsIgnoreCase("LOSS")) {

            entry.setLost(entry.getLost() + 1);
        }

        return standingRepository.save(entry);
    }
}