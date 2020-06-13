package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Lek;
import main.mrs.model.OcenaKlinika;
import main.mrs.repository.OcenaKlinikaRepository;

@Service
public class OcenaKlinikaService {
	@Autowired
	private OcenaKlinikaRepository OcenaKlinikaRepository;

	public OcenaKlinika findOne(Long id) {
		return OcenaKlinikaRepository.findById(id).orElseGet(null);
	}

	public List<OcenaKlinika> findAll() {
		return OcenaKlinikaRepository.findAll();
	}

	public Page<OcenaKlinika> findAll(Pageable page) {
		return OcenaKlinikaRepository.findAll(page);
	}

	public OcenaKlinika save(OcenaKlinika OcenaKlinika) {
		return OcenaKlinikaRepository.save(OcenaKlinika);
	}

	public void remove(Long id) {
		OcenaKlinikaRepository.deleteById(id);
	}
	
	public void delete(OcenaKlinika ocena) {
		 OcenaKlinikaRepository.delete(ocena);
		
	}

	public OcenaKlinika findOcenu(int klinikaId, int pacijentId) {
		// TODO Auto-generated method stub
		return OcenaKlinikaRepository.findOcenu(klinikaId, pacijentId);
	}
}
