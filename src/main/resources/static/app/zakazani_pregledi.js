Vue.component('zakazani-pregledi', {
	data: function(){
		return{
			pregledi: null,
			istorijaPregleda: null,
			idPacijenta: 1,
			ocene: {ocenaLekar: 0, ocenaKlinika: 0},
			oceneBackup: {ocenaLekar: 0, ocenaKlinika: 0},
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
		<h3> Otkazivanje termina </h3>
		
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th>Sala</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in pregledi">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				<td>broj sale</td>
				<!--<td>{{p.sala.broj}}</td>-->
				<td><button class="btn btn-light" id="show-modal" @click="showModal = true" >Otkazi</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Potvrdi otkazivanje</h3>
        					<p slot="body">Da li ste sigurni?</p>
        					<div slot="footer">
        						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="otkazi(p.id, index)"> Potvrdi </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false"> Odustani </button>								
							</div>
						</modal></td>
			</tr>
		</table>
		
		<h3> Istorija pregleda </h3>
		
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th>Sala</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in istorijaPregleda">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				<td>{{p.sala.broj}}</td>
				<td><button class="btn btn-light" id="show-modal" @click="showModal = true" v-on:click="select(p)">Oceni</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Ocenjivanje</h3>
        					<table slot="body" class="table table-hover table-light">
								<tbody>
										
									<tr>
										<td>Ocena Klinike</td>
										<td><input class="form-control" type="number" min="0" max="10" v-model="ocene.ocenaKlinika"/></td>
									</tr>
									<tr>
										<td>Ocena Lekara</td>
										<td><input  class="form-control" type="number" min="0" max="10" v-model = "ocene.ocenaLekar"/></td>
									</tr>									
								</tbody>
								</table>
        					<div slot="footer">
        						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="oceni(p)"> Potvrdi </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false" v-on:click="restore()"> Odustani </button>								
							</div>
						</modal></td>
			</tr>
		</table>
		</div>
	`, 
	
	methods : {
		
		otkazi : function(pregledId, i){
			// upit da li je siguran - prozor sa informacijama
			axios
	          .post('api/pregled/otkazi/'+pregledId+'/'+this.idPacijenta)
	          .then(res => {
	        	this.pregledi.splice(i,1);
	        	console.log('uspesno');
	        	// poruka o uspesnom otkazivanju
	          })
	          .catch((res)=>{
	        	  alert("Ne mozete otkazati pregled!");
	        	  console.log('neuspesno');
	          })
		},
		
		select: function(p)
		{
			// odmah podesi backup
			axios
			.post('api/pregled/ocene', p)
			.then(res=>{
				this.ocene = res.data;
				this.oceneBackup.ocenaLekar = this.ocene.ocenaLekar;
				this.oceneBackup.ocenaKlinika = this.ocene.ocenaKlinika;
			})
			
		},
		oceni : function(p)
		{
			// ceo pregled iz njega mi treba id klinike i ili id lekara ako je potvrdio ocenu
			// this.ocene;
			if(this.ocene.ocenaKlinika != this.oceneBackup.ocenaKlinika)
			{
				// oceni kliniku
				//.post('api/ocenaklinika/oceni/'+ p.lekar.klinika.id + '/' + this.idPacijenta + '/' + this.ocene.ocenaKlinika)
				axios
				.post('api/ocenaklinika/oceni/1/' + this.idPacijenta + '/' + this.ocene.ocenaKlinika)
				.then(res=>{
					
				})
			}
			if(this.ocene.ocenaLekar != this.oceneBackup.ocenaLekar)
			{
				// oceni lekara
				axios
				.post('api/ocenalekar/oceni/'+ p.lekar.id + '/' + this.idPacijenta+'/'+this.ocene.ocenaLekar)
				.then(res=>{
					
				})
			}
		},
		
		restore : function()
		{
			this.ocene.ocenaLekar = this.oceneBackup.ocenaLekar;
			this.ocene.ocenaKlinika = this.oceneBackup.ocenaKlinika;
		}
	},
	
	mounted () {
		axios
		.get('api/pregled/zakazaniPregledi/'+this.idPacijenta)
		.then(res => {
			this.pregledi = res.data;
		}),
		axios
		.get('api/pregled/istorijaPregleda/'+this.idPacijenta)
		.then(res => {
			this.istorijaPregleda = res.data;
		})
		
	},

});