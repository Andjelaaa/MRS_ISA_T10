Vue.component('tipovipregleda', {
	data: function(){
		return{
			tipoviPregleda: null,
			pretraga: '',
			selected: {naziv:'', opis: ''},
			selectedBackup: {naziv:'', opis: ''},
			showModal: false,
			
			admin:{},
			tipPregleda: {naziv:'', opis:'', brojAktvnih:0},
			nazivGreska: '',
			opisGreska: '',
			error: '',
			stavkaCenovnika: {},
			cenaGreska: '',

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
		        <a class="nav-link active" href="#/tipovipregleda">Tipovi pregleda</a>
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
		
		Pretraga: <input type="text" id="search" v-model="pretraga">
		<button v-on:click = "pretrazi()" class="btn btn-light">Pretrazi</button>
		<table class="table table-hover table-light">
		
		   <tr>		   		
		   		<th>Naziv</th>
		   		<th>Opis</th>
		   		<th>Cena</th>
		   </tr>
		  <tbody>
		   <tr  v-for="t in tipoviPregleda">
		   		<td>{{t.naziv}}</td>
		   		<td>{{t.opis}}</td>
		   		<td>{{t.stavka.cena}} RSD</td>
		   		<td>
					<button class="btn btn-light" id="show-modal" @click="showModal = true" v-on:click="select(t)">Izmeni</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Izmena tipa pregleda</h3>
        					<table slot="body" >
								<tbody>
										
									<tr>
										<td>Naziv:</td>
										<td><input class="form-control" type="text"  v-model="selected.naziv"/></td>
									</tr>
									<tr>
										<td>Opis:</td>
										<td><input  class="form-control" type="text" v-model = "selected.opis"/></td>
									</tr>
									<tr>
										<td>Cena:</td>
										<td><input  class="form-control" type="number" v-model = "selected.stavka.cena"/></td>
									</tr>
									
									
								</tbody>
								</table>
        					
        					<div slot="footer">
        						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="sacuvaj(selected)"> Sacuvaj </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false" v-on:click="restore(selected)"> Nazad </button>								
							</div>
						</modal>
				</td>
				
				<td><button class="btn btn-light" v-on:click="obrisi(t)">Obrisi</button></td>
		   </tr>
		   </tbody>
		</table>

		</div>
		<br>
		<br>
		<div class="float-right" style="width:65%">
		<h3> Novi tip pregleda </h3>
		<p>{{error}}</p>
		
		<table>
			<tbody>
			   <tr>			   
			   		<td>Naziv tipa pregleda: </td>
			   		<td><input class="form-control" id="naziv" type="text" v-model="tipPregleda.naziv"></td>
			   		<td style="color: red">{{nazivGreska}}</td>	
			   </tr>

			   <tr>			   		
			   		<td>Opis tipa pregleda: </td>
			   		<td><input class="form-control" id="opis" type="text" v-model="tipPregleda.opis"></td>
			   		<td style="color: red">{{opisGreska}}</td>
			   </tr>	
			    <tr>			   		
			   		<td>Cena tipa pregleda: </td>
			   		<td><input class="form-control" id="cena" type="number" v-model="stavkaCenovnika.cena"></td>
			   		<td style="color: red">{{cenaGreska}}</td>
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
			console.log(this.pretraga);
			if(this.pretraga){
				axios
		       	.get('api/tippregleda/search/'+ this.pretraga, { headers: { Authorization: 'Bearer ' + this.token }})
		       	.then(response => (this.tipoviPregleda = response.data));	
			}	
		},
		select : function(s){
			this.selectedBackup.naziv = s.naziv;
			this.selectedBackup.opis = s.opis;
			this.selectedBackup.cena = s.stavka.cena;
			this.selected = s;

		},
		restore: function(s){
			s.naziv = this.selectedBackup.naziv;
			s.opis = this.selectedBackup.opis;
			s.stavka.cena = this.selectedBackup.cena;
		},
		obrisi: function(s){
			axios
			.delete('api/tippregleda/'+s.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				 axios
			       	.get('api/tippregleda/all', { headers: { Authorization: 'Bearer ' + this.token }})
			       	.then(response => (this.tipoviPregleda = response.data));
			}).catch((res)=>{
				
				console.log('Neuspesno brisanje');
				alert('Ne mozete obrisati ovaj tip pregleda.');
			});
			
		},
		sacuvaj: function(s){
			axios
			.put('api/tippregleda/'+s.id, s, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('Uspesna izmena');
				if(s.stavka.cena != this.selectedBackup.cena){
					axios
					.post('api/stavkacenovnika/'+s.naziv, {cena: s.stavka.cena}, { headers: { Authorization: 'Bearer ' + this.token }})
					.then((res)=>{
						console.log('Uspesno');
						axios
				       	.get('api/tippregleda/all', { headers: { Authorization: 'Bearer ' + this.token }})
				       	.then(response => (this.tipoviPregleda = response.data));
					}).catch((res)=>{
						console.log('Neuspesna izmena');
					})
				}
				
			}).catch((res)=>{
				this.restore(s);
				alert('Ne mozete menjati ovaj tip pregleda!');
				console.log('Neuspesna izmena');
			});

			
		},
		validacija: function(){
			this.nazivGreska = '';
			this.opisGreska = '';
			
			if(!this.tipPregleda.naziv)
				this.nazivGreska = 'Naziv je obavezno polje!';

			if(!this.tipPregleda.opis)
				this.opisGreska = 'Opis je obavezno polje!';
			if(!this.stavkaCenovnika.cena)
				this.cenaGreska = 'Cena je obavezno polje!';
			if(this.tipPregleda.naziv && this.tipPregleda.opis && this.stavkaCenovnika.cena){
				return 0;
			}
			return 1;
			
		},
		dodaj : function(){	
			this.error = '';
			if(this.validacija()==1)
				return;
			
			axios
			.post('api/tippregleda', this.tipPregleda, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				axios
				.post('api/stavkacenovnika/'+this.tipPregleda.naziv, this.stavkaCenovnika, { headers: { Authorization: 'Bearer ' + this.token }})
				.then((res)=>{
					console.log('Uspesno');
					axios
			       	.get('api/tippregleda/all', { headers: { Authorization: 'Bearer ' + this.token }})
			       	.then(response => (this.tipoviPregleda = response.data));
				}).catch((res)=>{
					this.error = 'Neuspesno dodavanje';
				})
			}).catch((res)=>{
				this.error = 'Vec postoji pregled sa istim imenom';
				return;
			});
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
		           	.get('api/tippregleda/all', { headers: { Authorization: 'Bearer ' + this.token }})
		           	.then(response => (this.tipoviPregleda = response.data));	    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		 
	}

});