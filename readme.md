# Bank-prosjekt

[Åpne i Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2422/gr2422?new)

Dette prosjektet er en bankapplikasjon utviklet i JavaFX som skal gi brukeren mulighet til å adminstrere sine bankkontoer. For mer innformasjon om hvordan appen fungerer, se [`bank\readme.md`](bank/readme.md)

## Struktur
Prosjektet bruker Maven for bygging og kjøring og er delt inn i følgende moduler: 
- [`core`](bank/core): Inneholder kode og ressurser for kjernelogikken til applikasjonen, som f.eks. klasser som håndterer oppretting av ny bruker. 
- [`ui`](bank/ui): Inneholder kode og ressurser for brukergrensesnittet til applikasjonen, som fxml-filer og kontrollerklasse. 
- [`persistence`](bank/persistence): Inneholder kode og ressurser for lagring av data til fil, som lagring av en brukers saldo. 
- [`api`](bank/api): Inneholder kode og ressurser for applikasjonens API. 

## Bygging og kjøring av prosjektet
Prosjektet bruker Maven 3.9.9 med Java 17 til bygging og kjøring. 

Rotmappa til Maven-prosjektet er [`bank`](bank)-mappa. 
- For å bygge prosjektet, kjør `mvn install` fra [`bank`](bank)-mappa. 
- For å kjøre prosjektet, kjør `mvn javafx:run` fra [`bank\ui`](bank/ui)-mappen. 
- For å kun kjøre testene, kjør `mvn test` fra [`bank`](bank)-mappa. Dette vil også genere en rapport for testdekningsgrad som man finner i `target\site` i den aktuelle modulen.