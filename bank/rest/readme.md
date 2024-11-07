# Bank API Dokumentasjon

## Generell informasjon
Bank API-en tilbyr en RESTful tjeneste for å administrere brukere og kontoer i en bank. API-et gir funksjonalitet for å legge til og slette brukere, opprette og slette kontoer, samt utføre innskudd, uttak, overføringer, og betalinger. 

API-et forventer at alle forespørsler som krever brukerautentisering sender fødselsnummer (`ssn`) og passord (`password`) som parameter.

**Base URL**: `localhost:8080/bank`

## Endepunkter

### Hent bruker
- **Endepunkt**: `GET /users/{ssn}`
- **Beskrivelse**: Henter informasjon om en spesifikk bruker.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `password` (query): Passordet til brukeren.
- **Suksessrespons**: 200 OK, returnerer brukerdata.

### Legg til bruker
- **Endepunkt**: `POST /users/{ssn}`
- **Beskrivelse**: Legger til en ny bruker i banken.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `user` (body): JSON-objekt som inneholder brukerens informasjon.
- **Suksessrespons**: 201 Created, returnerer brukerdata.

### Slett bruker
- **Endepunkt**: `DELETE /users/{ssn}`
- **Beskrivelse**: Sletter en spesifikk bruker fra banken.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `password` (query): Passordet til brukeren.
- **Suksessrespons**: 200 OK, returnerer slettet brukerdata.

### Legg til konto
- **Endepunkt**: `POST /users/{ssn}/accounts/{account}`
- **Beskrivelse**: Legger til en konto for en spesifikk bruker.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `account` (path): Kontonummeret som skal legges til.
  - `password` (query): Passordet til brukeren.
  - `account` (body): JSON-objekt som inneholder kontoinformasjon.
- **Suksessrespons**: 201 Created, returnerer brukerdata.

### Slett konto
- **Endepunkt**: `DELETE /users/{ssn}/accounts/{account}`
- **Beskrivelse**: Sletter en konto fra en spesifikk bruker.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `account` (path): Kontonummeret som skal slettes.
  - `password` (query): Passordet til brukeren.
- **Suksessrespons**: 200 OK, returnerer brukerdata.

### Overføring
- **Endepunkt**: `POST /users/{ssn}/accounts/{sourceAccount}/transfer`
- **Beskrivelse**: Utfører en overføring mellom to kontoer.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `sourceAccount` (path): Kontonummeret for avsenderkontoen.
  - `password` (query): Passordet til brukeren.
  - `targetAccount` (query): Målkontonummeret.
  - `amount` (query): Beløpet som skal overføres.
- **Suksessrespons**: 200 OK, returnerer brukerdata.

### Betaling
- **Endepunkt**: `POST /users/{ssn}/accounts/{sourceAccount}/payment`
- **Beskrivelse**: Utfører en betaling fra en konto til en annen konto.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `sourceAccount` (path): Kontonummeret for avsenderkontoen.
  - `password` (query): Passordet til brukeren.
  - `targetAccount` (query): Målkontonummeret.
  - `amount` (query): Beløpet som skal betales.
- **Suksessrespons**: 200 OK, returnerer brukerdata.

### Uttak
- **Endepunkt**: `POST /users/{ssn}/accounts/{account}/withdraw`
- **Beskrivelse**: Utfører et uttak fra en spesifikk konto.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `account` (path): Kontonummeret det skal tas ut fra.
  - `password` (query): Passordet til brukeren.
  - `amount` (query): Beløpet som skal tas ut.
- **Suksessrespons**: 200 OK, returnerer brukerdata.

### Innskudd
- **Endepunkt**: `POST /users/{ssn}/accounts/{account}/deposit`
- **Beskrivelse**: Utfører et innskudd til en spesifikk konto.
- **Parameter**:
  - `ssn` (path): Fødselsnummeret til brukeren.
  - `account` (path): Kontonummeret det skal settes inn på.
  - `password` (query): Passordet til brukeren.
  - `amount` (query): Beløpet som skal settes inn.
- **Suksessrespons**: 200 OK, returnerer brukerdata.


## Feilmeldinger

Her er en oversikt over mulige HTTP-statuskoder og beskrivelse av hver feilmelding:

| Statuskode | Beskrivelse                                                                                                       |
|------------|-------------------------------------------------------------------------------------------------------------------|
| 200 OK     | Forespørselen ble behandlet korrekt og inneholder de forventede dataene.                                          |
| 201 Created| Brukeren eller kontoen ble opprettet korrekt.                                                                     |
| 400 Bad Request | Forespørselen inneholder ugyldig data eller format som ikke kan behandles av serveren.                      |
| 403 Forbidden   | Brukeren oppga feil legitimasjon (fødselsnummer og/eller passord) for å få tilgang til kontoen eller utføre handlingen. |
| 404 Not Found   | Brukeren som er forespurt finnes ikke i systemet. Dette kan oppstå ved feil fødselsnummer.  |
| 409 Conflict    | Uoverensstemmelse mellom data i forespørselen og eksisterende data. Dette kan gjelde fødselsnummer eller kontonummer. |
| 500 Internal Server Error | En intern serverfeil oppstod mens serveren forsøkte å lagre bruker- eller kontodata.                  |
