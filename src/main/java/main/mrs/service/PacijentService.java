package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.mrs.model.Pacijent;
import main.mrs.repository.PacijentRepository;

@Service
public class PacijentService{
	@Autowired
	private PacijentRepository PacijentRepository;
	

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public Pacijent findOne(Integer integer) {
		return PacijentRepository.findById(integer);
	}

	public List<Pacijent> findAll() {
		return PacijentRepository.findAll();
	}
	
	public Page<Pacijent> findAll(Pageable page) {
		return PacijentRepository.findAll(page);
	}

	public Pacijent save(Pacijent Pacijent) {
		return PacijentRepository.save(Pacijent);
	}

	public void remove(Long id) {
		PacijentRepository.deleteById(id);
	}
	
	public Pacijent findById(int pacijentId) {
		return PacijentRepository.findById(pacijentId);
	}

	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		Pacijent korisnik = PacijentRepository.findByEmail(arg0);
		if (korisnik == null) {
			return null;
		} else {
			return (Pacijent) korisnik;
		}
	}
	
	public String encodePassword(String lozinka) {
		return passwordEncoder.encode(lozinka);
	}

	public Pacijent findByEmail(String name) {
		return PacijentRepository.findByEmail(name);
	}

	public List<Pacijent> findByImeAndPrezimeAndLbo(String ime, String prezime, String lbo) {
		return PacijentRepository.findByImeAndPrezimeAndLbo(ime, prezime, lbo);
	}

	public Pacijent findByLbo(String lbo) {
		return PacijentRepository.findByLbo(lbo);
	}

	

}
