package com.leaguetrack.service;

import com.leaguetrack.model.team;
import com.leaguetrack.repository.teamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class teamService {

    private final teamRepository teamRepository;

    public teamService(teamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    public team addTeam(team data) {
        return teamRepository.save(data);
    }

    public List<team> getAllTeams() {
        return teamRepository.findAll();
    }


    public team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }


    public team updateTeam(Long id, team data) {

        team oldTeam = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        oldTeam.setName(data.getName());
        oldTeam.setDepartment(data.getDepartment());

        return teamRepository.save(oldTeam);
    }


    public void deleteTeam(Long id) {

        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found");
        }

        teamRepository.deleteById(id);
    }
}