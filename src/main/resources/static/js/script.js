// script.js
let quantity = 1;
let basePrice = 0;
let selectedTicketType = '';

function changeQuantity(change) {
    quantity += change;
    if (quantity < 1) quantity = 1;
    if (quantity > 4) quantity = 4;  // Limitiamo a 4 biglietti
    document.getElementById('quantity').value = quantity;
    updateTotal();
}

function updateTotal() {
    const total = basePrice * quantity;
    document.getElementById('total').innerText = `${total}€`;
}

const ticketButtons = document.querySelectorAll('.ticket-button');
ticketButtons.forEach(button => {
    button.addEventListener('click', function() {
        // Rimuove la classe 'selected' da tutti i pulsanti
        ticketButtons.forEach(btn => btn.classList.remove('selected'));
        // Aggiunge la classe 'selected' a quello cliccato
        this.classList.add('selected');

        // Legge il prezzo dal data-price e il testo del pulsante
        basePrice = parseInt(this.getAttribute('data-price'));
        selectedTicketType = this.innerText;
        updateTotal();
    });
});

document.getElementById('acquista-button').addEventListener('click', function() {
    // Salva in localStorage il prezzo totale (senza simbolo €) e il testo del biglietto
    const totalPrice = document.getElementById('total').textContent.replace('€','').trim();
    localStorage.setItem('ticketTotal', totalPrice);
    localStorage.setItem('ticketType', selectedTicketType);
});