package main.mrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;

@Service
public class KorisnikService implements UserDetailsService {

	@Autowired
	public PacijentService pacijentService;
	@Autowired
	public AdminKlinikeService adminKlinikeService;
	@Autowired
	private AdminKCService adminKCService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private MedSestraService medSestraService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if(pacijentService.loadUserByUsername(username) != null)
			return (Pacijent)pacijentService.loadUserByUsername(username);
		else if(adminKlinikeService.loadUserByUsername(username) != null)
			return (AdminKlinike)adminKlinikeService.loadUserByUsername(username);
		else if(adminKCService.loadUserByUsername(username) != null)
			return (AdminKC)adminKCService.loadUserByUsername(username);
		else if(lekarService.loadUserByUsername(username) != null)
			return (Lekar)lekarService.loadUserByUsername(username);
		else if(medSestraService.loadUserByUsername(username) != null)
			return (MedSestra)medSestraService.loadUserByUsername(username);
		throw new UsernameNotFoundException(String.format("Nije pronadjen korisnik sa email-om: '%s'.", username));
	}
	

}
