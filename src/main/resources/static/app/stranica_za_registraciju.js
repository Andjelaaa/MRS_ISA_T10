Vue.component('strzareg', {
	data: function(){
		return{
			imePacijenta: '',
			prezimePacijenta: '',
			email:'',
			adresaPacijenta:'',
			grad:'',
			drzava:'',
			lozinka:'',
			lozinkaPonovo:'',
			telefonPacijenta:'',
			lbo:'',
			greska:'',
			lozinkaGreska:''
		}
	},
	template: `
		<div class="container">
	<section id="content">
		<form action="">
			<h1>Registracija</h1>
			<div>
				<input type="text" placeholder="Email"   v-model="email"/>
			</div>
			<div>
				<input type="text" placeholder="Ime"   v-model="imePacijenta"/>
			</div>
			<div>
				<input type="text" placeholder="Prezime"   v-model="prezimePacijenta"/>
			</div>
			<div>
				<input type="text" placeholder="Adresa prebivalista"   v-model="adresaPacijenta"/>
			</div>
			<div>
				<input type="text" placeholder="Grad"   v-model="grad"/>
			</div>
			<div>
				<input type="text" placeholder="Drzava"   v-model="drzava"/>
			</div>
			<div>
				<input type="text" placeholder="Kontakt telefon"   v-model="telefonPacijenta"/>
			</div>
			<div>
				<input type="text" placeholder="LBO"  v-model="lbo"/>
			</div>
			<div>
				<input type="password" placeholder="Lozinka"  v-model="lozinka"/>
			</div>
			<div>
				<input type="password" placeholder="Ponoviti lozinku" v-model="lozinkaPonovo"/>
			</div>
			
			<div>
				<button v-on:click="nazad()" class="btn btn-light">Nazad</button>
			   	<button v-on:click="registracija()" class="btn btn-success">Registruj se</button>
			   	<p style="color: red">{{greska}}</p>
			</div>
		</form><!-- form -->

	</section><!-- content -->
</div><!-- container -->

	`,
		
		methods : {
			nazad : function(){
				this.$router.push('/')
				return;
			},
			validacija: function(){
				this.greska = '';
				this.lozinkaGreska = '';
				
				if(!this.imePacijenta){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!this.prezimePacijenta){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!this.email){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!this.adresaPacijenta){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!this.grad){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!this.drzava){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!(this.lozinka==this.lozinkaPonovo)){
					this.lozinkaGreska = 'Lozinke se ne poklapaju';
					return 1;
				}
				if(!this.telefonPacijenta){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				if(!this.lbo){
					this.greska = 'Sva polja su obavezna!';
					return 1;
				}
				
				return 0;
				
			},
			registracija : function(){	
				this.greska = '';
				if(this.validacija()==1)
					return;
				var novZahtev = {"email": this.email, "lozinka": this.lozinka,
								"ime": this.imePacijenta, "prezime": this.prezimePacijenta,
								"adresa": this.adresaPacijenta, "grad": this.grad,
								"drzava": this.drzava, "kontakt": this.telefonPacijenta, "lbo": this.lbo};
				
				axios
				.post('api/zahtevreg', novZahtev)
				.then((res)=>{
					console.log('USPESNO');
					this.$router.push('/');
				}).catch((res)=>{
					console.log('NEUSPESNO');
					this.greska = 'Email vec postoji';
				}
					
				)
			}
			
		}
})