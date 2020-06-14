Vue.component('odobri_zaht', {

	data: function(){
		return{
			zahtevi:{},
			greska:'',
			opis:'',
			showModal: false,
			globInd: '',
			globZahtev:''
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
		
		
		<table class="table table-sm table-hover table-light " >
		   <tr>		   		
		   		<th>Email adresa</th>
		   		<th>Ime</th>
		   		<th>Prezime</th>
		   		<th>Adresa prebivalista</th>
		   		<th>Grad</th>
		   		<th>Drzava</th>
		   		<th>Broj telefona</th>
		   		<th>LBO</th>
		   		<th>Lozinka</th>
		   		<th></th>
		   		<th></th>
		   </tr>
		  
		   <tr  v-for="z,ind in zahtevi">
		   		<td>{{z.email}}</td>
		   		<td>{{z.ime}}</td>
		   		<td>{{z.prezime}}</td>
		   		<td>{{z.adresa}}</td>
		   		<td>{{z.grad}}</td>
		   		<td>{{z.drzava}}</td>
		   		<td>{{z.kontakt}}</td>
		   		<td>{{z.lbo}}</td>
		   		<td>{{z.lozinka}}</td>
		   		<td><button v-on:click = "prihvati(ind, z)" class="btn btn-success">Prihvati</button></td>
		   		<td><button class="btn btn-danger" id="show-modal" v-on:click="sacuvaj(z, ind)" @click="showModal = true" >Odbij zahtev</button>
						<modal v-if="showModal" @close="showModal = false">
	    
	    					<h3 slot="header">Unesite razlog odbijanja registracije</h3>
	    					<table slot="body" class="table table-hover table-light">
								<tbody>
									<tr>
										<td><input  class="form-control" type="text" v-model = "opis"/></td>
									</tr>									
								</tbody>
							</table>
	    					
	    					<div slot="footer">
	    						<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="save()"> Ukloni zahtev </button>       						
								<button style="margin:5px;" class="btn btn-secondary" @click="showModal=false" v-on:click="restore()"> Odustani </button>								
							</div>
						</modal>
				</td>
		   </tr>
		  
		</table>
		
		</div>
	
	`, 
	methods : {
		sacuvaj:function(zahtev,index ){
              this.globInd= index,
			 this. globZahtev =zahtev;
		},
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
		nazad : function(){
			this.$router.push('/sprofil');
			return;
		},
		
		prihvati:function(ind, zahtev){
			
			axios
			.post('api/verification/accepted', zahtev, { headers: { Authorization: 'Bearer ' + this.token }})
			.then((response)=>{
				this.greska='';
				this.zahtevi.splice(ind, 1);
				alert("Poslat email");
			}).catch((response)=>{
				this.greska = 'Email nije poslat';
				this.odbij(ind, zahtev);
				alert("Email vec zauzet");
			}
				
			);
			
			
		},
		odbij:function(ind, zahtevBrisi){
			axios
			.post('api/adminkc/denied/', {user: zahtevBrisi, opis: this.opis},{ headers: { Authorization: 'Bearer ' + this.token }})
			.then((response)=>{
				this.greska='';
				console.log(ind + "dasdadsdsadssadsad");
				this.zahtevi.splice(ind, 1);
				alert("Poslat email");
				
			}).catch((response)=>{
				this.greska = 'Email nije poslat';
			}
			);
			

		},
		restored:function(){
			this.opis = '';
			this.index ='';

		},
		save:function(){
			if(!this.opis){
				alert("Opis ne moze biti prazan");
				
			}			    
			else{
				this.odbij(this.globInd, this.globZahtev);
				
			}
			
		}
		
	}
	,
	mounted(){
		
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.admin = response.data; 
	        axios
			.put('/auth/dobaviulogu', this.admin, { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_ADMIN_KLINICKOG_CENTRA") {
		    		this.$router.push('/');
		    	}else{
		    		 axios
     				.get('api/zahtevreg/all',{ headers: { Authorization: 'Bearer ' + this.token }})
      				.then(response => (this.zahtevi = response.data));
		    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
	}		      		
	 

});