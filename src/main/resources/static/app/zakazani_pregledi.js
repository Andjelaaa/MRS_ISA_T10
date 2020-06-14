Vue.component('zakazani-pregledi', {
	data: function(){
		return{
			pregledi: [],
			operacije: [],
			istorijaPregleda: [],
			istorijaOperacija: [],
			idPacijenta: 1,
			pacijent: {},
			uloga: '',
			lekarId: 0,
			ocene: {ocenaLekar: 0, ocenaKlinika: 0},
			oceneBackup: {ocenaLekar: 0, ocenaKlinika: 0},
			showModal: false,
			isHidden: false,
			isHiddenIstorija: false,
			isHiddenOperacija: false,
			isHiddenIstorijaOperacija: false,
			currentSortPregled:'datumVreme',
			  currentSortDirPregled:'asc',
			currentSortIstorijaPregled:'datumVreme',
			  currentSortDirIstorijaPregled:'asc',
			currentSortOperacija:'datumVreme',
			  currentSortDirOperacija:'asc',
			currentSortIstorijaOperacija:'datumVreme',
			  currentSortDirIstorijaOperacija:'asc'
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
		        <a class="nav-link" href="#/profilpacijent">Profil: {{pacijent.ime}} {{pacijent.prezime}}</a>
		      </li>
		    </ul>
		     <form class="form-inline my-2 my-lg-0">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		    
		  </div>
		</nav>
		</br>
		<h2> Zakazani pregledi <button  class="btn btn-light" @click='isHidden = !isHidden'>+/-</button> </h2>
		
		<div v-show='isHidden'>
		<table class="table table-hover table-light ">
			<tr>
			<th @click="sortPregled('datumVreme')" class="class1">Datum i vreme</th>
			<th @click="sortPregled('trajanje')" class="class1">Trajanje</th>
			<th @click="sortPregled('tipPregleda.naziv')" class="class1">Tip pregleda</th>
			<th>Lekar</th>
			<th>Sala</th>
			<th>Status</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in sortedPregledi">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				
				<td v-if="p.sala != null">{{p.sala.broj}}</td>
				<td v-if="p.sala == null">/</td>
				<td>{{p.status}}</td>
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
		</div>
		
		<h2> Zakazane operacije <button  class="btn btn-light" @click='isHiddenOperacija = !isHiddenOperacija'>+/-</button> </h2>
		
		<div v-show='isHiddenOperacija'>
		<table class="table table-hover table-light ">
			<tr>
			<th @click="sortOperacija('datumVreme')" class="class1">Datum i vreme</th>
			<th @click="sortOperacija('trajanje')" class="class1">Trajanje</th>
			<th>Lekari</th>
			<th>Sala</th>
			<th>Status</th>
			</tr>
			
			<tr v-for="(o, index) in sortedOperacija">
				<td>{{o.datumVreme | formatDate}}</td>
				<td>{{o.trajanje}}</td>
				<td>{{o.lekar[0].ime}} {{o.lekar[0].prezime}}</td>
				
				<td v-if="o.sala != null">{{o.sala.broj}}</td>
				<td v-if="o.sala == null">/</td>
				<td>{{o.status}}</td>
			</tr>
		</table>
		</div>
		
		<h2> Istorija pregleda <button  class="btn btn-light" @click='isHiddenIstorija = !isHiddenIstorija'>+/-</button>  </h2>
		<div v-show='isHiddenIstorija'>
		<table class="table table-hover table-light ">
			<tr>
			<th @click="sortIstorijaPregled('datumVreme')" class="class1">Datum i vreme</th>
			<th @click="sortIstorijaPregled('trajanje')" class="class1">Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th @click="sortPregled('sala.broj')" class="class1">Sala</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in sortedIstorijaPregledi">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				<td v-if="p.sala != null">{{p.sala.broj}}</td>
				<td v-if="p.sala == null">/</td>

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
		
		<h2> Istorija operacije <button  class="btn btn-light" @click='isHiddenIstorijaOperacija = !isHiddenIstorijaOperacija'>+/-</button> </h2>
		
		<div v-show='isHiddenIstorijaOperacija'>
		<table class="table table-hover table-light ">
			<tr>
			<th @click="sortIstorijaOperacija('datumVreme')" class="class1">Datum i vreme</th>
			<th @click="sortIstorijaOperacija('trajanje')" class="class1">Trajanje</th>
			<th>Lekari</th>
			<th @click="sortIstorijaOperacija('sala.broj')" class="class1">Sala</th>
			<th></th>
			</tr>
			
			<tr v-for="(o, index) in sortedIstorijaOperacija">
				<td>{{o.datumVreme | formatDate}}</td>
				<td>{{o.trajanje}}</td>
				<td v-for="l in o.lekar"> {{l.ime}} {{l.prezime}} </td>
				<td v-if="o.sala.broj != null">{{o.sala.broj}}</td>
				<td v-if="o.sala.broj == null">/</td>
				<td></td>
			</tr>
		</table>
		</div>
		</div>
	`, 
	
	methods : {
		
		otkazi : function(pregledId, i){
			// upit da li je siguran - prozor sa informacijama
			axios
	          .get('api/pregled/otkazi/'+pregledId+'/'+this.pacijent.id,  { headers: { Authorization: 'Bearer ' + this.token }})
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
		
		otkaziOperaciju : function(operacijaId, i){
			// upit da li je siguran - prozor sa informacijama
			/*
			axios
	          .post('api/pregled/otkazi/'+pregledId+'/'+this.pacijent.id)
	          .then(res => {
	        	this.pregledi.splice(i,1);
	        	console.log('uspesno');
	        	// poruka o uspesnom otkazivanju
	          })
	          .catch((res)=>{
	        	  alert("Ne mozete otkazati pregled!");
	        	  console.log('neuspesno');
	          })
	          */
		},
		
		select: function(p)
		{
			// odmah podesi backup
			this.lekarId = p.lekar.id;
			axios
			.post('api/pregled/ocene', p, { headers: { Authorization: 'Bearer ' + this.token }})
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
				axios
				.get('api/ocenaklinika/oceni/'+ this.lekarId  + '/' + this.ocene.ocenaKlinika, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(res=>{
					
				})
			}
			if(this.ocene.ocenaLekar != this.oceneBackup.ocenaLekar)
			{
				
				axios
				.get('api/ocenalekar/oceni/'+ this.lekarId +'/'+this.ocene.ocenaLekar,  { headers: { Authorization: 'Bearer ' + this.token }})
				.then(res=>{
					
				})
			}
		},
		
		restore : function()
		{
			this.ocene.ocenaLekar = this.oceneBackup.ocenaLekar;
			this.ocene.ocenaKlinika = this.oceneBackup.ocenaKlinika;
		},
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		sortPregled: function(s) {
		    //if s == current sort, reverse
		    if(s === this.currentSortPregled) {
		      this.currentSortDirPregled = this.currentSortDirPregled==='asc'?'desc':'asc';
		    }
		    this.currentSortPregled = s;
		  },
		  sortIstorijaPregled: function(s) {
			    //if s == current sort, reverse
			    if(s === this.currentSortIstorijaPregled) {
			      this.currentSortDirIstorijaPregled = this.currentSortDirIstorijaPregled==='asc'?'desc':'asc';
			    }
			    this.currentSortIstorijaPregled = s;
		},
		sortOperacija: function(s) {
		    //if s == current sort, reverse
		    if(s === this.currentSortOperacija) {
		      this.currentSortDirOperacija = this.currentSortDirOperacija==='asc'?'desc':'asc';
		    }
		    this.currentSortOperacija = s;
		  },
		  
		  sortIstorijaOperacija: function(s) {
			    //if s == current sort, reverse
			    if(s === this.currentSortIstorijaOperacija) {
			      this.currentSortDirIstorijaOperacija = this.currentSortDirIstorijaOperacija==='asc'?'desc':'asc';
			    }
			    this.currentSortIstorijaOperacija = s;
			  },
	},
	
	computed:{
		  sortedPregledi:function() {
		    return this.pregledi.sort((a,b) => {
		      let modifier = 1;
		      if(this.currentSortDirPregled === 'desc') modifier = -1;
		      if(a[this.currentSortPregled] < b[this.currentSortPregled]) return -1 * modifier;
		      if(a[this.currentSortPregled] > b[this.currentSortPregled]) return 1 * modifier;
		      return 0;
		    });
		  },
		  
		  sortedIstorijaPregledi:function() {
			    return this.istorijaPregleda.sort((a,b) => {
			      let modifier = 1;
			      if(this.currentSortDirIstorijaPregled === 'desc') modifier = -1;
			      if(a[this.currentSortIstorijaPregled] < b[this.currentSortIstorijaPregled]) return -1 * modifier;
			      if(a[this.currentSortIstorijaPregled] > b[this.currentSortIstorijaPregled]) return 1 * modifier;
			      return 0;
			    });
			  },
		sortedOperacija:function() {
			return this.operacije.sort((a,b) => {
		      let modifier = 1;
		      if(this.currentSortDirOperacija === 'desc') modifier = -1;
		      if(a[this.currentSortOperacija] < b[this.currentSortOperacija]) return -1 * modifier;
		      if(a[this.currentSortOperacija] > b[this.currentSortOperacija]) return 1 * modifier;
		      return 0;
		    });
		  },
		  sortedIstorijaOperacija:function() {
				return this.istorijaOperacija.sort((a,b) => {
			      let modifier = 1;
			      if(this.currentSortDirIstorijaOperacija === 'desc') modifier = -1;
			      if(a[this.currentSortIstorijaOperacija] < b[this.currentSortIstorijaOperacija]) return -1 * modifier;
			      if(a[this.currentSortIstorijaOperacija] > b[this.currentSortIstorijaOperacija]) return 1 * modifier;
			      return 0;
			    });
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
		    	}
		    	else
		    	{
		    		axios
		    		.get('api/pregled/zakazaniPregledi', { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.pregledi = res.data;
		    		}),
		    		axios
		    		.get('api/pregled/istorijaPregleda/'+this.pacijent.id, { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.istorijaPregleda = res.data;
		    		}),
		    		axios
		    		.get('api/operacije/istorijaOperacija',{ headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.istorijaOperacija = res.data;
		    		}),
		    		axios
		    		.get('api/operacije/zakazane', { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.operacije = res.data;
		    		})
		    	}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });
	}

});