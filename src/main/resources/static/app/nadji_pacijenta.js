Vue.component('nadjipacijenta', {

	data: function(){
		return{	
			korisnik:{},
			uloga: '',
			pacijent: {},
			zKarton: {},
			pregled: {},
			pocinjanje:false,
			izvestaj: {opis:'', recept:{}, dijagnoza:{naziv:'', sifra:''}},
			dijagnoze:[],
			lekovi:[],
			odabraniLekovi:[],
			showModal: false,
			showModal1: false,
			showModal2: false,
			showModal3: false,
			noviTermin: {},
			novaDijagnoza:{naziv:'', sifra:''},
			datumVremeGreska: '',
			tipTerminaGreska: '',
			trajanjeGreska: '',
			tipTermina: '',
			istorijaPregleda: {},
			selected: {krvnaGrupa:'', visina:'',tezina:'', dioptrija:'' },
			selectedBackup:  {krvnaGrupa:'', visina:'',tezina:'', dioptrija:'' },
			
			selectedIzvestaj: {datumVreme:'', trajanje:'',tipPregleda:{}, lekar:'', sala:{},
			izvestaj:{ id:'',dijagnoza:'',  recept:{lek:[]}} },
		
			selectedBackupIzvestaj:  {datumVreme:'', trajanje:'',tipPregleda:{}, lekar:'', sala:{},
			izvestaj:{ id:'', dijagnoza:'', recept:{lek:[]}} },
			promenjenaDijagnoza:'',
			funkcionalnost:false,
			menjaDijagnozu:'',
			menjaLekove:[]
		}
	}, 
	
	template: `
	<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  	<a v-if="uloga=='ROLE_LEKAR'" class="navbar-brand" href="#/lekar">Pocetna</a>
			<a v-if="uloga=='ROLE_MED_SESTRA'" class="navbar-brand" href="#/medsestra">Pocetna</a>
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item active">
		        <a class="nav-link" href="#/pacijenti">Pacijenti</a>
		      </li>
		      
		      <li class="nav-item">
		        <a class="nav-link" href="#/odmor">Zahtev za godisnji odmor/odsustvo</a>
		      </li>
		        <li class="nav-item">
		        <a v-if="uloga=='ROLE_MED_SESTRA'" class="nav-link" href="#/overa">Overa recepata</a>
		      </li>
		      <li class="nav-item">
		        <a  v-if="uloga=='ROLE_MED_SESTRA'" class="nav-link" href="#/kalendarr">Radni kalendar</a>
		        <a  v-if="uloga=='ROLE_LEKAR'" class="nav-link" href="#/kalendarlekar">Radni kalendar</a>
		      </li>
		      <li class="nav-item">
		        <a  v-if="uloga=='ROLE_MED_SESTRA'" class="nav-link" href="#/medsestra">Profil: {{korisnik.ime}} {{korisnik.prezime}}</a>
		        <a v-if="uloga=='ROLE_LEKAR'" class="nav-link" href="#/profil">Profil: {{korisnik.ime}} {{korisnik.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>

		<div class="float-left" style="margin: 20px">		
		<h3> Pacijent {{pacijent.ime}} {{pacijent.prezime}} </h3>
		
		<table class="table table-hover table-light ">		
		   <tr>		   		
		   		<th>Krvna grupa</th>
		   		<th>Visina</td>
		   		<th>Tezina</th>
		   		<th>Dioptrija</th>
		   		<th>Pol</th>
		   		<th>Datum rodjenja</th>
		   		<th></th>
		   </tr>
		  <tbody>
		   <tr>
		   		<td>{{zKarton.krvnaGrupa}}</td>
		   		<td>{{zKarton.visina}}</td>
		   		<td>{{zKarton.tezina}}</td>
		   		<td>{{zKarton.dioptrija}}</td>
		   		<td>{{zKarton.pol}}</td>
		   		<td>{{zKarton.datumRodjenja | formatDate}}</td>
		   		<td>
		   		<button v-if="pocinjanje"  class="btn btn-light" id="show-modal" @click="showModal1 = true" v-on:click="izmeniKarton(zKarton)">Izmeni karton</button>
				<modal v-if="showModal1" @close="showModal1 = false">
	    			<h3 slot="header">Izmena podataka u zdravstvenom kartonu</h3>
	    			<table slot="body" class="table table-hover table-light">
						<tbody>
							<tr>
							  <td>Krvna grupa</td>
							  <td><input class="form-control" type="text"  v-model="selected.krvnaGrupa"/></td>
							</tr>
							<tr>
							  <td>Visina</td>
							  <td><input  class="form-control" type="text" v-model = "selected.visina"/></td>
							 </tr>
							  <tr>
								<td>Tezina</td>
								<td><input  class="form-control" type="text" v-model = "selected.tezina"/></td>
							   </tr>	
							   <tr>
								 <td>Dioptrija</td>
								 <td><input  class="form-control" type="text" v-model = "selected.dioptrija"/></td>
								</tr>								
							</tbody>
					</table>
	    					
	    			<div slot="footer">
	    				<button @click="showModal1=false" style="margin:5px;" class="btn btn-success" v-on:click="save(selected)"> Sacuvaj izmene </button>       						
						<button style="margin:5px;" class="btn btn-secondary" @click="showModal1=false" v-on:click="restore(selected)"> Odustani </button>								
					</div>
				</modal>
		   	</td>		   		
		   </tr>
		   </tbody>
		    
		</table>
	<button v-if="uloga=='ROLE_LEKAR' && pregled!=null" class="btn btn-light" v-on:click="pocni()"> Zapocni pregled </button>
	<p v-if="pregled==null"> Nema pregleda za zapocinjanje </p>
	<br>
			<h3> Istorija pregleda </h3>
		
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th>Sala</th>
			<th>Dijagnoza</th>
			<th>Lekovi</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in istorijaPregleda">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				<td>{{p.sala.broj}}</td>
				<td v-if="p.izvestaj.dijagnoza!=null">{{p.izvestaj.dijagnoza.naziv}} </td>
				<td v-else></td>
				<td v-if="p.izvestaj.recept!=null"> <p v-for="zz in p.izvestaj.recept.lek"> {{zz.naziv}} {{zz.sifra}}</p> </td>
				<td v-else></td>
				<td>
				<td><button class="btn btn-light" id="show-modal"  v-on:click="selectIzmenu(p)">Izmeni</button>
						<modal v-if="showModal2 && funkcionalnost" @close="showModal2 = false">
        
        					<h3 slot="header">Izmena izvestaja</h3>
        					<table slot="body" class="table table-hover table-light">
								<tbody>
										
									<tr>
										<td>Dijagnoza</td>
										 <td>
											<select class="form-control"  v-model="menjaDijagnozu">
												<option v-for="t in dijagnoze" :value="t">{{t.naziv}} {{t.sifra}}</option>
											</select>
										 </td>
									</tr>
									<tr>
								 		<td>Lekovi</td>
										 <td>
										 <div class="dropdown">
											<button class="btn btn-light dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												Odaberi lek
											</button>
											<form class="dropdown-menu" aria-labelledby="dropdownMenuButton" >
													<label class="dropdown-item" v-for="l in lekovi" name="" value="l.naziv">
														<input id="l" name="l.naziv" :value="l" type="checkbox" v-model="menjaLekove">{{l.naziv}}
													</label>
											</form>
										</div>
										 
										 </td>
									</tr>
																		
								</tbody>
								</table>
								<div slot="footer">
									<button @click="showModal2=false" style="margin:5px;" class="btn btn-success" v-on:click="izmeniPoslednjiIzvestaj(p)"> Izmeni </button>       						
									<button style="margin:5px;" class="btn btn-secondary" @click="showModal2=false" v-on:click="restoreIzvestaj(p)"> Odustani </button>								
								</div>
						</modal>
						<modal v-if="showModal3 && !funkcionalnost" @close="showModal3 = false">
        
        					<h3 slot="header">Obavestenje</h3>
        					<table slot="body" class="table table-hover table-light">
								<tbody>

									<tr>
										<td>Rok za izmenu zdravstvenog kartona je prosao.</td>
									</tr>									
								</tbody>
								</table>
								<div slot="footer">
									
									<button style="margin:5px;" class="btn btn-secondary" @click="showModal3=false"> OK </button>								
								</div>
						</modal>
				</td>
			</tr>
		</table>
	
	</div>
    <div class="float-right" style="margin: 20px" v-if="pocinjanje">
		<h3> Unesi izvestaj </h3>
		
		<table class="table table-hover table-light ">		
		  <tbody>
		   <tr>
		   		<td>Informacije o pregledu</td>
		   		<td><input type="text" class="form-control" v-model="izvestaj.opis"></td>   		
		   </tr>
		    <tr>
		   		<td>Dijagnoza</td>
		   		<td>
		   			<select class="form-control"  v-model="novaDijagnoza">
						<option v-for="t in dijagnoze" :value="t">{{t.naziv}} {{t.sifra}}</option>
					</select>
		   		</td>   		
		   </tr>
		    <tr>
		   		<td>Dodaj lekove u receptu</td>
		   		<td>
		   			 <div class="dropdown">
						  <button class="btn btn-light dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    Odaberi lek
						  </button>
						  <form class="dropdown-menu" aria-labelledby="dropdownMenuButton" >
						    	<label class="dropdown-item" v-for="l in lekovi" name="" value="l.naziv">
						    		<input id="l" name="l.naziv" :value="l" type="checkbox" v-model="odabraniLekovi">{{l.naziv}}
						    	</label>
						  </form>
					</div>
		   		</td>   		
		   </tr>
		   <tr>
		   		<td>
		   			<button class="btn btn-light" id="show-modal" @click="showModal = true">Zakazi novi pregled/operaciju</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Novi termin</h3>
        					<table slot="body" >
								<tbody>
									<tr>
										<td>Novi termin za: </td>
										<td>
											<select class="form-control" id="selectSala" v-model="tipTermina">
												<option value="pregled">Pregled</option>
												<option value="operacija">Operacija</option>
											</select>
											<p style="color: red">{{tipTerminaGreska}}</p>
										</td>
										
										
									</tr>	
									<tr>			   
								   		<td>Datum i vreme: </td>
								   		<td><input class="form-control" id="datumvreme" type="datetime-local" v-model="noviTermin.datumVreme">
								   			<p style="color: red">{{datumVremeGreska}}</p>
								   		</td>
								   		
			   						</tr>
									<tr>			   
								   		<td>Trajanje: </td>
								   		<td><input class="form-control" id="trajanje" type="number" v-model="noviTermin.trajanje">
								   			<p style="color: red">{{trajanjeGreska}}</p>
								   		</td>
								   		
			   						</tr>
									
									
								</tbody>
								</table>
        					
        					<div slot="footer">
        						<button style="margin:5px;" class="btn btn-success" v-on:click="noviPO()"> Sacuvaj </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false" v-on:click="isprazniPolja()"> Nazad </button>								
							</div>
						</modal>
		   		</td>
		   		<td>
		   			<button class="btn btn-light" v-on:click="zavrsiPregled()"> Zavrsi pregled </button>
		   		</td>   		
		   </tr>
		   </tbody>
		    
		</table>
	</div>	
	</div>
	
	`, methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		pocni:function(){
			this.pocinjanje = true;
		},
		save: function(objekat){
			axios
			.post('api/pregled/izmenikarton/'+ this.korisnik.id, objekat,{ headers: { Authorization: 'Bearer ' + this.token }} )
			.then((response)=>{
				 alert("Uspesno ste izmenili");
				
			}).catch((response)=>{
				 this.selected.krvnaGrupa = this.selectedBackup.krvnaGrupa;
				 this.selected.visina = this.selectedBackup.visina;
				 this.selected.tezina = this.selectedBackup.tezina;
				 this.selected.dioptrija = this.selectedBackup.dioptrija;
				 alert("Pogresili ste sa izmenom");
			});
		},
		restore:function(zkarton){
			zkarton.krvnaGrupa = this.selectedBackup.krvnaGrupa;
			zkarton.visina = this.selectedBackup.visina;
			zkarton.tezina = this.selectedBackup.tezina;
			zkarton.dioptrija = this.selectedBackup.dioptrija;
			
			
		},
		izmeniKarton: function(zkarton){
			this.selectedBackup.krvnaGrupa = zkarton.krvnaGrupa;
			this.selectedBackup.visina = zkarton.visina;
			this.selectedBackup.tezina = zkarton.tezina;
			this.selectedBackup.dioptrija = zkarton.dioptrija;
			this.selected = zkarton;
			
		},
		validacija: function(){
			this.datumVremeGreska = '';
			this.trajanjeGreska = '';
			
			if(!this.tipTermina)
				this.tipTerminaGreska = 'Tip termina je obavezno polje!';
			if(!this.noviTermin.trajanje)
				this.trajanjeGreska = 'Trajanje je obavezno polje!';
			if(!this.noviTermin.datumVreme)
				this.datumVremeGreska = 'Datum i vreme je obavezno polje!';


			if(this.tipTermina && this.noviTermin.trajanje && this.noviTermin.datumVreme){
				return 0;
			}
			return 1;
			
		},
		noviPO:function(){
			if(this.validacija()==1)
				return;
			this.showModal = false;
			this.noviTermin.lekar = this.korisnik;
			this.noviTermin.pacijent = this.pacijent;

			if(this.tipTermina == 'pregled'){
				axios
				.post('api/pregled/lekarzahtev', this.noviTermin, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then((res)=>{
					alert('Uspesno poslat zahtev za pregled!');
				}).catch((res)=>{
					alert('Neuspesno!');
					this.error = 'Greska pri dodavanju';
				});
				
			}else if(this.tipTermina == 'operacija'){
				this.noviTermin.lekar = [];
				this.noviTermin.lekar.push(this.korisnik);
				axios
				.post('api/operacije/lekarzahtev', this.noviTermin, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then((res)=>{
					alert('Uspesno poslat zahtev za operaciju!');
				}).catch((res)=>{
					alert('Neuspesno!');
					this.error = 'Greska pri dodavanju';
				});				
			}
			this.isprazniPolja();
			
			
			
		},
	    zavrsiPregled:function(){
	    	if(!this.izvestaj.opis)
	    		alert("Niste uneli informacije o pregledu!");
	    	else{
	    		this.pocinjanje = false;
				this.izvestaj.recept.lek = this.odabraniLekovi;

			    this.izvestaj.dijagnoza.naziv = this.novaDijagnoza.naziv;
				this.izvestaj.dijagnoza.sifra = this.novaDijagnoza.sifra;
				
		    	axios
	           	.post('api/izvestaj/'+ this.pregled.id, this.izvestaj, { headers: { Authorization: 'Bearer ' + this.token }} )
	           	.then((response) => {
					
                       axios
						.get('api/pregled/istorijaPregleda/'+this.pacijent.id, { headers: { Authorization: 'Bearer ' + this.token }} )
						.then(response => {
							this.istorijaPregleda = response.data;
							alert("Uspesno je zavrsen izvestaj");
						}).catch((response)=>
	    	           			{console.log("IStorija nesto pravi problemdd");}
						);

	           	});
		    	this.izvestaj= {opis:'', recept:{}, dijagnoza:{naziv:'', sifra:''}};
		    	this.pregled= null;
	    	}
	    },
	    isprazniPolja : function() {
	    	this.noviTermin= {};
			this.datumVremeGreska= '';
			this.tipTerminaGreska= '';
			this.trajanjeGreska= '';
			this.tipTermina= '';
		},
		selectIzmenu: function(pregled){
			var zavrsenPregled = new Date(new Date(pregled.datumVreme).getTime()+ pregled.trajanje); 
			var trenutnoVreme = new Date();
			var diff = (trenutnoVreme - zavrsenPregled)/60000;// in milliseconds
			
			console.log(diff+" dasd");
			if(diff<= 30 && pregled.lekar.id == this.korisnik.id){  // ako je proslo 30 min od pregleda ne moze da izmeni poy
				this.showModal2 = true;
				this.showModal3 = false;
				this.funkcionalnost = true;
				this.selectedBackupIzvestaj.datumVreme = pregled.datumVreme;
				this.selectedBackupIzvestaj.trajanje = pregled.trajanje;
				this.selectedBackupIzvestaj.tipPregleda = pregled.tipPregleda;
				this.selectedBackupIzvestaj.lekar = pregled.lekar;
				this.selectedBackupIzvestaj.sala = pregled.sala;
				this.selectedBackupIzvestaj.izvestaj.id = pregled.izvestaj.id;
				this.selectedBackupIzvestaj.izvestaj.dijagnoza = pregled.izvestaj.dijagnoza;
				this.selectedBackupIzvestaj.izvestaj.recept.lek = pregled.izvestaj.recept.lek;
				this.selectedIzvestaj = pregled;
			}
			else{
				this.showModal3 = true;
				this.showModal2 = false;
				this.funkcionalnost = false;
				
			}
		},

		izmeniPoslednjiIzvestaj: function(pregled){
			if(this.menjaDijagnozu && this.menjaLekove.length==0){
				console.log("udje1");
				    this.selectedIzvestaj.izvestaj.recept.lek = this.selectedBackupIzvestaj.izvestaj.recept.lek;
					this.selectedIzvestaj.izvestaj.dijagnoza = this.menjaDijagnozu;
					axios
					.put('api/recept/izmeniDijagnozu/'+ pregled.id +'/'+ this.selectedBackupIzvestaj.izvestaj.id, this.selectedIzvestaj.izvestaj.dijagnoza, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then((response)=>{
						
						alert("Uspesno ste izmenili dati pregled");
						
					}).catch((response)=>{
						this.selectedIzvestaj.datumVreme = this.selectedBackupIzvestaj.datumVreme;
						this.selectedIzvestaj.trajanje = this.selectedBackupIzvestaj.trajanje;
						this.selectedIzvestaj.tipPregleda =this.selectedBackupIzvestaj.tipPregleda
						this.selectedIzvestaj.lekar = this.selectedBackupIzvestaj.lekar;
						this.selectedIzvestaj.sala =this.selectedBackupIzvestaj.sala;
						this.selectedIzvestaj.izvestaj.id =this.selectedBackupIzvestaj.izvestaj.id;
						this.selectedIzvestaj.izvestaj.dijagnoza = this.selectedBackupIzvestaj.izvestaj.dijagnoza;
						this.selectedIzvestaj.izvestaj.recept.lek = this.selectedBackupIzvestaj.izvestaj.recept.lek;
						alert("Pogresili ste sa izmenom");
					});
			}
			else if(!this.menjaDijagnozu && this.menjaLekove){
				console.log("udje2");
				this.selectedIzvestaj.izvestaj.dijagnoza = this.selectedBackupIzvestaj.izvestaj.dijagnoza;
				this.selectedIzvestaj.izvestaj.recept.lek =this.menjaLekove;
				var objekat ={"dijagnozaDTO": this.selectedIzvestaj.izvestaj.dijagnoza, "lekoviDTO": this.selectedIzvestaj.izvestaj.recept.lek};
				axios
					.put('api/recept/izmeniLekove/'+ pregled.id+'/'+ this.selectedBackupIzvestaj.izvestaj.id, objekat, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then((response)=>{
					
						alert("Uspesno ste izmenili dati pregled");
						
					}).catch((response)=>{
						this.selectedIzvestaj.datumVreme = this.selectedBackupIzvestaj.datumVreme;
						this.selectedIzvestaj.trajanje = this.selectedBackupIzvestaj.trajanje;
						this.selectedIzvestaj.tipPregleda =this.selectedBackupIzvestaj.tipPregleda
						this.selectedIzvestaj.lekar = this.selectedBackupIzvestaj.lekar;
						this.selectedIzvestaj.sala =this.selectedBackupIzvestaj.sala;
						this.selectedIzvestaj.izvestaj.id =this.selectedBackupIzvestaj.izvestaj.id;
						this.selectedIzvestaj.izvestaj.dijagnoza = this.selectedBackupIzvestaj.izvestaj.dijagnoza;
						this.selectedIzvestaj.izvestaj.recept.lek = this.selectedBackupIzvestaj.izvestaj.recept.lek;
						alert("Pogresili ste sa izmenom");
					});
			}
			else if(this.menjaDijagnozu && this.menjaLekove){
				console.log("udje3");
				this.selectedIzvestaj.izvestaj.dijagnoza = this.menjaDijagnozu;
				this.selectedIzvestaj.izvestaj.recept.lek =this.menjaLekove;
				var objekat ={"dijagnozaDTO": this.selectedIzvestaj.izvestaj.dijagnoza, "lekoviDTO": this.selectedIzvestaj.izvestaj.recept.lek};
				axios
					.put('api/recept/izmeniOba/'+ pregled.id+'/'+ this.selectedBackupIzvestaj.izvestaj.id, objekat, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then((response)=>{
						
						alert("Uspesno ste izmenili dati pregled");
						
					}).catch((response)=>{
						this.selectedIzvestaj.datumVreme = this.selectedBackupIzvestaj.datumVreme;
						this.selectedIzvestaj.trajanje = this.selectedBackupIzvestaj.trajanje;
						this.selectedIzvestaj.tipPregleda =this.selectedBackupIzvestaj.tipPregleda
						this.selectedIzvestaj.lekar = this.selectedBackupIzvestaj.lekar;
						this.selectedIzvestaj.sala =this.selectedBackupIzvestaj.sala;
						this.selectedIzvestaj.izvestaj.id =this.selectedBackupIzvestaj.izvestaj.id;
						this.selectedIzvestaj.izvestaj.dijagnoza = this.selectedBackupIzvestaj.izvestaj.dijagnoza;
						this.selectedIzvestaj.izvestaj.recept.lek = this.selectedBackupIzvestaj.izvestaj.recept.lek;
						alert("Pogresili ste sa izmenom");
					});
			}
			else{
				console.log("udje4");
				this.restoreIzvestaj(pregled);
			}
			
		},
		restoreIzvestaj:function(pregled){
			pregled.datumVreme = this.selectedBackupIzvestaj.datumVreme;
			pregled.trajanje = this.selectedBackupIzvestaj.trajanje;
 			pregled.tipPregleda =this.selectedBackupIzvestaj.tipPregleda
			pregled.lekar = this.selectedBackupIzvestaj.lekar;
			pregled.sala =this.selectedBackupIzvestaj.sala;
			pregled.izvestaj.id = this.selectedBackupIzvestaj.izvestaj.id;
			pregled.izvestaj.dijagnoza = this.selectedBackupIzvestaj.izvestaj.dijagnoza;
			pregled.izvestaj.recept.lek = this.selectedBackupIzvestaj.izvestaj.recept.lek;
			this.selectedIzvestaj.izvestaj.recept.lek =this.selectedBackupIzvestaj.izvestaj.recept.lek;
		}
	},
	
	mounted(){
		
	this.token = localStorage.getItem("token");
	axios
	.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
    .then(response => { this.korisnik = response.data;
	    axios
		.put('/auth/dobaviulogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
	    .then(response => {
	    	this.uloga = response.data;
	    	if (this.uloga != "ROLE_LEKAR" && this.uloga != "ROLE_MED_SESTRA") {
	    		this.$router.push('/');
	    	}else{
	    	
	    		axios
	           	.get('api/pacijent/'+this.$route.params.lbo, { headers: { Authorization: 'Bearer ' + this.token }} )
	           	.then(response => {
	           			this.pacijent = response.data; 
						this.zKarton = this.pacijent.zKarton;
						
						
	           			axios
	    	           	.get('api/pregled/'+this.pacijent.id+'/'+ this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
	    	           	.then(response => {
							this.pregled = response.data;
							
	    	           	}).catch((response)=>
	    	           			{this.pregled = null; 
	    	           			console.log("Pregled ne postoji");}
						);
						axios
						.get('api/pregled/istorijaPregleda/'+this.pacijent.id, { headers: { Authorization: 'Bearer ' + this.token }} )
						.then(response => {
							this.istorijaPregleda = response.data;
							console.log(this.istorijaPregleda.length +" nece zadnje da ucita");
						}).catch((response)=>
	    	           			{console.log("IStorija nesto pravi problem");}
						);
					   
							
					   
				   });
				   	
					axios
					.get('api/dijagnoze/all',{ headers: { Authorization: 'Bearer ' + this.token }})
					.then(response => {
						this.dijagnoze = response.data;
						
					});
					axios
						.get('api/lekovi/all',{ headers: { Authorization: 'Bearer ' + this.token }})
						.then(response => (this.lekovi = response.data));
				
	    	}
	    })
	    .catch(function (error) { console.log(error);});
	    
    })
    .catch(function (error) { router.push('/') });	 
}

});