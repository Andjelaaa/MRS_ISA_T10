Vue.component('dpregled', {
	data: function(){
		return{
			admin: {},
			uloga: '',
			pregled: {tipPregleda: null, lekar: null, sala: null, popust: 0},
			lekari: null,
			sale: null, 
			tipoviPregleda: null,
			tipPregleda: {},
			lekar: {},
			sala: {},			
			
			datumVremeGreska: '',
			tipPregledaGreska: '',
			trajanjeGreska: '',
			salaGreska: '',
			lekarGreska: '',
			popustGreska: '',
			error: ''
				
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
		        <a class="nav-link" href="#/tipovipregleda">Tipovi pregleda</a>
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
		<h3 style="margin:20px">Novi predefinisani pregled</h3>
		<p>{{error}}</p>
		<table style="margin:20px">
			<tbody>
				<tr>			   
			   		<td>Datum i vreme: </td>
			   		<td><input class="form-control" id="datumvreme" type="datetime-local" v-model="pregled.datumVreme"></td>
			   		<td style="color: red">{{datumVremeGreska}}</td>
			   </tr>			   
			   <tr>			   
			   		<td>Tip pregleda: </td>
					<td>
						<select class="form-control" id="selectTP" v-model="tipPregleda.naziv" v-on:change="tipPregledaa">
							<option v-for="t in tipoviPregleda" :value="t.naziv">{{t.naziv}}</option>
						</select>
					</td>			   		
					<td style="color: red">{{tipPregledaGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Trajanje: </td>
			   		<td><input class="form-control" id="trajanje" type="number" v-model="pregled.trajanje"></td>
			   		<td style="color: red">{{trajanjeGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Sala: </td>
					<td>
						<select class="form-control" id="selectSala" v-model="sala.naziv">
							<option v-for="s in sale" :value="s.naziv">{{s.naziv}}</option>
						</select>
					</td>			   		
					<td style="color: red">{{salaGreska}}</td>
			   </tr>
			   
			   <tr>			   
			   		<td>Lekar: </td>
					<td>
						<select class="form-control" id="selectLekar" v-model="lekar.email">
							<option v-for="l in lekari" :value="l.email">{{l.ime}} {{l.prezime}}</option>
						</select>
					</td>			   		
					<td style="color: red">{{lekarGreska}}</td>
			   </tr>
			   <tr>			   
			   		<td>Popust: </td>
			   		<td><input class="form-control" id="popust" type="number" v-model="pregled.popust"></td>
			   		<td style="color: red">{{popustGreska}}</td>
			   </tr>
			   
			   
			    <tr>
			   
			   		<td><button v-on:click="nazad()" class="btn btn-light">Nazad</button></td>
			   		<td><button v-on:click="dodaj()" class="btn btn-light">Dodaj</button></td>
			   		<td></td>
			   </tr>
		   </tbody>
		</table>
		
	
		</div>
	
	`, 
	methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
	    },
		nazad : function(){
			this.$router.push('/admin')
			return;
		},
		validacija: function(){
			this.datumVremeGreska = '';
			this.trajanjeGreska = '';
			this.tipPregledaGreska = '';
			this.salaGreska ='';
			this.lekarGreska = '';

			
			if(!this.tipPregleda.naziv)
				this.tipPregledaGreska = 'Tip pregleda je obavezno polje!';
			if(!this.pregled.trajanje)
				this.trajanjeGreska = 'Trajanje je obavezno polje!';
			if(!this.pregled.datumVreme)
				this.datumVremeGreska = 'Datum i vreme je obavezno polje!';
			if(!this.lekar.email)
				this.lekarGreska = 'Lekar je obavezno polje!';
			if(!this.sala.naziv)
				this.salaGreska = 'Sala je obavezno polje!';
			if(!this.pregled.popust)
				this.popustGreska = 'Popust je obavezno polje!';
			if(this.pregled.trajanje < 0 || this.pregled.trajanje > 60){
				this.trajanjeGreska = 'Trajanje mora biti izmedju 0 i 60 minuta!';
				return 1;
			}


			if(this.tipPregleda.naziv && this.pregled.trajanje && this.pregled.datumVreme && this.lekar.email && this.sala.naziv && this.pregled.popust){
				return 0;
			}
			return 1;
			
		},
		 tipPregledaa: function() {
	    	console.log(this.tipPregleda.naziv);
	    	 axios
	          .get('api/tippregleda/'+this.tipPregleda.naziv+'/lekari/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
	          .then(res => {
	        	  this.lekari = res.data;

	          })
	    },
		dodaj : function(){	
			
			if(this.validacija()==1)
				return;
			
			this.error = '';
			this.pregled.tipPregleda = this.tipPregleda;
			this.pregled.sala = this.sala;
			this.pregled.lekar = this.lekar;
			
			
			axios
			.post('api/pregled', this.pregled, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('uspesno');
				alert('Uspesno!');
				this.pregled = {};
				this.lekar = {};

			}).catch((res)=>{
				this.error = 'Greska pri dodavanju, proverite unete podatke!';
			}
				
			)
		}
		
	},
	mounted () {
		
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
		             .then(res => {
		           	  this.tipoviPregleda = res.data;

		             })
		             
		              axios
		             .get('api/lekar/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		             .then(res => {
		           	  this.lekari = res.data;

		             })
		             
		              axios
		             .get('api/sala/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		             .then(res => {
		           	  this.sale = res.data;

		             })		    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
          
    },

});