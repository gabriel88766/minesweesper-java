# minesweesper-java

Built from scratch, using Java Swing and Awt. <br>
This project was made using IntelliJ IDEA Community Edition. <br>
Clone this project in IntelliJ IDEA and run it with java 20. <br>

# Gameplay

1. Choose difficulty (Easy: grid 9x9 with 10 mines, Medium(default): grid 16x16 with 40 mines, Hard: grid 30x16 with 99 mines)<br>
2. Then click anywhere in the map to start (It's granted there is no mines neither in the clicked square nor adjacencies)<br>
3. If some square has a number, there is #number of mines near that square.<br>
4. If some square is empty, there is no mines near that square.<br>
5. If you open some empty square, all adjacent squares will be opened recursively.<br>
6. You can right-click to mark some square as a mine.<br>
7. Once you have marked all mines near a number, you can click in that number to open all squares near that number, <br>
except those marked as mine. If you marked some mine incorrectly, the real mine will be activated, and you will lose.

# Snapshot

![snapshot](https://github.com/gabriel88766/minesweesper-java/assets/56970226/8382719f-e496-4541-8295-73eb1f2f402d)
