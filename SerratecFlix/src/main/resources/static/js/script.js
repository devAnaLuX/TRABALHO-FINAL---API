const menuToggle = document.querySelector('.menu-toggle');
const navLinks = document.querySelector('.nav-links');

menuToggle.addEventListener('click', () => {
  navLinks.classList.toggle('active');
  menuToggle.classList.toggle('open');
});

document.addEventListener('click', (event) => {
  const botaoClicado = event.target.closest('.nav-api-btn');

  if (botaoClicado) {
    const botoes = Array.from(document.querySelectorAll('.nav-api-btn'));
    const secoes = document.querySelectorAll('.section');
    const indiceClicado = botoes.indexOf(botaoClicado);
    botoes.forEach(btn => btn.classList.remove('active'));
    botaoClicado.classList.add('active');
    if (secoes[indiceClicado]) {
      secoes.forEach(sec => sec.classList.remove('active'));
      secoes[indiceClicado].classList.add('active');
    }
  }
});