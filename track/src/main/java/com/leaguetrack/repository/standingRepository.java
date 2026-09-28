package com.leaguetrack.repository;

import com.leaguetrack.model.standing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface standingRepository extends JpaRepository<standing, Long> {

    Optional<standing> findByTeamId(Long teamId);
}