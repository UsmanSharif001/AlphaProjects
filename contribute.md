# Bidrag til vores projekt:

## Forudsættende krav for at kunne bidrage til projektet
1. Hav installeret et integreret udviklingsmiljø til brug af hentning & redigerering af projektet. I vores projekt har vi brugt IntelliJ IDEA
2. JDK 17: For at kunne biddrage til projektet skal du bruge JDK 17-version - dette er blevet brugt til opsætning af serveren samt selve projektet (se under pom.xml l. 16)
3. Maven skal være installeret på din computer for at kunne køre programmet - du kan med fordel i din terminal skrive: mvn install for at få afhængighederne
4. MYSQL: I dette projekt bruger mysql-connector-j (se under pom.xml l.38-44) til at forbinde med databasen MYSQL-workbench. Hav derfor installeret MYSQL-workbench.
5. MYSQL: Hav indlæst de nyeste scripts for at være opdateret på ændringer i serveren. Dette kan være enten tabelstrukturen, men også indholdet af data.
6. Microsoft Teams: Kontakt os på Microsoft Teams eller bliv en del af projektgruppen NUMT - Alpha Solution

## Generelt
- For at vi alle kan forstå og blive bedre på projektet sammen ønsker vi at det bliver brugt de standardiseret kodekonventioner. Dvs. standard beskrivende navne & camelCase
- Hvis der bliver lavet tilføjelser bliver der skrevet i pull-requesten hvad dit commit handler om, hvor det kan ses og hvorfor disse ændringer er relevante
- Lav ikke tilføjelser hvor noget virker halvt eller ikke er testet igennem. Dette vil spare både dig og os unødig besvær
- Tænk logisk, hvis en feature du gerne vil lave har noget med f. eks Task at gøre skal du bidrage med det i det dataflow hvor Task indgår - i dette tænkte eksempel vil det sige TaskController,  -Service, -Repository osv.
- Ved tilføjelse / ændring af projektet hav gerne opdateret vores åbne project "AlphaProject" under Projects på GitHub. Brug de rigtige issues og sæt det ind i det korrekte view samt kolonne. 
- Ved ændring af den generelle struktur og større refaktorering vil vi gerne underrettes
- Afslutningsvis er alle niveauer velkomne til at bidrage til projektet. Dette er især vigtigt for os at understrege da vi også selv er studerende i faget.


## Setup

Efter de forudsættende krav skal du nu hente projektet!

1. Før du går igang anbefaler vi at du også undersøger vores AlphaProject ud inde under Projects på GitHub vil du finde projektet "AlphaProject"
2. Dette gøres ved at klone projektet - vi anbefaler at du sætter dig ind i vores projekt ved at undersøge klasserne og opsætningen.
3. Dernæst læs & forstå vores modeller vi bruger til projektet for at give et overskueligt overblik
4. Husk at hvis der går tid mellem dette opdater da din main lokalt. Der kan forekomme nye tilføjelser eller ændringer, som kan have betydning for hvad I/du vil bidrage med
5. Efter en opdateret lokal main, kan du nu lave en branch. Denne branch har som regelt navnet "hvad-du-gerne-vil-tilføje-feature"


**I/du ønskes en god fornøjelse og held & lykke med dine tilføjelser - de bedste kodehilsener NUMT-projektgruppen**

