package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Klinika;
import main.mrs.repository.KlinikaRepository;

@Service
public class KlinikaService {
	
	@Autowired
	private KlinikaRepository KlinikaRepository;
	
	public Klinika findOne(Integer id) {
		return KlinikaRepository.findById(id).orElseGet(null);
	}

	public List<Klinika> findAll() {
		return KlinikaRepository.findAll();
	}
	
	public Page<Klinika> findAll(Pageable page) {
		return KlinikaRepository.findAll(page);
	}

	public Klinika save(Klinika Klinika) {
		return KlinikaRepository.save(Klinika);
	}

	public void remove(Long id) {
		KlinikaRepository.deleteById(id);
	}

	public Klinika findOneById(int klinikaId) {
		// TODO Auto-generated method stub
		return KlinikaRepository.findOneById(klinikaId);
	}
}
