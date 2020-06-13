package main.mrs.verification_handler;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;
import main.mrs.model.Pacijent;



public class OnAccessLinkEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	private String putanja;
	private Locale lokalni;
	private Pacijent pacijent;
	
	
	
	public OnAccessLinkEvent(Pacijent pacijent, Locale lokalni, String putanja) {
		super(pacijent);
		this.pacijent = pacijent;
		this.lokalni = lokalni;
		this.putanja = putanja;
	}



	public String getPutanja() {
		return putanja;
	}



	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}



	public Locale getLokalni() {
		return lokalni;
	}



	public void setLokalni(Locale lokalni) {
		this.lokalni = lokalni;
	}



	public Pacijent getPacijent() {
		return pacijent;
	}



	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
}
