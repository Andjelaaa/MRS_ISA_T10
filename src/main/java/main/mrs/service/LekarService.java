package main.mrs.service;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.repository.LekarRepository;

@Service
@Transactional
public class LekarService {
	@Autowired
	private LekarRepository LekarRepository;

	public Lekar findOne(Integer id) {
		return LekarRepository.findById(id).orElseGet(null);
	}

	public List<Lekar> findAll() {
		return LekarRepository.findAll();
	}

	public Page<Lekar> findAll(Pageable page) {
		return LekarRepository.findAll(page);
	}
	@Transactional(readOnly = false)
	public Lekar save(Lekar Lekar) {
		return LekarRepository.save(Lekar);
	}

	public void remove(Integer id) {
		LekarRepository.deleteById(id);
	}

	public Lekar findByEmailLock(String email, LockModeType pessimisticWrite) {
		return LekarRepository.findByEmailLock(email, pessimisticWrite);
	}
	
	public Lekar findByEmail(String email) {
		return LekarRepository.findByEmail(email);
	}

	public List<Lekar> findByImeAndPrezime(String ime, String prezime) {
		return LekarRepository.findByImeAndPrezime(ime, prezime);
	}
	
	public UserDetails loadUserByUsername(String arg0) {
		Lekar korisnik = LekarRepository.findByEmail(arg0);
		if (korisnik == null) {
			return null;
		} else {
			return (Lekar) korisnik;
		}
	}

	public List<Lekar> findByTipPregledaId(Integer id) {
		// TODO Auto-generated method stub
		return LekarRepository.findByTipPregledaId(id);
	}

	public Integer findByIdOp(Integer id_operacije) {
		return LekarRepository.findByIdOp(id_operacije);
	}

	public List<Lekar> findAllByIdKlinike(Integer id) {
		return LekarRepository.findAllByIdKlinike(id);
	}

	public Lekar findById(int lekarId) {
		// TODO Auto-generated method stub
		return LekarRepository.findById(lekarId);
	}

	public List<Lekar> findByImeAndPrezimeAndKlinika(String upperCase, String upperCase2, Integer idKlinike) {
		// TODO Auto-generated method stub
		return LekarRepository.findByImeAndPrezimeAndKlinika(upperCase, upperCase2, idKlinike);
	}
}
