package main.mrs.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="MedicinskaSestra")
public class MedSestra extends Korisnik implements UserDetails {
	
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	public Klinika klinika;
   
   @Column(name="radvr_pocetak", unique=false, nullable=false)
   private String radvr_pocetak;
   
   @Column(name="radvr_kraj", unique=false, nullable=false)
   private String radvr_kraj;
   
   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name="med_sestra_id", nullable=true)
   public Set<Odsustvo> odsustvo;
   
	public Set<Odsustvo> getOdsustvo() {
	return odsustvo;
	}
	
	
	public void setOdsustvo(Set<Odsustvo> odsustvo) {
		this.odsustvo = odsustvo;
	}


	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autoritet> autoriteti;
   
 
	public Klinika getKlinika() {
		return klinika;
	}
	
	
	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	public List<Autoritet> getAutoriteti() {
	return autoriteti;
	}
	
	public void setAutoriteti(List<Autoritet> autoriteti) {
		this.autoriteti = autoriteti;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.autoriteti;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getLozinka();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	public String getRadvr_pocetak() {
		return radvr_pocetak;
	}


	public void setRadvr_pocetak(String radvr_pocetak) {
		this.radvr_pocetak = radvr_pocetak;
	}


	public String getRadvr_kraj() {
		return radvr_kraj;
	}


	public void setRadvr_kraj(String radvr_kraj) {
		this.radvr_kraj = radvr_kraj;
	}


}