Vue.component('lekarzp', {

	data: function(){
		return{	
			lekar:{},
			uloga: '',
			pregledi: [],
			showModal: false,
		}
	}, 
	
	template: `
	<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/lekar">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/pacijenti">Pacijenti</a>
		      </li>
		      
		      <li class="nav-item">
		        <a class="nav-link" href="#/odmor">Zahtev za godisnji odmor/odsustvo</a>
		      </li>
		      <li class="nav-item">
		        <a  class="nav-link" href="#/kalendarlekar">Radni kalendar</a>
		       </li>
		       <li class="nav-item">
		        <a  class="nav-link active" href="#/lekar/pregledi">Zakazani pregledi</a>
		       </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/profil">Profil: {{lekar.ime}} {{lekar.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		<div class="float-left" style="margin: 20px">
		<h3> Otkazivanje termina </h3>
		<p> Otkazivanje je dozvoljeno ukoliko pregled nije zakazan za manje od 24h </p>
		
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th></th>
			</tr>
			
			<tr v-for="(p, index) in pregledi">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				<td><button class="btn btn-light" id="show-modal" @click="showModal = true" >Otkazi</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Potvrdi otkazivanje</h3>
        					<p slot="body">Da li ste sigurni?</p>
        					<div slot="footer">
        						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="otkazi(p, index)"> Potvrdi </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false"> Odustani </button>								
							</div>
						</modal></td>
			</tr>
		</table>
		</div>
	</div>
	
	`, methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
	},
	otkazi : function(p, i){
		// upit da li je siguran - prozor sa informacijama
		axios
          .post('api/pregled/otkazi/'+p.id+'/'+p.pacijent.id, { headers: { Authorization: 'Bearer ' + this.token }})
          .then(res => {
        	this.pregledi.splice(i,1);
        	console.log('uspesno');
        	// poruka o uspesnom otkazivanju
          })
          .catch((res)=>{
        	  alert("Ne mozete otkazati pregled!");
        	  console.log('neuspesno');
          })
	}
	
},
mounted(){
	
	this.token = localStorage.getItem("token");
	axios
	.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
    .then(response => { this.lekar = response.data;
	    axios
		.put('/auth/dobaviulogu', this.lekar, { headers: { Authorization: 'Bearer ' + this.token }} )
	    .then(response => {
	    	this.uloga = response.data;
	    	if (this.uloga != "ROLE_LEKAR") {
	    		this.$router.push('/');
	    	}else{
	    		axios
	    		.get('api/pregled/zakazaniZaLekara/'+this.lekar.id, { headers: { Authorization: 'Bearer ' + this.token }})
	    		.then(res => {
	    			this.pregledi = res.data;
	    		})
	    		
	    	}
	    })
	    .catch(function (error) { console.log(error);});
	    
    })
    .catch(function (error) { this.$router.push('/'); });	 
}

});