package main.mrs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import main.mrs.dto.KorisnikTokenDTO;
import main.mrs.dto.PacijentDTO;
import main.mrs.model.Autoritet;
import main.mrs.model.Korisnik;
import main.mrs.model.Pacijent;
import main.mrs.repository.AutoritetRepository;
import main.mrs.security.TokenUtils;
import main.mrs.security.auth.JwtAuthenticationRequest;

@Service
public class AutoritetService {

	@Autowired
	private AutoritetRepository autoritetRepository;


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	TokenUtils tokenUtils;


	public Object createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return "Pogrešan email ili lozinka.";
		}

		Korisnik korisnik = (Korisnik) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenUtils.generateToken(korisnik.getEmail());
		int expiresIn = tokenUtils.getExpiredIn();

		return new KorisnikTokenDTO(jwt, expiresIn);
	}

	public List<Autoritet> findById(Long id) {
		Autoritet aut = autoritetRepository.getOne(id);
		List<Autoritet> autoriteti = new ArrayList<>();
		autoriteti.add(aut);
		return autoriteti;
	}

	public List<Autoritet> findByName(String name) {
		Autoritet aut = autoritetRepository.findByIme(name);
		List<Autoritet> autoriteti = new ArrayList<>();
		autoriteti.add(aut);
		return autoriteti;
	}

}