package com.leaguetrack.service;

import com.leaguetrack.model.fixture;
import com.leaguetrack.model.team;
import com.leaguetrack.repository.fixtureRepository;
import com.leaguetrack.repository.teamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class fixtureService {

    private final fixtureRepository fixtureRepository;
    private final teamRepository teamRepository;

    public fixtureService(fixtureRepository fixtureRepository,
                          teamRepository teamRepository) {
        this.fixtureRepository = fixtureRepository;
        this.teamRepository = teamRepository;
    }

    public String generateFixtures() {

        List<team> teams = teamRepository.findAll();

        if (teams.size() < 2) {
            return "At least 2 teams are required";
        }

        Thread fixtureThread = new Thread(() -> {

            for (int i = 0; i < teams.size(); i++) {

                for (int j = i + 1; j < teams.size(); j++) {

                    fixture newFixture = new fixture();

                    newFixture.setTeam1Id(teams.get(i).getId());
                    newFixture.setTeam2Id(teams.get(j).getId());
                    newFixture.setMatchDate("2026-10-" + (j + 1));
                    newFixture.setStatus("Scheduled");

                    fixtureRepository.save(newFixture);
                }
            }
        });

        fixtureThread.start();

        try {
            fixtureThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "Fixtures generated successfully";
    }

    public List<fixture> getAllFixtures() {
        return fixtureRepository.findAll();
    }
}