package main.mrs.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.mrs.model.Sala;
import main.mrs.repository.SalaRepository;

@Service
@Transactional
public class SalaService {
	@Autowired
	private SalaRepository SalaRepository;

	public Sala findOne(Integer id) {
		return SalaRepository.findById(id).orElseGet(null);
	}

	public List<Sala> findAll() {
		return SalaRepository.findAll();
	}

	public Page<Sala> findAll(Pageable page) {
		return SalaRepository.findAll(page);
	}
	
	@Transactional(readOnly= false)
	public Sala save(Sala Sala) {
		return SalaRepository.save(Sala);
	}

	public void remove(Integer id) {
		SalaRepository.deleteById(id);
	}
	public Sala findByBroj(int broj) {
		return SalaRepository.findByBroj(broj);
	}

	public Sala findByNaziv(String naziv) {
		return SalaRepository.findByNaziv(naziv);
	}

	public List<Sala> findSearchNaziv(String searchParam) {
		return SalaRepository.findSearchNaziv(searchParam);
	}

	public List<Sala> findAllByIdKlinike(Integer id) {
		return SalaRepository.findAllByIdKlinike(id);
	}
}
