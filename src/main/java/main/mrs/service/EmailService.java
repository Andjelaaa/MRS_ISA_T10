package main.mrs.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.Odsustvo;
import main.mrs.model.Operacija;
import main.mrs.model.Pacijent;
import main.mrs.model.Pregled;
import main.mrs.model.ZahtevReg;



@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */
	@Autowired
	private Environment env;
	/*
	 * Anotacija za oznacavanje asinhronog zadatka
	 * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
	 */
//	@Async
//	public void sendNotificaitionAsync(ZahtevReg user) throws MailException, InterruptedException {
//		System.out.println("Slanje emaila...");
//
//		try {
//			MimeMessage message = javaMailSender.createMimeMessage();
//	
//	        message.setSubject("Potvrda registracije na Klinici");
//	        MimeMessageHelper helper;
//	        helper = new MimeMessageHelper(message, true);
//	        helper.setFrom(env.getProperty("spring.mail.username"));
//	        helper.setTo(user.getEmail());
//	        String text ="Pozdrav " + user.getIme() + ",<br>potvrdite email za aktivaciju vaseg naloga na sajtu Klinike.<br> <a href=http://localhost:8080/>Aktivacioni link</a>";
//	        message.setContent(text,"text/html");
//			javaMailSender.send(message);
//			System.out.println("Email poslat!");
//		}
//		catch(Exception e) {
//			System.out.println("Doslo je do greske...");
//		}
//		
//		
//	}
	
	
	
	@Async
	public void sendNotificaitionDeniedAsync(ZahtevReg user, String opis) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(user.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Zahtev za registracijom je odbijen");
			mail.setText("Pozdrav " + user.getIme() + ",\n\nzahtev Vam je odbijen iz razloga \n"+ opis);
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
		
	}
	
	@Async
	public void posaljiObavestenjeZakazanPregled(Pacijent pacijent, Pregled pregled ) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(pacijent.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Zakazan pregled");
			mail.setText("Postovani " + pacijent.getIme() + ",\n\nZakazani pregled:\n\nDatum i vreme: "+ pregled.getDatumVreme()+
					"\nTrajanje: "+pregled.getTrajanje() + "\nTip pregleda: " + pregled.getTipPregleda().getNaziv() + 
					"\nLekar: " + pregled.getLekar().getIme() + " " + pregled.getLekar().getPrezime());
//					+"\nSala: " + pregled.getSala().getNaziv() + "\nKlinika: " + pregled.getSala().getKlinika().getNaziv()+
//					"\nCena: " + pregled.getTipPregleda().getStavka().getCena() + 
//					"\nPopust: " + pregled.getPopust() + 
//					"\nUkupna cena: " + pregled.getTipPregleda().getStavka().getCena() * (100-pregled.getPopust()));
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
	}
	
	@Async
	public void posaljiObavestenjeOtkazanPregled(Pacijent pacijent, Pregled pregled ) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(pacijent.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Otkazan pregled");
			mail.setText("Postovani " + pacijent.getIme() + ",\n\nOtkazan pregled:\n\nDatum i vreme: "+ pregled.getDatumVreme()+
					"\nTrajanje: "+pregled.getTrajanje() + "\nTip pregleda: " + pregled.getTipPregleda().getNaziv() + 
					"\nLekar: " + pregled.getLekar().getIme() + " " + pregled.getLekar().getPrezime());
//					+"\nSala: " + pregled.getSala().getNaziv() + "\nKlinika: " + pregled.getSala().getKlinika().getNaziv()+
//					"\nCena: " + pregled.getTipPregleda().getStavka().getCena() + 
//					"\nPopust: " + pregled.getPopust() + 
//					"\nUkupna cena: " + pregled.getTipPregleda().getStavka().getCena() * (100-pregled.getPopust()));
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
		
	}

	@Async
	public void sendNotificaitionAsync(String url, String recipient, String subject) {
		System.out.println("Slanje emaila...");

		try {
			 
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setText("Potvrdite vas email za aktivaciju profila");
	        email.setTo(recipient);
	        email.setSubject(subject);
	        email.setText("http://localhost:8080/#" + url);
	        System.out.println(url);
	        javaMailSender.send(email);	
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}
	
	@Async
	public void mailAdminuZakazanPregled(Pregled pregled) {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			System.out.println((new ArrayList<AdminKlinike>(pregled.getLekar().getKlinika().getAdminKlinike())).get(0).getEmail());
			mail.setTo((new ArrayList<AdminKlinike>(pregled.getLekar().getKlinika().getAdminKlinike())).get(0).getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Zahtev za pregled");
			mail.setText("Postovani,\n\nZahtev za pregled:\n\nDatum i vreme: "+ pregled.getDatumVreme()+
					"\nTrajanje: "+pregled.getTrajanje() + "\nTip pregleda: " + pregled.getTipPregleda().getNaziv() + 
					"\nLekar: " + pregled.getLekar().getIme() + " " + pregled.getLekar().getPrezime()+
					"\nPacijent: " + pregled.getPacijent().getIme() + " " + pregled.getPacijent().getPrezime());
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getCause());
			System.out.println("Doslo je do greske...");
		}
		
	}
	
	@Async
	public void mailAdminuZakazanaOperacija(Operacija operacija, Lekar l) {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			System.out.println((new ArrayList<AdminKlinike>(l.getKlinika().getAdminKlinike())).get(0).getEmail());
			mail.setTo((new ArrayList<AdminKlinike>(l.getKlinika().getAdminKlinike())).get(0).getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Zahtev za operaciju");
			mail.setText("Postovani,\n\nZahtev za operaciju:\n\nDatum i vreme: "+ operacija.getDatumVreme()+
					"\nTrajanje: "+operacija.getTrajanje() +
					"\nLekar: " + l.getIme() + " " + l.getPrezime()+
					"\nPacijent: " + operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime());
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getCause());
			System.out.println("Doslo je do greske...");
		}
		
	}

	public void posaljiPacijentuOdobrenPregled(Pregled p) {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(p.getPacijent().getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Odobren zahtev za pregled");
			mail.setText("Postovani,\n\nZahtev za pregled:\n\nDatum i vreme: "+ p.getDatumVreme()+
					"\nTrajanje: "+p.getTrajanje() +
					"\nLekar: " + p.getLekar().getIme() + " " + p.getLekar().getPrezime()+
					"\nPacijent: " + p.getPacijent().getIme() + " " + p.getPacijent().getPrezime());
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}

	public void posaljiLekaruOdobrenPregled(Pregled p) {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(p.getLekar().getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Odobren zahtev za pregled");
			mail.setText("Postovani,\n\nZahtev za pregled:\n\nDatum i vreme: "+ p.getDatumVreme()+
					"\nTrajanje: "+p.getTrajanje() +
					"\nLekar: " + p.getLekar().getIme() + " " + p.getLekar().getPrezime()+
					"\nPacijent: " + p.getPacijent().getIme() + " " + p.getPacijent().getPrezime());
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}

	public void posaljiPacijentuOdobrenaOperacija(Operacija operacija) {
		System.out.println("Slanje emaila...");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(operacija.getPacijent().getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Odobren zahtev za pregled");
			mail.setText("Postovani,\n\nZahtev za pregled:\n\nDatum i vreme: "+ operacija.getDatumVreme()+
					"\nTrajanje: "+operacija.getTrajanje() +
					"\nLekar: " + (new ArrayList<Lekar>(operacija.getLekar()).get(0).getIme()) + " " + 
					(new ArrayList<Lekar>(operacija.getLekar()).get(0).getPrezime())+
					"\nPacijent: " + operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime());
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}

	public void posaljiLekaruOdobrenaOperacija(Operacija operacija) {
		System.out.println("Slanje emaila...");
		try {
			for(Lekar l : operacija.getLekar()) {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setTo(l.getEmail());
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Odobren zahtev za operaciju");
				mail.setText("Postovani,\n\nZahtev za operaciju:\n\nDatum i vreme: "+ operacija.getDatumVreme()+
						"\nTrajanje: "+operacija.getTrajanje() +
						"\nLekar: " + (new ArrayList<Lekar>(operacija.getLekar()).get(0).getIme()) + " " +
						(new ArrayList<Lekar>(operacija.getLekar()).get(0).getPrezime())+
						"\nPacijent: " + operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime());
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
			}
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
		
	}

	public void posaljiOdobrenoOdsustvo(String email, Odsustvo o) {
		System.out.println("Slanje emaila...");
		try {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setTo(email);
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Odobren zahtev za "+o.getTip().toLowerCase());
				mail.setText("Postovani,\n\nZahtev za "+o.getTip().toLowerCase()+" je odobren:\n\nPocetak: "+ o.getPocetak()+
						"\nKraj: "+o.getKraj());
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}

	public void posaljiOdbijenoOdsustvo(String email, Odsustvo o, String obr) {
		System.out.println("Slanje emaila...");
		try {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setTo(email);
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Odbijen zahtev za "+o.getTip().toLowerCase());
				mail.setText("Postovani,\n\nZahtev za "+o.getTip().toLowerCase()+" je odbijen:\n\nObrazlozenje: "+obr+"\n\nPocetak: "+ o.getPocetak()+
						"\nKraj: "+o.getKraj());
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}
	


	
}
