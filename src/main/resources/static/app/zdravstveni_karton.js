Vue.component('zdravstveni-karton', {
	data: function(){
		return{
			pregledi: null,
			lekovi: null,
			pacijent: {},
			uloga: '',
			isHiddenDijagnoza: false,
			isHiddenLekovi: false
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
		<h2> Istorija bolesti <button  class="btn btn-light" @click='isHiddenDijagnoza = !isHiddenDijagnoza'>+/-</button> </h2>
		
		<div v-show='isHiddenDijagnoza'>
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum</th>
			<th>Dijagnoza</th>
			<th>Lekar</th>
			</tr>
			
			<tr v-for="(p, index) in pregledi">
				<td>{{p.datum | formatOnlyDate}}</td>
				<td>{{p.dijagnozaNaziv}}</td>
				<td>{{p.lekar}}</td>
			</tr>
		</table>
		</div>
		
		<h2> Prepisani lekovi <button  class="btn btn-light" @click='isHiddenLekovi = !isHiddenLekovi'>+/-</button> </h2>
		
		<div v-show='isHiddenLekovi'>
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum</th>
			<th>Lek</th>
			<th>Lekar</th>
			</tr>
			
			<tr v-for="(p, index) in lekovi">
				<td>{{p.datum | formatOnlyDate}}</td>
				<td>{{p.dijagnozaNaziv}}</td>
				<td>{{p.lekar}}</td>
			</tr>
		</table>
		</div>
		
	
	</div>
	`, 
	
	methods : {
		
		
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		}
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
		    		.get('api/dijagnoze/pacijent', { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.pregledi = res.data;
		    		}),
		    		axios
		    		.get('api/lekovi/pacijent', { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.lekovi = res.data;
		    		})
		    		
		    	}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });
	}

});