# ConcertoMassimo
ConcertoMassimo è un’applicazione web sviluppata in Java con Spring Boot per la gestione della vendita e della generazione di biglietti per eventi musicali. Il sistema consente agli utenti di registrarsi, effettuare login, selezionare il tipo di biglietto (Standard, VIP, Platea, Tribuna, ecc.) e acquistarlo. Inoltre, l’app genera dinamicamente il biglietto in formato PDF, integrando un QR code per la verifica dell’autenticità.

### Caratteristiche principali
Registrazione e autenticazione: Gli utenti possono registrarsi e autenticarsi tramite una semplice interfaccia.
Selezione del biglietto: Vengono proposti vari tipi di biglietto (ad esempio, Standard, VIP, Platea, Tribuna) con prezzi differenti.
Aggiornamento dati anagrafici: I dati personali dell’utente vengono aggiornati nel database durante il processo di acquisto.
Generazione del biglietto in PDF: Utilizzando Apache PDFBox, il sistema genera un file PDF personalizzato con il nome, il cognome, il tipo di biglietto e un QR code (generato con ZXing) che rimanda a informazioni sull’evento.
Interfaccia utente responsive: L’applicazione utilizza HTML, CSS e JavaScript per garantire un’esperienza utente fluida sia su desktop che su dispositivi mobili.
### Tecnologie utilizzate
- Java 21 – Linguaggio di programmazione principale
- Spring Boot – Framework per la creazione dell’applicazione web
- Spring Security – Gestione dell’autenticazione e della sicurezza
- Spring Data JPA – Per l’accesso al database (MySQL)
- Thymeleaf – Motore di template per la generazione delle view HTML
- Apache PDFBox – Libreria per la creazione e la manipolazione dei file PDF
- ZXing – Libreria per la generazione e la lettura di codici QR
- MySQL – Database relazionale per la memorizzazione dei dati
### Installazione
##### Prerequisiti
- Java 21 o versione successiva
- Maven
- MySQL (configurare un database chiamato ConcertoMassimo in locale)
- IDE (ad esempio, IntelliJ IDEA o Eclipse)
Configurazione del database
Nel file **`src/main/resources/application.properties**` assicurati che i parametri di connessione al database siano corretti:

```
spring.datasource.url=jdbc:mysql://localhost:3308/ConcertoMassimo
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
### Build e avvio dell’applicazione
1- Clona il repository:

```bash
git clone https://github.com/tuo-username/ConcertoMassimo.git
cd ConcertoMassimo
```

2- Costruisci il progetto con Maven:

```bash
mvn clean install
```

3- Avvia l’applicazione:

```bash
mvn spring-boot:run
```

Oppure esegui la classe principale **`ConcertoMassimoApplication.java**` dal tuo IDE.

## Utilizzo
**Homepage**: Accedi alla homepage per visualizzare le immagini degli eventi in corso.

**Login/Registrazione**: Clicca sul pulsante "Accedi" per autenticarti o registrarti.

**Selezione Biglietto**: Sulla pagina principale, scegli il tipo di biglietto desiderato (Standard, VIP, Platea, Tribuna). Il prezzo verrà aggiornato dinamicamente.

**Form d’Ordine**: Dopo aver selezionato il biglietto, verrai reindirizzato ad un form dove potrai inserire o aggiornare i tuoi dati anagrafici e le preferenze di consegna.

**Pagamento e Generazione PDF**: Dopo aver completato l’ordine, verrai indirizzato alla pagina di pagamento e successivamente potrai scaricare il biglietto in formato PDF, che includerà un QR code per la verifica.

### Come funziona
**Apache PDFBox**: La libreria PDFBox è utilizzata per creare documenti PDF a partire da template predefiniti. Nel progetto, viene caricata un’immagine di sfondo (il template del biglietto) su cui vengono disegnati testo e QR code.

**ZXing**: La libreria ZXing genera un QR code in formato immagine. Questo QR code viene poi integrato nel PDF generato, offrendo un ulteriore livello di sicurezza e autenticità al biglietto.
### Contribuire
Le contribuzioni sono le benvenute! Se hai suggerimenti, correzioni o nuove funzionalità da proporre, crea una issue o invia una pull request.

## Licenza
Questo progetto è distribuito con la licenza **MIT License**.

## Contatti
Per eventuali domande o informazioni, contattaci via email all'indirizzo: mohamedelja@hotmail.it, ivanamoruso976@gmail.com.

