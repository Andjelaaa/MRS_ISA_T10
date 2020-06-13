package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.StavkaCenovnika;
import main.mrs.repository.StavkaCenovnikaRepository;

@Service
public class StavkaCenovnikaService {
	@Autowired
	private StavkaCenovnikaRepository StavkaCenovnikaRepository;

	public StavkaCenovnika findOne(Integer id) {
		return StavkaCenovnikaRepository.findById(id).orElseGet(null);
	}

	public List<StavkaCenovnika> findAll() {
		return StavkaCenovnikaRepository.findAll();
	}

	public Page<StavkaCenovnika> findAll(Pageable page) {
		return StavkaCenovnikaRepository.findAll(page);
	}

	public StavkaCenovnika save(StavkaCenovnika StavkaCenovnika) {
		return StavkaCenovnikaRepository.save(StavkaCenovnika);
	}

	public void remove(Integer id) {
		StavkaCenovnikaRepository.deleteById(id);
	}

}
