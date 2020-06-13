package main.mrs.model;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class AdminKC extends Korisnik implements UserDetails{
	
	
//	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
//	@JoinColumn(name="adminKC_id", nullable=true)
//	public Set<ZahtevReg> zahtevReg;
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    public KlinickiCentar klinickiCentar;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autoritet> autoriteti;


	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}
	
	
	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
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