Vue.component('dodajadmina', {
	data: function(){
		return{
			adresa: '',
			drzava: '',
			email: '',
			grad: '',
			ime:'',
			prezime: '',
			lozinka:'',
			lozinka1:'',
			kontakt:'',
			klinika:'',
			greska0: '',
			greska1: '',
			greska2: '',
			greska3: '',
			greska4: '',
			greska5: '',
			greska6: '',
			greska7: '',
			dbError: '',
			uloga:'',
			token:''
			
		   
		}
	}, 
	template: `
	<div>
	
	<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/sprofil">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/regklinika">Registruj kliniku</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/odobri_zahtev">Zahtevi za registraciju</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/sifrarnik1">Sifrarnik lekova</a>
		      </li>
		       <li class="nav-item">
		        <a class="nav-link" href="#/sifrarnik2">Sifrarnik dijagnoza</a>
		      </li>
		       <li class="nav-item">
		        <a class="nav-link" href="#/">Odjavi se</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		    </form>
		  </div>
		</nav>
		</br>
		<h3> Registracija admina klinike: </h3>
		{{dbError}}
		<table>
			<tr>
		   		<td>Email: </td>
		   		<td><input class="form-control" id="email" type="text" v-model="email"></td>
		   		<td style="color: red">{{greska0}}</td>

		   </tr>
		   <tr>
		   		<td>Ime: </td>
		   		<td><input class="form-control" id="ime" type="text" v-model="ime"></td>
		   		<td style="color: red">{{greska1}}</td>

		   </tr>
		   <tr>
		   		
		   		<td>Prezime: </td>
		   		<td><input class="form-control" id="prezime" type="text" v-model="prezime"></td>
		   		<td style="color: red">{{greska2}}</td>
		   		
		   </tr>
		   <tr>
		   
		   		<td>Adresa prebivalista: </td>
		   		<td><input class="form-control" id="adresa" type="text" v-model="adresa"></td>
		   		<td style="color: red">{{greska3}}</td>
		   		
		   </tr>
		   <tr>
		   	
		   		<td>Grad: </td>
		   		<td><input class="form-control" id="grad" type="text" v-model="grad"></td>
		   		<td style="color: red">{{greska4}}</td>
		  
		   </tr>
		   <tr>
		   
		   		<td>Drzava: </td>
		   		<td><input class="form-control" id="drzava" type="text" v-model="drzava"></td>
		   		<td style="color: red">{{greska5}}</td>
		   
		   </tr>
		    <tr>
		   
		   		<td>Broj telefona: </td>
		   		<td><input class="form-control" id="kontakt" type="text" v-model="kontakt"></td>
		   		<td style="color: red">{{greska6}}</td>
		   
		   </tr>
		    <tr>
		   
		   		<td>Lozinka: </td>
		   		<td><input  class="form-control" id="lozinka" type="password" v-model="lozinka"></td>
		   		<td style="color: red"></td>
		   
		   </tr>
		    <tr>
		   
		   		<td>Ponovi lozinku: </td>
		   		<td><input class="form-control" id="lozinka1" type="password" v-model="lozinka1"></td>
		   		<td style="color: red">{{greska7}}</td>
		   
		   </tr>
		    <tr>
		   
		   		<td><button v-on:click = "nazad()" class="btn btn-light">Nazad</button></td>
		   		<td><button v-on:click = "regAdmina()" class="btn btn-success">Registruj admina</button></td>	   
		   </tr>
		   
		</table>
		
		</div>
	
	`, 

	methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		nazad : function(){
			this.$router.push('/sprofil');
			return;
		},
		validacija : function(){
			this.greska0= '';
			this.greska1= '';
			this.greska2 ='';
			this.greska3= '';
			this.greska4= '';
			this.greska5= '';
			this.greska6= '';
			this.greska7='';
			this.dbError= '';
			
			
			if(this.lozinka !=this.lozinka1)
				this.greska7 = 'Lozinke moraju biti iste!';
			if(!this.lozinka && this.lozinka1)
				this.greska7 = 'Lozinke su obavezne!';
			if(!this.email)
				this.greska0 = 'Email je obavezno polje!';
			if(!this.ime)
				this.greska1 = 'Ime je obavezno polje!';
			if(!this.prezime)
				this.greska2 = 'Prezime je obavezno polje!';
			if(!this.adresa)
				this.greska3 = 'Adresa je obavezno polje!';
			if(!this.grad)
				this.greska4 = 'Grad je obavezno polje!';
			if(!this.drzava)
				this.greska5 = 'Drzava je obavezno polje!';
			if(!this.kontakt)
				this.greska6 = 'Broj je obavezno polje!';
						
			if(this.lozinka && this.lozinka1 && this.email && this.ime && this.prezime &&this.adresa
					&& this.grad&& this.drzava &&this.kontakt){
				return 0;
			}
			return 1;			
		},
		regAdmina: function(){
			this.dbError = '';

			if(this.validacija()==1)
				return;
			var newAdmin ={"ime": this.ime, "email": this.email,
					"prezime": this.prezime,"grad": this.grad,
					"drzava": this.drzava,"kontakt": this.kontakt,
					"lozinka": this.lozinka,"adresa": this.adresa};
			console.log(this.$route.params.id+" dasdasd");
			axios
			.post('api/admini/' + this.$route.params.id, newAdmin, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((response)=>{
				 this.greska0= '';
				 this.greska1= '';
				 this.greska2 ='';
				 this.greska3= '';
				 this.greska4= '';
				 this.greska5= '';
				 this.greska6= '';
				 this.greska7='';
				 this.adresa='';
				 this.drzava= '';
				 this.email= '';
				 this.grad= '';
				 this.ime='';
				 this.prezime= '';
				 this.lozinka='';
				 this.lozinka1='';
				 this.kontakt='';
				 this.dbError= '';
				 alert("Uspesno dodat novi admin");
			}).catch((response)=>{
				 this.greska0= '';
				 this.greska1= '';
				 this.greska2 ='';
				 this.greska3= '';
				 this.greska4= '';
				 this.greska5= '';
				 this.greska6= '';
				 this.greska7='';
				 this.dbError = 'Korisnik vec postoji';
			});
		}		
	},
	mounted(){
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
	    .then(response => { this.medicinska_sestra = response.data;
		    axios
			.put('/auth/dobaviulogu', this.medicinska_sestra, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_ADMIN_KLINICKOG_CENTRA") {
		    		router.push('/');
				}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });	
		
	}

});