# Bidrag

Nedenfor har vi kort beskrevet bidragene hver av medlemmene har hatt på prosjektet. 

## Aimár

I starten jobbet jeg litt med initielle kontrollere og scener i `ui`-modulen, og lagde en grundig plan, vha. Figma, på hvordan designet av appen skulle se ut videre. Jeg jobbet også med å koble ui til persistence-modulen. 

Senere tok jeg på meg ansvar for å utvikle og teste REST-serveren og API-et, samt å lage en access-klasse i brukergrensesnittet (`ui`) som håndterer kommunikasjon med API-et. Målet har vært å sikre en stabil, sikker og godt dokumentert kommunikasjon mellom frontend og backend for å gi en best mulig brukeropplevelse. 

Jeg har også jobbet med å etablere og vedlikeholde prosjektets struktur og arbeidsvaner, som å legge til alt av nødvendige avhengigheter og plugins, oppsett av che og pipelines, oppsett av maler for issues, merge requests og commits, og å løse eventuelle konflikter i byggingen av prosjektet. 

I tillegg har jeg gjort justeringer i øvrige moduler for bedre ytelse og kodekvalitet. Blant annet har jeg lagt til flere tester for å få testdekningen opp til over 80% og fikset alt av stilformat for å samsvare med Google Java Style. Og ved å lage diverse diagrammer og oppdatere samtlige `README`-filer regelmessig har jeg også sørget for at all nødvendig informasjon for prosjektet er tilgjengelig.

## Felix
Jeg jobbet i starten av prosjektet med å utvikle kjernefunksjonaliteten(core) til applikasjonen. Jeg fikk da ansvar til å lage Account klassen til første release. I tillegg utviklet jeg første utgave av overføring mellom kontoer, og persistens, hvor jeg laget "UserDeserializer", ved bruk av jackson bibilioteket, for release1, som kun lagret brukerdata.

Til release 2 jobbet jeg med å videreutvikler JSON-lagringen ved å legge til lagring av kontoer. Dette innebærte også å oppdatere lagring av brukere etter hvert som User-klassen ble større.

Til release 3 har jeg jobbet med å koble til login UI-et til det nye REST-apiet, ved å endre på de eksisterende UI-klassene. Dette innebærte også å gjøre store endringer i testklassene i UI, slik at de ikke avhenger av at apiet kjører. I tillegg til å oppdatere de eksisterende klassene, videreutviklet jeg UI-et til å inkludere "transfer" og "deposit", med deres egne testklasser.

Til siste release har jeg også laget klassediagramet som representerer kjernefunksjonaliteten, vha. plantUML, og dokumentasjonen over bærekraftsvirkningene til applikasjonen. I tillegg har jeg gjort en del feilretting av UI-et, slik at brukeren får bedre tilbakemeldinger og stopper å krasje.

## Kasper

I starten av prosjektet begynte jeg med å skrive tester i core, der jeg utviklet userTest, som tester grunnleggende funksjonalitet.

Etter hvert begynte jeg med å utvikle UI, der jeg fokuserte på å lage en intuitiv og enkel brukeropplevelse. Jeg utviklet flere av klassene, for eksempel "overview" og "newAccount", samt fortsatte å utvikle userTest til å dekke ny funksjonalitet.

Til release 3 har jeg fortsatt å utvikle UI, der jeg måtte endre flere av de eksisterende kontrollerne for å koble de til REST-apiet, samt endret og opprettet de tilhørende testklassene. Jeg utviklet også UI til å ta imot ny og avgjørende funksjonalitet som "deleteAccount", "payment" og "withdrawal", og opprettet tilhørende testklasser

Til slutt bidro jeg til dokumentasjon ved å skrive om utfordringene i prosjektet, samt har jeg fikset en del småting i UI for å maksimere brukeroplevelsen.

## Sindre

Jeg startet med å jobbe i Core-modulen, der jeg laget User-klassen for å etablere grunnleggende brukerfunksjonalitet. Deretter jobbet jeg med å utvikle flere tester i persistence-modulen, for å øke testdekningen og sikre stabiliteten i systemet.

Jeg introduserte Bank-klassen som en styrende klasse for Core, som også delegerer oppgaver videre; dette krevde også endringer i Core-modulen for å tilpasse den nye strukturen.

Videre jobbet jeg med å tilpasse flere av klassene i UI til å fungere med Rest-API-et for å bedre koblingen mellom UI og serverkommunikasjonen. For å strukturere controller-hierarkiet, laget jeg en Controller-superklasse som de andre kontrollerklassene arver fra, noe som bidrar til en mer ryddig kodebase.

Til slutt bidro jeg til prosjektets dokumentasjon, inkludert AI-verktøydokumentasjon, UML-diagram og release-dokumentasjon, noe som har sikret en klar oversikt over prosjektets struktur og funksjonalitet.
