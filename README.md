# FLUSSO DI ESECUZIONE

Register:

L'utente visita http://localhost:8080/showRegister.

Il metodo showRegisterPage nel AuthController restituisce la vista register.html.

L'utente compila il form e invia i dati.

Il form invia una richiesta POST a /controller/register.

Il metodo register nel TicketController elabora i dati:

Se l'utente esiste già, reindirizza a /showRegister con un messaggio di errore.

Se la registrazione ha successo, reindirizza a /showRegister con un messaggio di successo.

La pagina register.html visualizza il messaggio di errore o successo.


Login
Decidere il tipo di biglietto
Incremento del numero di biglietti e aggiornare poi il prezzo di conseguenza
Biglietto PDF
