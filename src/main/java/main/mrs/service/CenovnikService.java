package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Cenovnik;
import main.mrs.repository.CenovnikRepository;

@Service
public class CenovnikService {
	@Autowired
	private CenovnikRepository CenovnikRepository;

	public Cenovnik findOne(Integer id) {
		return CenovnikRepository.findById(id).orElseGet(null);
	}

	public List<Cenovnik> findAll() {
		return CenovnikRepository.findAll();
	}

	public Page<Cenovnik> findAll(Pageable page) {
		return CenovnikRepository.findAll(page);
	}

	public Cenovnik save(Cenovnik Cenovnik) {
		return CenovnikRepository.save(Cenovnik);
	}

	public void remove(Integer id) {
		CenovnikRepository.deleteById(id);
	}

}

