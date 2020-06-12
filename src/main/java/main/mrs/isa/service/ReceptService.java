package main.mrs.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.mrs.model.Recept;
import main.mrs.repository.ReceptRepository;

@Service
@Transactional
public class ReceptService {
	@Autowired
	private ReceptRepository ReceptRepository;

	public Recept findOne(Integer integer) {
		return ReceptRepository.findById(integer).orElseGet(null);
	}

	public List<Recept> findAll() {
		return ReceptRepository.findAll();
	}

	public Page<Recept> findAll(Pageable page) {
		return ReceptRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public Recept save(Recept Recept) {
		return ReceptRepository.save(Recept);
	}

	public void remove(Long id) {
		ReceptRepository.deleteById(id);
	}



}
