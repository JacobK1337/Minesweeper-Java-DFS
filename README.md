# Intro
### Minesweeper Java game made using Java Swing
![firstGif](https://user-images.githubusercontent.com/81765291/122640019-5cf81e80-d0fd-11eb-96df-c5e32e6fbac4.gif)

- **Player can choose the game size (10x10/8 bombs) (15x15/30 bombs) (24x24/70 bombs)**
- **Flagging the field is done by clicking right mouse button**
- **Revealing an empty field (field that have no bombs around and isn't a bomb) will reveal other fields around**


![unluckyClick](https://user-images.githubusercontent.com/81765291/122640148-240c7980-d0fe-11eb-8a19-561d4d77ad18.gif)

- **Clicking a bomb-field will reveal other bomb-fields every 100ms, then after 2,5 second break a restart-game screen will pop up**

# DFS algorithm
- **Revealing an empty field (field that have no bombs around and isn't a bomb) will reveal other fields around**
- **So, if an empty field reveals other empty field, the fields around both of them will be revealed**
- **It is done using DFS algorithm**
- **Every empty field is connected into a graph with other empty fields around (creating an island) and when a button representing empty field is clicked, DFS function will be called**

![image](https://user-images.githubusercontent.com/81765291/122640307-30450680-d0ff-11eb-919a-da25752c9633.png)

### Clicking any field that is marked with a white dot will start DFS function and will consider other marked fields around

