package main.mrs.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.LekarDTO;
import main.mrs.dto.ZauzecaSlobodniDTO;
import main.mrs.model.Operacija;
import main.mrs.model.PomocnaKlasa7;
import main.mrs.model.Pregled;
import main.mrs.model.Sala;
import main.mrs.service.OperacijaService;
import main.mrs.service.PregledService;
import main.mrs.service.SalaService;

@RestController
public class SystemController {
	
	SimpleDateFormat sdf=null;
	SimpleDateFormat sdf1=null;
	@Autowired
	public OperacijaController controllerOp;
	
	@Autowired
	public SalaController controllerSala;
	
	@Autowired
	public PregledController controllerPregled;
	
	@Autowired
	public OperacijaService OperacijaService;

	@Autowired
	public PregledService PregledService;
	
	@Autowired
	public SalaService SalaService;
	/*
	 * Logika se izvrsava u vremenskim trenucima definisanih cron sintaksom. 
	 * ${greeting.cron} -> cita se vrednost koja je definisana u src/main/application.properties fajlu.
	 */
	@Scheduled(cron = "${greeting.cron}")
	public void cronJob() {
		
		// neka logika 
		logikaZaDodeljivanjeSalaZaOperaciju();
		logikaZaDodeljivanjeSalaZaPregled();
		
	}

	private void logikaZaDodeljivanjeSalaZaOperaciju() {
		/////logika za dodelu
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
		//ne treba dto
		List<Operacija> operacije = OperacijaService.findAllZahtevi();

		List<Sala> sveSale = SalaService.findAll();
		Random rand = new Random(); 
		Integer idSale = 1;
		try {
			idSale = rand.nextInt(sveSale.size()-1) + 1; // ovo je zato sto krene od 0
		}
		catch(Exception e) {
			System.out.println("Puklo je");
		}
		sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		//sve sale
		for(Operacija op : operacije) {
			List<Sala> sale =  dobaviSale(op);
			List<LekarDTO> lekari = new ArrayList<>();
			PomocnaKlasa7 p = new PomocnaKlasa7(lekari);
			if(!sale.isEmpty()) {
				
				controllerOp.rezervisiSaluZaOperaciju(op.getId(), sale.get(0).getId(), sdf.format(op.getDatumVreme()), p);
				System.out.println(" odradio ");
			}
			else {
				//ovde ako nema slobodnih sala za taj datum onda bilo koji
				int datum = op.getDatumVreme().getDate() + 1;
				op.getDatumVreme().setDate(datum);
				ResponseEntity<ZauzecaSlobodniDTO>  slobodni=controllerSala.getZauzecaZaDatumOP(sdf1.format(op.getDatumVreme()), idSale, op.getId());
				controllerOp.rezervisiSaluZaOperaciju(op.getId(), idSale, sdf.format(slobodni.getBody().getPrviSlobodan()),p);
				System.out.println(" odradio 222 ");
			}
			
		}

	}

	private List<Sala> dobaviSale(Operacija operacija) {
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		long curTimeInMs = operacija.getDatumVreme().getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS));
		
		List<Pregled> SviPregledi = PregledService.findAll();
		List<Sala> ZauzeteSale = new ArrayList<Sala>();
		
		List<Operacija> SveOperacije=  OperacijaService.findAll();
		for (Pregled p : SviPregledi) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(operacija.getDatumVreme())) || 
				(p.getDatumVreme().after(operacija.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(operacija.getDatumVreme()) && p.getDatumVreme().before(operacija.getDatumVreme())) ||
				(p.getDatumVreme().after(operacija.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		for (Operacija p : SveOperacije) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(operacija.getDatumVreme())) || 
				(p.getDatumVreme().after(operacija.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(operacija.getDatumVreme()) && p.getDatumVreme().before(operacija.getDatumVreme())) ||
				(p.getDatumVreme().after(operacija.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		List<Sala> slobodne = new ArrayList<Sala>();
		List<Sala> sveSale = SalaService.findAll();
		for (Sala sala : sveSale) {
			if(!ZauzeteSale.contains(sala)) {
				System.out.println(sala.getNaziv());
				slobodne.add(sala);
			}
		}
		
		return slobodne;
	}

	private void logikaZaDodeljivanjeSalaZaPregled() {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
		//ne treba dto
		List<Pregled> pregledi = PregledService.findAllZahtevi();

		List<Sala> sveSale = SalaService.findAll();
		Random rand = new Random(); 
		Integer idSale = 1;
		try {
			idSale = rand.nextInt(sveSale.size()-1) + 1; // ovo je zato sto krene od 0
		}
		catch(Exception e) {
			System.out.println("Puklo je");
		}
		sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		//sve sale
		for(Pregled p : pregledi) {
			List<Sala> sale =  dobaviSalePregled(p);
			if(!sale.isEmpty()) {
				controllerPregled.rezervisiSaluZaPregled(p.getId(), sale.get(0).getId(), sdf.format(p.getDatumVreme()));
				System.out.println(" odradio ");
			}
			else {
				//ovde ako nema slobodnih sala za taj datum onda bilo koji
				int datum = p.getDatumVreme().getDate() + 1;
				p.getDatumVreme().setDate(datum);
				ResponseEntity<ZauzecaSlobodniDTO>  slobodni=controllerSala.getZauzecaZaDatumOP(sdf1.format(p.getDatumVreme()), idSale, p.getId());
				controllerPregled.rezervisiSaluZaPregled(p.getId(), idSale, sdf.format(slobodni.getBody().getPrviSlobodan()));
				System.out.println(" odradio 222 ");
			}
			
		}

		
		
	}

	private List<Sala> dobaviSalePregled(Pregled pregled) {
sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		long curTimeInMs = pregled.getDatumVreme().getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (pregled.getTrajanje() * ONE_MINUTE_IN_MILLIS));
		
		List<Pregled> SviPregledi = PregledService.findAll();
		List<Sala> ZauzeteSale = new ArrayList<Sala>();
		
		List<Operacija> SveOperacije=  OperacijaService.findAll();
		for (Pregled p : SviPregledi) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(pregled.getDatumVreme())) || 
				(p.getDatumVreme().after(pregled.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(pregled.getDatumVreme()) && p.getDatumVreme().before(pregled.getDatumVreme())) ||
				(p.getDatumVreme().after(pregled.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		for (Operacija p : SveOperacije) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(pregled.getDatumVreme())) || 
				(p.getDatumVreme().after(pregled.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(pregled.getDatumVreme()) && p.getDatumVreme().before(pregled.getDatumVreme())) ||
				(p.getDatumVreme().after(pregled.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		List<Sala> slobodne = new ArrayList<Sala>();
		List<Sala> sveSale = SalaService.findAll();
		for (Sala sala : sveSale) {
			if(!ZauzeteSale.contains(sala)) {
				System.out.println(sala.getNaziv());
				slobodne.add(sala);
			}
		}
		
		return slobodne;
	}

}
