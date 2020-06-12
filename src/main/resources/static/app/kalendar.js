Vue.component('calendar', {
	data:function() {
		return{
			medicinska_sestra:{},
			filterDate: undefined,
		    selectedMonth: new Date(),
		    currentMonthAndYear: 'Maj 2020',
		    token:'',
		    pocetak:'',
		    kraj:'',
		    uloga:'',
		    odsustva:[],
		    odmori:[]
		}
	    
	  },
	  template:`
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
	              		<p >{{v.title.getDate()}}</p>
	            		<p v-if="v.key=='0'">{{pocetak}}-{{kraj}}</p>
	            		<p v-if="v.key=='1'" style="background:#F3EE3F">Odsustvo</p>
	            		<p v-if="v.key=='2'" style="background:#8BED79">Odmor</p>
	            		
	              
			  	   
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
		odjava : function(){
			localStorage.removeItem("token");
			this.$router.push('/');
		},
	    previousMonth: function() {
	      var tmpDate = this.selectedMonth;
	      var tmpMonth = tmpDate.getMonth() - 1;
	      this.selectedMonth = new Date(tmpDate.setMonth(tmpMonth));
	      this.currentMonthAndYear = moment(this.selectedMonth).format('MMM YYYY');
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
	   
	    isActive: function(date) {
	      return date === this.filterDate;
	    },
	 	 getCalendarMatrix: function(date) {
	      var calendarMatrix = []

		  var startDay = new Date(date.getFullYear(), date.getMonth(), 1)
		  var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0)
		      // Modify the result of getDay so that we treat Monday = 0 instead of Sunday = 0
	      var startDow = (startDay.getDay() + 6) % 7;
	      var endDow = (lastDay.getDay() + 6) % 7;
		      // If the month didn't start on a Monday, start from the last Monday of the previous month
	      startDay.setDate(startDay.getDate() - startDow);
		      // If the month didn't end on a Sunday, end on the following Sunday in the next month
	      lastDay.setDate(lastDay.getDate() + (6 - endDow));

		     
	      var week = [];
			     
			   
	      while (startDay <= lastDay) {
	    	  var validator=0;
		    	  
			  for(let i in this.odsustva){
			   		if(this.dFormat(startDay) == this.dFormat(this.odsustva[i])){
			   			var obj ={ title: new Date(startDay), key: '1' };
			   			week.push(obj)// odsustva
			   			validator=1;
			   		}
			    		
			  }
			  for(let i in this.odmori){
			   		if(this.dFormat(startDay) == this.dFormat(this.odmori[i])){
			   			var obj ={ title: new Date(startDay), key: '2' };
			   			week.push(obj)// odmor
			   			validator=1;
			    	}
			    		
			 }
			 if(validator==0){
			 	var obj ={ title: new Date(startDay), key: '0' };
			 	week.push(obj);
			 }
			 if (week.length === 7) {
			     calendarMatrix.push(week);
			     week = [];     
			 }
			 startDay.setDate(startDay.getDate() + 1);
	     }    
		return calendarMatrix;
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
		    	this.medicinska_sestra = response.data;	
				axios
				.put('/auth/dobaviulogu', this.medicinska_sestra, { headers: { Authorization: 'Bearer ' + this.token }} )
		   		.then(response => {
		    	this.uloga = response.data;
		    	if (this.uloga != "ROLE_MED_SESTRA") {
		    		this.$router.push('/');
				}
				else{	
				


		    	this.pocetak = this.medicinska_sestra.radvr_pocetak;
		    	this.kraj=this.medicinska_sestra.radvr_kraj;
		    	
		    	for(let i in this.medicinska_sestra.odsustvo){
		    		
		    		if(this.medicinska_sestra.odsustvo[i].tip =="Odsustvo"){
		    			
		    			var pp = new Date(this.medicinska_sestra.odsustvo[i].pocetak);
		    			var kk = new Date(this.medicinska_sestra.odsustvo[i].kraj);
		    			while(pp<=kk){
		    				this.odsustva.push(new Date(pp));
		    				pp.setDate(pp.getDate() + 1);
		    				
		    			}
		    			
		    			
		    		}
		    		else{

		    			var p = new Date(this.medicinska_sestra.odsustvo[i].pocetak);
		    			var k =new Date(this.medicinska_sestra.odsustvo[i].kraj);	
		    			while(p<=k){
		    				this.odmori.push(new Date(p));
		    				p.setDate(p.getDate() + 1);
		    			}
		    					    			
		    		}	
		    		
		    		
		    	}
		 
		   	
				}
		    })	
		 });
			
		}
		

	});