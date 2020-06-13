Vue.component('lekari-klinike', {
	data: function(){
		return{
			pacijent: {},
			uloga: '',
			sviLekari: [],
			pretraga: {ime:'', prezime:''},
			klinika: {naziv: '', adresa: '', prosecnaOcena: 0, kontaktKlinike: '000/000'},
			tipPregleda: {},
			tipoviPregleda: null,
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
		        <a class="nav-link" href="#/">Zdravstveni karton</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/profilpacijent">Profil: {{pacijent.ime}} {{pacijent.prezime}}</a>
		     </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
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
			<h3>Svi lekari</h3>
			<br>
			
			<table class="table table-hover table-light">
			  <tr>		   		
		   		<th @click="sort('ime')" class="class1">Ime i prezime</th>
		   		<th>Email adresa</th>
		   		<th>Kontakt</th>
		   		<th @click="sort('prosecnaOcena')" class="class1">Prosecna ocena</th>
		   		<th></th>
		   </tr>
		  
		   <tr v-for="s in sortedLekari">
		   		<td>{{s.ime}} {{s.prezime}}</td>
		   		<td>{{s.email}}</td>
		   		<td>{{s.kontakt}}</td>
		   		<td>{{s.prosecnaOcena}}</td>		   		
			</tr>
		   
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
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		
		brzoZakazivanje: function(klinikaId)
		{
			this.$router.push('/predefinisanipregledi/'+ this.$route.params.name)
		},
		sort: function(s) {
		    //if s == current sort, reverse
		    if(s === this.currentSort) {
		      this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
		    }
		    this.currentSort = s;
		  },
		pretrazi: function(){
			axios
	       	.post('api/lekar/search/klinika/'+this.$route.params.name, this.pretraga, { headers: { Authorization: 'Bearer ' + this.token }})
	       	.then(response => (this.sviLekari = response.data));

		}
	
		
	},
	computed:{
		  sortedLekari:function() {
		    return this.sviLekari.sort((a,b) => {
		      let modifier = 1;
		      if(this.currentSortDir === 'desc') modifier = -1;
		      if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
		      if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
		      return 0;
		    });
		  }
		},
		
	mounted(){
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.pacijent = response.data; 
	        axios
			.put('/auth/dobaviulogu', this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_PACIJENT") {
		    		this.$router.push('/');
		    	}else{
		    		
		    		axios
		    		.get('api/klinika/detalji/'+ this.$route.params.name,  { headers: { Authorization: 'Bearer ' + this.token }} )
		    		.then(res => {
		    			this.klinika = res.data;
		    		}),
		    		
		    		 axios
		    	       	.get('api/lekar/lekariKlinike/'+this.$route.params.name, { headers: { Authorization: 'Bearer ' + this.token }})
		    	       	.then(response => (this.sviLekari = response.data));	 
		    			    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
		
	}

});