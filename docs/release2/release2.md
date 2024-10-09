# Release 2 dokumentasjon

## Innhold i denne releasen
I andre release har vi hovedsakelig hatt fokus på å kvalitetsjekke kode og strukturere filstrukturer fra forrige release. Vi har også startet med å legge til funksjonalitet for bankkontoer. Det meste av arbeidet for bankkontoer er gjort i core-modulen og er derfor ikke ferdig implementert i ui. Grunnfunksjonalitet som oppretting og lagring av bankkontoer i ui er implementert. 

### Modularisering:
Ettersom prosjektet vårt allerede var modularisert forrige release, trengte vi ikke å gjøre noen store strukturelle endringer denne releasen. Vi har kun måtte endret noen av pom-filene for å legge til nye plugins. Og vi har lagt til module-info filer i modulene. 

### Kodekvalitet: 
Vi har blant annet implementert kvalitetssjekkere som SpotBugs for å sjekke etter bugs og Chekstyle for å verifisere at kodestilen vår følger gode standarder. Vi har brukt reglene til Google Java Style for å sjekke koden vår og gjort endringer på skrevet kode der nødvendig. Testdekning med Jacoco var allerede implementert forrige release. UI-modulen testes med TestFX og vi har implementert mulighet for å teste dem i bakgrunnen (headless mode). 

### Dokumentasjon:
Vi har lagt til Javadoc på alle klasser og metoder. I rotmappa ligger det også et package-diagram som viser filstrukturen på Maven-prosjektet. Vi utvidet også med én ny brukerhistorie. 

### Arbeidsvaner:
I denne releasen hadde vi fokus på gode arbeidsvaner. Vi lagde issues på alt av oppgaver vi gjorde og alle commits ble gjort til egen branch for så å opprette en merge request inn til master. Alle commit-meldinger er riktig strukturert med en oversikt over scope, type og beskrivelse av hva som er endret. Vi drev med parprogrammering og dokumenterte co-author som en fotnote i commit-meldinger. 

### Persistens:
For lagring av data har vi brukt json. Filen [UserData.json](../../bank\persistence\src\main\resources\bank\persistence\UserData.json) er strukturert som en liste av [User](../../bank\core\src\main\java\bank\core\User.java)-instanser med feltene `ssn`, `name` og `password`. Denne er også utvidet til å inneholde feltet `accounts` som da er en liste bestående av en brukers tilhørende [Account](../../bank\core\src\main\java\bank\core\Account.java)-instanser. Vi bruker json fordi det er lett lesbart og nyttig for lagring av hierarkisk data slik User- og Account-klassene inneholder. 

### Ny funksjonalitet
Vi begynte å implementere funskjonalitet for bankkontoer. Da lagde vi nye klasser Account og Bank med tilhørende tester. Vi har implementert det meste av kjernelogikken for Account, User og Bank. Funksjonalitet når Account er ferdig implementert med ui, er betaling, overføring, innskudd og uttak (se [bank/readme.md](../../bank/readme.md)). Foreløpig er det kun mulig å opprette og å se eksisterende bankkontoer i UI-et. UI-et er også utbedret slik at det er mer brukervennlig og penere. Det er også gjort noen endringer i VSCode config filene. Blant annet er det lagt til anbefalte utvidelser og en task som automatisk kjører nødvendige mvn commands i terminalen for å installere og starte appen (se [readme.md](../../readme.md)).

## Gjennomførte milepæler og issues
- *Milepæl 1: Testing av moduler*
  - tester til Account
  - implementer Checkstyle og Spotbugs for sjekking av kodekvalitet
  - skriv ui-test til OverviewController.java
  - skriv ui-test til LoginController.java
  - skriv ui-test til CreateUserController.java
  - skriv ui-test til BankApp.java
  - skriv test til UserDeserializer-klassen
  - skriv test til UserDataStorage-klassen
  - skriv test til BankPersistence-klassen
  - legg til ui headlessTests
  - oppdater test til Bruker-klassen for å dekke ny funskjonalitet
  - tester til Account
  - lag test til Bank klasse
- *Milepæl 2: Bankkontoer*
  - lage Account klasse
  - oppdater readme til å illustrere ny funksjonalitet for bankkontoer
  - utvid Bruker-klassen til å inneholde kontoer (med saldo)
  - utvid med flere brukerhistorier
  - utvid Account-funksjonalitet
  - fix login bug på branch 39
  - legge til kontonummer i Account klassen og oppdatere tilhørende klasser
  - lagring av kontoer i json
  - spotbug in Bank.java
  - legge til kontonummer i UserDeserializer
- *Milepæl 3: Dokumentasjon til release 2*
  - dokumenter arkitektur med minst et diagram (bruk PlantUML) i tillegg til teksten i readme
  - skriv release 2 dokumentasjon
  - fjern duplikat tekst "For å kun kjøre testene..." i readme
  - utvid mvn kommando forklaring
  - skriv release 2 dokumentasjon
- *Diverse issues:*
  - lag penere ui
  - implementer sletting av brukere fra json
  - legg til module-info.java i modulene
  - legg til TestUtils og Utils klasser for lagring av filstier
  - oppdater UserDataStorage til å ta inn argument for filsti
  - legg til vscode config filer

## Plan for videre arbeid
- Implementer alt av Account/User-funksjonalitet i ui. Det innebærer at man skal kunne overføre, betale, gjøre innskudd og uttak. Det er lagd skisser for hvordan dettte bør se ut. 
- Mer robust testdekning. Per nå er testdekning på 87%, 84% og 74% for hhv. core, persistence og ui. Det som må testes grundigere neste release blir hovedsakelig funksjonalitet knyttet til bankkontoer. 