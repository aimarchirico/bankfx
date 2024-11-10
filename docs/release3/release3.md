# Release 3 dokumentasjon

Denne versjonen av prosjektet introduserer flere viktige funksjoner og forbedringer, som bidrar til et mer robust, brukervennlig og release-klart system.

## Hva er nytt i denne releasen?

### 1. **REST API Implementering**
Vi har implementert et fullt REST API som muliggjør kommunikasjon mellom front-end og back-end. Dette skiller logisk mellom brukergrensesnittet og serverlogikken, noe som gir bedre skalerbarhet og vedlikeholdbarhet. Front-end kan nå sende forespørsler til back-end via API-et, som svarer med de nødvendige dataene. Se [`rest/readme.md`](../../bank/rest/readme.md) for grundig informasjon om hvordan API-et fungerer.  

### 2. **Funksjonalitet for Bruker- og Kontoadministrasjon**
I denne versjonen har vi lagt til funksjonalitet som gjør det mulig å:
- **Slette bruker og konto:** Brukere kan nå slette både sin egen brukerprofil og tilhørende konto.
- **Overføring, betaling, innskudd og uttak av penger:** Vi har utviklet og integrert funksjoner som tillater innskudd, uttak, betaling og overføringer mellom konti.

### 3. **Diagrammer**
Vi har lagt til relevante diagrammer for å bedre illustrere systemets arkitektur og flyt. Disse diagrammene bidrar til å gi både utviklere og brukere en bedre forståelse av hvordan systemet fungerer. Diagrammene ligger i [`diagrams`](../../diagrams)-mappen, men er også illustrert i diverse readme-filer.  

### 4. **Installerbar fil**
Som en del av denne releasen har vi lagt til støtte for å pakke prosjektet til en installasjonsfil. Denne funksjonaliteten gjør det enklere å distribuere og installere applikasjonen. Den nødvendige informasjonen for hvordan dette kan gjøres er lagt til i [`readme`](../../readme.md)-filen.

### 5. **Testing og kodekvalitet**
Testing har vært en prioritet i denne releasen, og alle moduler er grundig testet. Samtlige moduler har en testdekning på over 80%, som sikrer høy kodekvalitet og stabilitet. Vi har opprettholdt testing gjennom hele utviklingsprosessen, og resultatene av testene er dokumentert. 

Samtlige klasser samsvarer med Google Java Style og ingen spotbugs er oppdaget, noe som sikrer god, oversiktlig og kvalitetsikret kode. 

### 6. **Arbeidsvaner og Prosess**
- **Issues, Merge Requests og Commits:** Vi har vært veldig grundige med å lage detaljerte issues, merge requests, og commits. Vi har brukt maler for å sikre at all nødvendig informasjon blir dokumentert og håndtert på en strukturert måte.
- **Språkbruk:** Større dokumentasjon, merge requests og issues har blitt skrevet på norsk, mens all kode, Javadoc og commits er skrevet på engelsk. Dette sikrer at vi har et felles språk i koden, samtidig som kommunikasjonen rundt prosjektet er tilgjengelig på norsk.

## Mulige skaleringer
Eventuelle fremtidige versjoner kan inneholde blant annet:
- En sikrere måte å kommunisere fødselsnummer og passord over API-en på. 
- Utvidet funksjonalitet som transaksjonshistorikk.
- Mulighet for endring av kontonavn og andre profilinnstillinger.

## Gjennomførte milepæler og issues
- *Milepæl 1: REST API*
  - feat(ui) koble newAccount til rest-api
  - feat(ui): koble createUserController til rest-api
  - feat(ui): koble overview til rest-api
  - feat(ui): koble login til rest-api
  - feat(rest): lag rest/BankApplication
  - feat(ui): lag UserAccess for knytte ui til api
  - feat(rest): lag rest/BankController
  - test(rest): implementer tester for rest modulen
  - feat(rest): lag BankService
- *Milepæl 2: Utvidet funksjonalitet Release 3*
  - feat(mvn): legg til plugins for pakking av prosjekt
  - feat(ui): implementer sletting av bankkonto i ui
  - feat(ui): implementer uttak av penger i ui
  - feat(ui): implementer betaling til ekstern konto i ui
  - feat(ui): implementer overføring mellom egne kontoer i ui
  - feat(ui): lage Transfer og Deposit UI
  - feat(ui): oppdater overview og newaccount for å ta imot ny funksjonalitet
  - feat(ui): Lag ny superklasse Controller
  - feat(ui): implementer sletting av bruker i ui
  - fix(ui): transfer,withdrawal,deposit krasjer dersom konto ikke er valgt
  - fix(ui): feilmelding må vises ved ugyldig transfer/deposit
  - fix(core): sett maks beløpsgrense for inntak
  - fix(ui): vis kontonummer i overview
  - fix(core): sett krav om bruker-unikt navn for bankkonto
  - fix(ui): funksjonell navigeringsmeny for alle skjermer
  - fix(ui): forbedre format på feilmelding når kontonummer er ugyldig
- *Milepæl 3: Release 3 dokumentasjon*
  - docs(templates): mindre endringer i merge request templates
  - docs(templates): maler til issues og merge requests
  - docs(readme): legg til spotbugs og checkstyle info i readme
  - docs(rest): REST dokumentasjon
  - docs(diagram): oppdater pakkediagram
  - docs(diagram): lag sekvensdiagram
  - docs(diagram): lag klassediagram
  - docs(ai-tools): Oppdater ai-tools.md
  - docs(sustainability): dokumenter bærekraftsaspekter
  - docs(readme): vis diagram i readme
  - docs(contribution): dokumenter bidrag
  - docs(challenges): dokumenter utfordringer
  - docs(release3): skriv release 3 dokumentasjon