Vue.component('profilpacijent', {

	data: function(){
		return{	
			pacijent:{},
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
		        <a class="nav-link" href="#/">Zdravstveni karton</a>
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
		</table>
		<div class="float-left" style="margin: 20px">
		<table class="table">
			<tbody>
			   <tr>		   		
			   		<td>Ime</td>
			   		<td>{{pacijent.ime}}</td>
			   </tr>
			  
			   <tr>
			   		<td>Prezime</td>
			   		<td>{{pacijent.prezime}}</td>
			   </tr>
			    <tr>
			   		<td>Email</td>
			   		<td>{{pacijent.email}}</td>	  
			   	</tr>
			   	<tr>
			   		<td>LBO</td>
			   		<td>{{pacijent.lbo}}</td>
			   	</tr>
			   	<tr>
			   		<td>Adresa</td>
			   		<td>{{pacijent.adresa}}</td>
			   	</tr>
			   	<tr>
			   		<td>Grad</td>
			   		<td>{{pacijent.grad}}</td>	   
			   	</tr>
				<tr>
			   		<td>Drzava</td>
			   		<td>{{pacijent.drzava}}</td>	  
			   	</tr>
			   	<tr>
			   		<td>Kontakt</td>
			   		<td>{{pacijent.kontakt}}</td>
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
										<td>{{selected.email}}</td>
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
			this.selectedBackup.ime = this.pacijent.ime;
			this.selectedBackup.prezime = this.pacijent.prezime;
			this.selectedBackup.email = this.pacijent.email;
			this.selectedBackup.adresa = this.pacijent.adresa;
			this.selectedBackup.grad = this.pacijent.grad;
			this.selectedBackup.drzava = this.pacijent.drzava;
			this.selectedBackup.kontakt = this.pacijent.kontakt;
			this.selected = this.pacijent;

		},
		restore: function(){
			this.pacijent.ime = this.selectedBackup.ime;
			this.pacijent.prezime = this.selectedBackup.prezime;
			this.pacijent.email = this.selectedBackup.email;
			this.pacijent.adresa = this.selectedBackup.adresa;
			this.pacijent.grad = this.selectedBackup.grad;
			this.pacijent.drzava = this.selectedBackup.drzava;
			this.pacijent.kontakt = this.selectedBackup.kontakt;
		},
		sacuvaj: function(){
	
			if(this.pacijent.ime != '' && this.pacijent.prezime != '' && this.pacijent.email != '' 
				&& this.pacijent.adresa != '' && this.pacijent.grad != ''
					&& this.pacijent.drzava !='' && this.pacijent.kontakt != ''){
				
			// provera da li je sve ok
			//if(true){
				axios
				.put('api/pacijent/'+this.pacijent.id, this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }})
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
			.get('api/pacijent/promenaLozinke/'+this.pacijent.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
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
	    .then(response => { this.pacijent = response.data;
		    axios
			.put('/auth/dobaviulogu', this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_PACIJENT") {
		    		router.push('/');
		    	}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });	 
	}
	
});