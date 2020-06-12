Vue.component('calendar_doc', {
	data:function() {
		return{
			korisnik:{},
			filterDate: undefined,
		    selectedMonth: new Date(),
		    currentMonthAndYear: 'Maj 2020',
		    token:'',
		    pocetak:'',
		    kraj:'',
		    uloga:'',
		    odsustva:[],
		    odmori:[],
		    operacije:[],
		    pregledi:[]
		}
	    
	  },
	  template:`
	  <div>
		   <nav class="navbar navbar-expand navbar-light" style="background-color: #e3f2fd;">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  	<a  class="navbar-brand" href="#/lekar">Pocetna</a>
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item active">
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
		        <a  class="nav-link" href="#/profil">Profil: {{korisnik.ime}} {{korisnik.prezime}}</a>
		      </li>
		    </ul>
		    <form class="form-inline my-2 my-lg-0">
		      
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" v-on:click="odjava()">Odjavi se</button>
		    </form>
		  </div>
		</nav>
		</br>
		
		<br>
		<ul class="nav nav-tabs">
		<li class="nav-item">
		    <a class="nav-link" href="#/kalendarnedeljni">Nedeljni Kalendar</a>
		  </li>
		 <li class="nav-item">
		    <a class="nav-link active" href="#/kalendarlekar">Mesecni Kalendar</a>
		  </li>
		  
		  <li class="nav-item">
		    <a class="nav-link" href="#/kalendargodisnji">Godisnji Kalendar</a>
		  </li>
		</ul>

		<div class="container" id="app">
		  <div class="row">
	    <div class="col-md-12">
	      <h3>
	      Radni kalendar
	      </h3>
	
	      <transition name="fade">
	        <div class="alert alert-success" v-if="filterDate != undefined"> Date selected is: {{formattedDate}}</div>
	      </transition>
	      <div id="app-table" class="table-responsive">
	        <table class="table table-bordered fullscreen">
	          <thead class="thead-default">
	            <tr>
	              <th colspan="1">
	                <a href="#" class="prev" v-on:click="previousMonth"> prethodni </a>
	              </th>
	              <th colspan="5" class="center-title">
	                {{currentMonthAndYear}}
	              </th>
	              <th colspan="1">
	                <a href="#" class="next" v-on:click="nextMonth"> sledeci </a>
	              </th>
	            </tr>
	            <tr>
	              <th>Ponedeljak</th>
	              <th>Utorak</th>
	              <th>Sreda</th>
	              <th>Cetvrtak</th>
	              <th>Petak</th>
	              <th>Subota</th>
	              <th>Nedelja</th>
	            </tr>
	          </thead>
	         
	          <tbody class="tbody-default" data-bind="foreach:gridArray">
	            <tr v-for="item in gridArray">
	              <td v-for="(v,i) in item" :key="i"> 
	              	   <p >{{v.date.getDate()}}</p>
	              	   <p v-if="v.datas[0].key=='0'|| v.datas[0].key=='3' || v.datas[0].key=='4'">{{pocetak}}-{{kraj}}</p>
	              	<div v-for="(value,kk) in v.datas" :key="kk"> 

	            		
	            		<p v-if="value.key=='1'" style="background:#F3EE3F">Odsustvo</p>
	            		<p v-if="value.key=='2'" style="background:#8BED79">Odmor</p>
	            		<p v-if="value.key=='3'" style="background:#16CEF0">
	            		Operacija
	            		<br>
	            		Vreme pocetka: {{dFormatSati(value.vreme)}}
	            		<br>
	            		Trajanje: {{value.trajanje}}
	            		<br>
	            		Pacijent: {{value.ime}} {{value.prezime}}
	            		</p>
	            		
	            		<p v-if="value.key=='4'" style="background:#70F9F0">
	            		<a :href="'#/pacijenti/' + value.lbo" >Pregled</a>
	            		<br>
		  				Vreme pocetka: {{dFormatSati(value.vreme)}}
		  				<br>
		  			    Trajanje: {{value.trajanje}}
	            		<br>
	            		Pacijent: {{value.ime}} {{value.prezime}}
	            		</p>
	            	  	   
	              </div>
	              </td>
	            </tr>
	
	          </tbody>
	        </table>
	      </div>
	
		  </div>
		  </div>
		  
		  </div>
  </div>`,
	  methods: {
	    previousMonth: function() {
	      var tmpDate = this.selectedMonth;
	      var tmpMonth = tmpDate.getMonth() - 1;
	      this.selectedMonth = new Date(tmpDate.setMonth(tmpMonth));
	      this.currentMonthAndYear = moment(this.selectedMonth).format('MMM YYYY');
	    },
	    
	    dFormatSati: function(date) {
	        var d = new Date(date),
	            hours = '' + d.getHours() ,
	            minutes = '' + d.getMinutes();

	        if (hours.length < 2) 
	        	hours = '0' + hours;
	        if (minutes.length < 2) 
	        	minutes = '0' + minutes;

	        return [hours, minutes].join('-');
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
	    nextMonth: function() {
	      var tmpDate = this.selectedMonth;
	      var tmpMonth = tmpDate.getMonth() + 1;
	      this.selectedMonth = new Date(tmpDate.setMonth(tmpMonth));
	      this.currentMonthAndYear = moment(this.selectedMonth).format('MMM YYYY');
	    },
	    odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
	    isActive: function(date) {
	      return date === this.filterDate;
	    },
	    getCalendarMatrix: function(date) {
		      var calendarMatrix = []
              
			  var startDay = new Date(date.getFullYear(), date.getMonth(), 1)
			  var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0)
		      var startDow = (startDay.getDay() + 6) % 7;
		      var endDow = (lastDay.getDay() + 6) % 7;
			      
		      startDay.setDate(startDay.getDate() - startDow);
			      
		      lastDay.setDate(lastDay.getDate() + (6 - endDow));

			     
		      var week = [];
			  
		      
		      while (startDay <= lastDay) {
		    	  var weekdatas=[];
		    	  var validator=0;
		    	  var abc=0;
			    	  
				  for(let i in this.odsustva){
				   		if(this.dFormat(startDay) == this.dFormat(this.odsustva[i])){
				   			var p ={ key: '1' ,
					   				 ime: '',
									 prezime:  '',
									 trajanje: '',
									 vreme: '',
									 lbo:'' };
				   			weekdatas.push(p);
				   			
				   			var obj = {date: new Date(startDay),datas: weekdatas};
				   			week.push(obj);
				   			validator=1;
				   			
				   		}
				    		
				  }
				  for(let i in this.odmori){
				   		if(this.dFormat(startDay) == this.dFormat(this.odmori[i])){
				   			var p ={ key: '2' ,
					   				 ime: '',
									 prezime:  '',
									 trajanje: '',
									 vreme: '',
									 lbo:'' };
				   			weekdatas.push(p);
				   							   			
				   			var obj ={ date: new Date(startDay),datas: weekdatas};
				   			week.push(obj);
				   			validator=1;
				    	}
				    		
				 }
				
				 for(let i in this.operacije){
					 if((this.dFormat(startDay) == this.dFormat(this.operacije[i].datumVreme)&& abc!=0)){
						 abc++;
						 var p ={key: '3' , ime: this.operacije[i].pacijent.ime,
								 prezime:  this.operacije[i].pacijent.prezime,
								 trajanje: this.operacije[i].trajanje,
								 vreme: new Date(this.operacije[i].datumVreme),
								 lbo:this.operacije[i].pacijent.lbo };
						 var date= new Date(startDay);
						 var weekHelper= []
						 weekHelper = this.spajanjeDatuma(p,date,week);
						 week = [];
						 week = weekHelper;
						 console.log(startDay.getDate()+" na ovaj datum");
						
					 }
					 if((this.dFormat(startDay) == this.dFormat(this.operacije[i].datumVreme) && abc==0)){
						 abc++;
						 var p ={key: '3' , ime: this.operacije[i].pacijent.ime,
								 prezime:  this.operacije[i].pacijent.prezime,
								 trajanje: this.operacije[i].trajanje,
								 vreme: new Date(this.operacije[i].datumVreme),
								 lbo:this.operacije[i].pacijent.lbo };
						 weekdatas.push(p);
						 
						 var obj ={ date: new Date(startDay),datas: weekdatas};
				   		 week.push(obj);
				   		 validator=1;
				   		 
					 }
				 }
				 for(let i in this.pregledi){
					 if((this.dFormat(startDay) == this.dFormat(this.pregledi[i].datumVreme )&& abc!=0)){
						 abc++;
						 var p ={key: '4' , ime: this.pregledi[i].pacijent.ime,
								 prezime:  this.pregledi[i].pacijent.prezime,
								 trajanje: this.pregledi[i].trajanje,
								 vreme: new Date(this.pregledi[i].datumVreme),
								 lbo:this.pregledi[i].pacijent.lbo };
						 var date= new Date(startDay);
						 var weekHelper= []
						 weekHelper = this.spajanjeDatuma(p,date,week);
						 week = [];
						 week = weekHelper;
						 console.log(startDay.getDate()+" na ovaj datum");
						
					 }
					 
					 if((this.dFormat(startDay) == this.dFormat(this.pregledi[i].datumVreme )&& abc==0)){
						 abc++;
						 var p ={key: '4' , ime: this.pregledi[i].pacijent.ime,
								 prezime:  this.pregledi[i].pacijent.prezime,
								 trajanje: this.pregledi[i].trajanje,
								 vreme: new Date(this.pregledi[i].datumVreme),
								 lbo:this.pregledi[i].pacijent.lbo };
						 weekdatas.push(p);
						 var obj ={ date: new Date(startDay), datas: weekdatas};
				   		 week.push(obj)
				   		 validator=1;

					 }
				 }
				 
				 if(validator==0){
					 	var p ={ key: '0' , ime: '',
							 prezime:  '',
							 trajanje: '',
							 vreme: '',
							 lbo:''};
					 	
					 	weekdatas.push(p);
					 	var obj ={ date: new Date(startDay),datas: weekdatas};
					 	week.push(obj);
				 }
				 if (week.length === 7) {
				     calendarMatrix.push(week);
				     week = [];     
				 }
				 startDay.setDate(startDay.getDate() + 1);
				 weekdatas=[];
		     }    
		    
			return calendarMatrix;
		    },
		    spajanjeDatuma:function(p,date,week){
		    
		    	for(let i in week){
		    		if(week[i].date.getDate() == date.getDate())
		    		{
		    			week[i].datas.push(p);
		    			
		    		}
		    		
		    	}
		    	return week;
		    	
		    }
		},
	  computed: {
	    gridArray: function() {
	      var grid = this.getCalendarMatrix(this.selectedMonth);
	      return grid;
	    },
	    formattedDate: function() {
	      return this.filterDate ? moment(this.filterDate).format('lll') : '';
	    }
	  }
	  ,
	  mounted(){
	  	 
		  this.token = localStorage.getItem("token");
			axios
			.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
		    .then(response => { 
		    	this.korisnik = response.data;
				
				axios
				.put('/auth/dobaviulogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		   		.then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_LEKAR") {
		    		this.$router.push('/');
				}
				else{	
		    	
		    	this.pocetak = this.korisnik.rvPocetak;
		    	this.kraj=this.korisnik.rvKraj;
		    	
		    	
		    	for(let i in this.korisnik.odsustvo){
		 
		    		if(this.korisnik.odsustvo[i].tip =="Odsustvo"){
		    			var pp = new Date(this.korisnik.odsustvo[i].pocetak);
		    			var kk = new Date(this.korisnik.odsustvo[i].kraj);
		    			while(pp<=kk){
		    				this.odsustva.push(new Date(pp));
		    				pp.setDate(pp.getDate() + 1);
		    				
		    			}
		    			
		    			
		    		}
		    		else{

		    			var p = new Date(this.korisnik.odsustvo[i].pocetak);
		    			var k =new Date(this.korisnik.odsustvo[i].kraj);	
		    			while(p<=k){
		    				this.odmori.push(new Date(p));
		    				p.setDate(p.getDate() + 1);
		    			}
		    					    			
		    		}	
		    		
		    		
		    	}
		    	axios
					.get('api/pregled/lekarpre/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
				    .then(response => {
				    	this.pregledi = response.data;
				    })
				.catch((response)=> { console.log("Doslo je do greske sa dobavljanjem preglega");});
		    	
		    	axios
				.get('api/operacije/lekarop/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
			    .then(response => {
			    	this.operacije = response.data;			    	
			    })
			    .catch((response)=> { console.log("Doslo je do greske sa dobavljanjem operacija");});
		       	
				}
		    })	
		 });
			
		}
		

	});