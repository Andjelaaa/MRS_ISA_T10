Vue.component('lekari', {
	data: function(){
		return{
			admin: {},
			uloga: '',
			lekari: null,
			pretraga: {ime:'', prezime:''},
			
			lekar: {tipPregleda: null},
			tipPregleda: {},
			tipoviPregleda: null,
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
			specijalizacijaGreska: '',
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
		      <li class="nav-item active">
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
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
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
		   		<th>Specijalizacija</th>
		   		<th>Adresa</th>
		   		<th>Prosecna ocena</th>
		   		<th>Radno vreme</th>
		   		<th></th>
		   </tr>
		  <tbody>
		   <tr v-for="s, i in lekari">
		   		<td>{{s.ime}} {{s.prezime}}</td>
		   		<td>{{s.email}}</td>
		   		<td>{{s.kontakt}}</td>
		   		<td>{{s.tipPregleda.naziv}}</td>
		   		<td>{{s.adresa}}, {{s.grad}}</td>
		   		<td>{{s.prosecnaOcena}}</td>		   		
		   		<td>{{s.rvPocetak}} - {{s.rvKraj}}</td>
				<td><button class="btn btn-light" v-on:click="obrisi(s, i)">Obrisi</button></td>
		   </tr>
		   </tbody>
		    
		</table>
		
		</div>
		<br>
		<div class="float-right" style="width:35%">
		<h3> Registracija lekara </h3>
		<p>{{error}}</p>
		<table >
			<tbody>
				<tr>			   
			   		<td>Email: </td>
			   		<td><input class="form-control" id="email" type="text" v-model="lekar.email"></td>
			   		<td style="color: red">{{emailGreska}}</td>
			   </tr>
			   <tr>
			   		<td>Lozinka: </td>
			   		<td><input class="form-control" id="lozinka" type="text" v-model="lekar.lozinka"></td>
			   		<td style="color: red">{{lozinkaGreska}}</td>
			   </tr>
			   <tr>
			   
			   		<td>Ime: </td>
			   		<td><input class="form-control" id="ime" type="text" v-model="lekar.ime"></td>
			   		<td style="color: red">{{imeGreska}}</td>	
			   </tr>
			   <tr>
			   		<td>Prezime: </td>
			   		<td><input class="form-control" id="prezime" type="text" v-model="lekar.prezime"></td>
			   		<td style="color: red">{{prezimeGreska}}</td>
			   </tr>	
			   <tr>			   
			   		<td>Adresa: </td>
			   		<td><input class="form-control" id="adresa" type="text" v-model="lekar.adresa"></td>
			   		<td style="color: red">{{adresaGreska}}</td>
			   </tr>
			   <tr>
			   		<td>Grad: </td>
			   		<td><input class="form-control"  id="grad" type="text" v-model="lekar.grad"></td>
			   		<td style="color: red">{{gradGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Drzava: </td>
			   		<td><input  class="form-control" id="drzava" type="text" v-model="lekar.drzava"></td>
			   		<td style="color: red">{{drzavaGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Kontakt telefon: </td>
			   		<td><input class="form-control" id="kontakt" type="text" v-model="lekar.kontakt"></td>
			   		<td style="color: red">{{kontaktGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Pocetak radnog vremena (format HH:MM): </td>
			   		<td><input class="form-control" id="rvPocetak" type="text" v-model="lekar.rvPocetak"></td>
			   		<td style="color: red">{{pocGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Kraj radnog vremena (format HH:MM): </td>
			   		<td><input class="form-control" id="rvKraj" type="text" v-model="lekar.rvKraj"></td>
			   		<td style="color: red">{{krajGreska}}</td>
			   </tr>
			   <tr>
			   		<td>Specijalizacija: </td>
			   		<td>
						<select class="form-control"  id="selectTP" v-model="tipPregleda.naziv">
							<option v-for="t in tipoviPregleda" :value="t.naziv">{{t.naziv}}</option>
						</select>
					</td>
			   		<td style="color: red">{{specijalizacijaGreska}}</td>
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
	       	.post('api/lekar/search/'+this.admin.id, this.pretraga, { headers: { Authorization: 'Bearer ' + this.token }})
	       	.then(response => (this.lekari = response.data));

		},
		obrisi: function(s, i){
			console.log(s.id);
			axios
			.delete('api/lekar/'+s.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				this.lekari.splice(i,1);
//				console.log(this.token+ '  skfjfkfj');
//				axios
//    	       	.get('api/lekar/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
//    	       	.then(response => (this.lekari = response.data));
			}).catch((res)=>{
				console.log('Neuspesno brisanje');
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
			this.specijalizacijaGreska = '';
			this.pocGreska = '';
			this.krajGreska = '';
			this.kontaktGreska = '';
			
			if(!this.lekar.email)
				this.emailGreska = 'Email je obavezno polje!';
			if(!this.lekar.kontakt)
				this.kontaktGreska = 'Kontakt je obavezno polje!';
			if(!this.lekar.ime)
				this.imeGreska = 'Ime je obavezno polje!';
			if(!this.lekar.prezime)
				this.prezimeGreska = 'Prezime je obavezno polje!';
			if(!this.lekar.lozinka)
				this.lozinkaGreska = 'Lozinka je obavezno polje!';
			if(!this.lekar.adresa)
				this.adresaGreska = 'Adresa je obavezno polje!';
			if(!this.lekar.grad)
				this.gradGreska = 'Grad je obavezno polje!';
			if(!this.lekar.drzava)
				this.drzavaGreska = 'Drzava je obavezno polje!';
			if(!this.lekar.rvPocetak)
				this.pocGreska = 'Ovo je obavezno polje!';
			if(!this.lekar.rvKraj)
				this.krajGreska = 'Ovo je obavezno polje!';
			if(!this.lekar.tipPregleda)
				this.specijalizacijaGreska = 'Specijalizacija je obavezno polje!';

			if(this.lekar.email && this.lekar.ime && this.lekar.prezime && this.lekar.lozinka && this.lekar.adresa && this.lekar.grad && this.lekar.drzava && this.lekar.tipPregleda
					&& this.lekar.rvPocetak && this.lekar.rvKraj && this.lekar.kontakt){
				return 0;
			}
			return 1;
			
		},
		dodaj : function(){	
			this.error = '';
			this.lekar.tipPregleda = this.tipPregleda;
			if(this.validacija()==1)
				return;
			
			axios
			.post('api/lekar/'+this.admin.id, this.lekar, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				console.log('ajskf  '+this.token)
				axios
		       	.get('api/lekar/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		       	.then(response => (this.lekari = response.data));
				this.lekar = {};
				 
				
			}).catch((res)=>{
				this.error = 'Vec postoji korisnik sa istim email-om';
				this.lekar = {};
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
		    	       	.get('api/lekar/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		    	       	.then(response => (this.lekari = response.data));
		    			 
		    			 axios
		    	         .get('api/tippregleda/all', { headers: { Authorization: 'Bearer ' + this.token }})
		    	         .then(res => {
		    	       	  this.tipoviPregleda = res.data;

		    	         })		    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
		
	}

});