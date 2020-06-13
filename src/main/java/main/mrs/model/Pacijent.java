package main.mrs.model;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity

public class Pacijent extends Korisnik implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="enabled")
	private boolean aktivan;
	
	@Column(name="lbo", unique=true, nullable=false)
	private String lbo;
	
	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	public ZKarton zKarton;
	
	@OneToMany(mappedBy="pacijent",fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	public Set<Pregled> pregled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autoritet> autoriteti;

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public ZKarton getzKarton() {
		return zKarton;
	}

	public void setzKarton(ZKarton zKarton) {
		this.zKarton = zKarton;
	}

	public String getLbo() {
		return lbo;
	}

	public void setLbo(String lbo) {
		this.lbo = lbo;
	}

	public Set<Pregled> getPregled() {
		if (pregled == null)
			pregled = new java.util.HashSet<Pregled>();
		return pregled;
	}

	public void setPregled(Set<Pregled> newPregled) {
		this.pregled = newPregled;
	}

	public void addPregled(Pregled newPregled) {
		if (newPregled == null)
			return;
		if (this.pregled == null)
			this.pregled = new java.util.HashSet<Pregled>();
		if (!this.pregled.contains(newPregled))
			this.pregled.add(newPregled);
	}

	public void removePregled(Pregled oldPregled) {
		if (oldPregled == null)
			return;
		if (this.pregled != null)
			if (this.pregled.contains(oldPregled))
				this.pregled.remove(oldPregled);
	}

	public void removeAllPregled() {
		if (pregled != null)
			pregled.clear();
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