# Bidrag

## Aimár

I starten jobbet jeg litt med initielle kontrollere og scener i ui-modulen, og lagde også en grundig plan på hvordan designet av appen skulle se ut videre. Jeg jobbet også med å koble ui til persistence-modulen etter at den var laget.

Senere fikk jeg ansvar for å utvikle og teste REST-serveren og API-et, samt å lage en access-klasse i brukergrensesnittet (ui) som håndterer tilkoblingen til API-et. Målet har vært å sikre en stabil og godt dokumentert kommunikasjon mellom systemets komponenter, noe som legger grunnlaget for en helhetlig og funksjonell løsning.

Jeg har også jobbet med å etablere og vedlikeholde prosjektets struktur, inkludert å legge til alt av nødvendige avhengigheter og plugins, og å løse eventuelle konflikter i byggingen av prosjektet, som gjør at prosjektet kjører effektivt og kan videreutvikles uten problemer.

I tillegg har jeg gjort noen justeringer i modulene core, persistence og ui for bedre ytelse og kodekvalitet. Blant annet har jeg lagt til litt diverse tester for å øke testdekningen og fikset alt av stilformat for å samsvare med Google Java Style. Ved å oppdatere diverse README-filer regelmessig har jeg også sørget for at all nødvendig informasjon for prosjektet er tilgjengelig.

## Felix

## Kasper

## Sindre

Jeg startet med å jobbe i Core-modulen, der jeg laget User-klassen med tilhørende UserTest for å etablere grunnleggende brukerfunksjonalitet. Deretter jobbet jeg med å utvikle flere tester i persistence-modulen, for å øke testdekningen og sikre stabiliteten i systemet.

Jeg introduserte Bank-klassen som en styrende klasse for Core, som også delegerer oppgaver videre; dette krevde også endringer i Core-modulen for å tilpasse den nye strukturen.

Videre jobbet jeg med å tilpasse flere av klassene i UI til å fungere med Rest-API-et for å bedre koblingen mellom UI og serverkommunikasjonen. For å strukturere controller-hierarkiet, laget jeg en Controller-superklasse som de andre kontrollerklassene arver fra, noe som bidrar til en mer ryddig kodebase.

Til slutt bidro jeg til prosjektets dokumentasjon, inkludert AI-verktøydokumentasjon, UML-diagram og release-dokumentasjon, noe som har sikret en klar oversikt over prosjektets struktur og funksjonalitet.
