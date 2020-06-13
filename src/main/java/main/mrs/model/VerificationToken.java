package main.mrs.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "verification_token")
public class VerificationToken {
	private static final int expirationTime =24*60;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="token")
	private String token;
	
	
	@OneToOne(targetEntity = Pacijent.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "pacijent_id", nullable = false)
	private Pacijent pacijent;

	@Column(name="datum_kreiranja")
	private Date datumKreiranja;
	
	@Column(name="datum_unistavanja")
	private Date datumUnistavanja;
	
	public VerificationToken() {
		super();
	}

	public VerificationToken(String token, Pacijent pacijent) {
		super();
		Calendar calendar = Calendar.getInstance();
		
		this.token = token;
		this.pacijent = pacijent;
		this.datumKreiranja = new Date(calendar.getTime().getTime());
		this.datumUnistavanja = setEndDate(); 
	}

	private Date setEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Date getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(Date datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public Date getDatumUnistavanja() {
		return datumUnistavanja;
	}

	public void setDatumUnistavanja(Date datumUnistavanja) {
		this.datumUnistavanja = datumUnistavanja;
	}
	
}
