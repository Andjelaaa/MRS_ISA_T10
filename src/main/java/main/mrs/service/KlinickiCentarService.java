package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.KlinickiCentar;
import main.mrs.model.Klinika;
import main.mrs.repository.KlinickiCentarRepository;

@Service
public class KlinickiCentarService {
	@Autowired
	private KlinickiCentarRepository KlinickiCentarRepository;
	
	public KlinickiCentar findOne(Integer id) {
		return KlinickiCentarRepository.findById(id).orElseGet(null);
	}

	public List<KlinickiCentar> findAll() {
		return KlinickiCentarRepository.findAll();
	}
	
	public Page<KlinickiCentar> findAll(Pageable page) {
		return KlinickiCentarRepository.findAll(page);
	}

	public KlinickiCentar save(KlinickiCentar Klinika) {
		return KlinickiCentarRepository.save(Klinika);
	}

	public void remove(Long id) {
		KlinickiCentarRepository.deleteById(id);
	}



}
