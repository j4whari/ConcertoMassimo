document.addEventListener('DOMContentLoaded', function () {
    console.log("Form page script loaded");

    // Recupera i valori salvati in localStorage
    const savedTotal = localStorage.getItem('ticketTotal');
    const savedTicketType = localStorage.getItem('ticketType');
    console.log("Retrieved from localStorage:", savedTotal, savedTicketType);

    if (savedTotal) {
        // Converti in numero e gestisci eventuali errori di parsing
        const ticketPrice = parseFloat(savedTotal) || 0;
        console.log("Parsed ticket price:", ticketPrice);

        // Aggiorna il display del prezzo del biglietto
        // Usa l'ID 'ticketPriceDisplay' che abbiamo impostato nella sezione riepilogo
        const ticketPriceElement = document.getElementById('ticketPriceDisplay');
        if (ticketPriceElement) {
            if (savedTicketType) {
                ticketPriceElement.textContent = `€ ${ticketPrice.toFixed(2)} (${savedTicketType})`;
            } else {
                ticketPriceElement.textContent = `€ ${ticketPrice.toFixed(2)}`;
            }
        }

        // Calcola il totale finale aggiungendo commissioni e IVA
        const serviceCharge = 9.90;
        const vat = 5.60;
        const finalTotal = ticketPrice + serviceCharge + vat;
        console.log("Final calculation:", ticketPrice, "+", serviceCharge, "+", vat, "=", finalTotal);

        // Aggiorna il totale finale nella sezione riepilogo
        // Usa l'ID 'totalPriceDisplay' per aggiornare il valore dinamicamente
        const totalPriceElement = document.getElementById('totalPriceDisplay');
        if (totalPriceElement) {
            totalPriceElement.textContent = `€ ${finalTotal.toFixed(2)}`;
        }

        // Se esiste un elemento per il totale nella schermata di pagamento, aggiorna anche quello
        const finalTotalElement = document.getElementById('final-total');
        if (finalTotalElement) {
            finalTotalElement.textContent = `${finalTotal.toFixed(2)}€`;
        }
    } else {
        console.log("No ticket price found in localStorage");
    } });

//     // Gestione dei bottoni e del form di pagamento
//     const payButtons = document.querySelectorAll("#payButton, #payButtonSummary");
//     const loadingScreen = document.getElementById("loadingScreen");
//     const paymentScreen = document.getElementById("paymentScreen");
//     const paymentForm = document.getElementById("paymentForm");
//
//     if (payButtons && loadingScreen && paymentScreen && paymentForm) {
//         payButtons.forEach(button => {
//             button.addEventListener("click", function (event) {
//                 event.preventDefault();
//                 loadingScreen.style.display = "flex";
//                 setTimeout(() => {
//                     loadingScreen.style.display = "none";
//                     paymentScreen.style.display = "flex";
//                 }, 2000);
//             });
//         });
//
//         paymentForm.addEventListener("submit", function (event) {
//             event.preventDefault();
//             loadingScreen.style.display = "flex";
//             setTimeout(() => {
//                 loadingScreen.style.display = "none";
//                 alert("Pagamento effettuato con successo!");
//                 window.location.href = "/biglietto"; // Reindirizza alla pagina di conferma
//             }, 2000);
//         });
//     } else {
//         console.error("Uno o più elementi per il pagamento non trovati nel DOM.");
//     }
// });
//
// // Funzione per calcolare e aggiornare il totale finale (se necessiti di richiamarla altrove)
// function updateFinalTotal(ticketPrice) {
//     const serviceCharge = 9.90;
//     const vat = 5.60;
//     const finalTotal = (ticketPrice + serviceCharge + vat).toFixed(2);
//     const finalTotalElement = document.getElementById('final-total');
//     if (finalTotalElement) {
//         finalTotalElement.textContent = `${finalTotal}€`;
//     } else {
//         console.error("Elemento 'final-total' non trovato nel DOM.");
//     }
// }