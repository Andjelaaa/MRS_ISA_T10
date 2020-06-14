Vue.component('medsestre', {
	data: function(){
		return{
			admin: {},
			uloga: '',
			medsestre: null,
			pretraga: {ime:'', prezime:''},
			
			sestra: {},
			imeGreska: '',
			prezimeGreska: '',
			emailGreska: '',
			lozinkaGreska: '',
			adresaGreska: '',
			gradGreska: '',
			drzavaGreska: '',
			kontaktGreska: '',
			pocGreska: '',
			krajGreska: '',
			error: ''
		}
	}, 
	
	template: `
		<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/admin">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/lekari">Lekari</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/medsestre">Medicinske sestre</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/sale">Sale</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/tipovipregleda">Tipovi pregleda</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/dpregled">Novi termin za pregled</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/izvestaji">Izvestaji</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/zahtevipo">Zahtevi za pregled/operaciju</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/zahtevioo">Zahtevi za odmor/odsustvo</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/profiladmin">Profil: {{admin.ime}} {{admin.prezime}}</a>
		      </li>
		      
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <!--input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"-->
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		

		<div class="float-left">		
		
		Ime: <input  type="text" v-model="pretraga.ime" >
		Prezime: <input  type="text" v-model="pretraga.prezime">
		<button v-on:click = "pretrazi()" class="btn btn-light">Pretrazi</button>
		<table class="table table-hover table-light ">
		
		   <tr>		   		
		   		<th>Ime i prezime</th>
		   		<th>Email adresa</th>
		   		<th>Kontakt</th>
		   		<th>Adresa</th>
		   		<th>Radno vreme</th>
		   		<th></th>
		   </tr>
		  <tbody>
		   <tr v-for="s in medsestre">
		   		<td>{{s.ime}} {{s.prezime}}</td>
		   		<td>{{s.email}}</td>
		   		<td>{{s.kontakt}}</td>
		   		<td>{{s.adresa}}, {{s.grad}}</td>
		   		<td>{{s.radvr_pocetak}} - {{s.radvr_kraj}}</td>
				<td><button class="btn btn-light" v-on:click="obrisi(s)">Obrisi</button></td>
		   </tr>
		   </tbody>
		    
		</table>
		
		</div>
		<br>
		<div class="float-right" style="width:35%">
		<h3> Registracija medicinske sestre </h3>
		<p>{{error}}</p>
		<table >
			<tbody>
				<tr>			   
			   		<td>Email: </td>
			   		<td><input class="form-control" id="email" type="text" v-model="sestra.email"></td>
			   		<td style="color: red">{{emailGreska}}</td>
			   </tr>
			   <tr>
			   		<td>Lozinka: </td>
			   		<td><input class="form-control" id="lozinka" type="text" v-model="sestra.lozinka"></td>
			   		<td style="color: red">{{lozinkaGreska}}</td>
			   </tr>
			   <tr>
			   
			   		<td>Ime: </td>
			   		<td><input class="form-control" id="ime" type="text" v-model="sestra.ime"></td>
			   		<td style="color: red">{{imeGreska}}</td>	
			   </tr>
			   <tr>
			   		<td>Prezime: </td>
			   		<td><input class="form-control" id="prezime" type="text" v-model="sestra.prezime"></td>
			   		<td style="color: red">{{prezimeGreska}}</td>
			   </tr>	
			   <tr>			   
			   		<td>Adresa: </td>
			   		<td><input class="form-control" id="adresa" type="text" v-model="sestra.adresa"></td>
			   		<td style="color: red">{{adresaGreska}}</td>
			   </tr>
			   <tr>
			   		<td>Grad: </td>
			   		<td><input class="form-control"  id="grad" type="text" v-model="sestra.grad"></td>
			   		<td style="color: red">{{gradGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Drzava: </td>
			   		<td><input  class="form-control" id="drzava" type="text" v-model="sestra.drzava"></td>
			   		<td style="color: red">{{drzavaGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Kontakt telefon: </td>
			   		<td><input class="form-control" id="kontakt" type="text" v-model="sestra.kontakt"></td>
			   		<td style="color: red">{{kontaktGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Pocetak radnog vremena (format HH:MM): </td>
			   		<td><input class="form-control" id="rvPocetak" type="text" v-model="sestra.radvr_pocetak"></td>
			   		<td style="color: red">{{pocGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Kraj radnog vremena (format HH:MM): </td>
			   		<td><input class="form-control" id="rvKraj" type="text" v-model="sestra.radvr_kraj"></td>
			   		<td style="color: red">{{krajGreska}}</td>
			   </tr>

			   
			    <tr>
			   
			   		<td></td>
			   		<td><button v-on:click="dodaj()" class="btn btn-success float-right">Dodaj</button></td>
			   </tr>
		   </tbody>
		</table>
	
		</div>
		

		</div>
	
	`, 
	methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},

		pretrazi: function(){
			axios
	       	.post('api/medsestraa/search/'+this.admin.id, this.pretraga, { headers: { Authorization: 'Bearer ' + this.token }})
	       	.then(response => (this.medsestre = response.data));

		},
		obrisi: function(s){
			console.log(s.id);
			axios
			.delete('api/medsestraa/'+s.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				alert("Uspesno brisanje");
				 axios
			       	.get('api/medsestraa/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
			       	.then(response => (this.medsestre = response.data));
			}).catch((res)=>{
				console.log('Neuspesno brisanje');
				alert("Neuspesno brisanje");
			});
			
		},
		validacija: function(){
			this.imeGreska = '';
			this.prezimeGreska = '';
			this.emailGreska = '';
			this.lozinkaGreska = '';
			this.adresaGreska = '';
			this.gradGreska = '';
			this.drzavaGreska = '';
			this.pocGreska = '';
			this.krajGreska = '';
			this.kontaktGreska = '';
			
			if(!this.sestra.email)
				this.emailGreska = 'Email je obavezno polje!';
			if(!this.sestra.kontakt)
				this.kontaktGreska = 'Kontakt je obavezno polje!';
			if(!this.sestra.ime)
				this.imeGreska = 'Ime je obavezno polje!';
			if(!this.sestra.prezime)
				this.prezimeGreska = 'Prezime je obavezno polje!';
			if(!this.sestra.lozinka)
				this.lozinkaGreska = 'Lozinka je obavezno polje!';
			if(!this.sestra.adresa)
				this.adresaGreska = 'Adresa je obavezno polje!';
			if(!this.sestra.grad)
				this.gradGreska = 'Grad je obavezno polje!';
			if(!this.sestra.drzava)
				this.drzavaGreska = 'Drzava je obavezno polje!';
			if(!this.sestra.radvr_pocetak)
				this.pocGreska = 'Ovo je obavezno polje!';
			if(!this.sestra.radvr_kraj)
				this.krajGreska = 'Ovo je obavezno polje!';
			
            if(/[0-9]{2,2}:[0-9]{2,2}/.test(this.sestra.radvr_pocetak) == false)
	        {
				this.pocGreska = 'Los format!';
                return 1;
				
			}
			
			if(/[0-9]{2,2}:[0-9]{2,2}/.test(this.sestra.radvr_kraj) == false)
			{
				this.krajGreska = 'Los format!';
                return 1;
				
			}
			if(this.sestra.email && this.sestra.ime && this.sestra.prezime && this.sestra.lozinka && this.sestra.adresa && this.sestra.grad && this.sestra.drzava
					&& this.sestra.radvr_pocetak && this.sestra.radvr_kraj && this.sestra.kontakt){
				return 0;
			}
			return 1;
			
		},
		dodaj : function(){	
			this.error = '';
			if(this.validacija()==1)
				return;
			
			axios
			.post('api/medsestraa/'+this.admin.id, this.sestra, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				axios
		       	.get('api/medsestraa/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		       	.then(response => (this.medsestre = response.data));
				this.sestra = {};
				 
				
			}).catch((res)=>{
				this.error = 'Vec postoji korisnik sa istim email-om';
				this.sestra = {};
			}
			
				
			)
		}
		
	},
	mounted(){
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.admin = response.data; 
	        axios
			.put('/auth/dobaviulogu', this.admin, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_ADMIN_KLINIKE") {
		    		this.$router.push('/');
		    	}else{
		    		 axios
		    	       	.get('api/medsestraa/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		    	       	.then(response => (this.medsestre = response.data));
		    			 	    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
		
	}

});