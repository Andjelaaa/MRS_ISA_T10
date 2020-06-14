Vue.component('zahtevioo', {

	data: function(){
		return{	
			admin:{},
			klinika: {},
			zahtevi: [],
			showModal: false,
			obrazlozenje: '',
			obrazlozenjeGreska: '',
			selected: {},
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
		        <a class="nav-link active" href="#/zahtevioo">Zahtevi za odmor/odsustvo</a>
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
		<div class="float-left" style="margin-left: 20px">
			<h3> Zahtevi za odmore i odsustva </h3>
		<table class="table table-hover table-light">
			<thead>
				<th>Medicinski radnik</th>
				<th>Tip</th>
				<th>Pocetak</th>
				<th>Kraj</th>
				<th>Opis</th>
				<th></th>
				<th></th>
			
			</thead>
			<tbody>
			
			   <tr v-for="z in zahtevi">			   		
			   		<td v-if="z.lekar == null">{{z.sestra.ime}} {{z.sestra.prezime}}</td>
			   		<td v-else>{{z.lekar.ime}} {{z.lekar.prezime}}</td>
			   		<td>{{z.tip}}</td>
			   		<td>{{z.pocetak | formatDate}}</td>
			   		<td>{{z.kraj | formatDate}}</td>
			   		<td>{{z.opis}}</td>
			   		<td><button v-on:click="odobri(z)" class="btn btn-success my-2 my-sm-0">Odobri</button></td>
			   		<td><button id="show-modal" @click="showModal = true" v-on:click="select(z)" class="btn btn-danger my-2 my-sm-0">Odbij</button>
			   					   		
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Uneti razlog odbijanja</h3>
        					<table slot="body" >
								<tbody>
										
									<tr>
										<td><input class="form-control" type="text"  v-model="obrazlozenje"/></td>
										<td>{{obrazlozenjeGreska}}</td>
									</tr>
									
								</tbody>
								</table>
        					
        					<div slot="footer">
        						<button style="margin:5px;" class="btn btn-success" v-on:click="odbij(selected)"> Posalji </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false"> Nazad </button>								
							</div>
						</modal>
			   		
			   		
			   		</td>
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
		odobri: function(zahtev){
			axios
	      	.post('api/zahteviodsustvo/odobri', zahtev,{ headers: { Authorization: 'Bearer ' + this.token }})
	      	.then(response => {
	      		alert('Uspesno odobren zahtev! Mejl poslat!');
	      		axios
		      	.get('api/zahteviodsustvo/all/zahtevi/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
		      	.then(response => {
		      		this.zahtevi = response.data;			      		
		      	})
	      		console.log('uspesno');			      		
	      	})
	        .catch(function (error) { console.log('greska') });	
			
		},
		select: function(z){
			this.selected = z;
		},
		odbij: function(z){
			if(this.obrazlozenje ==''){
				this.obrazlozenjeGreska='Obavezno polje!';
				return;
			}
			this.showModal = false;
			axios
	      	.post('api/zahteviodsustvo/odbij/'+this.obrazlozenje, z,{ headers: { Authorization: 'Bearer ' + this.token }})
	      	.then(response => {
	      		alert('Zahtev odbijen! Mejl poslat!');
	      		axios
		      	.get('api/zahteviodsustvo/all/zahtevi/'+this.admin.id,{ headers: { Authorization: 'Bearer ' + this.token }})
		      	.then(response => {
		      		this.zahtevi = response.data;			      		
		      	})
	      		console.log('uspesno');			      		
	      	})
	        .catch(function (error) { console.log('greska') });
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
			      	.get('api/zahteviodsustvo/all/zahtevi/'+this.admin.id,{ headers: { Authorization: 'Bearer ' + this.token }})
			      	.then(response => {
			      		this.zahtevi = response.data;			      		
			      	})
			        .catch(function (error) { console.log('Greska') });	
		    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
	}		      		
	 

});