package main.mrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.ZahtevReg;
import main.mrs.repository.ZahtevRegRepository;

@Service
public class ZahtevRegService {
	@Autowired
	private ZahtevRegRepository ZahtevRegRepository;
	
	public ZahtevReg findOne(Long id) {
		return ZahtevRegRepository.findById(id).orElseGet(null);
	}
	public ZahtevReg findByEmail(String email) {
		return ZahtevRegRepository.findByEmail(email);
	}

	public List<ZahtevReg> findAll() {
		return ZahtevRegRepository.findAll();
	}
	
	public Page<ZahtevReg> findAll(Pageable page) {
		return ZahtevRegRepository.findAll(page);
	}

	public ZahtevReg save(ZahtevReg ZahtevReg) {
		return ZahtevRegRepository.save(ZahtevReg);
	}

	public void remove(Integer id) {
		ZahtevRegRepository.deleteById(id);
	}
	public void delete(ZahtevReg z) {
		ZahtevRegRepository.delete(z);
	}
}
