let scrollAmount = 0;
const scrollStep = document.querySelector(".artista-card").offsetWidth + 20; // Larghezza della card + gap
const slider = document.querySelector(".slider");

function scrollSlider(direction) {
    const maxScroll = slider.scrollWidth - slider.clientWidth;
    scrollAmount += direction * scrollStep;

    if (scrollAmount < 0) {
        scrollAmount = 0;
    } else if (scrollAmount > maxScroll) {
        scrollAmount = maxScroll;
    }

    slider.scrollTo({
        left: scrollAmount,
        behavior: "smooth"
    });
}