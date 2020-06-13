package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Dijagnoza;
import main.mrs.repository.DijagnozaRepository;

@Service
public class DijagnozaService {
	@Autowired
	private DijagnozaRepository DijagnozaRepository;

	public Dijagnoza findOne(Integer integer) {
		return DijagnozaRepository.findById(integer).orElseGet(null);
	}

	public List<Dijagnoza> findAll() {
		return DijagnozaRepository.findAll();
	}

	public Page<Dijagnoza> findAll(Pageable page) {
		return DijagnozaRepository.findAll(page);
	}

	public Dijagnoza save(Dijagnoza Dijagnoza) {
		return DijagnozaRepository.save(Dijagnoza);
	}

	public void remove(Long id) {
		DijagnozaRepository.deleteById(id);
	}

	public Dijagnoza findByNaziv(String naziv) {
		return DijagnozaRepository.findByNaziv(naziv);
	}

	public void delete(Dijagnoza nadjiDijagnozu) {
		DijagnozaRepository.delete(nadjiDijagnozu);
		
	}

}
