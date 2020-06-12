package main.mrs.isa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.mrs.model.Pregled;
import main.mrs.repository.PregledRepository;

@Service
public class PregledService {
	@Autowired
	private PregledRepository PregledRepository;

	public Pregled findOne(Long id) {
		return PregledRepository.findById(id).orElseGet(null);
	}

	public List<Pregled> findAll() {
		return PregledRepository.findAll();
	}

	public Page<Pregled> findAll(Pageable page) {
		return PregledRepository.findAll(page);
	}

	public Pregled save(Pregled Pregled) {
		return PregledRepository.save(Pregled);
	}

	public void remove(Long id) {
		PregledRepository.deleteById(id);
	}

	public Pregled findById(long pregledId) {
		// TODO Auto-generated method stub
		return PregledRepository.findById(pregledId);
	}
	public List<Pregled> findByTipPregleda(int tipPregledaId) {
		// TODO Auto-generated method stub
		return PregledRepository.findByTipPregleda(tipPregledaId);
	}

	public List<Pregled> findAfterDate(Date datum) {
		// TODO Auto-generated method stub
		return PregledRepository.findAfterDate(datum);
	}

	public List<Pregled> getUnscheduled(int klinikaId) {
		// TODO Auto-generated method stub
		return PregledRepository.getUnscheduled(klinikaId);
	}

	public List<Pregled> getScheduled(int pacijentId) {
		// TODO Auto-generated method stub
		return PregledRepository.getScheduled(pacijentId);
	}

	public List<Pregled> nadjiPregledeLekaraZaDan(Integer id, Date date1) {
		// TODO Auto-generated method stub
		return PregledRepository.nadjiPregledeLekaraZaDan(id, date1);
	}

	public List<Pregled> dobaviIstoriju(int pacijentId) {
		// TODO Auto-generated method stub
		return PregledRepository.dobaviIstoriju(pacijentId);
	}

	public List<Pregled> findAllZahteviKlinike(Integer integer) {
		return PregledRepository.findAllZahteviKlinike(integer);
	}
	public List<Pregled> findAllZahtevi() {
		return PregledRepository.findAllZahtevi();
	}

	public List<Pregled> findByLekarId(Integer id) {
		return  PregledRepository.findAllByLekarId(id);
	}

	public List<Pregled> getPreglediByPL(Integer pacijent_id, Integer lekar_id) {
		return  PregledRepository.getPreglediByPL(pacijent_id,lekar_id);
	}

	public List<Pregled> izvestaj(Date pocetak, Date kraj, Integer id) {
		return PregledRepository.izvestaj(pocetak,kraj, id);
	}

	public Integer getPreglediZaDatum(Date danas, Integer id) {
		return PregledRepository.getPreglediZaDatum(danas, id);
		
	}

	public Integer getPreglediZaSate(Date danas, Date danas2, Integer idKlinike) {
		return PregledRepository.getPreglediZaSate(danas, danas2, idKlinike);
	}

	public List<Pregled> getScheduledForDr(int lekarId) {
		// TODO Auto-generated method stub
		return PregledRepository.getScheduledForDr(lekarId);
	}

	public List<Pregled> findZavrsene(Integer id) {
		return PregledRepository.getZavrsenePregledeZaRecept(id);
	}

	public List<Pregled> findByLekarIdNotFinished(Integer id) {
		return  PregledRepository.findAllByLekarIdNotFinished(id);
	}

	public List<Pregled> findAllByType(Integer id) {
		return  PregledRepository.findAllByType(id);
	}



}
