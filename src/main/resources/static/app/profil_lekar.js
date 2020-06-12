Vue.component('profillekar', {

	data: function(){
		return{	
			lekar:{},
			uloga: '',
			token: '',
			showModal:false,
			showModal2:false,
			selected:{},
			selectedBackup:{},
			lozinka: '',
			novaLozinka: ''
		}
	}, 
	
	template: `
	<div>
		<nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <a class="navbar-brand" href="#/lekar_pocetna">Pocetna</a>
		
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
		        <a  class="nav-link" href="#/lekar/pregledi">Zakazani pregledi</a>
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
		</table>
		<div class="float-left" style="margin: 20px">
		<table class="table">
			<tbody>
			   <tr>		   		
			   		<td>Ime</td>
			   		<td>{{lekar.ime}}</td>
			   </tr>
			  
			   <tr>
			   		<td>Prezime</td>
			   		<td>{{lekar.prezime}}</td>
			   </tr>
			    <tr>
			   		<td>Email</td>
			   		<td>{{lekar.email}}</td>	  
			   	</tr>
			   	<tr>
			   		<td>Adresa</td>
			   		<td>{{lekar.adresa}}</td>
			   	</tr>
			   	<tr>
			   		<td>Grad</td>
			   		<td>{{lekar.grad}}</td>	   
			   	</tr>
				<tr>
			   		<td>Drzava</td>
			   		<td>{{lekar.drzava}}</td>	  
			   	</tr>
			   	<tr>
			   		<td>Kontakt</td>
			   		<td>{{lekar.kontakt}}</td>
			   	</tr>
			   	<tr>
			   		<td><button class="btn btn-light float-right" id="show-modal2" @click="showModal2 = true">Promena lozinke</button>
						<modal v-if="showModal2" @close="showModal2 = false">
        
        					<h3 slot="header">Izmena profila</h3>
        					<table slot="body" >
								<tbody>

									<tr>
										<td>Nova lozinka:</td>
										<td><input  class="form-control" type="password" v-model = "novaLozinka"/></td>
									</tr>

									
								</tbody>
								</table>
        					
        					<div slot="footer">
        						<button @click="showModal2=false" style="margin:5px;" class="btn btn-success" v-on:click="sacuvajLozinku()"> Sacuvaj </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal2=false"> Nazad </button>								
							</div>
						</modal></td>
			   		
			   		
			   		
			   		<td>
			   		<button class="btn btn-light float-right" id="show-modal" @click="showModal = true" v-on:click="select()">Izmeni</button>
						<modal v-if="showModal" @close="showModal = false">
        
        					<h3 slot="header">Izmena profila</h3>
        					<table slot="body" >
								<tbody>
										
									<tr>
										<td>Ime:</td>
										<td><input class="form-control" type="text"  v-model="selected.ime"/></td>
									</tr>
									<tr>
										<td>Prezime:</td>
										<td><input  class="form-control" type="text" v-model = "selected.prezime"/></td>
									</tr>
									<tr>
										<td>Email:</td>
										<td><input  class="form-control" type="text" v-model = "selected.email"/></td>
									</tr>
									<tr>
										<td>Adresa:</td>
										<td><input  class="form-control" type="text" v-model = "selected.adresa"/></td>
									</tr>
									<tr>
										<td>Grad:</td>
										<td><input  class="form-control" type="text" v-model = "selected.grad"/></td>
									</tr>
									<tr>
										<td>Drzava:</td>
										<td><input  class="form-control" type="text" v-model = "selected.drzava"/></td>
									</tr>
									<tr>
										<td>Kontakt:</td>
										<td><input  class="form-control" type="text" v-model = "selected.kontakt"/></td>
									</tr>
									
								</tbody>
								</table>
        					
        					<div slot="footer">
        						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="sacuvaj()"> Sacuvaj </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false" v-on:click="restore()"> Nazad </button>								
							</div>
						</modal>
						</td>
			   	
			   	</tr>
			   	
			   	<tr>
			   		
			   	
			   	
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
		select : function(s){
			this.selectedBackup.ime = this.lekar.ime;
			this.selectedBackup.prezime = this.lekar.prezime;
			this.selectedBackup.email = this.lekar.email;
			this.selectedBackup.adresa = this.lekar.adresa;
			this.selectedBackup.grad = this.lekar.grad;
			this.selectedBackup.drzava = this.lekar.drzava;
			this.selectedBackup.kontakt = this.lekar.kontakt;
			this.selected = this.lekar;

		},
		restore: function(){
			this.lekar.ime = this.selectedBackup.ime;
			this.lekar.prezime = this.selectedBackup.prezime;
			this.lekar.email = this.selectedBackup.email;
			this.lekar.adresa = this.selectedBackup.adresa;
			this.lekar.grad = this.selectedBackup.grad;
			this.lekar.drzava = this.selectedBackup.drzava;
			this.lekar.kontakt = this.selectedBackup.kontakt;
		},
		sacuvaj: function(){
			if(this.lekar.ime != '' && this.lekar.prezime != '' && this.lekar.email != '' 
				&& this.lekar.adresa != '' && this.lekar.grad != ''
					&& this.lekar.drzava !='' && this.lekar.kontakt != ''){
				axios
				.put('api/lekar/'+this.lekar.id, this.lekar, { headers: { Authorization: 'Bearer ' + this.token }})
				.then((res)=>{
					console.log('Uspesna izmena');
				}).catch((res)=>{
					console.log('Neuspesna izmena');
					this.restore();
					alert('Neuspesna izmena');
				});
			}else{
				
				this.restore();
				alert('Neuspesna izmena');
			}
		},
		isprazni: function(){
			this.lozinka = '';
			this.novaLozinka = ''
		},
		sacuvajLozinku: function(){
			if(this.novaLozinka == ''){
				return;
			}
			
			axios
			.get('api/lekar/promenaLozinke/'+this.lekar.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((res)=>{
				console.log('Uspesna izmena');
				alert('Uspesna izmena lozinke');
			}).catch((res)=>{
				this.isprazni();
				console.log('Neuspesna izmenaaaa');
				alert('Neuspesna izmena');
			});
	
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
		    		router.push('/');
		    	}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });	 
	}
	
});