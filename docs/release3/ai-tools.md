# **Bruk av KI-verktøy i prosjektarbeidet**

I release 3 har vi som i realese 2 brukt KI-generert kode på enkelte områder. All KI-generert kode er tydelig merket med kommentarer som angir start og slutt, samt kildehenvisning. Vi har brukt KI-generert kode hovedsakelig i to ulike situasjoner:

Tilegnelse av ny kunnskap: Når vi skulle implementere kode vi ikke har erfaring med, har KI vært et nyttig verktøy for læring. For eksempel brukte vi KI til å forstå regex-uttrykk for passordvalidering i release 2, og etter denne erfaringen kunne vi selv lage lignende uttrykk senere i prosjektet. Når vi lagde REST API-et ble KI brukt for å få en innføring i Spring Framework. Da fikk vi gode eksempler på MVC-formatet til Spring-applikasjonen og eksempler på strukturen til metoder for GET/POST/DELETE. Etter en rask innføring, tilegnet vi kunnskapen til å lage våre egne API-endepunkt som kommuniserte med logikken til applikasjonen vår i `core` og `persistence`. 

Feilsøking og debugging: Vi har i hovedsak debugget selv, men ved fastlåste situasjoner har KI vært et nyttig supplement for å identifisere bugs. Ved pakking av prosjektet, fikk vi problemer med lagring av data til json. Filen var lagret i prosjektmappen og det skapte problemer ved lagring av dataen. Med litt ChatGPT og undersøkelse på nettet fant vi ut av en bedre standard for lagring av data som da ble å lagre i user.home-mappen  under .gr2422/bank/. Eventuelt vil mappen oprettes dersom den ikke først eksisteres. 

Vi vil understreke at KI aldri har generert hele klasser eller metoder, men kun har blitt brukt som et supplement til vår egen programmering.
