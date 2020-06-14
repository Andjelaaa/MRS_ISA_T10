package main.mrs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.mrs.model.Lekar;
import main.mrs.model.Odsustvo;
import main.mrs.repository.OdsustvoRepository;

@Service
@Transactional
public class OdsustvoService {
	@Autowired
	private OdsustvoRepository OdsustvoRepository;

	public List<Odsustvo> findAll() {
		return OdsustvoRepository.findAll();
	}
	
	public Page<Odsustvo> findAll(Pageable page) {
		return OdsustvoRepository.findAll(page);
	}

	@Transactional(readOnly= false)
	public Odsustvo save(Odsustvo Odsustvo) {
		return OdsustvoRepository.save(Odsustvo);
	}

	public void remove(Long id) {
		OdsustvoRepository.deleteById(id);
	}

	public Odsustvo daLiOdsustvuje(Integer id, Date date1) {
		// TODO Auto-generated method stub
		return OdsustvoRepository.daLiOdsustvuje(id, date1);
	}

	public Odsustvo findOne(Integer id) {
		return OdsustvoRepository.findById(id);
	}

	public List<Odsustvo> findAllZahtevi() {
		return OdsustvoRepository.findAllZahtevi();
	}

	public List<Odsustvo> findAllByIdLekar(Integer id) {
		return OdsustvoRepository.findAllByIdLekar(id);
	}
	

	
}
