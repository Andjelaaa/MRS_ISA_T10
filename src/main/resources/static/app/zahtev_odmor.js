Vue.component('odmoor', {

	data: function(){
		return{	
			korisnik:[],
			uloga: '',
			tipZahteva:'',
			opis:'',
			datPocetka:'',
			datKraja:'',
			greska:''
		}
	}, 
	
	template: `
	<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a v-if="uloga=='ROLE_LEKAR'" class="navbar-brand" href="#/lekar">Pocetna</a>
			<a v-if="uloga=='ROLE_MED_SESTRA'" class="navbar-brand" href="#/medsestra">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/pacijenti">Pacijenti</a>
		      </li>
		      
		      <li class="nav-item">
		        <a class="nav-link" href="#/odmor">Zahtev za godisnji odmor/odsustvo</a>
		      </li>
		        <li class="nav-item">
		        <a v-if="uloga=='ROLE_MED_SESTRA'" class="nav-link" href="#/overa">Overa recepata</a>
		      </li>
		      <li class="nav-item">
		        <a  v-if="uloga=='ROLE_MED_SESTRA'" class="nav-link" href="#/kalendarr">Radni kalendar</a>
		        <a  v-if="uloga=='ROLE_LEKAR'" class="nav-link" href="#/kalendarlekar">Radni kalendar</a>
		      </li>
		      <li class="nav-item" v-if="uloga=='ROLE_LEKAR'">
		        <a  class="nav-link" href="#/lekar/pregledi">Zakazani pregledi</a>
		       </li>
		      <li class="nav-item">
				<a  v-if="uloga=='ROLE_MED_SESTRA'" class="nav-link" href="#/medsestra">Profil: {{korisnik.ime}} {{korisnik.prezime}}</a>
		        <a v-if="uloga=='ROLE_LEKAR'" class="nav-link" href="#/profil">Profil: {{korisnik.ime}} {{korisnik.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		<div class="float-left" style="margin: 20px">
			<h3> Zahtev za godisnji odmor/odsustvo </h3>
		<table class="table">
			<tbody>
				<tr>			   
			   		<td>Zahtevam: </td>
			   		<td>
						<select class="form-control"  id="tipZahteva" v-model="tipZahteva">
							<option value="Odmor"> Odmor </option>
							<option value="Odsustvo"> Odsustvo </option>
						</select>
					</td>
			   </tr>
			 
			   <tr>
			   		<td>Pocev od: </td>
					<td><input id="datPocetka" type="datetime-local" v-model="datPocetka"></td>
			   </tr>
			    <tr>
			   		<td>Zakljucno sa: </td>
					<td><input id="datKraja" type="datetime-local" v-model="datKraja"></td>
			   </tr>
			   	   
			   
			    <tr v-if="tipZahteva=='Odsustvo'">
			   		<td>Radi</td>
			   		<td><input id="opis" type="text" v-model="opis"></td>
			   </tr>
			   
			    <tr>
			   
			   		<td></td>
			   		<td><button v-on:click="salji()" class="btn btn-light float-right"> Posalji zahtev </button></td>
			   		
			   </tr>
		   </tbody>
		</table>
		{{greska}}
		</div>
	</div>
	
	`, methods : {
		//:{email:'',lozinka:'',ime:'',prezime:'',prezime:'',adresa:'', grad:'',drzava:'',kontakt:''},
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		validacija:function(){
			if(!this.tipZahteva){
				this.greska="Tip zahteva je obavezan";
				return 1;
			}
				
			if(this.tipZahteva =="Odsustvo" && !this.opis){
				this.greska="Razlog odsustva je obavezan";
				return 1;
			}
			if(this.tipZahteva =="Odmor" ){
				this.opis="nema opisa";
			}
	
				
			if(!this.datPocetka || !this.datKraja){
				this.greska = "Sva polja su obavezna!!!";
				return 1;
			}
			return 0;
				
			
		},
		salji: function(){
			this.greska = '';
    
			if(this.validacija()==1)
				return;
			var zahtev ={ "tip": this.tipZahteva,"opis": this.opis, "pocetak": this.datPocetka, "kraj": this.datKraja};
			
			
			axios
			.post('api/zahteviodsustvo/'+this.korisnik.email, zahtev,{ headers: { Authorization: 'Bearer ' + this.token }})
			.then((response)=>{
				this.greska ='';
				console.log("usao");
				alert('Uspesno ste poslali zahtev!');
				if(this.uloga == "ROLE_MED_SESTRA")
					this.$router.push('/med_sestra_pocetna');
				else
					this.$router.push('/lekar');
			}).catch((response)=>{
				this.greska = "Uneti podaci nisu validni!";
			});
			
		}
		
	},
	mounted(){
		
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
	    .then(response => { this.korisnik = response.data;
		    axios
			.put('/auth/dobaviulogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_MED_SESTRA" && this.uloga != "ROLE_LEKAR") {
		    		this.$router.push('/');
		    	}
		    })
		    .catch((response)=>{ alert("NIJE OK");});
		    
	    })
	    .catch((response) =>{ this.$router.push('/'); });	 
	}
	
});