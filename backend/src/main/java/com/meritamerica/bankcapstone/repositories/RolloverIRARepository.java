package com.meritamerica.bankcapstone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.bankcapstone.models.RolloverIRA;


@Repository
public interface RolloverIRARepository extends JpaRepository<RolloverIRA, Long> {

}
