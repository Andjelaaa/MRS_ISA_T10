package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import main.mrs.model.AdminKlinike;
import main.mrs.model.MedSestra;
import main.mrs.repository.MedSestraRepository;

@Service
public class MedSestraService {

	@Autowired
	private MedSestraRepository MedSestraRepository;
	
	
	public MedSestra findOne(Integer id) {
		return MedSestraRepository.findById(id).orElseGet(null);
	}

	public List<MedSestra> findAll() {
		return MedSestraRepository.findAll();
	}
	
	public Page<MedSestra> findAll(Pageable page) {
		return MedSestraRepository.findAll(page);
	}

	public MedSestra save(MedSestra MedSestra) {
		return MedSestraRepository.save(MedSestra);
	}

	public void remove(Integer id) {
		MedSestraRepository.deleteById(id);
	}

	public MedSestra findByEmail(String name) {
		// TODO Auto-generated method stub
		return MedSestraRepository.findByEmail(name);
	}

	
	public UserDetails loadUserByUsername(String arg0) {
		MedSestra korisnik = MedSestraRepository.findByEmail(arg0);
		if (korisnik == null) {
			return null;
		} else {
			return (MedSestra) korisnik;
		}
	}

	public List<MedSestra> findByImeAndPrezime(String upperCase, String upperCase2) {
		return MedSestraRepository.findByImeAndPrezime(upperCase, upperCase2);
	}

	public List<MedSestra> findAllByIdKlinike(Integer id) {
		return MedSestraRepository.findAllByIdKlinike(id);
	}
}
