Vue.component('superprofil', {

	data: function(){
		return{
			klinike:[],
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
		
		<table  class="table  table-light ">
			<thead>
				<th>Naziv</th>
				<th>Adresa</th>
				<th>Opis</th>
				<th>Email</th>
				<th>Kontakt</th>
				<th></th>
			</thead>
			<tbody>
				<tr v-for="k in klinike">
				<td>{{k.naziv}}</td>
				<td>{{k.adresa}}</td>
				<td>{{k.opis}}</td>
				<td>{{k.emailKlinike}}</td>
				<td>{{k.kontaktKlinike}}</td>
				<td><button  v-on:click= "metoda(k.id)"  class="btn btn-light"> Dodaj admina </button></td>	
				</tr>			

			</tbody>	
		</table>

		</div>
	
	`,
	methods: {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		metoda:function(id){
			this.$router.push('/dadmin/' + id);
		}
	}
	, mounted(){
		
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
			      	.get('api/klinika/all',{ headers: { Authorization: 'Bearer ' + this.token }})
			      	.then(response => {
			      		this.klinike = response.data;			      		
			      	})
			        .catch(function (error) { console.log('Greska sa dobavljanjem') });	
				}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });	 
	}

});