# Utfordringer vi møtte i prosjektet

## Utfordring 1: Betaling mellom kontoer
En av de største utfordringene oppstod i core, da vi skulle finne en måte å overføre penger mellom kontoer som tilhører ulike brukere. Dette kom av at vi hadde to klasser - Account, som er klassen for hver konto, og User, som er klassen for hver bruker, og som inneholder en liste med alle kontoene som tilhører brukeren.

Vi så på ulike løsninger på problemet. Vi undersøkte for eksempel muligheten for at Account-klassen selv kunne håndtere en overføring, eller at User-klassen kunne brukes til å verifisere eierskap av kontoene. Den beste løsningen konkluderte vi at var å opprette en egen klasse, Bank, som instansierer alle overføringer mellom både interne kontoer for en bruker, og eksterne kontoer mellom to forskjellige brukere. Vi valgte denne løsningen da den var mer oversiktlig og strukturert. Klassen delegerer både til User-klassen for å validere input, og til Account for å ta seg av selve overføringen. 

Av dette lærte vi fordelen med tydelig ansvarsfordeling i koden, og at egne klasser bør ha en tydelig rolle, da dette fører til en mer oversiktlig struktur, og gjør koden lettere å forstå. Det ga oss også lærdom rundt hvordan ulike klasser kan samhandle på best mulig måte.

## Utfordring 2: Overgang til klient-server struktur
En annen utfordring var refaktoreringen og utvidelsene som krevdes i utbyggingen av API-et.  
I prosjektets tidlige faser var hele applikasjonen bygget som en samlet løsning, der både frontend og backend var tett integrert i samme struktur. Altså hadde ui-modulen direkte kommunikasjon med både core og persistence. 

I senere releaser skulle vi gå over til en to-delt løsning hvor vi skulle utvikle et REST API som skulle tilbys av en web-server og deretter at klienten benyttet seg av dette API-et. En slik overgangen viste seg å være en betydelig endring i arkitekturen og krevde en grundig refaktorering av flere moduler.

For å lage API-et ble ulike alternativer vurdert, men Spring Framework ble valgt da det virket som et lett, men likevel godt alternativ. Overgangen ble startet ved å definere tydelige REST API-endepunkter som frontend kunne bruke til å hente og sende data til backend. Ulike formateringer på endepunktene ble prøvd, men formatet vi valgte til slutt mener vi følger gode REST-standarder som logisk nesting av entiteter og bruken av nouns i endepunktene. Videre ble backend refaktorert for å håndtere API-forespørsler, og vi benyttet persistence-modulen for lagring av data som API-et mottok. Frontend-klienten ble deretter tilpasset til å gjøre HTTP-kall til API-endepunktene i stedet for å ha direkte tilgang til backend.

Overgangen krevde nøye og isolert testing for å sikre at all kommunikasjon mellom frontend og backend fungerte som forventet. Feilbehandling og mocking i enhetstestene ble viktige elementer for å sikre stabilitet og identifisere problemer tidlig. Selv om det var en tidkrevende prosess å gjøre denne overgangen, har den gjort systemet mer modulært, enklere å vedlikeholde og lettere å skalere i fremtiden.