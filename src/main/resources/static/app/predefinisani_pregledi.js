Vue.component('predefpregledi', {
	data: function(){
		return{
			pregledi: null,
			idPacijenta: 1,
			datum: null,
			tipoviPregleda: null,
			tipPregleda: {naziv: null},
			showModal: false
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
		<table style="margin:20px">
			<tr>
				<td>Pretrazi preglede od: </td>
				<td><input class="form-control" id="datum" type="date" v-model="datum"></td>
				<td><button  v-on:click = "pretragaDatum()" class="btn btn-light">Pretrazi</button></td>
			</tr>
			
			<tr>
				<td>Pretrazi po tipu pregleda</td>
				<td>
					<select class="form-control" id="selectTP" v-model="tipPregleda.naziv">
						<option v-for="t in tipoviPregleda" :value="t.naziv">{{t.naziv}}</option>
					</select>
				</td>
				<td><button v-on:click = "pretragaTip()" class="btn btn-light">Pretrazi</button></td>
			</tr>
		</table>
		<br>
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
		zakazi : function(pregledId, i){
			// upit da li je siguran
			
			this.pregledi.splice(i,1);
			axios
	          .post('api/pregled/'+pregledId+'/'+this.idPacijenta)
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
		pretragaDatum : function(){
			if(this.validacija() == 1)
				return;
			console.log(this.datum);
			axios
			.get('api/pregled/datum/'+this.datum)
			.then(res=>{
				this.pregledi = res.data;
				if(this.pregledi == null)
					console.log('nema rezultat');
			}).catch((res)=>{
				// nema rezultata ili nesto drugo da je u pitanju
				console.log('nema rezultat');
			})
		},
		
		pretragaTip: function(){
			if(this.validacija() == 1)
				return;
			axios
			.get('api/pregled/tip/'+this.tipPregleda.naziv)
			.then(res=>{
				this.pregledi = res.data;
				if(this.pregledi == null)
					console.log('nema rezultat');
			}).catch((res)=>{
				// nema rezultata ili nesto drugo da je u pitanju
				console.log('neuspesno');
			})
		},
		
		
	},
	
	mounted () {
//		axios
//		.get('api/pregled/slobodniPregledi/'+this.$route.params.name)
//		.then(res => {
//			this.pregledi = res.data;
//		})
// odkomentarisati kad se pregled poveze sa klinikom!
		axios
		.get('api/pregled/all')
		.then(res => {
			this.pregledi = res.data;
		})
		axios
          .get('api/tippregleda/all')
          .then(res => {
        	  this.tipoviPregleda = res.data;

          })
	},
});