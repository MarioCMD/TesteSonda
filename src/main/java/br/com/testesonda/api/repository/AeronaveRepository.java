package br.com.testesonda.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.testesonda.api.entity.Aeronave;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {

}
