Vue.component('izvestaji', {

	data: function(){
		return{	
			admin:{},
			klinika: {},
			datum: {pocetak:'', kraj:''},
			prihodi: 0.0,
			grafik: {},
			nivo: 'nedeljni', 

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
		        <a class="nav-link" href="#/sale">Sale</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/tipovipregleda">Tipovi pregleda</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/dpregled">Novi termin za pregled</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link active" href="#/izvestaji">Izvestaji</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/zahtevipo">Zahtevi za pregled/operaciju</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/zahtevioo">Zahtevi za odmor/odsustvo</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <!--input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"-->
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		<div class="float-left" style="margin-left: 20px">
			<h3> Prihodi klinike: </h3>
		<table class="table">
			<tbody>
			
			   <tr>
			   		
			   		<td>Od: </td>
			   		<td><input type="date" class="form-control" id="od" v-model="datum.pocetak"></td>
			   </tr>
			   <tr>
			   
			   		<td>Do: </td>
			   		<td><input type="date" class="form-control" id="od" v-model="datum.kraj"></td>	
			   </tr>

			   <tr>			   		
			   		<td></td>
			   		<td><button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="prikazi()">Prikazi</button></td>
			   </tr>
			   
			   <tr>			   		
			   		<td>Prihodi za period su: </td>
			   		<td>{{prihodi}} RSD</td>
			   </tr>

			    
		   </tbody>
		</table>
		</div>
		<div class="float-right" style="width:60%">
			<h3> Grafik odrzanih pregleda: </h3>
		<table class="table">
			<tbody>
			
			   <tr>			   		
			   		<td>Nivo pregleda grafika: </td>
			   		<td><select v-model="nivo" class="form-control">
			   				<option>dnevni</option>
			   				<option>nedeljni</option>
			   				<option>mesecni</option>
			   			</select>
			   		</td>
			   </tr>
			    
		   </tbody>
		</table>
		<column-chart :data="grafik" :colors="['#ccffcc', '#666']"></column-chart>
		</div>

	</div>
	
	`, 
	methods : {
		odjava : function(){
				localStorage.removeItem("token");
				this.$router.push('/');
		},
		prikazi: function(){
			axios
	      	.post('api/klinika/prihodi/'+this.klinika.id, this.datum, { headers: { Authorization: 'Bearer ' + this.token }})
	      	.then(response => {
	      		this.prihodi = response.data;
	      		
	      	})
	        .catch(function (error) { console.log('greska'); });	
			
		}
		
	},
	watch: {
		nivo: function(){
			if (this.nivo != '') {
				axios.get('api/klinika/grafik/' + this.nivo +'/'+this.klinika.id, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					this.grafik = response.data;
					
				})
				.catch(response => {
					console.log('greska');
				});	
			}
				
		},

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
			      	.get('api/admini/klinika', { headers: { Authorization: 'Bearer ' + this.token }} )
			      	.then(response => {
			      		this.klinika = response.data;
			      		axios.get('api/klinika/grafik/' + this.nivo +'/'+this.klinika.id, { headers: { Authorization: 'Bearer ' + this.token }})
			    		.then(response => {
			    			this.grafik = response.data;
			    			
			    		})
			      		
			      	})
			        .catch(function (error) { this.$router.push('/'); });		    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
	}		      		
	 

});