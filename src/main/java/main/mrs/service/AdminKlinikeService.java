package main.mrs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import main.mrs.model.AdminKlinike;
import main.mrs.repository.AdminKlinikeRepository;


@Service
public class AdminKlinikeService {

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	
	public AdminKlinike findOne(Integer idAdmina) {
		return adminKlinikeRepository.findById(idAdmina).orElseGet(null);
	}

	public List<AdminKlinike> findAll() {
		return adminKlinikeRepository.findAll();
	}
	
	public Page<AdminKlinike> findAll(Pageable page) {
		return adminKlinikeRepository.findAll(page);
	}

	public AdminKlinike save(AdminKlinike AdminKlinike) {
		return adminKlinikeRepository.save(AdminKlinike);
	}

	public void remove(Long id) {
		adminKlinikeRepository.deleteById(id);
	}

	public AdminKlinike findByEmail(String name) {
		// TODO Auto-generated method stub
		return adminKlinikeRepository.findByEmail(name);
	}
	
	public UserDetails loadUserByUsername(String arg0) {
		AdminKlinike korisnik = adminKlinikeRepository.findByEmail(arg0);
		if (korisnik == null) {
			return null;
		} else {
			return (AdminKlinike) korisnik;
		}
	}

}
