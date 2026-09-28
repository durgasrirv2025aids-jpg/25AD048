package com.leaguetrack.repository;

import com.leaguetrack.model.match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface matchRepository extends JpaRepository<match, Long> {
}