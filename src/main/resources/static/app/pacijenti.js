Vue.component('pacijenti', {

	data: function(){
		return{	
			korisnik:{},
			uloga: '',
			pacijenti: [],
			pretraga: {ime:'', prezime:'', lbo:''},
			currentSort:'ime',
		    currentSortDir:'asc'
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
		      <li class="nav-item" v-if="uloga=='ROLE_LEKAR'">
		        <a  class="nav-link" href="#/lekar/pregledi">Zakazani pregledi</a>
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
		<h3> Pacijenti </h3>
		Ime: <input  type="text" v-model="pretraga.ime" >
		Prezime: <input  type="text" v-model="pretraga.prezime">
		LBO: <input  type="text" v-model="pretraga.lbo">
		<button v-on:click = "pretrazi()" class="btn btn-light">Pretrazi</button>
		
		<table class="table table-hover table-light">		
		   <tr >		   		
		   		<th @click="sort('ime')" class="class1">Ime</th>
		   		<th @click="sort('prezime')" class="class1">Prezime</td>
		   		<th>Email adresa</th>
		   		<th>Kontakt</th>
		   		<th @click="sort('lbo')" class="class1">LBO</th>
		   		<th @click="sort('adresa')" class="class1">Adresa</th>
		   		<th></th>
		   </tr>
		  <tbody>
		   <tr v-for="s,i in sortedPacijenti">
		   		<td>{{s.ime}}</td>
		   		<td>{{s.prezime}}</td>
		   		<td>{{s.email}}</td>
		   		<td>{{s.kontakt}}</td>
		   		<td>{{s.lbo}}</td>
		   		<td>{{s.adresa}}, {{s.grad}}</td>
		   		<td><button class="btn btn-outline-success" v-on:click="nadjipacijenta(s,i)">Profil</button></td>		   		
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
		nadjipacijenta: function(s, i){
			this.$router.push('/pacijenti/'+s.lbo);			
		},
		pretrazi: function(){
			axios
	       	.post('api/pacijent/search/' + this.korisnik.email, this.pretraga, { headers: { Authorization: 'Bearer ' + this.token }})
	       	.then(response => (this.pacijenti = response.data));

		},
		sort:function(s) {
		    //if s == current sort, reverse
		    if(s === this.currentSort) {
		      this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
		    }
		    this.currentSort = s;
		  }
	
},
computed:{
	  sortedPacijenti:function() {
	    return this.pacijenti.sort((a,b) => {
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
    .then(response => { this.korisnik = response.data;
	    axios
		.put('/auth/dobaviulogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
	    .then(response => {
	    	this.uloga = response.data;
	    	if (this.uloga != "ROLE_LEKAR" && this.uloga != "ROLE_MED_SESTRA") {
	    		this.$router.push('/');
	    	}else{
	    		// dobavi pacijente
	    		axios
	           	.get('api/pacijent/all/'+this.korisnik.email, { headers: { Authorization: 'Bearer ' + this.token }})
	           	.then(response => (this.pacijenti = response.data));
	    	
	    	}
	    })
	    .catch(function (error) { console.log(error);});
	    
    })
    .catch(function (error) { router.push('/') });	 
}

});