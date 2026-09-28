package com.leaguetrack.repository;

import com.leaguetrack.model.fixture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fixtureRepository extends JpaRepository<fixture, Long> {
}