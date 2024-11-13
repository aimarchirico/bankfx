# Utfordringer vi støtte på i prosjektet

## Utfordring 1
En av de største utfordringene oppstod i core, da vi skulle finne en måte å overføre penger mellom kontoer som tilhører ulike brukere. Dette kom av at vi hadde to klasser - account, som er klassen for hver konto, og user, som er klassen for hver bruker, og som inneholder en liste med alle kontoene som tilhører brukeren.

Vi så på ulike løsninger på problemet. Vi undersøkte for eksempel muligheten for at account-klassen selv kunne håndtere en overføring, eller at user-klassen kunne brukes til å verifisere eierskap av kontoene. Den beste løsningen konkluderte vi at var å opprette en egen klasse, Bank, som instansierer alle overføringer mellom både interne kontoer for en bruker, og eksterne kontoer mellom to forskjellige brukere. Vi valgte denne løsningen da den var mer oversiktlig og strukturert. Klassen delegerer både til user-klassen for å validere input, og til account for å ta seg av selve overføringen. 

Av dette lærte vi fordelen med tydelig ansvarsfordeling i koden, og at egne klasser bør ha en tydelig rolle, da dette fører til en mer oversiktlig struktur, og gjør koden lettere å forstå. Det ga oss også lærdom rundt hvordan ulike klasser kan samhandle på best mulig måte