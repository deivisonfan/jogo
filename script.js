const gameArea = document.getElementById("gameArea");
const player = document.getElementById("player");
let bullets = [];
let enemies = [];

let playerX = window.innerWidth / 2 - 30;

function movePlayer(dir) {
  playerX += dir * 20;
  if (playerX < 0) playerX = 0;
  if (playerX > window.innerWidth - 60) playerX = window.innerWidth - 60;
  player.style.left = playerX + "px";
}

function shoot() {
  const bullet = document.createElement("img");
  bullet.src = "assets/bullet.png";
  bullet.className = "bullet";
  bullet.style.left = playerX + 25 + "px";
  bullet.style.bottom = "60px";
  gameArea.appendChild(bullet);
  bullets.push(bullet);
}

function createEnemy() {
  const enemy = document.createElement("img");
  enemy.src = "assets/enemy.png";
  enemy.className = "enemy";
  enemy.style.left = Math.random() * (window.innerWidth - 40) + "px";
  enemy.style.top = "0px";
  gameArea.appendChild(enemy);
  enemies.push(enemy);
}

function update() {
  bullets.forEach((b, i) => {
    b.style.bottom = parseInt(b.style.bottom) + 10 + "px";
    if (parseInt(b.style.bottom) > window.innerHeight) {
      b.remove();
      bullets.splice(i, 1);
    }
  });

  enemies.forEach((e, i) => {
    e.style.top = parseInt(e.style.top) + 3 + "px";
    if (parseInt(e.style.top) > window.innerHeight) {
      e.remove();
      enemies.splice(i, 1);
      createEnemy();
    }

    bullets.forEach((b, bi) => {
      const br = b.getBoundingClientRect();
      const er = e.getBoundingClientRect();
      if (
        br.left < er.right &&
        br.right > er.left &&
        br.top < er.bottom &&
        br.bottom > er.top
      ) {
        b.remove();
        e.remove();
        bullets.splice(bi, 1);
        enemies.splice(i, 1);
        createEnemy();
      }
    });
  });

  requestAnimationFrame(update);
}

document.addEventListener("keydown", (e) => {
  if (e.key === "ArrowLeft") movePlayer(-1);
  if (e.key === "ArrowRight") movePlayer(1);
  if (e.key === " ") shoot();
});

for (let i = 0; i < 3; i++) createEnemy();
update();
