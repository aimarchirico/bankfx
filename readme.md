# Bank-prosjekt

[Åpne i Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2422/gr2422?new)

Dette prosjektet er en bankapplikasjon utviklet i JavaFX som skal gi brukeren mulighet til å adminstrere sine bankkontoer. For mer innformasjon om hvordan appen fungerer, se [`bank/readme.md`](bank/readme.md)

## Struktur
Prosjektet bruker Maven for bygging og kjøring. 

Rotmappa til Maven-prosjektet er [`bank`](bank)-mappa og har følgende moduler: 
- [`core`](bank/core): Inneholder kode og ressurser for kjernelogikken til applikasjonen, som f.eks. klasser som håndterer oppretting av ny bruker. 
- [`ui`](bank/ui): Inneholder kode og ressurser for brukergrensesnittet til applikasjonen, som fxml-filer og kontrollerklasse. 
- [`persistence`](bank/persistence): Inneholder kode og ressurser for lagring av data til fil, som lagring av en brukers saldo. 
- [`api`](bank/api): Inneholder kode og ressurser for applikasjonens API. 

## Nødvendige versjoner
- Maven 3.9.9
- Java 17.0.12-tem

Kjør `mvn -version` for å verifisere at riktig versjon av Maven og Java er innstallert. Dersom versjonene er feil, kan det ikke garanteres at prosjektet kjøres. Får du feilmelding har du ikke installert dem riktig.

## Nyttige kommandoer
- Fra [`bank`](bank)-mappa kan du:
  - Kjøre `mvn clean` for å fjerne tidligere kompilert versjon.
  - Kjøre `mvn compile` for å kompilere kildekoden.
  - Kjøre `mvn test` for å kjøre testene. Dette vil også genere en rapport for testdekningsgrad som man finner i `target/site` i den aktuelle modulen.
  - Kjøre `mvn install` for å installere prosjektet lokalt
- Fra [`bank/ui`](bank/ui)-mappa kan du:
  - Kjøre `mvn javafx:run` for å starte appen (må først kompileres).

## Stegvis forklaring for kjøring
1. Kjør `cd bank` (gitt at du starter helt i rotmappa).
2. Kjør `mvn -version` for å verifisere riktig installasjon.
3. Kjør `mvn clean install`.
4. Kjør `cd ui`.
5. Kjør `mvn javafx:run`.
6. Hvis du har gjort det riktig skal appen nå åpnes!

Alternativt er det laget en VSCode task ved navn `run_all` som vil gjøre steg 1-5 for deg. 
Det er også en task ved navn `run` som kjører `compile` fremfor `install`, og derfor ikke kjøre alt av kvalitetssjekker og tester. Den kjører heller ikke `mvn -version` for å sjekke riktige versjoner. Fordelen er at dersom alt allerede er riktig så vil den åpne appen mye raskere. 