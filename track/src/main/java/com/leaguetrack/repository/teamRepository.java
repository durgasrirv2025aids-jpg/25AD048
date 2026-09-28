package com.leaguetrack.repository;

import com.leaguetrack.model.team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface teamRepository extends JpaRepository<team, Long> {
}