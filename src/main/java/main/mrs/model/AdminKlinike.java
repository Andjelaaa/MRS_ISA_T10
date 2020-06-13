package main.mrs.model;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class AdminKlinike extends Korisnik implements UserDetails{
   
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="admin_klinike_id", nullable=true)
    public Set<Odsustvo> odsustvo;
	
	@ManyToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    public Klinika klinika;
   
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autoritet> autoriteti;

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

   public Set<Odsustvo> getOdsustvo() {
      if (odsustvo == null)
         odsustvo = new HashSet<Odsustvo>();
      return odsustvo;
   }
   
   public void setOdsustvo(Set<Odsustvo> newOdsustvo) {
      this.odsustvo = newOdsustvo;
   }
   
   public void addOdsustvo(Odsustvo newOdsustvo) {
      if (newOdsustvo == null)
         return;
      if (this.odsustvo == null)
         this.odsustvo = new HashSet<Odsustvo>();
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
   public Klinika getKlinika() {
      return klinika;
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
   
   
}