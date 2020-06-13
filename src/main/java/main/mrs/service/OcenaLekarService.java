package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.OcenaLekar;
import main.mrs.repository.OcenaLekarRepository;

@Service
public class OcenaLekarService {

	@Autowired
	private OcenaLekarRepository OcenaLekarRepository;

	public OcenaLekar findOne(Long id) {
		return OcenaLekarRepository.findById(id).orElseGet(null);
	}

	public List<OcenaLekar> findAll() {
		return OcenaLekarRepository.findAll();
	}

	public Page<OcenaLekar> findAll(Pageable page) {
		return OcenaLekarRepository.findAll(page);
	}

	public OcenaLekar save(OcenaLekar OcenaLekar) {
		return OcenaLekarRepository.save(OcenaLekar);
	}

	public void remove(Long id) {
		OcenaLekarRepository.deleteById(id);
	}
	
	public void delete(OcenaLekar ocena) {
		 OcenaLekarRepository.delete(ocena);
		
	}

	public OcenaLekar findOcenu(int lekarId, int pacijentId) {
		// TODO Auto-generated method stub
		return OcenaLekarRepository.findOcenu(lekarId, pacijentId);
	}
}
