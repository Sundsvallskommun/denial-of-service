# Denial-Of-Service

## Leverantör

Sundsvalls kommun

## Beskrivning
Denial-Of-Service är ett verktyg som är konstruerat för att lasttesta tjänster via HTTP-GET eller HTTP-POST.
Verktyget är baserat på [Gatling](https://gatling.io/)


## Tekniska detaljer

### Konfigurera simuleringar
Öppna och editera filen: /src/test/resources/simulation.conf

Beroende på om du skall köra anrop baserade på HTTP GET eller HTTP POST editerar du motsvarande objekt i filen enligt tabellen nedan.

|Property|Beskrivning|
|---|---|
|`scenarioName`|Scenarionamn (skrivs ut i loggar, etc).|
|`url`|URL till tjänsten/endpointen som skall lasttestas.|
|`authorizationHeader`|Anger värdet i headern "Authorization". T.ex. "Bearer eyJ4NXQiOiJ...".|
|`acceptHeader`|Anger värdet i headern "Accept". T.ex. "application/json".|
|`contentTypeHeader`|Anger värdet i headern "Content-Type". T.ex. "application/json".|
|`requestBody`|Body för HTTP-POST requests.|
|`constant.usersPerSec`|Antal requests/users per sekund.|
|`constant.duringSeconds`|Antal sekunder att köra simulering.|

### Kör tjänsten (d.v.s. exekvera last-test)

Kör detta script och följ sedan scriptets instruktioner:

```
./run.sh
```

## 
Copyright (c) 2021 Sundsvalls kommun