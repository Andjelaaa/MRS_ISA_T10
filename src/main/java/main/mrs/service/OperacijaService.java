package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.mrs.model.Operacija;
import main.mrs.repository.OperacijaRepository;

@Service
public class OperacijaService {
	@Autowired
	private OperacijaRepository OperacijaRepository;

	public Operacija findOne(Integer idOperacije) {
		return OperacijaRepository.findById(idOperacije).orElseGet(null);
	}

	public List<Operacija> findAll() {
		return OperacijaRepository.findAll();
	}

	public Page<Operacija> findAll(Pageable page) {
		return OperacijaRepository.findAll(page);
	}
	
	public Operacija save(Operacija Operacija) {
		return OperacijaRepository.save(Operacija);
	}

	public void remove(Long id) {
		OperacijaRepository.deleteById(id);
	}

	public List<Integer> findByLekarId(Integer id) {
		return OperacijaRepository.findAllByLekarId(id);
	}

	public List<Operacija> findAllById(List<Integer> idOperacija) {
		return OperacijaRepository.findAllByIdIn(idOperacija);
	}

	public List<Operacija> findAllZahteviKlinike(Integer integer) {
		return OperacijaRepository.findAllZahteviKlinike(integer);
	}
	
	public List<Operacija> findAllZahtevi() {
		return OperacijaRepository.findAllZahtevi();
	}

	public Operacija findOneIfNotFinished(Integer i) {
		return OperacijaRepository.findOneIfNotFinished(i);
	}

	public List<Operacija> findAllBySalaId(Integer id) {
		return OperacijaRepository.findAllBySalaId(id);
	}

	public List<Operacija> getScheduled(Integer id) {
		// TODO Auto-generated method stub
		return OperacijaRepository.getScheduled(id);
	}

	public List<Operacija> getRealized(Integer id) {
		// TODO Auto-generated method stub
		return OperacijaRepository.getRealized(id);
	}





}
