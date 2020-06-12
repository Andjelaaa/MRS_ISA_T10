Vue.component('empty', {
	data: function(){		
		   return {
		    	kor : {},
		    	korisnik : {
		    		email : "",
		    		password : "",
		    	},
		    	uspesanLogin : true,
		    	token : "",
		    	uloga : "",
		    	greska: '',
		    	novaLozinka: '',
		    	showModal: false
		    }
	},

	template: `

<div class="container">
	<section id="content">
		<form action="">
			<h1>Login</h1>
			<p style="color:red">{{greska}}</p>
			<div>
				<input type="text" placeholder="Email" required="" id="username" v-model="korisnik.username"/>
			</div>
			<div>
				<input type="password" placeholder="Password" required="" id="password" v-model="korisnik.password"/>
			</div>
			<div>
				<button v-on:click="login()" class="btn btn-success">Log in</button>
				<br>
				<a href="/#/registracija">Registracija</a>
				
			</div>
		</form><!-- form -->
		
		
		<modal v-if="showModal" @close="showModal = false">

			<h3 slot="header">Promena lozinke</h3>
			<table slot="body" >
				<tbody>

					<tr>
						<td>Nova lozinka:</td>
						<td><input  class="form-control" type="password" v-model = "novaLozinka"/></td>
					</tr>

					
				</tbody>
				</table>
			
			<div slot="footer">
				<button @click="showModal=false" style="margin:5px;" class="btn btn-success" v-on:click="sacuvajLozinku()"> OK </button>       						
			</div>
		</modal>

	</section><!-- content -->
</div><!-- container -->

	`, 
	methods : {
		login : function () {
			axios
			.post('auth/login', this.korisnik)
			.then(response => (this.validacija(response.data)))
			.catch(function (error) { 
				console.log(error);
				this.uspesanLogin = false;
				this.greska = "Probajte ponovo.";
			});

		},
		validacija : function (korisnikToken) {
			this.token = korisnikToken.accessToken;
			if (this.token == null) {
				this.uspesanLogin = false;
				this.greska = "Probajte ponovo."
			} else {
				this.uspesanLogin = true;

				axios
				.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
		        .then(response => {
		        	this.kor = response.data;
					localStorage.setItem("token", this.token);
						
					console.log(this.kor.ime);
					if(this.kor.promenioLozinku == false){
						this.showModal = true;
					}else{
						axios
			    		.put('/auth/dobaviulogu', this.kor, { headers: { Authorization: 'Bearer ' + this.token }} )
			            .then(response => {
			            	this.uloga = response.data;
			            	if (this.uloga == "ROLE_PACIJENT") {
			            		console.log("PACIJENT JE");
			            		this.$router.push('/pacijent');
			            	} else if (this.uloga == "ROLE_LEKAR") {
			            		console.log("LEKAR JE");
			            		this.$router.push('/lekar');
			            	} else if (this.uloga == "ROLE_MED_SESTRA") {
			            		console.log("MED SESTRA JE");
			            		this.$router.push('/med_sestra_pocetna');
			            	} else if (this.uloga == "ROLE_ADMIN_KLINICKOG_CENTRA") {
			            		console.log("ADMIN KC JE");
			            		this.$router.push('/sprofil');
			            	} else if (this.uloga == "ROLE_ADMIN_KLINIKE") {
			            		console.log("ADMIN JE");
			            		this.$router.push('/admin');
			            	} else {
			            		this.greska = "Probajte ponovo.";
			            	}
			            })
			            .catch(function (error) { console.log(error); this.greska = "Probajte ponovo.";});
					}
					
					
					
		        })
		        .catch(function (error) { console.log(error); this.greska = "Probajte ponovo.";});
			}
		},
		sacuvajLozinku: function(){
			if(this.novaLozinka == '')
				return;
			
			axios
    		.put('/auth/dobaviulogu', this.kor, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => {
            	this.uloga = response.data;
            	if (this.uloga == "ROLE_PACIJENT") {
            		console.log("PACIJENT JE");
            		axios
					.get('api/pacijent/promenaLozinke/'+this.kor.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
					.then((res)=>{
						console.log('Uspesna izmena');
					}).catch((res)=>{
						console.log('Neuspesna izmenaaaa');
					});
            		this.$router.push('/pacijent');
            	} else if (this.uloga == "ROLE_LEKAR") {
            		console.log("LEKAR JE");
            		console.log(this.novaLozinka);
            		axios
					.get('api/lekar/promenaLozinke/'+this.kor.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
					.then((res)=>{
						console.log('Uspesna izmena');
					}).catch((res)=>{
						console.log('Neuspesna izmenaaaa');
					});
            		this.$router.push('/lekar');
            	} else if (this.uloga == "ROLE_MED_SESTRA") {
            		console.log("MED SESTRA JE");
            		axios
					.get('api/medsestraa/promenaLozinke/'+this.kor.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
					.then((res)=>{
						console.log('Uspesna izmena');
					}).catch((res)=>{
						console.log('Neuspesna izmenaaaa');
					});
            		this.$router.push('/med_sestra_pocetna');
            	} else if (this.uloga == "ROLE_ADMIN_KLINICKOG_CENTRA") {
            		console.log("ADMIN KC JE");
            		axios
					.get('api/adminkc/promenaLozinke/'+this.kor.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
					.then((res)=>{
						console.log('Uspesna izmena');
					}).catch((res)=>{
						console.log('Neuspesna izmenaaaa');
					});
            		this.$router.push('/sprofil');
            	} else if (this.uloga == "ROLE_ADMIN_KLINIKE") {
            		console.log("ADMIN JE");
            		axios
					.get('api/admini/promenaLozinke/'+this.kor.id+'/'+this.novaLozinka, { headers: { Authorization: 'Bearer ' + this.token }})
					.then((res)=>{
						console.log('Uspesna izmena');
					}).catch((res)=>{
						console.log('Neuspesna izmenaaaa');
					});
            		this.$router.push('/admin');
            	} else {
            		this.greska = "Probajte ponovo.";
            	}
            })
            .catch(function (error) { console.log(error); this.greska = "Probajte ponovo.";});
		}
	}

});