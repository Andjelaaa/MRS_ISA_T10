insert into klinicki_centar (naziv) values ('Klinicki centar Srbije');
insert into adminkc (promenio_lozinku, ime, prezime, email, lozinka, adresa, grad, drzava, kontakt) values (false, 'Jovan', 'Jovanic', 'tt@gmail.com','$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Topolska 18', 'Beograd', 'Srbija', '0652458615');

insert into klinika (naziv, adresa, opis, prosecna_ocena, email_klinike, kontakt_klinike, kc_id, broj_ocena) values ('Kardiologija', 'Bulevar Oslobodjenja 12, Novi Sad', 'kardiologija i kardiohirurgija', 7.6, 'kardio@klinika.com', '365/658',1, 5);
insert into klinika (naziv, adresa, opis, prosecna_ocena, email_klinike, kontakt_klinike, kc_id, broj_ocena) values ('Ortopedija', 'Bulevar Oslobodjenja 16, Novi Sad', 'ortopedija i fizikalna medicina', 8.2, 'orto@klinika.com', '365/656',1, 7);


insert into admin_klinike (adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, klinika_id, promenio_lozinku) 
values ('Cara Dusana 123', 'Srbija', 'nedicteodora1@gmail.com', 'Novi Sad', 'Aca', '065154513', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Peric', 1, false);
insert into admin_klinike (adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, klinika_id, promenio_lozinku) 
values ('Cara Dusana 222', 'Srbija', 'admin2@gmail.com', 'Novi Sad', 'Maja', '066154333', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Jovic', 2, false);

insert into cenovnik (id) values (nextval('Cenovnik_id_SEQ'));


insert into tip_pregleda (naziv, opis, broj_aktvnih) values ('Opsti', 'opsti pregled', 0);
insert into tip_pregleda (naziv, opis, broj_aktvnih) values ('Ultrazvuk', 'ultrazvuk abdomena', 0);
insert into tip_pregleda (naziv, opis, broj_aktvnih) values ('Ocni', 'odredjivanje dioptrije', 0);


insert into stavka_cenovnika (cena, cenovnik_id, tip_pregleda_id) values (2000, 1, 1);
update tip_pregleda set stavka_id=1 where id=1;
insert into stavka_cenovnika (cena, cenovnik_id, tip_pregleda_id) values (3000, 1, 2);
update tip_pregleda set stavka_id=2 where id=2;
insert into stavka_cenovnika (cena, cenovnik_id, tip_pregleda_id) values (1500, 1, 3);
update tip_pregleda set stavka_id=3 where id=3;


insert into lek (naziv,sifra) values ('Bromazepam', 'B1');
insert into lek (naziv, sifra) values ('Hloramfenikol', 'H1');
insert into lek (naziv, sifra) values ('Panklav', 'P1');
insert into lek (naziv, sifra) values ('Cetirizin', 'C1');
insert into lek (naziv, sifra) values ('Ketotifen', 'K1');
insert into lek (naziv, sifra) values ('Zoloft', 'Z1');
insert into lek (naziv, sifra) values ('Diazepam', 'D11');
insert into lek (naziv, sifra) values ('Amoksicilin', 'E1');
insert into lek (naziv, sifra) values ('Enalapril', 'F1');


insert into dijagnoza (naziv,sifra) values ('Poremećaji koagulacije', 'D56 D69');
insert into dijagnoza (naziv, sifra) values ('Hemoliticke anemije', 'G80 G83');
insert into dijagnoza (naziv, sifra) values ('Bolesti jetre', 'K70 K77');
insert into dijagnoza (naziv, sifra) values ('Virusne infekcije', 'A80 A89');
insert into dijagnoza (naziv, sifra) values ('Helmintijaze', 'D2');
insert into dijagnoza (naziv, sifra) values ('Glaukom', 'G60 G66');
insert into dijagnoza (naziv, sifra) values ('Skolioza', 'D66 D77');
insert into dijagnoza (naziv, sifra) values ('Hipertenzija', 'I10 I15');


insert into sala (naziv, broj, klinika_id, izmena, version) values ('Sala A', '1', 1,  1, 1);
insert into sala (naziv, broj, klinika_id, izmena, version) values ('Sala B', '2', 2, 1, 1);
insert into sala (naziv, broj, klinika_id, izmena, version) values ('Sala C', '3', 1,  1, 1);
insert into sala (naziv, broj, klinika_id, izmena, version) values ('Sala D', '4', 2, 1, 1);




insert into lekar (adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, broj_ocena, klinika_id, prosecna_ocena, rv_kraj, rv_pocetak, tip_pregleda_id, promenio_lozinku,version, izmena_rezervisanja) 
values ('Bul. Oslobodjenja 123', 'Srbija', 'lekar1@gmail.com', 'Novi Sad', 'Nenad', '065154923', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Nenadovic', 0, 1, 0, '15:00', '07:00', 1, true,1,1);
insert into lekar (adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, broj_ocena, klinika_id, prosecna_ocena, rv_kraj, rv_pocetak, tip_pregleda_id, promenio_lozinku,version, izmena_rezervisanja) 
values ('Bul.Kralja Petra 567', 'Srbija', 'lekar2@gmail.com', 'Novi Sad', 'Nikola', '060514848', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Nikolic', 1, 2, 7, '21:00', '13:00', 1, true,1,1);
insert into lekar (adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, broj_ocena, klinika_id, prosecna_ocena, rv_kraj, rv_pocetak, tip_pregleda_id, promenio_lozinku,version, izmena_rezervisanja) 
values ('Novosajmiste 123', 'Srbija', 'lekar3@gmail.com', 'Novi Sad', 'Jovan', '06220514848', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Jovic', 1, 1, 7, '21:00', '13:00', 2, true,1,1);
insert into lekar (adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, broj_ocena, klinika_id, prosecna_ocena, rv_kraj, rv_pocetak, tip_pregleda_id, promenio_lozinku,version, izmena_rezervisanja) 
values ('Novosadski Sajam 7', 'Srbija', 'lekar4@gmail.com', 'Novi Sad', 'Marko', '06331333848', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Kostadinovic', 1,2, 9, '15:00', '07:00', 2, true,1,1);

insert into zkarton(visina, tezina, datum_rodjenja, pol, dioptrija, krvna_grupa) values (180,80,'1965-05-25 01:29:00', 'm', 2.5,'AB');
insert into zkarton(visina, tezina, datum_rodjenja, pol, dioptrija, krvna_grupa) values (170,70,'1998-05-25 01:29:00', 'z', 0.5,'B');
insert into zkarton(visina, tezina, datum_rodjenja, pol, dioptrija, krvna_grupa) values (190,60,'1990-05-25 01:29:00', 'm', 1.5,'A');

insert into pacijent(promenio_lozinku, ime, prezime, email, lozinka, adresa, grad, drzava, kontakt, lbo, enabled, z_karton_id) values (true, 'Marko', 'Markovic', 'markkom@gmail.com', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Adresa 18', 'Novi Sad', 'Srbija', '065123456', '123lbo22', true, 1);
insert into pacijent(promenio_lozinku, ime, prezime, email, lozinka, adresa, grad, drzava, kontakt, lbo, enabled, z_karton_id) values (true, 'Ana', 'Markovic', 'anana@gmail.com', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Adresa 20', 'Novi Sad', 'Srbija', '0651045155', '123123', true, 2);
insert into pacijent(promenio_lozinku, ime, prezime, email, lozinka, adresa, grad, drzava, kontakt, lbo, enabled, z_karton_id) values (true, 'Rajko', 'Jovanovic', 'rajkoj@gmail.com', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Adresa 52', 'Novi Sad', 'Srbija', '0651256132', '123567', true, 3);


insert into klinika_pacijent(klinika_id, pacijent_id) values (1,1);
insert into klinika_pacijent(klinika_id, pacijent_id) values (1,2);
insert into klinika_pacijent(klinika_id, pacijent_id) values (2,3);



insert into recept (ime_pacijenta,prezime_pacijenta,version) values ('Marko', 'Markovic', 1);
insert into recept (ime_pacijenta,prezime_pacijenta,version) values ('Ana', 'Markovic',1);
insert into recept (ime_pacijenta,prezime_pacijenta,version) values ('Rajko', 'Jovanovic',1);
insert into recept (ime_pacijenta,prezime_pacijenta,version) values ('Marko', 'Markovic',1);
insert into recept (ime_pacijenta,prezime_pacijenta,version) values ('Ana', 'Markovic',1);



insert into recept_lek (recept_id, lek_id) values(1,1);
insert into recept_lek (recept_id, lek_id) values(1,2);
insert into recept_lek (recept_id, lek_id) values(2,3);
insert into recept_lek (recept_id, lek_id) values(2,8);
insert into recept_lek (recept_id, lek_id) values(2,9);
insert into recept_lek (recept_id, lek_id) values(3,2);
insert into recept_lek (recept_id, lek_id) values(4,1);
insert into recept_lek (recept_id, lek_id) values(5,4);
insert into recept_lek (recept_id, lek_id) values(5,5);
insert into recept_lek (recept_id, lek_id) values(4,6);
insert into recept_lek (recept_id, lek_id) values(4,7);
insert into recept_lek (recept_id, lek_id) values(3,3);


insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Stabilno stanje', 1, 1);
insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Doci na kontrolu cim se osete pogorsanja',8,2);
insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Javiti se po potrebi', 2, 3);
insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Uzimati lekove na 8h', 7, 4);
insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Uzimati lekove jednom nedeljno', 3, 5);
insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Stabilno stanje', null, null);
insert into izvestaj (opis, dijagnoza_id, recept_id) values ('Stabilno stanje',null, null); 

--predefinisani pregledi
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-21 19:00:00', 5, 1, 30, null, 2, null, 2, 1, null, 2,1);
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-21 17:00:00', 0, 1, 30, null, 2, null, 2, 1, null, 2,1);

insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-21 08:00:00', 5, 1, 30, null, 1, null, 1, 1, null, 1,1);
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-21 10:00:00', 5, 1, 30, null, 1, null, 1, 1, null, 1,1);

--gotovi pregledi
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-06-10 17:00:00', 5, 4, 30, 1, 2, 1, 2, 1, 1, 2,1);
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-06-10 18:00:00', 5, 4, 30, 2, 2, 2, 2, 1, 2, 2,1);
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-06-03 18:00:00', 5, 4, 30, 2, 2, 2, 2, 1, 2, 2,1);
  
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-06-03 08:00:00', 5, 4, 30, 3, 1, 3, 1, 1, 3, 1,1);
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-06-06 10:00:00', 5, 4, 30, 5, 1, 1, 1, 1, 1, 1,1); 
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-10 17:00:00', 5, 1, 30, null, 2, 1, 2, 1, 1, 2,1);

insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-11 17:00:00', 5, 1, 30, null, 2, 1, 2, 1, 1, 2,1);

--zahtevi pregleda

insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-10 17:00:00', 5, 0, 30, null, 2, 1, null, 1, 1, 2,1);

insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-03 18:00:00', 5, 3, 45, null, 2, 1, null, 1, 1, 2,1);
  
insert into pregled(datum_vreme, popust, status, trajanje, izvestaj_id, lekar_id, pacijent_id, sala_id, tip_pregleda_id, zkarton_id, klinika_id, version)
values ('2020-07-11 17:00:00', 5, 0, 45, null, 2, 1, null, 1, 1, 2,1);
    


insert into ocena_lekar (lekar_id, ocena, pacijent_id) values (2, 7, 1);
insert into ocena_lekar (lekar_id, ocena, pacijent_id) values (3, 7, 1);
insert into ocena_lekar (lekar_id, ocena, pacijent_id) values (4, 9, 1);

insert into medicinska_sestra (promenio_lozinku, adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, klinika_id, radvr_kraj, radvr_pocetak) 
values (true,'Bul. Oslobodjenja 12', 'Srbija', 'meds1@gmail.com', 'Novi Sad', 'Manja', '064154123', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Nekic', 1, '15:00', '07:00');
insert into medicinska_sestra (promenio_lozinku, adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, klinika_id, radvr_kraj, radvr_pocetak) 
values (true, 'Bul. 222', 'Srbija', 'ms2@gmail.com', 'Novi Sad', 'Marija', '060345822', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Pekic', 2, '21:00', '13:00');
insert into medicinska_sestra (promenio_lozinku, adresa, drzava, email, grad, ime, kontakt, lozinka, prezime, klinika_id, radvr_kraj, radvr_pocetak) 
values (true, 'Bul. 221232', 'Srbija', 'm3@gmail.com', 'Novi Sad', 'Ana', '060565656565', '$2a$10$uf1G0CsocgVi9Uc8oYsQsuq9BBHxFZ5Vbirl4o8D87FnYirUnl5C2', 'Jokic', 1, '21:00', '13:00');


insert into autoritet (name) values ('ROLE_PACIJENT');
insert into autoritet (name) values ('ROLE_LEKAR');
insert into autoritet (name) values ('ROLE_ADMIN_KLINIKE');
insert into autoritet (name) values ('ROLE_ADMIN_KLINICKOG_CENTRA');
insert into autoritet (name) values ('ROLE_MED_SESTRA');

insert into pacijent_autoriteti (pacijent_id, autoriteti_id) values (1, 1);
insert into pacijent_autoriteti (pacijent_id, autoriteti_id) values (2, 1);
insert into pacijent_autoriteti (pacijent_id, autoriteti_id) values (3, 1);

insert into admin_klinike_autoriteti (admin_klinike_id, autoriteti_id) values (1, 3);
insert into admin_klinike_autoriteti (admin_klinike_id, autoriteti_id) values (2, 3);

insert into lekar_autoriteti (lekar_id, autoriteti_id) values (1, 2);
insert into lekar_autoriteti (lekar_id, autoriteti_id) values (2, 2);
insert into lekar_autoriteti (lekar_id, autoriteti_id) values (3, 2);
insert into lekar_autoriteti (lekar_id, autoriteti_id) values (4, 2);

insert into medicinska_sestra_autoriteti (med_sestra_id, autoriteti_id) values (1, 5);
insert into medicinska_sestra_autoriteti (med_sestra_id, autoriteti_id) values (2, 5);
insert into medicinska_sestra_autoriteti (med_sestra_id, autoriteti_id) values (3, 5);

insert into adminkc_autoriteti (adminkc_id, autoriteti_id) values (1, 4);

--odsustva

insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-06-16 12:00:00', 'Zbog privatnih problema', '2020-06-15 11:00:00', 1, 'Odsustvo' , 1, null, 1,1);
insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-06-25 12:00:00', 'nema opisa', '2020-06-21 11:00:00', 1, 'Odmor' , 1, null, 1,1);

insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-06-14 12:00:00', 'nema opisa', '2020-06-12 11:00:00', 1, 'Odmor' , null, 1, 1,1);
insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-06-10 12:00:00', 'Radi putovanja', '2020-06-05 11:00:00', 1, 'Odsustvo' , null, 1, 1,1);

insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-06-25 12:00:00', 'nema opisa', '2020-06-21 11:00:00', 1, 'Odmor' , 2, null, 2,1);
insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-06-17 12:00:00', 'Zbog privatnih problema', '2020-06-11 11:00:00', 1, 'Odsustvo' , 2, null, 2,1);




--zahtevi za odsustva


insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-07-20 12:00:00', 'Zelim da putujem', '2020-07-12 11:00:00', 0, 'Odsustvo' , null, 1, 1,1);

insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-07-20 12:00:00', 'nema opisa', '2020-07-12 11:00:00', 0, 'Odmor' , 1, null, 1,1);

insert into odsustvo (kraj, opis, pocetak, status, tip, lekar_id, med_sestra_id, admin_klinike_id,version)
values('2020-07-22 12:00:00', 'nema opisa', '2020-07-15 11:00:00', 0, 'Odmor' , 2, null, 1,1);


--operacije

insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-06-05 12:00:00', 4, 30, 1, null, 1, 1,1);
insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-06-05 10:00:00', 4, 30, 1, null, 2, 1,2);
insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-06-06 10:00:00', 4, 30, 1, null, 3, 1,3);

insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-06-06 19:00:00', 4, 30, 4, null, 3, 2,3);

insert into operacija_lekar(operacija_id, lekar_id) values(1,1);
insert into operacija_lekar(operacija_id, lekar_id) values(2,1);
insert into operacija_lekar(operacija_id, lekar_id) values(3,1);
insert into operacija_lekar(operacija_id, lekar_id) values(4,2);


--zahtevi za operaciju

insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-07-05 12:00:00', 3, 30, null, null, 1, 1,1);
insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-07-06 10:00:00', 3, 30, null, null, 2, 1,2);
insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-07-07 10:00:00', 3, 30, null, null, 3, 1,3);

insert into operacija (datum_vreme,status, trajanje, sala_id, stavka_cenovnika_id, zkarton_id, klinika_id, pacijent_id) 
values('2020-07-08 19:00:00', 3, 30, null, null, 3, 2,3);

insert into operacija_lekar(operacija_id, lekar_id) values(5,1);
insert into operacija_lekar(operacija_id, lekar_id) values(6,1);
insert into operacija_lekar(operacija_id, lekar_id) values(7,1);
insert into operacija_lekar(operacija_id, lekar_id) values(8,2);


insert into zahtev_registracije(ime, prezime, email, lozinka, adresa, grad, drzava, kontakt, lbo) values ('Stefana', 'Jokic', 'trajkovicka.9909@gmail.com', 'asdf', 'Bul. Mihajlo Pupin 18', 'Novi Sad', 'Srbija', '065123456', '3lbo133131');
insert into zahtev_registracije(ime, prezime, email, lozinka, adresa, grad, drzava, kontakt, lbo) values ('Dejan', 'Bodiroga', 'dejanzahteva@gmail.com', 'asdf', 'Bul. Mihajlo Pupin 22', 'Novi Sad', 'Srbija', '063232456', '3lb231231');
