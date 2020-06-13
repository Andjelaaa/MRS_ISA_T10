package main.mrs.model;
import java.util.Collection;
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
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Lekar ima recnik pregleda, kljuc je datum, vrednost pregled
 * 
 *  */
@Entity
@Table(name="Lekar")
public class Lekar extends Korisnik implements UserDetails {
	
   @Column(name="prosecnaOcena", unique=false, nullable=true)
   private Double prosecnaOcena;
   
   @Column(name="brojOcena", unique=false, nullable=true)
   private int brojOcena;
   
   @Column(name="rvPocetak", unique=false, nullable=false)
   private String rvPocetak;
   
   @Column(name="rvKraj", unique=false, nullable=false)
   private String rvKraj;
   
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   public TipPregleda tipPregleda;

  // @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   //public Set<Operacija> operacija;
   
   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name="lekar_id", nullable=true)
   public Set<Odsustvo> odsustvo;
   @Column(name="izmenaRezervisanja", unique=false, nullable=true)
   private int izmenaRezervisanja;
   @Version
   private Long version;
  // @OneToMany(mappedBy="lekar",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 //  public Set<Pregled> pregled;
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   public Klinika klinika;
   
   @ManyToMany(fetch = FetchType.EAGER)
	private List<Autoritet> autoriteti;
   
  

public Klinika getKlinika() {
	return klinika;
}

public void setKlinika(Klinika klinika) {
	this.klinika = klinika;
}

  
   public Set<Odsustvo> getOdsustvo() {
      if (odsustvo == null)
         odsustvo = new java.util.HashSet<Odsustvo>();
      return odsustvo;
   }
   
   public void setOdsustvo(Set<Odsustvo> newOdsustvo) {
      this.odsustvo = newOdsustvo;
   }
   
   public void addOdsustvo(Odsustvo newOdsustvo) {
      if (newOdsustvo == null)
         return;
      if (this.odsustvo == null)
         this.odsustvo = new java.util.HashSet<Odsustvo>();
      if (!this.odsustvo.contains(newOdsustvo))
         this.odsustvo.add(newOdsustvo);
   }
   
   public void removeOdsustvo(Odsustvo oldOdsustvo) {
      if (oldOdsustvo == null)
         return;
      if (this.odsustvo != null)
         if (this.odsustvo.contains(oldOdsustvo))
            this.odsustvo.remove(oldOdsustvo);
   }
   
   public void removeAllOdsustvo() {
      if (odsustvo != null)
         odsustvo.clear();
   }

public Double getProsecnaOcena() {
	return prosecnaOcena;
}

public void setProsecnaOcena(Double prosecnaOcena) {
	this.prosecnaOcena = prosecnaOcena;
}

public int getBrojOcena() {
	return brojOcena;
}

public void setBrojOcena(int brojOcena) {
	this.brojOcena = brojOcena;
}

public String getRvPocetak() {
	return rvPocetak;
}

public void setRvPocetak(String rvPocetak) {
	this.rvPocetak = rvPocetak;
}

public String getRvKraj() {
	return rvKraj;
}

public void setRvKraj(String rvKraj) {
	this.rvKraj = rvKraj;
}

public TipPregleda getTipPregleda() {
	return tipPregleda;
}

public void setTipPregleda(TipPregleda tipPregleda) {
	this.tipPregleda = tipPregleda;
}

//public Set<Pregled> getPregled() {
//	return pregled;
//}
//
//public void setPregled(Set<Pregled> pregled) {
//	this.pregled = pregled;
//}
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

public int getIzmenaRezervisanja() {
	return izmenaRezervisanja;
}

public void setIzmenaRezervisanja(int izmenaRezervisanja) {
	this.izmenaRezervisanja = izmenaRezervisanja;
}

public Long getVersion() {
	return version;
}

public void setVersion(Long version) {
	this.version = version;
}

}