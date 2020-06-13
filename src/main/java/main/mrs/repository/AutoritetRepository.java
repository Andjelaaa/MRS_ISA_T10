package main.mrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.Autoritet;

public interface AutoritetRepository extends JpaRepository<Autoritet, Long> {
	Autoritet findByIme(String ime);
}
