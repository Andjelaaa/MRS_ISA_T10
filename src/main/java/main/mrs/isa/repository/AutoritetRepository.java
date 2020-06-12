package main.mrs.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.Autoritet;

public interface AutoritetRepository extends JpaRepository<Autoritet, Long> {
	Autoritet findByIme(String ime);
}
