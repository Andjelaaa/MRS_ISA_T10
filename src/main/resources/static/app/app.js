const Empty = { template: '<empty></empty>' }
const RegistracijaKlinike = { template: '<regklinike></regklinike>' }
const StranicaZaRegistraciju = { template: '<strzareg></strzareg>' }
const ProfilSuperAdmina = { template: '<superprofil></superprofil>' }
const SifrarnikLekova = {template: '<sifrarnik1></sifrarnik1>'}
const SifrarnikDijagnoza = {template: '<sifrarnik2></sifrarnik2>'}
const OdobravanjeReg = {template: '<odobri_zaht></odobri_zaht>'}
const LekarPocetna = {template: '<lekar></lekar>'}
const AdminPocetna = {template: '<admin></admin>'}
const DodavanjePregleda = {template: '<dpregled></dpregled>'}
const TipoviPregleda = {template: '<tipovipregleda></tipovipregleda>'}
const PredefPregledi = {template: '<predefpregledi></predefpregledi>'}
const DodavanjeAdmina = {template: '<dodajadmina></dodajadmina>'}
const ProfilMedSestre = {template: '<medsestra></medsestra>'}
const Sale = {template: '<sale></sale>'}
const Lekari = {template: '<lekari></lekari>'}
const PacijentPocetna = {template: '<pacijent></pacijent>'}
const ZakazaniPregledi = {template: '<zakazani-pregledi></zakazani-pregledi>'}
const KlinikePrikaz = {template: '<klinike-prikaz></klinike-prikaz>'}
const KlinikaDetalji = {template: '<klinika-detalji></klinika-detalji>'}
const PotvrdaPacijenta = {template: '<potvrdareg></potvrdareg>'}
const PacijentiLista = {template: '<pacijenti></pacijenti>'}
const MedSestraPocetna = {template: '<med_sestra_pocetna></med_sestra_pocetna>'}
const OveraRecepata = {template: '<overa></overa>'}
const ZahtevOdmor =  {template: '<odmoor></odmoor>'}
const Kalendar =  {template: '<calendar></calendar>'}
const Kalendar2 =  {template: '<calendar_doc></calendar_doc>'}
const Kalendar3 =  {template: '<calendar_ned></calendar_ned>'}
const Kalendar4 =  {template: '<calendar_god></calendar_god>'}
const NadjiPacijenta =  {template: '<nadjipacijenta></nadjipacijenta>'}
const ZahteviPregledOperacija =  {template: '<zahtevipo></zahtevipo>'}
const ZakaziSalu = {template: '<zakazisalu></zakazisalu>'}
const ZakaziSaluOP = {template: '<zakazisaluop></zakazisaluop>'}
const Izvestaji = {template: '<izvestaji></izvestaji>'}
const ZahteviOdmorOdsustvo = {template: '<zahtevioo></zahtevioo>'}
const LekarZakazaniPregledi = {template: '<lekarzp></lekarzp>'}
const MedicinskeSestre = {template: '<medsestre></medsestre>'}
const ProfilLekar = {template: '<profillekar></profillekar>'}
const ProfilAdmin = {template: '<profiladmin></profiladmin>'}
const DodajSAdmin = {template: '<dodajsa></dodajsa>'}


const router = new VueRouter({
	mode: 'hash',
	  routes: [
	    { path: '/', component: Empty},
	    { path: '/regklinika', component: RegistracijaKlinike},
	    { path: '/registracija', component: StranicaZaRegistraciju},
	    { path: '/sprofil', component: ProfilSuperAdmina},
	    { path: '/sifrarnik1', component: SifrarnikLekova},
	    { path: '/sifrarnik2', component: SifrarnikDijagnoza},
	    { path: '/odobri_zahtev', component: OdobravanjeReg},
	    { path: '/lekar', component: LekarPocetna},
	    { path: '/admin', component: AdminPocetna},
	    { path: '/dpregled', component: DodavanjePregleda},
	    { path: '/tipovipregleda', component: TipoviPregleda},
	    { path: '/predefinisanipregledi/:name', component: PredefPregledi},
	    { path: '/dadmin/:id', component: DodavanjeAdmina},
	    { path: '/medsestra', component: ProfilMedSestre},
	    { path: '/sale', component: Sale},
	    { path: '/lekari', component: Lekari},
	    { path: '/pacijent', component: PacijentPocetna},
	    { path: '/pacijentpregledi', component: ZakazaniPregledi},
	    { path: '/klinike', component: KlinikePrikaz},
	    { path: '/detaljiKlinike/:name/:date/:tip', component: KlinikaDetalji},
	    { path: '/potvrdiRegistraciju/:token', component: PotvrdaPacijenta},
	    { path: '/pacijenti', component: PacijentiLista},
	    { path: '/med_sestra_pocetna', component: MedSestraPocetna},
	    { path: '/overa', component: OveraRecepata},
	    { path: '/odmor', component: ZahtevOdmor},
	    { path: '/kalendarr', component: Kalendar},
	    { path: '/kalendarlekar', component: Kalendar2},
	    { path: '/kalendarnedeljni', component: Kalendar3},
	    { path: '/kalendargodisnji', component: Kalendar4},
	    { path: '/pacijenti/:lbo', component: NadjiPacijenta},
	    { path: '/zahtevipo', component: ZahteviPregledOperacija},
	    { path: '/zakazisalu/:id', component: ZakaziSalu},
	    { path: '/zakazisaluop/:id', component: ZakaziSaluOP},
	    { path: '/izvestaji', component: Izvestaji},
	    { path: '/zahtevioo', component: ZahteviOdmorOdsustvo},
	    { path: '/lekar/pregledi', component: LekarZakazaniPregledi},
	    { path: '/medsestre', component: MedicinskeSestre},
	    { path: '/profil', component: ProfilLekar},
		{ path: '/profiladmin', component: ProfilAdmin},
		{ path: '/dodajsa', component: DodajSAdmin}
	    ]
});

Vue.filter('formatDate', function(value) {
	  if (value) {
	    return moment(String(value)).format('DD/MM/YYYY HH:mm')
	  }
});

Vue.filter('formatTime', function(value) {
	  if (value) {
	    return moment(String(value)).format('HH:mm')
	  }
});

var app = new Vue({
	router,
	el: '#klinika'
});