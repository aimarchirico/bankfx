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

### Nyttige kommandoer
- For å fjerne gamle Maven build-filer, kjør `mvn clean` fra [`bank`](bank)-mappa. 
- For å kjøre testene, kjør `mvn test` fra [`bank`](bank)-mappa. Dette vil også genere en rapport for testdekningsgrad som man finner i `target\site` i den aktuelle modulen.
- For å bygge prosjektet, kjør `mvn install` fra [`bank`](bank)-mappa. 
- For å kjøre prosjektet, kjør `mvn javafx:run` fra [`bank\ui`](bank/ui)-mappa. 


### Stegvis forklaring for kjøring
1. Kjør `mvn --version` for å verifisere at riktig Maven og Java er installert på datamaskinen din. Får du feilmelding har du ikke installert det riktig eller ikke lagt det til i PATH. 
2. For å navigere inn i [`bank`](bank)-mappa, kjør `cd bank` gitt at du er helt i roten av prosjektet.
3. Kjør `mvn clean install` .
4. Kjør `cd ui`.
5. Kjør `mvn javafx:run`.
5. Hvis du har gjort det riktig skal appen nå åpnes!