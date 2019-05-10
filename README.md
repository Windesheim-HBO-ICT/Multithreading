# Multithreading
Een thread is een proces wat gebruikt wordt voor de uitvoer van code. Standaard wordt één thread gestart voor de uitvoer van een applicatie. Deze enkele thread is dus verantwoordelijk voor de uitvoer van alle code van de applicatie. Zo ook het afhandelen van de gebruikers invoer zoals muisklikken of toetsenbord aanslagen. En ook het bijwerken van het scherm (bijv de paintComponent).

Willen we echter een stuk code continu laten uitvoeren in een loop, zoals bij het luisteren naar inkomende connecties of berichten, dan legt dit beslag op deze enkele thread en is er geen tijd meer om de code voor de schermafhandeling uit te voeren. Resultaat: de applicatie reageert niet meer.

Om dit op te lossen kunnen we gebruik maken van meerdere threads. We voeren daarmee de code die continu moet worden uitgevoerd op een aparte thread (= apart proces) uit, waarmee we de 'hoofd'-thread vrij houden voor de afhandeling van het scherm en de gebruikers input.

Deze repository bevat voorbeeld code voor de toepassing van multithreading in een Java applicatie. De code is niet optimaal, maar bewust zo eenvoudig mogelijk gehouden om de kern van het verhaal duidelijk te maken.

## singleThreaded
Dit deel bestaat uit een enkel JFrame met twee knoppen. Zodra je op de knop "Start Listening" klikt, wordt een continue loop gestart om te luisteren naar clients die connecten. De continue loop (en het wachten op listener.accept()) zorgen ervoor dat de enige thread voortdurend bezet is en dus geen tijd meer heeft voor de afhandeling van gebruikers invoer. In dit voorbeeld reageert de applicatie dus niet meer op muisklikken of bijvoorbeeld het veranderen van de grootte van het scherm.

## multiThreaded
In dit deel is de code voor het luisteren naar de clients verplaatst naar een aparte klasse: de Server klasse. Door deze de interface Runnable te laten implementeren is deze klasse te starten binnen een aparte thread. Hierdoor blijft de applicatie dus gewoon reageren op gebruikers input. Zodra een client verbinding maakt, dan levert dit een Socket object op. Deze plaatsen we vervolgens ook weer op een aparte thread in de vorm van een Client klasse zodat we tegelijkertijd kunnen communiceren met meerdere clients. We houden een lijst bij met daarin alle verbonden clients.

We hebben hier dus één 'hoofd'-thread voor de afhandeling van de user interface. Daarna een tweede thread voor het luisteren naar clients en daarna nog per client een aparte thread voor het communiceren met die client.

## callbackInterface
We gaan hier nog een stapje verder, want zodra we een bericht ontvangen van een client, dan moet dit bijvoorbeeld op het scherm worden getoond. Hiervoor is een Callback interface toegevoegd die we implementeren in het Hoofdscherm. Door nu het Hoofdscherm mee te geven aan de Server en op zijn beurt weer aan de Client, heeft de Client de mogelijkheid om de messageRecieved functie van het Hoofdscherm aan te roepen.

## client
Een simpele client die je vanaf de commandline meerdere keren kan starten om zo de connectie met de server te testen. Deze werkt samen met de applicatie van callbackInterface en kan gestart worden een ip adres, poort nummer en client naam als argument:
```
java -cp dist/Threads.jar client.Startup 127.0.0.1 59090 Client1
```
Door de client meerdere keren te starten met verschillende namen, kun je de communicatie met verschillende clients tegelijkertijd testen.
