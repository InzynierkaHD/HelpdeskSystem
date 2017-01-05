SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'

ALTER DATABASE `helpdesk` CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `uzytkownicy` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `typy_zgloszen` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `priorytety` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `produkty` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `firmy` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `firma_produkt` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `pracownicy` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `przedstawiciele` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `klienci` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `administratorzy` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `zgloszenia` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `komentarze` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `statusy` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `powiadomienia` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `powiadomienia_uzytkownika` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `historia_logowania` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `historia_statusow` CONVERT TO CHARACTER SET utf8;

------------TYPY_ZGŁOSZEŃ----------------

INSERT INTO `typy_zgloszen`(`Id_Typ`, `Nazwa`) VALUES (1,"Instalacja")
INSERT INTO `typy_zgloszen`(`Id_Typ`, `Nazwa`) VALUES (2,"Uruchomienie")
INSERT INTO `typy_zgloszen`(`Id_Typ`, `Nazwa`) VALUES (3,"Praca")
INSERT INTO `typy_zgloszen`(`Id_Typ`, `Nazwa`) VALUES (4,"Zapytanie")
INSERT INTO `typy_zgloszen`(`Id_Typ`, `Nazwa`) VALUES (5,"Inne")


--------------PRIORYTETY--------------

INSERT INTO `priorytety`(`Id_Priorytet`, `Nazwa`, `Stopien_waznosci`) VALUES (1,"Wysoki","4")
INSERT INTO `priorytety`(`Id_Priorytet`, `Nazwa`, `Stopien_waznosci`) VALUES (2,"Normalny","3")
INSERT INTO `priorytety`(`Id_Priorytet`, `Nazwa`, `Stopien_waznosci`) VALUES (3,"Niski","2")

--------------PRODUKTY--------------

INSERT INTO `produkty`(`Id_Produkt`, `Cena`, `Nazwa`, `Opis`) VALUES (1,"1999","POS System","POS system to rozbudowana aplikacja do kierowania i prowadzenia sklepu")
INSERT INTO `produkty`(`Id_Produkt`, `Cena`, `Nazwa`, `Opis`) VALUES (2,"4299","Azer","Aplikacja sluzaca do komunikacji z klientami")
INSERT INTO `produkty`(`Id_Produkt`, `Cena`, `Nazwa`, `Opis`) VALUES (3,"999","CompanyMenager","Ogromna aplikacja do kierowania interesem")
INSERT INTO `produkty`(`Id_Produkt`, `Cena`, `Nazwa`, `Opis`) VALUES (4,"499","Contacter","Najlepszy komunikator dla firmy z ogromnym wyborem opcji")

-------------FIRMY-----------------------

INSERT INTO `firmy`(`Id_Firma`, `Kod_pocztowy`, `Miejscowosc`, `Nazwa`, `Numer`, `Ulica`) VALUES (1,"21032","Lublin","ERPS Systemy","123","Jana Pawła")
INSERT INTO `firmy`(`Id_Firma`, `Kod_pocztowy`, `Miejscowosc`, `Nazwa`, `Numer`, `Ulica`) VALUES (2,"47925","Warszawa","BUDSTAN","54a","Leszczyny")
INSERT INTO `firmy`(`Id_Firma`, `Kod_pocztowy`, `Miejscowosc`, `Nazwa`, `Numer`, `Ulica`) VALUES (3,"94630","Gdynia","Komplex","13/23","Brezi")
INSERT INTO `firmy`(`Id_Firma`, `Kod_pocztowy`, `Miejscowosc`, `Nazwa`, `Numer`, `Ulica`) VALUES (4,"14585","Kielce","BerlinSTONE","45","Domaszowice")
INSERT INTO `firmy`(`Id_Firma`, `Kod_pocztowy`, `Miejscowosc`, `Nazwa`, `Numer`, `Ulica`) VALUES (5,"15273","Katowice","MEGA-KOMP","5a","Nowego Skoszyna")

---------FIRMA-PRODUKT---------------

INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (1,1,1,1)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (2,1,1,2)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (3,1,1,4)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (4,1,2,2)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (5,1,2,3)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (6,1,3,4)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (7,1,4,1)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (8,1,4,3)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (9,1,5,2)
INSERT INTO `firma_produkt`(`Id_Firma_Produkt`, `Czy_pomoc_aktywna`, `Id_firmy`, `Id_produktu`) VALUES (10,1,5,3)

-----------UZYTKOWNICY---------------

---------------Przedstawiciele-------------


INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (1,0,0,"koc12@gmail.com","28408ddfc43556c8aac697c4ed68eeaf3c59b539b3ab90b43f8728e5361f949a","Piotr","p.koc","Koc",'2017-01-02 04:19:27',0,"765982563")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (2,0,0,"daniel.rakowski@o2.com","0911365a68adbadc9d27636abdf6158e0187ab186201d536df8a91d7d3e5dd6d","Daniel","daniel12","Rakowski",'2017-01-02 11:11:43',0,"674671263")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (3,0,0,"magda1234@gmail.com","94167247eb4ad5ff91c0173f6ebbe2d119908719169a7a95efcf59b3c3f31277","Magdalena","magda.wrona","Wrona",'2016-11-22 18:39:31',0,"880672451")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (4,0,0,"robert.twardowski@gmail.com","fb6f5b3267c8155170c3c84b2ea09d0164e26f85dc62b6c42d1fd45fb7d07cca","Robert","twardowskir","Twardowski",'2016-12-29 12:32:43',0,"562294182")

INSERT INTO `przedstawiciele`(`Id_Pzedstawiciel`, `Id_firmy`, `Id_uzytkownika`) VALUES (1,1,1)
INSERT INTO `przedstawiciele`(`Id_Pzedstawiciel`, `Id_firmy`, `Id_uzytkownika`) VALUES (2,2,2)
INSERT INTO `przedstawiciele`(`Id_Pzedstawiciel`, `Id_firmy`, `Id_uzytkownika`) VALUES (3,3,3)
INSERT INTO `przedstawiciele`(`Id_Pzedstawiciel`, `Id_firmy`, `Id_uzytkownika`) VALUES (4,4,4)


---------------KlienciFirma1-------------


INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (5,0,0,"maria123@wp.com","449f67c6f79f3af19b90996ce0d7776fccf72be29665de1e315cd58fd593df31","Maria","mariakowal","Kowal",'2017-01-01 15:10:02',0,"658254912")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (6,0,0,"jan.kret1@o2.com","810f03144fb60da94c7ec1c8e683bd858fef926e7d81f017d3e9489b803658cb","Jan","jan.kret1","Kret",'2017-01-01 12:43:21',0,"674681095")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (7,0,0,"adamowski88@gmail.com","f5055e89209a740d1cf9fe0a0d4d6982a07e3fd4f05689aad66925165ca0f420","Krystian","krystian88","Adamowski",'2017-01-02 17:21:55',0,"688765431")

INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (1,1,1,5)
INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (2,1,1,6)
INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (3,1,1,7)


---------------KlienciFirma2-------------

INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (8,0,0,"tworek123@gmail.com","bb47fad3a8690b305dceb97c83fec01d11e70747f2726277920c9767f7ec4dad","Manuel","tworek123","Tworek",'2016-12-30 13:54:21',0,"699870450")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (9,0,0,"marian.oleszczak@o2.pl","f1ca3c394c6d156c720270508f92d234868bd1b642aab4100a59bcf2144f72d8","Marian","moleszczak","Oleszczak",'2017-01-01 22:32:12',0,"778900762")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (10,0,0,"teresa123@gmail.com","e541e51aa827a68b92d194df6ad231459d02085ff667136ce5c7919386fcd5d7","Teresa","nowacka1","Nowacka",'2016-12-28 20:43:21',0,"667894562")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (11,0,0,"kopytkopiotr@wp.com","63c4dcb1aa33df181e4931bb56d01f5c5d3bc792c0f9536a3d20cf228517e976","Piotr","pkopytko1","Kopytko",'2017-01-02 12:12:43',0,"887645124")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (12,0,0,"anna.nowak@gmail.com","dcce6b2538824a00efbb7210e364b2ec038edc6f1e32679e3678a6ba558d4758","Anna","anowak","Nowak",'2017-01-01 15:55:21',0,"781231232")

INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (4,2,2,8)
INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (5,2,2,9)
INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (6,2,2,10)
INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (7,2,2,11)
INSERT INTO `klienci`(`Id_Klient`, `Id_przedstawiciela`, `Id_firmy`, `Id_uzytkownika`) VALUES (8,2,2,12)

---------------KlienciFirma3-------------




---------------KlienciFirma4-------------



-----------Pracownicy-------------------

INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (13,0,0,"damianrak2@gmail.com","24bbaa38d4f74642b11ba4cc948daa5a93b37f5f6d264903b318ae32b9eb4e08","Damian","damianrak","Rak",'2017-01-02 15:23:21',0,"5567812561")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (14,0,0,"borecki.krzysztof@wp.pl","654fa6c0b05fe604a53cb89d6de9dcc760204f26947176aee08e1c3cc1f72014","Krzysztof","borecki.krzysztof","Borecki",'2017-01-02 16:01:43',0,"889651452")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (15,0,0,"edyta.ilczuk@gmail.com","bee058c8fad261aaade46716a92fae4279aea200a923f11684e87f03bda89bef","Edyta","edyta.ilczuk","Ilczuk",'2017-01-02 14:15:54',0,"565891452")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (16,0,0,"adam.gozdur1@gmail.com","66c16d7b8633128905834fad8887f38947209a1c6d8e9f8ff21d49da69db017d","Adam","gozdura","Gozdur",'2017-01-01 09:05:41',0,"996483211")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (17,0,0,"fuskamil@gmail.pl","965cfd009c9ad13a8c1140a54ff3e0af4721ba4a6b92723b47359c30017cf944","Kamil","fuskamil","Fus",'2017-01-02 14:22:32',0,"668465827")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (18,0,0,"kbartnik@o2.com","6214797cde53a6cc5921aaebaabdedb2855c0f1b3dc1af80ccd228b462d8d7a2","Katarzyna","bartnikkatarzyna","Bartnik",'2017-01-02 09:56:43',0,"785621781")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (19,0,0,"dariusz.rabowski@gmail.com","8bd155200fea22188b9103f485fc58f2e1b3b4ede62c160a719e4e90bbdb900a","Dariusz","rabowski12","Rabowski",'2017-01-02 13:32:54',0,"642995012")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (20,0,0,"nysaarkadiusz21@wp.pl","8291cc11b0d2c5b1ca817a83ace0587643244f60af90302e92d4cfdfd500248b","Arkadiusz","areknysa","Nysa",'2017-01-01 10:54:02',0,"768946105")

INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (1,13)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (2,14)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (3,15)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (4,16)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (5,17)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (6,18)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (7,19)
INSERT INTO `pracownicy`(`Id_Pracownik`, `Id_uzytkownika`) VALUES (8,20)

-----------Administratorzy-------------------




INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (21,0,0,"a.widowski@gmail.com","233d729799b0a3f16be76691059e90ecca4c92bb149feec3b21a45e7c851d72d","Adam","widowski","Widowski",'2017-01-02 17:54:32',0,"781337854")
INSERT INTO `uzytkownicy`(`Id_Uzytkownika`, `Czy_blokowany`, `Czy_usuniety`, `Email`, `Haslo`, `Imie`, `Login`, `Nazwisko`, `Ostatnie_logowanie`, `Bledne_logowania`,`Telefon`) VALUES (22,0,0,"t.dzienowski@gmail.com","eb07aab58eaae5b13316d918c173849426b2da22d2c06e2147b964d60fcb7716","Tomasz","dzienowski","Dzienowski",'2017-01-01 15:13:21',0,"754772927")


INSERT INTO `administratorzy`(`Id_Administrator`, `Id_uzytkownika`) VALUES (1,21)
INSERT INTO `administratorzy`(`Id_Administrator`, `Id_uzytkownika`) VALUES (2,22)





----------ZGLOSZENIA----------

INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('1', '2017-01-03 10:54:38', NULL, 'Dziwny komunikat przy korzystaniu z aplikacji', 'Witam, mam problem przy korzystaniu z aplikacji, mianowicie po 10 minutach od uruchomienia pojawia sie komunikat:"ERROR NO: 123232! Contact with us!"', '1', '1', '3', '1', '1');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('2', '2016-11-30 21:34:54', NULL, 'Temat7', 'To jest 7 temat', '1', '4', '1', '5', '2');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('3', '2017-01-02 11:43:02', NULL, 'Temat7', 'To jest 7 temat', '1', '2', '1', '2', '3');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('4', '2016-12-12 14:26:54', NULL, 'Temat7', 'To jest 7 temat', '1', '5', '3', '4', '4');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('5', '2016-11-23 12:43:01', NULL, 'Temat7', 'To jest 7 temat', '1', '2', '1', '1', '2');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('6', '2016-12-30 22:47:55', NULL, 'Temat7', 'To jest 7 temat', '1', '5', '2', '4', '1');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('7', '2016-09-23 04:57:23', NULL, 'Temat7', 'To jest 7 temat', '1', '6', '3', '3', '5');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('8', '2016-10-14 16:23:54', NULL, 'Temat7', 'To jest 7 temat', '1', '8', '1', '3', '6');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('9', '2016-11-04 08:43:19', NULL, 'Temat7', 'To jest 7 temat', '1', '4', '2', '2', '7');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('10', '2016-11-21 14:12:26', NULL, 'Temat7', 'To jest 7 temat', '1', '1', '3', '5', '2');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('11', '2016-12-12 17:54:47', NULL, 'Temat7', 'To jest 7 temat', '1', '8', '3', '1', '4');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('12', '2017-01-01 10:37:07', NULL, 'Temat7', 'To jest 7 temat', '1', '7', '1', '2', '5');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('13', '2016-08-27 21:59:54', NULL, 'Temat7', 'To jest 7 temat', '1', '7', '2', '5', '6');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('14', '2017-01-02 12:52:31', NULL, 'Temat7', 'To jest 7 temat', '1', '5', '3', '4', '2');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('15', '2016-09-10 16:35:22', NULL, 'Temat7', 'To jest 7 temat', '1', '3', '2', '2', '5');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('16', '2016-12-11 18:20:58', NULL, 'Temat7', 'To jest 7 temat', '1', '6', '3', '3', '1');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('17', '2016-10-25 20:39:19', NULL, 'Temat7', 'To jest 7 temat', '1', '2', '1', '1', '5');
INSERT INTO `zgloszenia` (`Id_Zgloszenie`, `Data_dodania`, `Data_zakonczenia`, `Temat`, `Tresc`, `Id_firma_produkt`, `Id_wlasciciela`, `Id_priorytetu`, `Id_typu`, `Id_user`) VALUES ('18', '2016-12-30 12:23:35', NULL, 'Temat7', 'To jest 7 temat', '1', '1', '1', '4', '8');


--------KOMENTARZE------------




---------STATUSY-----------


INSERT INTO `statusy`(`Id_Status`, `Nazwa`) VALUES (null,"Nowe")
INSERT INTO `statusy`(`Id_Status`, `Nazwa`) VALUES (null,"Przyjete")
INSERT INTO `statusy`(`Id_Status`, `Nazwa`) VALUES (null,"Odrzucone")
INSERT INTO `statusy`(`Id_Status`, `Nazwa`) VALUES (null,"Zrealizow




----HISTORIA STATUSOW------------

INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,1,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,2,2)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,3,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,4,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,5,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,6,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,7,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,8,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,9,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,10,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,11,1)
INSERT INTO `historia_statusow`(`Id_Hist_Statusu`, `Data`, `Id_pracownika`, `Id_zgloszenia`, `Id_statusu`) VALUES (null,'2016-12-11',1,12,1)

-------POWIADOMIENIA---------


INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (1,"Nowy klient_przedst","Dodanie nowego wspolpracownika")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (2,"Nowy klient_admin","Dodanie nowego wspolpracownika")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (3,"Nowy klient_klient","Dodanie nowego pracownika do firmy przez przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (4,"Nowy przedstawiciel","Dodanie nowego przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (5,"Nowy pracownik","Dodanie nowego pracownika")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (6,"Blokowany prz_prz_a","Zablokowanie przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (7,"Blokowany uz_prz_a","Zablokowanie klienta przez administratora")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (8,"Usuniety uz_prz_a","Usuniecie klienta przez administratora")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (9,"Blokowany uz_prz_p","Zablokowanie klienta przez przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (10,"Usuniety uz_prz_p","Usuniecie klienta przez przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (11,"Blokowany pra_prz_a","Zablokowanie pracownika")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (12,"Usuniety pra_prz_a","Usuniecie pracownika")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (13,"Usuniety prz_prz_a","Usuniecie przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (14,"Odblokowany prz_prz_a","Odblokowanie przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (15,"Odblokowany pra_prz_a","Odblokowanie pracownika")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (16,"Odblokowany uz_prz_a","Odblokowanie klienta przez administratora")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (17,"Odblokowany uz_prz_p","Odblokowanie klienta przez przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (18,"User_zabl_a","Konto zostalo zablokowane przez administratora")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (19,"User_odbl_a","Konto zostalo odblokowane przez administratora")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (20,"User_zabl_p","Konto zostalo zablokowane przez przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (21,"User_odbl_p","Konto zostalo odblokowane przez przedstawiciela klienta")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (22,"User_dodanie_kom","Dodanie nowego komentarza do zgloszenia")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (23,"User_dodanie_zgl","Dodanie nowego zgloszenia")
INSERT INTO `powiadomienia`(`Id_Powiadomienie`, `Nazwa`, `Tresc`) VALUES (24,"Zmiana_statusu","Zmiana statusu zgloszenia")