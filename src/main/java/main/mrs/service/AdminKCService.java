package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.repository.AdminKCRepository;

@Service
public class AdminKCService {
 
	@Autowired
	private AdminKCRepository adminKCRepository;
	
	public AdminKC findOne(Integer id) {
		return adminKCRepository.findById(id).orElseGet(null);
	}

	public List<AdminKC> findAll() {
		return adminKCRepository.findAll();
	}
	
	public Page<AdminKC> findAll(Pageable page) {
		return adminKCRepository.findAll(page);
	}

	public AdminKC save(AdminKC AdminKC) {
		return adminKCRepository.save(AdminKC);
	}

	public void remove(Long id) {
		adminKCRepository.deleteById(id);
	}

	public AdminKC findByEmail(String name) {
		// TODO Auto-generated method stub
		return adminKCRepository.findByEmail(name);
	}
	public UserDetails loadUserByUsername(String arg0) {
		AdminKC korisnik = adminKCRepository.findByEmail(arg0);
		if (korisnik == null) {
			return null;
		} else {
			return (AdminKC)korisnik;
		}
	}
	

}
