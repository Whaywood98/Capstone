package com.meritamerica.bankcapstone.repositories;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.bankcapstone.models.CDAccount;

@Entity
public interface CDAccountRepository extends JpaRepository<CDAccount, Long> {

}
