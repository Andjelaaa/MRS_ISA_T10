Vue.component('zakazisalu', {

	data: function(){
		return{	
			admin:{},
			klinika: {},
			pregled: {},
			sale: [],
			zauzeca: [],
			prviSlobodni: [],
			noviDatum: null,
			pretragaSale: [],
			pretragaZauzeca: [],
			pretragaPrviSlobodni: [],
			searchparam:'',
			greskaDatum:'',
			salePomoc:[]
			
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
		        <a class="nav-link " href="#/izvestaji">Izvestaji</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link active" href="#/zahtevipo">Zahtevi za pregled/operaciju</a>
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
			<h3> Slobodne sale </h3> <h3 v-if="!noviDatum">{{prviSlobodni[0] | formatDate}}</h3>
		<input type='date' v-model='noviDatum'>
		<button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="nadjiZaDatum()">Nadji</button>
		{{greskaDatum}}
		<br>
		<input type='text' v-model='searchparam' placeholder='Naziv ili broj sale'>
		<button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="pretraga()">Pretrazi</button>
		<table class="table table-hover table-light">
			<thead>
				<th>Naziv</th>
				<th>Broj</th>
				<th>Zauzeća za odabrani datum</th>
				<th>Prvi slobodni termin</th>
				<th></th>
			
			</thead>
			<tbody>
			
			   <tr v-for="s, i in pretragaSale">			   		
			   		<td>{{s.naziv}}</td>
			   		<td>{{s.broj}}</td>
			   		<td><p  v-for="z in pretragaZauzeca[i]">{{z.pocetak | formatDate}} - {{z.kraj | formatDate}}</p></td>
			   		<td><p>{{pretragaPrviSlobodni[i] | formatDate}}</p></td>
			   		<td><button class="btn btn-success my-2 my-sm-0" type="submit" v-on:click="rezervisi(s, pretragaPrviSlobodni[i])">Rezervisi</button></td>

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
		rezervisi: function(s, prviSlobodan){
			axios
	      	.get('api/pregled/rezervisi/'+this.$route.params.id+'/'+s.id +'/'+prviSlobodan, { headers: { Authorization: 'Bearer ' + this.token }})
	      	.then(response => {
	      		alert('Uspesno rezervisana sala! Mejl poslat!');
	      		this.$router.push('/zahtevipo');
	      	})
	        .catch(function (error) { console.log('Greska');  });	

			
		},
		 validateDate: function() {
		        
    			if (new Date(this.noviDatum).valueOf() < new Date().valueOf()) {
       				  this.validator = false; //date is before today's date
   				} else {
					 this.validator = true; //date is today or some day forward
				}
		},
		dFormat: function(date) {
	        var d = new Date(date),
	            month = '' + (d.getMonth() + 1),
	            day = '' + d.getDate(),
	            year = d.getFullYear();

	        if (month.length < 2) 
	            month = '0' + month;
	        if (day.length < 2) 
	            day = '0' + day;

	        return [year, month, day].join('-');
	    },
		nadjiZaDatum: function(){
			this.greskaDatum ='';
			if(!this.noviDatum){
				this.greskaDatum ='Niste uneli datum.';
				return;
			}
		    else{
				this.validateDate();
			    if(!this.validator){
				  this.greskaDatum ='Niste uneli validan datum.';
				  return;
				}
				else{
					axios
					.get('api/sala/all/'+this.admin.id, { headers: { Authorization: 'Bearer ' + this.token }})
					.then(response => {
						this.sale = [];
						this.zauzeca = [];
						this.prviSlobodni = [];
						this.pretragaZauzeca = [];
						this.pretragaPrviSlobodni = [];
						this.pretragaSale = [];
						this.salePomoc = response.data;

						for(let s of this.salePomoc){
							console.log(s.id);
							axios
							.get('api/sala/prvislobodan/'+this.dFormat(this.noviDatum)+'/'+s.id+'/'+this.$route.params.id, { headers: { Authorization: 'Bearer ' + this.token }})
							.then(response => {			      		
								this.retVal = response.data;
								this.zauzeca.push(this.retVal.zauzeca);
								this.prviSlobodni.push(this.retVal.prviSlobodan);
								this.pretragaZauzeca.push(this.retVal.zauzeca);
								this.pretragaPrviSlobodni.push(this.retVal.prviSlobodan);
								this.pretragaSale.push(s);
								
							})
							.catch(function (error) { console.log('Greska11');  });	
							
						}
					})
					.catch(function (error) { console.log('Greska22') });		
			





				}








			}
			
		},
		pretraga: function(){
			this.pretragaSale = [];
			this.pretragaZauzeca = [];
			this.pretragaPrviSlobodan = [];
			
			var i;
			for (i = 0; i < this.sale.length; i++) {
				if(this.sale[i].naziv.includes(this.searchparam) || this.sale[i].broj.toString().includes(this.searchparam)){
					this.pretragaSale.push(this.sale[i]);
					this.pretragaZauzeca.push(this.zauzeca[i]);
					this.pretragaPrviSlobodan.push(this.prviSlobodni[i]);
				}
			}
			
		}
		
	},
	created(){
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
			      	.get('api/sala/slobodnesale/'+this.$route.params.id, { headers: { Authorization: 'Bearer ' + this.token }})
			      	.then(response => {
			      		this.sale = response.data;
			      		this.pretragaSale = response.data;
			      		for(var s of this.sale){
			      			console.log(s.id);
			      			axios
					      	.get('api/sala/zauzece/'+this.$route.params.id+'/'+s.id, { headers: { Authorization: 'Bearer ' + this.token }})
					      	.then(response => {
					      		this.retVal = response.data;
					      		this.zauzeca.push(this.retVal.zauzeca);
					      		this.prviSlobodni.push(this.retVal.prviSlobodan);
					      		this.pretragaZauzeca.push(this.retVal.zauzeca);
					      		this.pretragaPrviSlobodni.push(this.retVal.prviSlobodan);
					      	})
					        .catch(function (error) { console.log('Greska11') });	
			      			
			      		}
			      	})
			        .catch(function (error) { console.log('Greska22') });		    		
		    	}
		    })
		    .catch(function (error) { console.log(error); });
   
        })
        .catch(function (error) { router.push('/'); });
		
	}		      		
	 

});