Vue.component('pacijent', {
	data: function(){
		return{
			pacijent: {},
			uloga: '',
			pregledi: [],
			operacije: [],
			isHidden: false,
			isHiddenOperacija: false
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
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Tip pregleda</th>
			<th>Lekar</th>
			<th>Sala</th>
			<th>Status</th>
			</tr>
			
			<tr v-for="(p, index) in pregledi">
				<td>{{p.datumVreme | formatDate}}</td>
				<td>{{p.trajanje}}</td>
				<td>{{p.tipPregleda.naziv}}</td>
				<td>{{p.lekar.ime}} {{p.lekar.prezime}}</td>
				
				<td v-if="p.sala != null">{{p.sala.broj}}</td>
				<td v-if="p.sala == null">/</td>
				<td>{{p.status}}</td>
				
			</tr>
		</table>
		</div>
		
		<h2> Zakazane operacije <button  class="btn btn-light" @click='isHiddenOperacija = !isHiddenOperacija'>+/-</button> </h2>
		
		<div v-show='isHiddenOperacija'>
		<table class="table table-hover table-light ">
			<tr>
			<th>Datum i vreme</th>
			<th>Trajanje</th>
			<th>Lekari</th>
			<th>Sala</th>
			<th>Status</th>
			</tr>
			
			<tr v-for="(o, index) in operacije">
				<td>{{o.datumVreme | formatDate}}</td>
				<td>{{o.trajanje}}</td>
				<td>{{o.lekar[0].ime}} {{o.lekar[0].prezime}}</td>
				
				<td v-if="o.sala != null">{{o.sala.broj}}</td>
				<td v-if="o.sala == null">/</td>
				<td>{{o.status}}</td>
			</tr>
		</table>
		</div>
	
	</div>
	`, 
	methods: {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		}
	},
	mounted(){
		
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
		    	else{
		    		axios
		    		.get('api/pregled/zakazaniPregledi', { headers: { Authorization: 'Bearer ' + this.token }})
		    		.then(res => {
		    			this.pregledi = res.data;
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