Vue.component('profiladmin', {

	data: function(){
		return{	
			admin:{},
			uloga: '',
			token: '',
			showModal:false,
			showModal2:false,
			selected:{},
			selectedBackup:{},
			lozinka: 'asdf',
			novaLozinka: ''
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
		        <a class="nav-link" href="#/zahtevioo">Zahtevi za odmor/odsustvo</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link active" href="#/profiladmin">Profil: {{admin.ime}} {{admin.prezime}}</a>
		      </li>
		      
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      <!--input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"-->
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>		</br>
		</table>
		<div class="float-left" style="margin: 20px">
		<table class="table">
			<tbody>
			   <tr>		   		
			   		<td>Ime</td>
			   		<td>{{admin.ime}}</td>
			   </tr>
			  
			   <tr>
			   		<td>Prezime</td>
			   		<td>{{admin.prezime}}</td>
			   </tr>
			    <tr>
			   		<td>Email</td>
			   		<td>{{admin.email}}</td>	  
			   	</tr>
			   	<tr>
			   		<td>Adresa</td>
			   		<td>{{admin.adresa}}</td>
			   	</tr>
			   	<tr>
			   		<td>Grad</td>
			   		<td>{{admin.grad}}</td>	   
			   	</tr>
				<tr>
			   		<td>Drzava</td>
			   		<td>{{admin.drzava}}</td>	  
			   	</tr>
			   	<tr>
			   		<td>Kontakt</td>
			   		<td>{{admin.kontakt}}</td>
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
			this.selectedBackup.ime = this.admin.ime;
			this.selectedBackup.prezime = this.admin.prezime;
			this.selectedBackup.email = this.admin.email;
			this.selectedBackup.adresa = this.admin.adresa;
			this.selectedBackup.grad = this.admin.grad;
			this.selectedBackup.drzava = this.admin.drzava;
			this.selectedBackup.kontakt = this.admin.kontakt;
			this.selected = this.admin;

		},
		restore: function(){
			this.admin.ime = this.selectedBackup.ime;
			this.admin.prezime = this.selectedBackup.prezime;
			this.admin.email = this.selectedBackup.email;
			this.admin.adresa = this.selectedBackup.adresa;
			this.admin.grad = this.selectedBackup.grad;
			this.admin.drzava = this.selectedBackup.drzava;
			this.admin.kontakt = this.selectedBackup.kontakt;
		},
		sacuvaj: function(){
			if(this.admin.ime != '' && this.admin.prezime != '' && this.admin.email != '' 
				&& this.admin.adresa != '' && this.admin.grad != ''
					&& this.admin.drzava !='' && this.admin.kontakt != ''){
				axios
				.put('api/admini/'+this.admin.id, this.admin, { headers: { Authorization: 'Bearer ' + this.token }})
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
			.get('api/admini/promenaLozinke/'+this.admin.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
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
	    .then(response => { this.admin = response.data;
		    axios
			.put('/auth/dobaviulogu', this.admin, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_ADMIN_KLINIKE") {
		    		//router.push('/');
		    		console.log(this.uloga);
		    	}
		    })
		    .catch(function (error) { console.log(error);});
		    
	    })
	    .catch(function (error) { router.push('/'); });	 
	}
	
});