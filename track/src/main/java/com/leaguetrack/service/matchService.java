package com.leaguetrack.service;

import com.leaguetrack.model.match;
import com.leaguetrack.repository.matchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class matchService {

    private final matchRepository matchRepository;

    public matchService(matchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public match addMatch(match data) {
        return matchRepository.save(data);
    }

    public List<match> getAllMatches() {
        return matchRepository.findAll();
    }

    public match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public match updateMatch(Long id, match data) {

        match oldMatch = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        oldMatch.setTeam1Id(data.getTeam1Id());
        oldMatch.setTeam2Id(data.getTeam2Id());
        oldMatch.setMatchDate(data.getMatchDate());
        oldMatch.setStatus(data.getStatus());

        return matchRepository.save(oldMatch);
    }


    public void deleteMatch(Long id) {

        if (!matchRepository.existsById(id)) {
            throw new RuntimeException("Match not found");
        }

        matchRepository.deleteById(id);
    }
}