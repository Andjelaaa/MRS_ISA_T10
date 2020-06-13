package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Lek;
import main.mrs.model.TipPregleda;
import main.mrs.repository.LekRepository;

@Service
public class LekService {
	@Autowired
	private LekRepository LekRepository;

	public Lek findOne(Long id) {
		return LekRepository.findById(id).orElseGet(null);
	}

	public List<Lek> findAll() {
		return LekRepository.findAll();
	}

	public Page<Lek> findAll(Pageable page) {
		return LekRepository.findAll(page);
	}

	public Lek save(Lek Lek) {
		return LekRepository.save(Lek);
	}

	public void remove(Long id) {
		LekRepository.deleteById(id);
	}
	public Lek findByNaziv(String naziv) {
		return LekRepository.findByNaziv(naziv);
	}

	public void delete(Lek nadjiLek) {
		 LekRepository.delete(nadjiLek);
		
	}
}
