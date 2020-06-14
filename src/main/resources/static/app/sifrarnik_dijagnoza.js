Vue.component('sifrarnik2', {
	data: function(){
		return{
			dijagnoze:{},
			naziv:'',
			sifra:'',
			greska:'',
			greska1:'',
			greska2:'',
			showModal: false,
			selected: {naziv:'', sifra:''},
			selectedBackup: {naziv:'', sifra:''},
			token:'',
			uloga:''
		}
	}, 
	
	template: `
		<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/sprofil">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/regklinika">Registruj kliniku</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/odobri_zahtev">Zahtevi za registraciju</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/sifrarnik1">Sifrarnik lekova</a>
		      </li>
		       <li class="nav-item">
		        <a class="nav-link" href="#/sifrarnik2">Sifrarnik dijagnoza</a>
			  </li>
			   <li class="nav-item">
		        <a class="nav-link" href="#/dodajsa">Dodaj super admina </a>
		      </li>
		    </ul>
		     <form class="form-inline my-2 my-lg-0">     
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>

		
		<div class="float-left">
		<h1>Sifrarnik dijagnoza </h1>
		<table class="table table-hover table-light" >
		   <tr>		   		
		   		<th>Naziv</th>
		   		<th>Sifra</th>
		   		<th></th>
		   </tr>
		  
		   <tr  v-for="l in dijagnoze">
		   		<td>{{l.naziv}}</td>
		   		<td>{{l.sifra}}</td>
		   		<td>		   		
			   		<button class="btn btn-light" id="show-modal" @click="showModal = true" v-on:click="select(l)">Izmeni</button>
						<modal v-if="showModal" @close="showModal = false">
	    
	    					<h3 slot="header">Izmena leka</h3>
	    					<table slot="body" class="table table-hover table-light">
								<tbody>
										
									<tr>
										<td>Naziv</td>
										<td><input class="form-control" type="text"  v-model="selected.naziv"/></td>
									</tr>
									<tr>
										<td>Sifra</td>
										<td><input  class="form-control" type="text" v-model = "selected.sifra"/></td>
									</tr>									
								</tbody>
								</table>
	    					
	    					<div slot="footer">
	    						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="save()"> Sacuvaj izmene </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false" v-on:click="restore(selected)"> Odustani </button>								
							</div>
						</modal>
				</td>
		   </tr>
		    
		   </table>
		   </div>
		   <div class="float-right" style="width:45%">
		   <h1>Unesite novu dijagnozu</h1>
		   <table class="table table-hover table-light ">

		   <tr>		   		
		   		<td>Naziv</td>
		   		<td><input class="form-control" id="naziv" type="text" v-model="naziv"></td>
		   		<td style="color: red">{{greska1}}</td>
		   </tr>
		  
		   <tr>
		   		<td>Sifra</td>
		   		<td><input class="form-control" id="sifra" type="text" v-model="sifra"></td>
		   		<td style="color: red">{{greska2}}</td>
		   </tr>
		    <tr>
		   		<td></td>
		   		<td><button v-on:click = "napraviDijagnozu()" class="btn btn-light">Dodaj dijagnozu</button></td>	   
		   		
		   </tr>
		   
		  </table>
		  {{greska}}
		  </div>
		</div>
	
	`, 
	methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		validacija : function(){
			this.greska1 = '';
			this.greska2 = '';
			
			if(!this.naziv)
				this.greska1 = 'Naziv je obavezno polje!';

			if(!this.sifra)
				this.greska2 = 'Sifra je obavezno polje!';
			if(this.naziv && this.sifra){
				return 0;
			}
			return 1;
			
			
		},
		napraviDijagnozu: function(){
			this.greska = '';
			if(this.validacija()==1)
				return;
			this.greska1 = '';
			this.greska2 = '';
			var newDijagn ={ "naziv": this.naziv, "sifra": this.sifra};
			axios
			.post('api/dijagnoze', newDijagn,{ headers: { Authorization: 'Bearer ' + this.token }})
			.then((response)=>{
				this.dijagnoze.push(newDijagn);
				this.naziv ='';
				this.sifra='';
				this.greska='';
			}).catch((response)=>{
				this.greska = 'Dijagnoza vec postoji';
			});
		},
		select : function(l){
			this.selectedBackup.naziv = l.naziv;
			this.selectedBackup.sifra = l.sifra;
			this.selected = l;

		},
		restore: function(l){
			l.naziv = this.selectedBackup.naziv;
			l.sifra = this.selectedBackup.sifra;
		},
		save: function(){
			axios
			.post('api/dijagnoze/izmena', {nova:this.selected, stara:this.selectedBackup},{ headers: { Authorization: 'Bearer ' + this.token }})
			.then((response)=>{
				 this.naziv ='';
				 this.sifra='';
				 this.greska = '';
			}).catch((response)=>{
				 this.naziv ='';
				 this.sifra='';
				 this.selected.naziv = this.selectedBackup.naziv;
				 this.selected.sifra = this.selectedBackup.sifra;
				 this.greska = 'Dijagnoza vec postoji';
			});
		}
		
		
	},
	mounted(){
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
	    .then(response => { this.medicinska_sestra = response.data;
		    axios
			.put('/auth/dobaviulogu', this.medicinska_sestra, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_ADMIN_KLINICKOG_CENTRA") {
		    		router.push('/');
				}
				else{
					 axios
      			 	.get('api/dijagnoze/all', { headers: { Authorization: 'Bearer ' + this.token }},{ headers: { Authorization: 'Bearer ' + this.token }})
       				.then(response => (this.dijagnoze = response.data));	      		
				}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });	
		
	}

});