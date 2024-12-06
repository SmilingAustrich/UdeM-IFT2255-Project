// scripts.js

document.addEventListener('mousemove', (e) => {
    const background = document.querySelector('.hexagonal-background');
    let x = (window.innerWidth - e.pageX * 0.1) / 100;
    let y = (window.innerHeight - e.pageY * 0.1) / 100;
    background.style.transform = `translate(${x}px, ${y}px)`;
});
