document.addEventListener('DOMContentLoaded', function() {
    console.log("Form page script loaded");

    // Get the saved values from localStorage
    const savedTotal = localStorage.getItem('ticketTotal');
    const savedTicketType = localStorage.getItem('ticketType');
    console.log("Retrieved from localStorage:", savedTotal, savedTicketType);

    if (savedTotal) {
        // Convert to number and handle parsing errors
        const ticketPrice = parseFloat(savedTotal) || 0;
        console.log("Parsed ticket price:", ticketPrice);

        // Update ticket price display with ticket type
        const ticketPriceElement = document.querySelector('.summary-item:first-child span:nth-child(2)');
        if (ticketPriceElement) {
            if (savedTicketType) {
                ticketPriceElement.textContent = `€ ${ticketPrice.toFixed(2)} (${savedTicketType})`;
            } else {
                ticketPriceElement.textContent = `€ ${ticketPrice.toFixed(2)}`;
            }
        }

        // Calculate final total with fixed fees
        const serviceCharge = 9.90;
        const vat = 5.60;
        const finalTotal = ticketPrice + serviceCharge + vat;
        console.log("Final calculation:", ticketPrice, "+", serviceCharge, "+", vat, "=", finalTotal);

        // Update total in the summary section
        const summaryTotalElement = document.querySelector('.summary-total span:nth-child(2)');
        if (summaryTotalElement) {
            summaryTotalElement.textContent = `€ ${finalTotal.toFixed(2)}`;
        }

        // Update final total in the payment button
        const finalTotalElement = document.getElementById('final-total');
        if (finalTotalElement) {
            finalTotalElement.textContent = `${finalTotal.toFixed(2)}€`;
        }
    } else {
        console.log("No ticket price found in localStorage");
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Get the saved total from localStorage
    const savedTotal = localStorage.getItem('ticketTotal');

    // Find the ticket price element in the summary section
    const ticketPriceElement = document.querySelector('.summary-item span:nth-child(2)');

    // Update it with the saved total (if available)
    if (savedTotal) {
        // Format the price with two decimal places and € symbol
        ticketPriceElement.textContent = `€ ${parseFloat(savedTotal).toFixed(2)}`;

        // Calculate and update the final total
        updateFinalTotal(parseFloat(savedTotal));
    }

    // Function to calculate and update the final total
    function updateFinalTotal(ticketPrice) {
        const serviceCharge = 9.90;
        const vat = 5.60;

        // Calculate total with all components
        const finalTotal = (ticketPrice + serviceCharge + vat).toFixed(2);

        // Update the final total display
        document.getElementById('final-total').textContent = `${finalTotal}€`;
    }
});
document.addEventListener("DOMContentLoaded", function() {
    const payButtons = document.querySelectorAll("#payButton, #payButtonSummary");
    const loadingScreen = document.getElementById("loadingScreen");
    const paymentScreen = document.getElementById("paymentScreen");
    const paymentForm = document.getElementById("paymentForm");

    payButtons.forEach(button => {
        button.addEventListener("click", function(event) {
            event.preventDefault();
            loadingScreen.style.display = "flex";
            setTimeout(() => {
                loadingScreen.style.display = "none";
                paymentScreen.style.display = "flex";
            }, 2000);
        });
    });

    paymentForm.addEventListener("submit", function(event) {
        event.preventDefault();
        loadingScreen.style.display = "flex";
        setTimeout(() => {
            loadingScreen.style.display = "none";
            alert("Pagamento effettuato con successo!");
            window.location.href = "/biglietto"; // Reindirizza alla pagina di conferma
        }, 2000);
    });
});
