Vue.component('potvrdareg', {
	data:function(){
	      return{
			potvrda:'',
			
		  }
	},
	template: `
	     <div>
	    
		<table class="table">
			<tr>
			<td ><b>Uspesno ste aktivirali Vas profil. Mozete se ulogovati.</b></td>
			<td ><button v-on:click="logovanje()" class="btn btn-light">Uloguj se</button></td>
			</tr>
		</table>
		</div>
	`, 
	
	methods : {
		logovanje: function()
		{
			this.$router.push('/');
		},
		
		
	},
	
	mounted () {
		axios
		.get('api/verification/potvrdiRegistraciju/'+this.$route.params.token)
		.then(res => {
			potvrda = res.data;
		})
		
	},

});