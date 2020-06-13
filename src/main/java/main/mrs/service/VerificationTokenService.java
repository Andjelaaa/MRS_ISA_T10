package main.mrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.mrs.model.Pacijent;
import main.mrs.model.VerificationToken;
import main.mrs.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {
	
	@Autowired 
	private VerificationTokenRepository verificationTokenRepository;
	
	
	public VerificationToken save(VerificationToken token) {
		return verificationTokenRepository.save(token);
	}
	
	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}
	public VerificationToken findByPacijent(Pacijent pacijent) {
		return verificationTokenRepository.findByPacijent(pacijent);
	}	

}
