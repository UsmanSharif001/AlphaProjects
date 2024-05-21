# Projektkalkuleringsværktøj for Alpha Solutions
## Værktøjet til projekter, der skaber struktur, overblik og en fælles vision.
Velkommen til GitHub repositoriet for AlphaProjects!

Dette projekt er lavet af 4 datamatiker studerende på KEA (Københavns Erhvervsakademi) i forbindelse med 2. semester eksamen. Projektet er lavet i samarbejde med Alpha Solutions, som har ageret kravstiller, og desuden en underviser som PO (product owner). Projektet skal forestille et internt projektkalkuleringsværktøj, der muliggør at nedbryde projekter til subprojekter og opgaver. AlphaProjects har til formål, at skabe netop dette gennem en intuitiv og overskuelig platform. 

AlphaProjects benytter SpringBoot for backend logik, Thymeleaf til frontend templating og MySQL til databasestyring. Desuden er projektet designet til en CI-CD pipeline, ved hjælp af Github actions til test og Microsoft Azure til deployment. 

## Funktionalitet
Når du første gang kommer til siden, bliver du bedt om at logge in. Herfra kommer du til et overblik over projekter. Hvis du har admin rettigheder, vil det være muligt at kunne tilgå medarbejedernes rettigheder og skills. 
Udover at kunne nedbryde projekter i subprojekter og opgaver, er der implementeret CRUD-funktioner (Create, Read, Update & Delete) i alle lag. AlphaSolutions udtrykte begejstring for konceptet 'shared vision', hvorfor der i projektoverblikket, er implementeret en mulighed for at tilgå netop dette. Denne funktion er med til at skabe en fælles vision blandt projektdeltagerne, således de får en fælles vision og forståelse, og arbejder i samme retning. 
Hele webapplikationen er selfølgelig pakket pænt ind i en UI(picoCSS) der skaber sammenhæng og intuitiv brugervenlighed. 

Link til deployed version(ikke indsat endnu)

## Teknologier
* Backend: SpringBoot (Java)
* Frontend: Thymeleaf, CSS, HTML
* Database: MySQL
* CI/CD: GitHub Actions
* Deployment: Azure Web Apps

## Kom igang
### Forudsætninger
* Java JDK 17 eller nyere
* Maven
* MySQL Server
* Azure konto

### Installation
1. Klon git repository: https://github.com/UsmanSharif001/AlphaProjects.git
2. Opret MySQL database 'alphasolution_db'. Kør MySQL scripts fra src/main/resources/sql.
3. Konfigurer applikationsindstillinger: Rediger src/main/resources/application.properties , så du kan indstille databasetilslutninger:
```
spring.datasource.url=jdbc:mysql://localhost:3306/alphasolution_db (indsæt evt. deployed database)
spring.datasource.username=root
spring.datasource.password=password1
```
4. Byg og kør projektet med Maven:
```
mvn package
java -jar target/opskriftsbog-0.0.1-SNAPSHOT.jar
```
5. Applikationen kan nu køres lokalt på http://localhost:8080

### Testing og Deployment med GitHub ActionsCI/CD
For kontinuerlig integration og deployment benyttes GitHub Actions. Workflowet for CI/CD processerne er defineret i main.yml filen, som inkluderer opgaver som automatiske tests, bygninger, og deployment til Azure Web Apps.

### Bidrag
Alle er velkomne til at komme med bidrag, vi ser dog helst at du læser CONTRIBUTE.md, hvor vi har beskrevet retningslinjerne for kommende bidrag.

### Licens
Dette projekt er udgivet under MIT licensen.

### Kontakt
Hvis du har spørgsmål, er du velkommen til at sende en email til mala0007@stud.kea.dk, thsk0001@stud.kea.dk, ussh0001@stud.kea.dk eller nipe0001@stud.kea.dk.
