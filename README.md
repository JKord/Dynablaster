# Dynablaster

Bomberman is an arcade-style maze-based video game developed by Hudson Soft.
The original home computer game Bomber Man (爆弾男 Bakudan Otoko?) was released in 1983 for the MSX, NEC PC-8801, NEC PC-6001,
Sharp MZ-700 and FM-7 in Japan, and a censored version for the MSX and ZX Spectrum in Europe as Eric and the Floaters.
It had a Japanese sequel known as 3-D Bomberman, in which Bomberman navigates the maze in the first-person. In 1985,
Bomberman was released for the Family Computer. It spawned the long-running series with many installments building on its basic gameplay.
The earlier game Warp & Warp by Namco is most likely the inspiration for the Bomberman gameplay.

Used technologies and tools:
- Java, jhipster, Spring Boot
- MySql, Hibernate, WebSockets, OAuth
- AngularJS, Bootstrap, npm, Gulp, Bower
- Spring MVC, Spring Security, Spring Social
- gradle, liquibase, swagger

It is possible add:
- Status active of player for game is not ready
- Edit logic move of bots (collision with walls ..) - used algorithm A* now
- Set up for "Play with bots" for enemy is use algorithm A* and set current position of a player for search a path
- Make statistics
- Bonuses
- Add exit from game for another player in multi player

The game bugs:
- Socket connection init (refresh page)
- Bomb is not showing on another window player in multi player (move logic on server)
- Count users in lobby - remove user form list lobby
- In multi player a player move very strangely
