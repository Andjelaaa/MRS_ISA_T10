Vue.component('predefpregledi', {
	data: function(){
		return{
			pregledi: null,
			pacijent: {},
			uloga: {},
			datum: null,
			greska:"",
			tipoviPregleda: null,
			tipPregleda: {naziv: null},
			showModal: false,
			nemaRezultata: ""
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
		        <a class="nav-link" href="#/profilpacijent">Profil: {{this.pacijent.ime}} {{this.pacijent.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		<table style="margin:20px">
			<tr>
				<td>Pretrazi preglede od: </td>
				<td><input class="form-control" id="datum" type="date" v-model="datum"></td>
				<td>{{this.greska}}</td>
			</tr>
			
			<tr>
				<td>Pretrazi po tipu pregleda</td>
				<td>
					<select class="form-control" id="selectTP" v-model="tipPregleda.naziv">
						<option v-for="t in tipoviPregleda" :value="t.naziv">{{t.naziv}}</option>
					</select>
				</td>
				<td><button v-on:click = "pretrazi()" class="btn btn-light">Pretrazi</button></td>
			</tr>
		</table>
		<br>
		<h4>{{this.nemaRezultata}}</h4>
		<table  class="table table-hover table-light ">
			<tr>
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th>Sala</th>
			<th>Cena</th>
			<th>Popust</th>
			<th>Cena sa popustom</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in pregledi">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				<td>{{p.sala.broj}}</td>
				<td>{{p.tipPregleda.stavka.cena}} RSD</td>
				<td>{{p.popust}} %</td>
				<td>{{(100-p.popust)*p.tipPregleda.stavka.cena/100}} RSD</td>
				<td><button class="btn btn-light" id="show-modal" @click="showModal = true" >Zakazi</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Potvrdi zakazivanje</h3>
        					<p slot="body">Da li ste sigurni?</p>
        					<div slot="footer">
        						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="zakazi(p.id, index)"> Potvrdi </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false"> Odustani </button>								
							</div>
						</modal></td>
			</tr>
		</table>
		</div>
	`,
	
	methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		zakazi : function(pregledId, i){
			// upit da li je siguran
			
			this.pregledi.splice(i,1);
			axios
	          .get('api/pregled/'+pregledId, { headers: { Authorization: 'Bearer ' + this.token }})
	          .then(res => {
	        	console.log('uspesno');
	        	// poruka o uspesnom zakazivanju
	          })
	          .catch((res)=>{
	        	  console.log('neuspesno');
	          })
		},
		validacija : function(){
			return 0;
		},
		
		 pretrazi: function(){
			 if(!this.datum || !this.tipPregleda.naziv){
					this.greska = 'Datum i tip pregleda su obavezni!';
					return 1;
			}
			 this.nemaRezultata = "";
			 this.greska = "";
				axios
		       	.get('api/pregled/search/'+ this.datum + '/' + this.tipPregleda.naziv, { headers: { Authorization: 'Bearer ' + this.token }})
		       	.then(res => {this.pregledi = res.data;
		       		if(this.pregledi[0] == null){
		       			this.nemaRezultata = "Nema rezultata pretrage";
		       			console.log('nema rezultat');
		       		}
		       		
		       	}).catch((res)=>{
					// nema rezultata ili nesto drugo da je u pitanju
					console.log('neuspesno');
				})

			},
		
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
		    	}else{
		    		/*
		    		axios
		    		.get('api/pregled/all')
		    		.then(res => {
		    			this.pregledi = res.data;
		    			*/
		    		// pravicemo se da ovo radi, jer u bazi nema ni jednog predef pregleda, mrzelo me da pravim
		    		axios
		    		.get('api/pregled/slobodniPregledi/'+this.$route.params.name,  { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.pregledi = res.data;
		    		})
		    		axios
		              .get('api/tippregleda/all', { headers: { Authorization: 'Bearer ' + this.token }})
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