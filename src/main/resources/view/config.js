import { GraphicEntityModule } from './entity-module/GraphicEntityModule.js';

export const modules = [
	GraphicEntityModule
];

export const playerColors = ['#ffb505', '#26c831'];
export const defaultOverSampling = 2;
export const demo = {
  playerCount: 2,
  logo: 'logo.png',
  overlayAlpha: 0.2,
  agents: [{
    index: 0,
    name: 'Alice',
    avatar: 'https://www.codingame.com/servlet/fileservlet?id=' + 16085713250612 + '&format=viewer_avatar',
    type: 'CODINGAMER',
    color: '#ffb505',
    typeData: {
      me: true,
      nickname: 'Player 1'
    }
  }, {
    index: 1,
    name: 'Bob',
    avatar: 'https://www.codingame.com/servlet/fileservlet?id=' + 16085756802960 + '&format=viewer_avatar',
    type: 'CODINGAMER',
    color: '#26c831',
    typeData: {
      me: true,
      nickname: 'Player 2'
    }
  }],
  frames: [
    "KEY_FRAME 0\n{\"global\":{\"entitymodule\":{\"width\":1920,\"height\":1080}},\"frame\":{\"duration\":200,\"entitymodule\":[\"C S\",\"C S\",\"C G\",\"C A\",\"C A\",\"C A\",\"C G\",\"C A\",\"C A\",\"C A\",\"C G\",\"C G\",\"C A\",\"C A\",\"C A\",\"C A\",\"C G\",\"C A\",\"C A\",\"C A\",\"C A\",\"C G\",\"C A\",\"C A\",\"C A\",\"C A\",\"C G\",\"C A\",\"C A\",\"C A\",\"C A\",\"C G\",\"C R\",\"C S\",\"C T\",\"C S\",\"C T\",\"C T\",\"C G\",\"C R\",\"C S\",\"C T\",\"C S\",\"C T\",\"C T\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"C C\",\"U 208 1 f 16777215 x 425 y 765 R 3 W 0\",\"U 289 1 f 16777215 x 561 y 1003 R 3 W 0\",\"U 112 1 f 16777215 x 867 y 187 R 3 W 0\",\"U 44 1 f 16777215 ff 'Comic Sans MS' ax 0.5 x 85 y 590 s 60 T SCORE sc 16777215\",\"U 159 1 f 16777215 x 119 y 493 R 3 W 0\",\"U 200 1 f 16777215 x 51 y 731 R 3 W 0\",\"U 181 1 f 16777215 x 221 y 697 R 3 W 0\",\"U 34 1 bw 120 i $0 x 25 y 25 bh 120\",\"U 80 1 f 16777215 x 731 y 119 R 3 W 0\",\"U 98 1 f 16777215 x 391 y 187 R 3 W 0\",\"U 20 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost2/down/1.png,ghost2/down/2.png,ghost2/down/3.png,ghost2/down/4.png v 1 l 1 ax 0.1 p 0\",\"U 227 1 f 16777215 x 629 y 799 R 3 W 0\",\"U 211 1 f 16777215 x 901 y 765 R 3 W 0\",\"U 42 1 f -2 ff 'Comic Sans MS' ax 0.5 x 85 y 190 s 40 T $1\",\"U 265 1 f 16777215 x 901 y 901 R 3 W 0\",\"U 19 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost2/right/1.png,ghost2/right/2.png,ghost2/right/3.png,ghost2/right/4.png v 0 l 1 ax 0.1 p 0\",\"U 154 1 f 16777215 x 221 y 459 R 3 W 0\",\"U 228 1 f 16777215 x 663 y 799 R 3 W 0\",\"U 2 1 bw 952 i level1.png ay 0 ax 0 bh 1054\",\"U 89 1 f 16777215 x 85 y 187 R 3 W 0\",\"U 127 1 f 16777215 x 85 y 289 R 3 W 0\",\"U 196 1 f 16777215 x 799 y 697 R 3 W 0\",\"U 7 1 C 6,4,5 x 705 y 503 z 1\",\"U 88 1 f 16777215 x 51 y 187 R 3 W 0\",\"U 169 1 f 16777215 x 731 y 561 R 3 W 0\",\"U 73 1 f 16777215 x 527 y 85 R 3 W 0\",\"U 61 1 f 16777215 x 629 y 51 R 3 W 0\",\"U 266 1 f 16777215 x 51 y 935 R 3 W 0\",\"U 204 1 f 16777215 x 731 y 731 R 3 W 0\",\"U 38 1 f -1 ff 'Comic Sans MS' ax 0.5 x 85 y 660 s 70 T 0 sc -1\",\"U 218 1 f 16777215 x 323 y 799 R 3 W 0\",\"U 299 1 f 16777215 x 901 y 1003 R 3 W 0\",\"U 155 1 f 16777215 x 731 y 459 R 3 W 0\",\"U 59 1 f 16777215 x 561 y 51 R 3 W 0\",\"U 83 1 f 16777215 x 221 y 153 R 3 W 0\",\"U 139 1 f 16777215 x 629 y 289 R 3 W 0\",\"U 110 1 f 16777215 x 799 y 187 R 3 W 0\",\"U 79 1 f 16777215 x 527 y 119 R 3 W 0\",\"U 149 1 f 16777215 x 731 y 357 R 3 W 0\",\"U 258 1 f 16777215 x 595 y 901 R 3 W 0\",\"U 29 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost4/right/1.png,ghost4/right/2.png,ghost4/right/3.png,ghost4/right/4.png v 0 l 1 ax 0.1 p 0\",\"U 11 1 C 10,8,9 x 1215 y 503 z 1\",\"U 6 1 sx 0.8 d 1000 sy 0.8 ay 0.5 I pacman/die/1.png,pacman/die/2.png,pacman/die/3.png,pacman/die/4.png,pacman/die/5.png,pacman/die/6.png,pacman/die/7.png,pacman/die/8.png,pacman/die/9.png,pacman/die/10.png,pacman/die/11.png,pacman/die/12.png,pacman/die/13.png ax 0.5 t -1\",\"U 219 1 f 16777215 x 357 y 799 R 3 W 0\",\"U 4 1 sx 0.8 sy 0.8 ay 0.5 I pacman/idle.png ax 0.5 p 0 t -1\",\"U 124 1 f 16777215 x 731 y 255 R 3 W 0\",\"U 192 1 f 16777215 x 663 y 697 R 3 W 0\",\"U 288 1 f 16777215 x 527 y 1003 R 3 W 0\",\"U 213 1 f 16777215 x 85 y 799 R 3 W 0\",\"U 223 1 f 16777215 x 493 y 799 R 3 W 0\",\"U 86 1 f 16777215 x 731 y 153 R 3 W 0\",\"U 157 1 f 16777215 x 51 y 493 R 3 W 0\",\"U 93 1 f 16777215 x 221 y 187 R 3 W 0\",\"U 56 1 f 16777215 x 391 y 51 R 3 W 0\",\"U 186 1 f 16777215 x 391 y 697 R 3 W 0\",\"U 41 1 bw 120 i $1 x 25 y 25 bh 120\",\"U 16 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost1/up/1.png,ghost1/up/2.png,ghost1/up/3.png,ghost1/up/4.png v 0 l 1 ax 0.1 p 0\",\"U 105 1 f 16777215 x 629 y 187 R 3 W 0\",\"U 128 1 f 16777215 x 119 y 289 R 3 W 0\",\"U 64 1 f 16777215 x 731 y 51 R 3 W 0\",\"U 120 1 f 16777215 x 51 y 255 R 3 W 0\",\"U 252 1 f 16777215 x 323 y 901 R 3 W 0\",\"U 53 1 f 16777215 x 289 y 51 R 3 W 0\",\"U 21 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost2/up/1.png,ghost2/up/2.png,ghost2/up/3.png,ghost2/up/4.png v 0 l 1 ax 0.1 p 0\",\"U 99 1 f 16777215 x 425 y 187 R 3 W 0\",\"U 280 1 f 16777215 x 255 y 1003 R 3 W 0\",\"U 290 1 f 16777215 x 595 y 1003 R 3 W 0\",\"U 46 1 f 16777215 x 51 y 51 R 3 W 0\",\"U 66 1 f 16777215 x 799 y 51 R 3 W 0\",\"U 190 1 f 16777215 x 595 y 697 R 3 W 0\",\"U 137 1 f 16777215 x 561 y 289 R 3 W 0\",\"U 260 1 f 16777215 x 731 y 901 R 3 W 0\",\"U 175 1 f 16777215 x 731 y 663 R 3 W 0\",\"U 163 1 f 16777215 x 867 y 493 R 3 W 0\",\"U 180 1 f 16777215 x 187 y 697 R 3 W 0\",\"U 74 1 f 16777215 x 731 y 85 R 3 W 0\",\"U 24 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost3/right/1.png,ghost3/right/2.png,ghost3/right/3.png,ghost3/right/4.png v 0 l 1 ax 0.1 p 0\",\"U 87 1 f 16777215 x 901 y 153 R 3 W 0\",\"U 138 1 f 16777215 x 595 y 289 R 3 W 0\",\"U 40 1 f 16777215 x 5 w 160 y 5 c -2 W 5 h 160\",\"U 109 1 f 16777215 x 765 y 187 R 3 W 0\",\"U 250 1 f 16777215 x 187 y 901 R 3 W 0\",\"U 251 1 f 16777215 x 221 y 901 R 3 W 0\",\"U 272 1 f 16777215 x 527 y 969 R 3 W 0\",\"U 27 1 C 28,30,31,29 x 1010 y 500\",\"U 140 1 f 16777215 x 731 y 289 R 3 W 0\",\"U 125 1 f 16777215 x 901 y 255 R 3 W 0\",\"U 275 1 f 16777215 x 85 y 1003 R 3 W 0\",\"U 25 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost3/down/1.png,ghost3/down/2.png,ghost3/down/3.png,ghost3/down/4.png v 1 l 1 ax 0.1 p 0\",\"U 142 1 f 16777215 x 799 y 289 R 3 W 0\",\"U 146 1 f 16777215 x 221 y 323 R 3 W 0\",\"U 205 1 f 16777215 x 901 y 731 R 3 W 0\",\"U 150 1 f 16777215 x 221 y 391 R 3 W 0\",\"U 257 1 f 16777215 x 561 y 901 R 3 W 0\",\"U 177 1 f 16777215 x 85 y 697 R 3 W 0\",\"U 185 1 f 16777215 x 357 y 697 R 3 W 0\",\"U 269 1 f 16777215 x 901 y 935 R 3 W 0\",\"U 297 1 f 16777215 x 833 y 1003 R 3 W 0\",\"U 39 1 C 42,44,41,45,40,43 x 1600 y 80\",\"U 62 1 f 16777215 x 663 y 51 R 3 W 0\",\"U 54 1 f 16777215 x 323 y 51 R 3 W 0\",\"U 84 1 f 16777215 x 425 y 153 R 3 W 0\",\"U 26 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost3/up/1.png,ghost3/up/2.png,ghost3/up/3.png,ghost3/up/4.png v 0 l 1 ax 0.1 p 0\",\"U 254 1 f 16777215 x 391 y 901 R 3 W 0\",\"U 232 1 f 16777215 x 867 y 799 R 3 W 0\",\"U 133 1 f 16777215 x 357 y 289 R 3 W 0\",\"U 179 1 f 16777215 x 153 y 697 R 3 W 0\",\"U 28 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost4/left/1.png,ghost4/left/2.png,ghost4/left/3.png,ghost4/left/4.png v 0 l 1 ax 0.1 p 0\",\"U 85 1 f 16777215 x 527 y 153 R 3 W 0\",\"U 271 1 f 16777215 x 425 y 969 R 3 W 0\",\"U 115 1 f 16777215 x 221 y 221 R 3 W 0\",\"U 23 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost3/left/1.png,ghost3/left/2.png,ghost3/left/3.png,ghost3/left/4.png v 0 l 1 ax 0.1 p 0\",\"U 143 1 f 16777215 x 833 y 289 R 3 W 0\",\"U 197 1 f 16777215 x 833 y 697 R 3 W 0\",\"U 286 1 f 16777215 x 459 y 1003 R 3 W 0\",\"U 226 1 f 16777215 x 595 y 799 R 3 W 0\",\"U 292 1 f 16777215 x 663 y 1003 R 3 W 0\",\"U 277 1 f 16777215 x 153 y 1003 R 3 W 0\",\"U 174 1 f 16777215 x 221 y 663 R 3 W 0\",\"U 239 1 f 16777215 x 833 y 833 R 3 W 0\",\"U 81 1 f 16777215 x 901 y 119 R 8 W 0\",\"U 298 1 f 16777215 x 867 y 1003 R 3 W 0\",\"U 216 1 f 16777215 x 255 y 799 R 3 W 0\",\"U 234 1 f 16777215 x 119 y 833 R 3 W 0\",\"U 261 1 f 16777215 x 765 y 901 R 3 W 0\",\"U 296 1 f 16777215 x 799 y 1003 R 3 W 0\",\"U 167 1 f 16777215 x 731 y 527 R 3 W 0\",\"U 13 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost1/left/1.png,ghost1/left/2.png,ghost1/left/3.png,ghost1/left/4.png v 0 l 1 ax 0.1 p 0\",\"U 166 1 f 16777215 x 221 y 527 R 3 W 0\",\"U 201 1 f 16777215 x 221 y 731 R 3 W 0\",\"U 82 1 f 16777215 x 51 y 153 R 3 W 0\",\"U 100 1 f 16777215 x 459 y 187 R 3 W 0\",\"U 50 1 f 16777215 x 187 y 51 R 3 W 0\",\"U 156 1 f 16777215 x 17 y 493 R 3 W 0\",\"U 10 1 sx 0.8 d 1000 sy 0.8 ay 0.5 I pacman/die/1.png,pacman/die/2.png,pacman/die/3.png,pacman/die/4.png,pacman/die/5.png,pacman/die/6.png,pacman/die/7.png,pacman/die/8.png,pacman/die/9.png,pacman/die/10.png,pacman/die/11.png,pacman/die/12.png,pacman/die/13.png ax 0.5 t -2\",\"U 45 1 f -2 ff 'Comic Sans MS' ax 0.5 x 85 y 660 s 70 T 0 sc -2\",\"U 194 1 f 16777215 x 731 y 697 R 3 W 0\",\"U 293 1 f 16777215 x 697 y 1003 R 3 W 0\",\"U 188 1 f 16777215 x 527 y 697 R 3 W 0\",\"U 209 1 f 16777215 x 527 y 765 R 3 W 0\",\"U 17 1 C 20,21,18,19 x 942 y 500\",\"U 126 1 f 16777215 x 51 y 289 R 3 W 0\",\"U 184 1 f 16777215 x 323 y 697 R 3 W 0\",\"U 52 1 f 16777215 x 255 y 51 R 3 W 0\",\"U 63 1 f 16777215 x 697 y 51 R 3 W 0\",\"U 135 1 f 16777215 x 425 y 289 R 3 W 0\",\"U 14 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost1/right/1.png,ghost1/right/2.png,ghost1/right/3.png,ghost1/right/4.png v 0 l 1 ax 0.1 p 0\",\"U 95 1 f 16777215 x 289 y 187 R 3 W 0\",\"U 202 1 f 16777215 x 425 y 731 R 3 W 0\",\"U 278 1 f 16777215 x 187 y 1003 R 3 W 0\",\"U 69 1 f 16777215 x 901 y 51 R 3 W 0\",\"U 168 1 f 16777215 x 221 y 561 R 3 W 0\",\"U 183 1 f 16777215 x 289 y 697 R 3 W 0\",\"U 57 1 f 16777215 x 425 y 51 R 3 W 0\",\"U 259 1 f 16777215 x 629 y 901 R 3 W 0\",\"U 51 1 f 16777215 x 221 y 51 R 3 W 0\",\"U 77 1 f 16777215 x 221 y 119 R 3 W 0\",\"U 215 1 f 16777215 x 221 y 799 R 3 W 0\",\"U 119 1 f 16777215 x 901 y 221 R 3 W 0\",\"U 210 1 f 16777215 x 731 y 765 R 3 W 0\",\"U 230 1 f 16777215 x 731 y 799 R 3 W 0\",\"U 130 1 f 16777215 x 187 y 289 R 3 W 0\",\"U 117 1 f 16777215 x 629 y 221 R 3 W 0\",\"U 136 1 f 16777215 x 527 y 289 R 3 W 0\",\"U 176 1 f 16777215 x 51 y 697 R 3 W 0\",\"U 178 1 f 16777215 x 119 y 697 R 3 W 0\",\"U 285 1 f 16777215 x 425 y 1003 R 3 W 0\",\"U 244 1 f 16777215 x 731 y 867 R 3 W 0\",\"U 118 1 f 16777215 x 731 y 221 R 3 W 0\",\"U 187 1 f 16777215 x 425 y 697 R 3 W 0\",\"U 212 1 f 16777215 x 51 y 799 R 8 W 0\",\"U 71 1 f 16777215 x 221 y 85 R 3 W 0\",\"U 101 1 f 16777215 x 493 y 187 R 3 W 0\",\"U 103 1 f 16777215 x 561 y 187 R 3 W 0\",\"U 207 1 f 16777215 x 221 y 765 R 3 W 0\",\"U 294 1 f 16777215 x 731 y 1003 R 3 W 0\",\"U 268 1 f 16777215 x 527 y 935 R 3 W 0\",\"U 3 1 C 208,289,112,159,200,181,80,98,227,211,265,154,228,2,89,127,196,88,169,73,61,266,204,218,299,155,59,83,139,110,79,149,258,219,124,192,288,213,223,86,157,93,56,186,105,128,64,120,252,53,99,280,290,46,66,190,137,260,175,163,180,74,87,138,109,250,251,272,140,125,275,142,146,205,150,257,177,185,269,297,62,54,84,254,232,133,179,85,271,115,143,197,286,226,292,277,174,239,81,298,216,234,261,296,167,166,201,82,100,50,156,194,293,188,209,126,184,52,63,135,95,202,278,69,168,183,57,259,51,77,215,119,210,230,130,117,136,176,178,285,244,118,187,212,71,101,103,207,294,268,67,171,233,222,111,55,283,224,273,68,65,48,102,75,158,76,91,281,134,243,284,242,276,189,255,145,164,153,78,198,253,106,172,107,263,279,225,241,148,256,245,282,144,193,72,113,248,264,60,104,132,267,165,206,173,114,70,221,240,249,47,152,141,160,217,295,58,247,274,182,92,162,131,96,116,123,287,90,291,122,231,237,214,220,170,235,94,262,161,236,229,199,203,195,246,121,49,191,270,108,129,97,238,151,147 x 484 y 10\",\"U 67 1 f 16777215 x 833 y 51 R 3 W 0\",\"U 171 1 f 16777215 x 731 y 595 R 3 W 0\",\"U 233 1 f 16777215 x 901 y 799 R 8 W 0\",\"U 222 1 f 16777215 x 459 y 799 R 3 W 0\",\"U 111 1 f 16777215 x 833 y 187 R 3 W 0\",\"U 55 1 f 16777215 x 357 y 51 R 3 W 0\",\"U 283 1 f 16777215 x 357 y 1003 R 3 W 0\",\"U 224 1 f 16777215 x 527 y 799 R 3 W 0\",\"U 273 1 f 16777215 x 901 y 969 R 3 W 0\",\"U 68 1 f 16777215 x 867 y 51 R 3 W 0\",\"U 65 1 f 16777215 x 765 y 51 R 3 W 0\",\"U 48 1 f 16777215 x 119 y 51 R 3 W 0\",\"U 102 1 f 16777215 x 527 y 187 R 3 W 0\",\"U 75 1 f 16777215 x 901 y 85 R 3 W 0\",\"U 30 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost4/down/1.png,ghost4/down/2.png,ghost4/down/3.png,ghost4/down/4.png v 1 l 1 ax 0.1 p 0\",\"U 158 1 f 16777215 x 85 y 493 R 3 W 0\",\"U 36 1 bw 80 i pacman/idle.png ax 0.5 x 85 y 370 bh 80 t -1\",\"U 76 1 f 16777215 x 51 y 119 R 8 W 0\",\"U 91 1 f 16777215 x 153 y 187 R 3 W 0\",\"U 281 1 f 16777215 x 289 y 1003 R 3 W 0\",\"U 134 1 f 16777215 x 391 y 289 R 3 W 0\",\"U 243 1 f 16777215 x 629 y 867 R 3 W 0\",\"U 284 1 f 16777215 x 391 y 1003 R 3 W 0\",\"U 242 1 f 16777215 x 323 y 867 R 3 W 0\",\"U 276 1 f 16777215 x 119 y 1003 R 3 W 0\",\"U 15 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost1/down/1.png,ghost1/down/2.png,ghost1/down/3.png,ghost1/down/4.png v 1 l 1 ax 0.1 p 0\",\"U 189 1 f 16777215 x 561 y 697 R 3 W 0\",\"U 255 1 f 16777215 x 425 y 901 R 3 W 0\",\"U 145 1 f 16777215 x 901 y 289 R 3 W 0\",\"U 164 1 f 16777215 x 901 y 493 R 3 W 0\",\"U 5 1 sx 0.8 d 150 sy 0.8 ay 0.5 I pacman/eat/2.png,pacman/eat/3.png,pacman/eat/3.png,pacman/eat/2.png,pacman/eat/1.png,pacman/eat/1.png l 1 ax 0.5 t -1\",\"U 153 1 f 16777215 x 731 y 425 R 3 W 0\",\"U 31 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost4/up/1.png,ghost4/up/2.png,ghost4/up/3.png,ghost4/up/4.png v 0 l 1 ax 0.1 p 0\",\"U 32 1 C 36,35,33,34,37,38 x 150 y 80\",\"U 33 1 f 16777215 x 5 w 160 y 5 c -1 W 5 h 160\",\"U 78 1 f 16777215 x 425 y 119 R 3 W 0\",\"U 198 1 f 16777215 x 867 y 697 R 3 W 0\",\"U 253 1 f 16777215 x 357 y 901 R 3 W 0\",\"U 106 1 f 16777215 x 663 y 187 R 3 W 0\",\"U 22 1 C 23,24,26,25 x 874 y 500\",\"U 172 1 f 16777215 x 221 y 629 R 3 W 0\",\"U 107 1 f 16777215 x 697 y 187 R 3 W 0\",\"U 263 1 f 16777215 x 833 y 901 R 3 W 0\",\"U 279 1 f 16777215 x 221 y 1003 R 3 W 0\",\"U 225 1 f 16777215 x 561 y 799 R 3 W 0\",\"U 241 1 f 16777215 x 221 y 867 R 3 W 0\",\"U 148 1 f 16777215 x 221 y 357 R 3 W 0\",\"U 256 1 f 16777215 x 527 y 901 R 3 W 0\",\"U 245 1 f 16777215 x 833 y 867 R 3 W 0\",\"U 282 1 f 16777215 x 323 y 1003 R 3 W 0\",\"U 144 1 f 16777215 x 867 y 289 R 3 W 0\",\"U 193 1 f 16777215 x 697 y 697 R 3 W 0\",\"U 72 1 f 16777215 x 425 y 85 R 3 W 0\",\"U 113 1 f 16777215 x 901 y 187 R 3 W 0\",\"U 248 1 f 16777215 x 119 y 901 R 3 W 0\",\"U 264 1 f 16777215 x 867 y 901 R 3 W 0\",\"U 60 1 f 16777215 x 595 y 51 R 3 W 0\",\"U 104 1 f 16777215 x 595 y 187 R 3 W 0\",\"U 132 1 f 16777215 x 323 y 289 R 3 W 0\",\"U 267 1 f 16777215 x 425 y 935 R 3 W 0\",\"U 165 1 f 16777215 x 935 y 493 R 3 W 0\",\"U 206 1 f 16777215 x 51 y 765 R 3 W 0\",\"U 173 1 f 16777215 x 731 y 629 R 3 W 0\",\"U 114 1 f 16777215 x 51 y 221 R 3 W 0\",\"U 70 1 f 16777215 x 51 y 85 R 3 W 0\",\"U 221 1 f 16777215 x 425 y 799 R 3 W 0\",\"U 240 1 f 16777215 x 119 y 867 R 3 W 0\",\"U 249 1 f 16777215 x 153 y 901 R 3 W 0\",\"U 37 1 f 16777215 ff 'Comic Sans MS' ax 0.5 x 85 y 590 s 60 T SCORE sc 16777215\",\"U 47 1 f 16777215 x 85 y 51 R 3 W 0\",\"U 152 1 f 16777215 x 221 y 425 R 3 W 0\",\"U 141 1 f 16777215 x 765 y 289 R 3 W 0\",\"U 160 1 f 16777215 x 153 y 493 R 3 W 0\",\"U 8 1 sx 0.8 sy 0.8 ay 0.5 I pacman/idle.png ax 0.5 p 0 t -2\",\"U 217 1 f 16777215 x 289 y 799 R 3 W 0\",\"U 295 1 f 16777215 x 765 y 1003 R 3 W 0\",\"U 58 1 f 16777215 x 527 y 51 R 3 W 0\",\"U 247 1 f 16777215 x 85 y 901 R 3 W 0\",\"U 274 1 f 16777215 x 51 y 1003 R 3 W 0\",\"U 182 1 f 16777215 x 255 y 697 R 3 W 0\",\"U 92 1 f 16777215 x 187 y 187 R 3 W 0\",\"U 162 1 f 16777215 x 833 y 493 R 3 W 0\",\"U 12 1 C 15,13,14,16 x 926 y 384\",\"U 131 1 f 16777215 x 221 y 289 R 3 W 0\",\"U 18 1 d 100 sx 0.8 sy 0.8 ay 0.15 I ghost2/left/1.png,ghost2/left/2.png,ghost2/left/3.png,ghost2/left/4.png v 0 l 1 ax 0.1 p 0\",\"U 96 1 f 16777215 x 323 y 187 R 3 W 0\",\"U 116 1 f 16777215 x 323 y 221 R 3 W 0\",\"U 123 1 f 16777215 x 629 y 255 R 3 W 0\",\"U 287 1 f 16777215 x 493 y 1003 R 3 W 0\",\"U 90 1 f 16777215 x 119 y 187 R 3 W 0\",\"U 35 1 f -1 ff 'Comic Sans MS' ax 0.5 x 85 y 190 s 40 T $0\",\"U 291 1 f 16777215 x 629 y 1003 R 3 W 0\",\"U 122 1 f 16777215 x 323 y 255 R 3 W 0\",\"U 231 1 f 16777215 x 833 y 799 R 3 W 0\",\"U 9 1 sx 0.8 d 150 sy 0.8 ay 0.5 I pacman/eat/2.png,pacman/eat/3.png,pacman/eat/3.png,pacman/eat/2.png,pacman/eat/1.png,pacman/eat/1.png l 1 ax 0.5 t -2\",\"U 237 1 f 16777215 x 629 y 833 R 3 W 0\",\"U 1 1 i Background.jpg ay 0 ax 0\",\"U 214 1 f 16777215 x 119 y 799 R 3 W 0\",\"U 220 1 f 16777215 x 391 y 799 R 3 W 0\",\"U 170 1 f 16777215 x 221 y 595 R 3 W 0\",\"U 235 1 f 16777215 x 221 y 833 R 3 W 0\",\"U 94 1 f 16777215 x 255 y 187 R 3 W 0\",\"U 262 1 f 16777215 x 799 y 901 R 3 W 0\",\"U 161 1 f 16777215 x 799 y 493 R 3 W 0\",\"U 236 1 f 16777215 x 323 y 833 R 3 W 0\",\"U 229 1 f 16777215 x 697 y 799 R 3 W 0\",\"U 199 1 f 16777215 x 901 y 697 R 3 W 0\",\"U 203 1 f 16777215 x 527 y 731 R 3 W 0\",\"U 195 1 f 16777215 x 765 y 697 R 3 W 0\",\"U 246 1 f 16777215 x 51 y 901 R 3 W 0\",\"U 121 1 f 16777215 x 221 y 255 R 3 W 0\",\"U 49 1 f 16777215 x 153 y 51 R 3 W 0\",\"U 191 1 f 16777215 x 629 y 697 R 3 W 0\",\"U 270 1 f 16777215 x 51 y 969 R 3 W 0\",\"U 108 1 f 16777215 x 731 y 187 R 3 W 0\",\"U 129 1 f 16777215 x 153 y 289 R 3 W 0\",\"U 97 1 f 16777215 x 357 y 187 R 3 W 0\",\"U 238 1 f 16777215 x 731 y 833 R 3 W 0\",\"U 151 1 f 16777215 x 731 y 391 R 3 W 0\",\"U 43 1 bw 80 i pacman/idle.png ax 0.5 x 85 y 370 bh 80 t -2\",\"U 147 1 f 16777215 x 731 y 323 R 3 W 0\"]}}\n",
    "INTERMEDIATE_FRAME 1\n",
    "KEY_FRAME 2\n{\"entitymodule\":[\"U 11 0 sx -1 sy 1 r 0\",\"U 20 1 v 0\",\"U 11 1 x 1181\",\"U 6 1 p \",\"U 21 1 v 1\",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 10 1 p \",\"U 17 1 y 466\",\"U 30 1 v 0\",\"U 5 1 l 0 p \",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 8 1 p \",\"U 9 1 p 0\"]}\n",
    "INTERMEDIATE_FRAME 3\n",
    "KEY_FRAME 4\n{\"entitymodule\":[\"U 11 1 x 1147\",\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 17 1 y 432\",\"U 14 1 v 1\",\"U 30 1 v 1\",\"U 15 1 v 0\",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 12 1 x 960\"]}\n",
    "INTERMEDIATE_FRAME 5\n",
    "KEY_FRAME 6\n{\"entitymodule\":[\"U 11 1 x 1113\",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 17 1 x 926 y 384\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 12 1 x 994\"]}\n",
    "INTERMEDIATE_FRAME 7\n",
    "KEY_FRAME 8\n{\"entitymodule\":[\"U 19 1 v 1\",\"U 21 1 v 0\",\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 17 1 x 960\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 8 1 p 0\",\"U 12 1 x 1028\",\"U 9 1 l 0 p \"]}\n",
    "INTERMEDIATE_FRAME 9\n",
    "KEY_FRAME 10\n{\"entitymodule\":[\"U 7 0 sx 1 sy 1 r 90\",\"U 11 0 sx 1 r 90\",\"U 166 0.5 a 1\",\"U 7 1 y 537\",\"U 11 1 y 537\",\"U 4 1 p \",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 166 1 a 0\",\"U 17 1 x 994\",\"U 30 1 v 0\",\"U 5 1 l 1 p 0\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 8 1 p \",\"U 12 1 x 1062\",\"U 9 1 l 1 p 0\"]}\n",
    "INTERMEDIATE_FRAME 11\n",
    "KEY_FRAME 12\n{\"entitymodule\":[\"U 168 0.5 a 1\",\"U 7 1 y 571\",\"U 11 1 y 571\",\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 17 1 x 1028\",\"U 168 1 a 0\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 12 1 x 1096\"]}\n",
    "INTERMEDIATE_FRAME 13\n",
    "KEY_FRAME 14\n{\"entitymodule\":[\"U 170 0.5 a 1\",\"U 7 1 y 605\",\"U 11 1 y 605\",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 17 1 x 1062\",\"U 14 1 v 0\",\"U 30 1 v 0\",\"U 15 1 v 1\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 12 1 y 418\",\"U 170 1 a 0\"]}\n",
    "INTERMEDIATE_FRAME 15\n",
    "KEY_FRAME 16\n{\"entitymodule\":[\"U 4 1 p 0\",\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 17 1 x 1096\",\"U 30 1 v 1\",\"U 5 1 l 0 p \",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 8 1 p 0\",\"U 12 1 y 452\",\"U 9 1 l 0 p \"]}\n",
    "INTERMEDIATE_FRAME 17\n",
    "KEY_FRAME 18\n{\"entitymodule\":[\"U 20 1 v 1\",\"U 19 1 v 0\",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 17 1 y 418\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 12 1 y 486\"]}\n",
    "INTERMEDIATE_FRAME 19\n",
    "KEY_FRAME 20\n{\"entitymodule\":[\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 17 1 y 452\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 12 1 y 520\"]}\n",
    "INTERMEDIATE_FRAME 21\n",
    "KEY_FRAME 22\n{\"entitymodule\":[\"U 172 0.5 a 1\",\"U 7 1 y 639\",\"U 11 1 y 639\",\"U 4 1 p \",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 17 1 y 486\",\"U 30 1 v 0\",\"U 5 1 l 1 p 0\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 172 1 a 0\",\"U 8 1 p \",\"U 12 1 y 554\",\"U 9 1 l 1 p 0\"]}\n",
    "INTERMEDIATE_FRAME 23\n",
    "KEY_FRAME 24\n{\"entitymodule\":[\"U 174 0.5 a 1\",\"U 7 1 y 673\",\"U 11 1 y 673\",\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 174 1 a 0\",\"U 17 1 y 520\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 12 1 y 588\"]}\n",
    "INTERMEDIATE_FRAME 25\n",
    "KEY_FRAME 26\n{\"entitymodule\":[\"U 191 0.5 a 1\",\"U 181 0.5 a 1\",\"U 181 1 a 0\",\"U 7 1 y 707\",\"U 11 1 y 707\",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 17 1 y 554\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 12 1 y 622\",\"U 191 1 a 0\"]}\n",
    "INTERMEDIATE_FRAME 27\n",
    "KEY_FRAME 28\n{\"entitymodule\":[\"U 7 0 r 0\",\"U 11 0 r 0\",\"U 192 0.5 a 1\",\"U 182 0.5 a 1\",\"U 7 1 x 739\",\"U 11 1 x 1147\",\"U 192 1 a 0\",\"U 27 1 y 500\",\"U 25 1 v 1\",\"U 26 1 v 0\",\"U 17 1 y 588\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 500\",\"U 182 1 a 0\",\"U 12 1 y 656\"]}\n",
    "INTERMEDIATE_FRAME 29\n",
    "KEY_FRAME 30\n{\"entitymodule\":[\"U 183 0.5 a 1\",\"U 193 0.5 a 1\",\"U 20 1 v 0\",\"U 7 1 x 773\",\"U 11 1 x 1181\",\"U 27 1 y 466\",\"U 25 1 v 0\",\"U 26 1 v 1\",\"U 17 1 x 1062\",\"U 183 1 a 0\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 466\",\"U 193 1 a 0\",\"U 12 1 y 690\",\"U 18 1 v 1\"]}\n",
    "INTERMEDIATE_FRAME 31\n",
    "KEY_FRAME 32\n{\"entitymodule\":[\"U 184 0.5 a 1\",\"U 194 0.5 a 1\",\"U 7 1 x 807\",\"U 11 1 x 1215\",\"U 24 1 v 1\",\"U 27 1 y 500\",\"U 26 1 v 0\",\"U 194 1 a 0\",\"U 17 1 x 1028\",\"U 184 1 a 0\",\"U 14 1 v 1\",\"U 30 1 v 1\",\"U 15 1 v 0\",\"U 31 1 v 0\",\"U 22 1 x 908\",\"U 12 1 x 1130\"]}\n",
    "INTERMEDIATE_FRAME 33\n",
    "KEY_FRAME 34\n{\"entitymodule\":[\"U 7 0 sx -1 r 90\",\"U 11 0 sx -1 r 90\",\"U 175 0.5 a 1\",\"U 7 1 y 673\",\"U 11 1 y 673\",\"U 175 1 a 0\",\"U 27 1 y 466\",\"U 17 1 x 994\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 x 942\",\"U 12 1 x 1164\"]}\n",
    "INTERMEDIATE_FRAME 35\n",
    "KEY_FRAME 36\n{\"entitymodule\":[\"U 173 0.5 a 1\",\"U 7 1 y 639\",\"U 11 1 y 639\",\"U 24 1 v 0\",\"U 27 1 y 500\",\"U 26 1 v 1\",\"U 17 1 x 960\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 432\",\"U 173 1 a 0\",\"U 12 1 x 1198\"]}\n",
    "INTERMEDIATE_FRAME 37\n",
    "KEY_FRAME 38\n{\"entitymodule\":[\"U 171 0.5 a 1\",\"U 7 1 y 605\",\"U 11 1 y 605\",\"U 27 1 y 466\",\"U 17 1 x 926\",\"U 14 1 v 0\",\"U 171 1 a 0\",\"U 30 1 v 0\",\"U 15 1 v 1\",\"U 31 1 v 1\",\"U 22 1 x 926 y 384\",\"U 12 1 y 724\"]}\n",
    "INTERMEDIATE_FRAME 39\n",
    "KEY_FRAME 40\n{\"entitymodule\":[\"U 7 0 sx 1 r 0\",\"U 7 1 x 841\",\"U 27 1 y 500\",\"U 26 1 v 0\",\"U 23 1 v 1\",\"U 17 1 x 892\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 x 892\",\"U 8 1 p 0\",\"U 12 1 y 758\",\"U 9 1 l 0 p \"]}\n",
    "INTERMEDIATE_FRAME 41\n",
    "KEY_FRAME 42\n{\"entitymodule\":[\"U 7 1 x 875\",\"U 27 1 y 466\",\"U 26 1 v 1\",\"U 23 1 v 0\",\"U 17 1 x 858\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 350\",\"U 12 1 y 792\"]}\n",
    "KEY_FRAME 43\n{\"duration\":2000,\"entitymodule\":[\"U 6 0.5 p 0\",\"U 5 0.5 p \",\"U 27 1 y 500\",\"U 13 1 v 1\",\"U 17 1 x 824\",\"U 30 1 v 1\",\"U 15 1 v 0\",\"U 31 1 v 0\",\"U 22 1 y 316\",\"U 12 1 x 1164\"]}\n",
    "KEY_FRAME 44\n{\"duration\":200,\"entitymodule\":[\"U 169 0.5 a 1\",\"U 7 1 v 0\",\"U 169 1 a 0\",\"U 11 1 y 571\",\"U 27 1 y 466\",\"U 17 1 x 790\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 282\",\"U 8 1 p \",\"U 12 1 x 1130\",\"U 9 1 l 1 p 0\"]}\n",
    "KEY_FRAME 45\n{\"entitymodule\":[\"U 167 0.5 a 1\",\"U 11 1 y 537\",\"U 21 1 v 1\",\"U 27 1 y 500\",\"U 26 1 v 0\",\"U 23 1 v 1\",\"U 167 1 a 0\",\"U 17 1 y 554\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 x 858\",\"U 12 1 x 1096\",\"U 18 1 v 0\"]}\n",
    "KEY_FRAME 46\n{\"entitymodule\":[\"U 11 1 y 503\",\"U 27 1 y 466\",\"U 17 1 y 520\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 x 824\",\"U 12 1 x 1062\"]}\n",
    "KEY_FRAME 47\n{\"entitymodule\":[\"U 11 0 r 0\",\"U 11 1 x 1181\",\"U 27 1 y 500\",\"U 17 1 y 486\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 x 790\",\"U 12 1 x 1028\"]}\n",
    "KEY_FRAME 48\n{\"entitymodule\":[\"U 11 1 x 1147\",\"U 27 1 y 466\",\"U 26 1 v 1\",\"U 23 1 v 0\",\"U 17 1 y 452\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 248\",\"U 12 1 x 994\"]}\n",
    "KEY_FRAME 49\n{\"entitymodule\":[\"U 11 1 x 1113\",\"U 16 1 v 1\",\"U 27 1 y 500\",\"U 13 1 v 0\",\"U 17 1 y 418\",\"U 30 1 v 1\",\"U 31 1 v 0\",\"U 22 1 y 214\",\"U 12 1 y 758\"]}\n",
    "KEY_FRAME 50\n{\"entitymodule\":[\"U 27 1 y 466\",\"U 17 1 y 384\",\"U 30 1 v 0\",\"U 31 1 v 1\",\"U 22 1 y 180\",\"U 8 1 p 0\",\"U 12 1 y 724\",\"U 9 1 l 0 p \"]}\n",
    "KEY_FRAME 51\n{\"entitymodule\":[\"U 19 1 v 1\",\"U 21 1 v 0\",\"U 27 1 x 976\",\"U 26 1 v 0\",\"U 28 1 v 1\",\"U 23 1 v 1\",\"U 17 1 x 824\",\"U 31 1 v 0\",\"U 22 1 x 756\",\"U 12 1 y 690\"]}\n",
    "KEY_FRAME 52\n{\"entitymodule\":[\"U 16 1 v 0\",\"U 27 1 x 942\",\"U 17 1 x 858\",\"U 14 1 v 1\",\"U 22 1 x 722\",\"U 12 1 x 1028\"]}\n",
    "KEY_FRAME 53\n{\"entitymodule\":[\"U 27 1 y 432\",\"U 28 1 v 0\",\"U 17 1 x 892\",\"U 31 1 v 1\",\"U 22 1 x 688\",\"U 12 1 x 1062\"]}\n",
    "KEY_FRAME 54\n{\"entitymodule\":[\"U 27 1 x 926 y 384\",\"U 25 1 v 1\",\"U 23 1 v 0\",\"U 17 1 x 926\",\"U 22 1 y 214\",\"U 12 1 x 1096\"]}\n",
    "KEY_FRAME 55\n{\"entitymodule\":[\"U 29 1 v 1\",\"U 16 1 v 1\",\"U 27 1 x 960\",\"U 17 1 x 960\",\"U 14 1 v 0\",\"U 31 1 v 0\",\"U 22 1 y 248\",\"U 12 1 y 656\"]}\n",
    "KEY_FRAME 56\n{\"entitymodule\":[\"U 11 0 sx 1 r 90\",\"U 11 1 y 537\",\"U 27 1 x 994\",\"U 17 1 x 994\",\"U 22 1 y 282\",\"U 8 1 p \",\"U 12 1 y 622\",\"U 9 1 l 1 p 0\"]}\n",
    "KEY_FRAME 57\n{\"entitymodule\":[\"U 11 1 y 571\",\"U 27 1 x 1028\",\"U 17 1 x 1028\",\"U 22 1 y 316\",\"U 12 1 y 588\"]}\n",
    "KEY_FRAME 58\n{\"entitymodule\":[\"U 11 1 y 605\",\"U 27 1 x 1062\",\"U 17 1 x 1062\",\"U 22 1 y 350\",\"U 12 1 y 554\"]}\n"
  ]
};
