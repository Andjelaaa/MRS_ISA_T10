Vue.component('overa', {

	data: function(){
		return{	
			medicinska_sestra:{},
			uloga: '',
			tipZahteva:'',
			opis:'',
			datPocetka:'',
			datKraja:'',
			recepti:{id:'', lek:{id:'',naziv:'',sifra:''}, imePacijenta:'', prezimePacijenta:''}
		}
	}, 
	
	template: `
	<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/med_sestra_pocetna">Pocetna</a>
		
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item">
		        <a class="nav-link" href="#/pacijenti">Pacijenti</a>
		      </li>
		      
		      <li class="nav-item">
		        <a class="nav-link" href="#/odmor">Zahtev za godisnji odmor/odsustvo</a>
		      </li>
		       <li class="nav-item">
		        <a class="nav-link" href="#/overa">Overa recepata</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/kalendarr">Radni kalendar</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#/medsestra">Profil: {{medicinska_sestra.ime}} {{medicinska_sestra.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		<div class="float-left" style="margin: 20px">
			<h3> Overite recepte </h3>
		<table class="table">
			<tbody>
				<table class="table table-sm table-hover table-light " >
				   <tr>		   		
				   		<th>Id recepta</th>
						<th>Lekovi</th>
						<th>Pacijent</th>
				   		<th></th>
				   </tr>
				  
				   <tr  v-for="r,ind in recepti">
				   		<td>{{r.id}}</td>
				   		<td>
				   			<p v-for="l in r.lek">{{l.naziv}}</p>
						</td>
						<td>{{r.imePacijenta}} {{r.prezimePacijenta}}</td>   
				   		<td><button v-on:click = "overa(r, ind)" class="btn btn-success">Overi</button></td>
				   </tr>
				 
				   </table>
				   </tbody>
			</table>
				
				
		</div>
	</div>
	
	`, methods : {
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		overa: function(recept, index){
			console.log(recept.lek.length);
			axios
	        .put('api/recept/izmeni/'+this.medicinska_sestra.email, recept,{ headers: { Authorization: 'Bearer ' + this.token }})
	        .then((response) => {
	          this.recepti.splice(index, 1);
	      	  alert("Uspesno ste overili recept");
	        })
	        .catch((response)=> {alert("Recept je vec overen!");});
			
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
		    	if (this.uloga != "ROLE_MED_SESTRA") {
		    		this.$router.push('/');
				}
				else{
					axios
					.get('api/recept/neovereni/' + this.medicinska_sestra.email,{ headers: { Authorization: 'Bearer ' + this.token }})
					.then((response) => {
					this.recepti = response.data;
					}).catch(response=>{alert("Doslo je do greske");});
				}
		    })
		    .catch((response)=> { console.log(error);});
		    
	    })
	    .catch((response)=>{ this.$router.push('/'); });	 
		
		
	}
	
	
});