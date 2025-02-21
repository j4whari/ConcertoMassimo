let quantity = 1;
let basePrice = 0;
let selectedTicketType = '';  // Variable to store the selected ticket type

function changeQuantity(change) {
    quantity += change;
    if (quantity < 1) quantity = 1;
    if (quantity > 4) quantity = 4;  // Limit quantity to 4
    document.getElementById('quantity').value = quantity;
    updateTotal();
}

function updateTotal() {
    const total = basePrice * quantity;
    document.getElementById('total').innerText = `${total}€`;  // Just show price in total
}

// Update base price and ticket type when selecting different ticket types
const ticketButtons = document.querySelectorAll('.ticket-button');
ticketButtons.forEach(button => {
    button.addEventListener('click', function() {
        // Remove 'selected' class from all buttons
        ticketButtons.forEach(btn => btn.classList.remove('selected'));

        // Add 'selected' class to the clicked button
        this.classList.add('selected');

        // Update base price and selected ticket type
        basePrice = parseInt(this.getAttribute('data-price'));  // Get price from the data-price attribute
        selectedTicketType = this.innerText;  // Get the ticket type from the button text
        updateTotal();
    });
});

document.getElementById('acquista-button').addEventListener('click', function() {
    // Get the current total price (without the € symbol)
    const totalPrice = document.getElementById('total').textContent.replace('€', '');

    // Save just the price to localStorage
    localStorage.setItem('ticketTotal', totalPrice);
});