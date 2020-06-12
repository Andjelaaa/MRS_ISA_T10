Vue.component('klinika-detalji', {
	data: function(){
		return{
			pretraga: {ime: '', prezime: '', ocena: 0, lekariTermini: []},
			klinika: {naziv: '', adresa: '', prosecnaOcena: 0, kontaktKlinike: '000/000'},
			idPacijenta: 1,
			tipPregleda: {naziv: null},
			datum: '',
			tipoviPregleda: null,
			greskaDatum: '',
			greskaTipPregleda: '',
			lekariTermini: null,
			sviSlobodni: null,
			showModal: false,
			izabranoVreme: '',
			izabraniLekar: null,
			slobodniTermini: [],
			sveJeOk: false,
			currentSort:'prosecnaOcena',
			  currentSortDir:'asc',
			zahtevPregled: {lekarEmail: '', tipPregledaNaziv: '', datum: '', vreme: '', klinikaId: '', pacijentId: ''}
		}
	},

	template: `
	<div>
	
	<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/pacijent">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/klinike">Klinike</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/pacijentpregledi">Pregledi/Operacije</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/">Zdravstveni karton</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/">Profil</a>
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
		
		
		<div class="float-left" style="margin:20px">
		<td><button v-on:click = "brzoZakazivanje()" class="btn btn-success">Brzo zakazivanje</button></td>
		<br>
		<h3>Detalji klinike</h3>
		<br>

		<table  class="table table-hover table-light ">
			<tr>
			<th>Naziv klinike</th>
			<td>{{klinika.naziv}}</td>
			</tr>
			<tr>
			<th>Adersa</th>
			<td>{{klinika.adresa}}</td>
			</tr>
			<tr>
			<th>Prosecna ocena</th>
			<td>{{klinika.prosecnaOcena}}</td>
			</tr>
			<tr>
			<th>Broj ocenjivanja</th>
			<td>{{klinika.brojOcena}}</td>
			</tr>
			<tr>
			<th>Kontakt</th>
			<td>{{klinika.kontaktKlinike}}</td>
			</tr>
			<th>Email</th>
			<td>{{klinika.emailKlinike}}</td>
			
		</table>
		</div>
		<div class="float-right" style="width:45%">
		
		
			<br>
			<h3>Slobodni lekari</h3>
			<br>
			
			<table class="table table-hover table-light">
			  <tr>		   		
		   		<th>Ime i prezime</th>
		   		<th>Email adresa</th>
		   		<th>Kontakt</th>
		   		<th @click="sort('prosecnaOcena')" class="class1">Prosecna ocena</th>
		   		<th></th>
		   </tr>
		  <tbody>
		   <tr v-for="s in lekariTermini">
		   		<td>{{s.lekar.ime}} {{s.lekar.prezime}}</td>
		   		<td>{{s.lekar.email}}</td>
		   		<td>{{s.lekar.kontakt}}</td>
		   		<td>{{s.lekar.prosecnaOcena}}</td>		   		
				<td>
					<button class="btn btn-light" id="show-modal" @click="showModal = true" v-on:click="izaberiTermin(s)">Izaberi Termin</button>
						<modal v-if="showModal" @close="showModal = false">
	    
	    					<h3 slot="header">Izbor termina</h3>
	    					<table slot="body" class="table table-hover table-light">
								<tbody>
									<tr><td>Lekar {{izabraniLekar.ime}} {{izabraniLekar.prezime}}</td>
									<td>slobodni intervali</td>
									<tr v-for="s in slobodniTermini">
										<td></td>
										<td>{{s.pocetak | formatTime}} - {{s.kraj | formatTime}}</td>
									</tr>
									<tr>
										<td>Unesi vreme</td>
										<td><input  class="form-control" type="time" v-model = "izabranoVreme"/></td>
									</tr>									
								</tbody>
								</table>
								
								<div slot="body">
								<p>Obratite paznju da pregled traje 30 min.</p>
								</div>
	    					<div slot="footer">
	    						
	    						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="zakazi(s)"> Posalji upit </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false"> Odustani </button>								
							</div>
						</modal>
				</td>
		   </tr>
		   </tbody>
		</table>
		<br>
		<h3>Pretraga</h3>
		<table class="table table-hover table-light">
			<tr><td>Ime:</td><td><input  type="text" v-model="pretraga.ime" ></td></tr>
			<tr><td>Prezime:</td><td><input  type="text" v-model="pretraga.prezime"></td></tr>
			<tr><td>Ocena:</td><td><input type="number" v-model="pretraga.ocena"></td></tr>
			<tr><td></td><td><button v-on:click = "pretrazi()" class="btn btn-light">Pretrazi</button></td></tr>
		</table>
		</div>
		</div>
	`, 
	
	methods : {
		brzoZakazivanje: function(klinikaId)
		{
			this.$router.push('/predefinisanipregledi/'+ this.$route.params.name)
		},
		
		izaberiTermin: function(s)
		{
			// s je objekat PomocnaKlasa5 ima u sebi Lekara i listu vremena kada je on slobodan
			// korisniku prikazati vreme kada je slobodan 
			this.izabraniLekar = s.lekar;
			this.slobodniTermini = s.slobodnoVreme;
			
		},
		
		zakazi: function(s)
		{
			// s je PomcnaKlasa5
			if(this.izabranoVreme)
			{
				axios
		       	.post('api/klinika/proveriTermin/'+ this.izabranoVreme, s)
		       	.then(res => {
		       		// ne znam kako drugacije ovaj prozor 
		       		var r = confirm("Potvrdi termin: " + this.$route.params.date + " vreme: " + this.izabranoVreme);
		       		if (r == true) {
		       			console.log("Potvrdjen termin treba poslati zahtev")
		       			this.zahtevPregled.lekarEmail = this.izabraniLekar.email;
		       			this.zahtevPregled.tipPregledaNaziv = this.$route.params.tip;
		       			this.zahtevPregled.datum = this.$route.params.date;
		       			this.zahtevPregled.vreme = this.izabranoVreme;
		       			this.zahtevPregled.klinikaId = this.$route.params.name;
		       			this.zahtevPregled.pacijentId = this.idPacijenta;
		       			this.sveJeOk = true;
		       			axios
		       			.post('api/pregled/pacijentzahtev', this.zahtevPregled)
		       			.then(res=>{
		       				
		       			})
		       			.catch((res)=>{
		       				
		       			})
		       		}
				})
		       	.catch((res)=>{
		       		alert("Izabrano vreme se ne uklapa u slobodne vremenske intervale!");
		       	})
			}
		},
		sort:function(s) {
		    //if s == current sort, reverse
		    if(s === this.currentSort) {
		      this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
		    }
		    this.currentSort = s;
		  },
		  
		  pretrazi: function(){
			  this.pretraga.lekariTermini = this.sviSlobodni;
				axios
		       	.post('api/lekar/slobodniLekari/search', this.pretraga)
		       	.then(response => (this.lekariTermini = response.data));

			}
		
		
	},
	
	computed:{
		  sortedLekari:function() {
		    return this.lekariTermini.sort((a,b) => {
		      let modifier = 1;
		      if(this.currentSortDir === 'desc') modifier = -1;
		      if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
		      if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
		      return 0;
		    });
		  }
		},
	
	mounted () {
		axios
		.get('api/klinika/detalji/'+ this.$route.params.name)
		.then(res => {
			this.klinika = res.data;
		})
		
		axios
		.get('api/klinika/slobodnitermini/lekari/'+ this.$route.params.date + '/' + this.$route.params.tip)
       	.then(response => {
       		this.lekariTermini = response.data;
       		this.sviSlobodni = this.lekariTermini;
       	})
       	.catch((res)=>{
        	  console.log('neuspesno');
       	})
		
	},

});