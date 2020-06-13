package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Izvestaj;
import main.mrs.repository.IzvestajRepository;

@Service
public class IzvestajService {
	@Autowired
	private IzvestajRepository IzvestajRepository;

	public Izvestaj findOne(Integer pregled_id) {
		return IzvestajRepository.findById(pregled_id).orElseGet(null);
	}

	public List<Izvestaj> findAll() {
		return IzvestajRepository.findAll();
	}

	public Page<Izvestaj> findAll(Pageable page) {
		return IzvestajRepository.findAll(page);
	}

	public Izvestaj save(Izvestaj Izvestaj) {
		return IzvestajRepository.save(Izvestaj);
	}

	public void remove(Long id) {
		IzvestajRepository.deleteById(id);
	}

	
}
