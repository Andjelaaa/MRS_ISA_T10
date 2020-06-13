Vue.component('klinike-prikaz', {
	data: function(){
		return{
			currentDate:'',
			n: null,
			y: 0,
			m: 0,
			d: 0,
			klinike: [],
			pacijent: {},
			uloga: {},
			pretraga: false,
			cena: 0.0,
			tipPregleda: {naziv: null},
			datum: null,
			tipoviPregleda: null,
			greskaDatum: '',
			greskaTipPregleda: '',
			currentSort:'prosecnaOcena',
			  currentSortDir:'asc'
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
		        <a class="nav-link" href="#/zdravstveniKarton">Zdravstveni karton</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/profilpacijenta">Profil: {{pacijent.ime}} {{pacijent.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		<p class="leva">Sve klinike</p>
		<p class="desna">Pretraga termina</p>
		<div class="float-left">
		<table class="table table-hover table-light">
			<tr>
			<th @click="sort('naziv')" class="class1">Naziv klinike</th>
			<th @click="sort('adresa')" class="class1">Adersa</th>
			<th @click="sort('prosecnaOcena')" class="class1">Prosecna ocena</th>
			<th>Kontakt</th>
			<th v-show='pretraga'>Cena</th>
			<th></th>
			</tr>
			
			<tr v-for="k, index in sortedKlinike">
				<td>{{k.naziv}}</td>
				<td>{{k.adresa}}</td>
				<td>{{k.prosecnaOcena}}</td>
				<td>{{k.kontaktKlinike}}</td>
				<td v-show='pretraga'>{{dobaviCenu()}}</td>
				<td><button v-on:click = "detalji(k.id)" class="btn btn-light" >Detalji</button></td>
			</tr>
		</table>
		</div>
		<div class="float-right" style="width:45%">
		<table class="table table-hover table-light">
			<tr>
				<td>Pretrazi preglede od: </td>
				<td><input class="form-control"  name="datum" id="datum" type="date" v-model="datum" ></td>
				<td>{{this.greskaDatum}}</td>
			</tr>
			
			<tr>
				<td>Pretrazi po tipu pregleda</td>
				<td>
					<select id="selectTP" class="form-control" v-model="tipPregleda.naziv">
						<option v-for="t in tipoviPregleda" :value="t.naziv">{{t.naziv}}</option>
					</select>
				</td>
				<td>{{this.greskaTipPregleda}}</td>
				
			</tr>
			<tr>
				<td></td>
				<td><button v-on:click = "pretragaTermina()" class="btn btn-light">Pretrazi</button></td>
			</tr>
		</table>
		</div>
		</div>
	`, 
	
	methods : {
		dobaviCenu()
		{
			// za klinikaId i this.tipPregleda.naziv nadji cenu
			if(!this.pretraga)
				return 0.0;
			axios
	       	.get('api/stavkacenovnika/cena/' + this.tipPregleda.naziv,  { headers: { Authorization: 'Bearer ' + this.token }} )
	       	.then(response => (this.cena = response.data))
	       	.catch((res)=>{
	        	  console.log('neuspesno');
	       	});
			return this.cena;
		},
		validacija: function(){
			this.greskaDatum = '';
			this.greskaTipPregleda = '';
			
			if(!this.datum){
				this.greskaDatum = 'Datum je obavezno polje!';
				return 1;
			}

			if(!this.tipPregleda.naziv){
				this.greskaTipPregleda = 'Tip pregleda je obavezno polje!';
				return 1;
			}
			if(this.datum && this.greskaTipPregleda.naziv){
				return 0;
			}
			return 0;

		},
		pretragaTermina: function(){
			if(this.validacija() == 1){
				console.log('cao');
				return;
			}
			
			this.greskaDatum = '';
			this.greskaTipPregleda = '';
			// da se desi pretraga po datumu i tipu pregleda
			this.pretraga = true;
			axios
	       	.get('api/klinika/slobodnitermini/'+ this.datum + '/' + this.tipPregleda.naziv, { headers: { Authorization: 'Bearer ' + this.token }})
	       	.then(response => (this.klinike = response.data))
	       	.catch((res)=>{
	       			this.greskaDatum = 'Proverite datum';
	        	  console.log('neuspesno');
	       	})
		},
		
		detalji: function(klinikaId){
			if(this.pretraga){
				// odabrani su kriterijumi pretrage
				//console.log(klinikaId);
				this.$router.push('/detaljiKlinike/'+klinikaId+'/'+this.datum+'/'+this.tipPregleda.naziv);
			}
			else{
				// samo detalji za kliniku
				// nisu odabrani kriterijumi pretrage
				this.$router.push('/lekariKlinike/'+klinikaId);
				
			}
		},
		sort:function(s) {
		    //if s == current sort, reverse
		    if(s === this.currentSort) {
		      this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
		    }
		    this.currentSort = s;
		  },
		  
		odjava : function(){
				localStorage.removeItem("token");
				this.$router.push('/');
		}
		
	},
	
	computed:{
		  sortedKlinike:function() {
		    return this.klinike.sort((a,b) => {
		      let modifier = 1;
		      if(this.currentSortDir === 'desc') modifier = -1;
		      if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
		      if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
		      return 0;
		    });
		  }
		},
	
	mounted () {
			
			this.token = localStorage.getItem("token");
			axios
			.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => { this.pacijent = response.data;
			    axios
				.put('/auth/dobaviulogu', this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }} )
			    .then(response => {
			    	this.uloga = response.data;
			    	if (this.uloga != "ROLE_PACIJENT") {
			    		router.push('/');
			    	}
			    	else
			    	{
			    		//this.currentDate = new Date().toLocaleDateString();
			 
			    		axios
			    		.get('api/klinika/all',  { headers: { Authorization: 'Bearer ' + this.token }} )
			    		.then(res => {
			    			this.klinike = res.data;
			    		})
			    		axios
			              .get('api/tippregleda/all',  { headers: { Authorization: 'Bearer ' + this.token }} )
			              .then(res => {
			            	  this.tipoviPregleda = res.data;
			            })
			    	}
			    })
			    .catch(function (error) { console.log(error);}); 
		    })
		    .catch(function (error) { router.push('/'); });
		}
});