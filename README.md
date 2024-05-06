# Surveillance Duty Organizer
This is a small project I worked on when I was serving in the Korean Army from 2022 to 2024. 



## About the Project
The code is designed to assist the the process of organizing surveillance duty shifts for my company. It first reads a text file containing the relevant information of the soldiers, such as their name, rank, or shifts taken in the previous week. It then writes  shifts for this cycle, a "fairness report," and the updated info of soldiers that could be used as input for the next cycle.

## Explanation About the Overall Design
I created several classes that would help me organize the code:

`Soldier` contains all the information about the said soldier.

`Partner` is a class that holds two soldiers, which is later used to put in shifts.

`Secretary` reads files to store a list of soldiers (which the Organizer takes) and produces relevant files regarding the next cycle.

`Organizer` receives the list of soldiers from `Secretary` to produce the next surveillance duty cycle while updating each of the soldiers' info.


## Usage
Note: You can change the filename of the input/output text files in Organizer.java. 
1. Run `javac Organizer.java` to compile the Java Source files.
2. Run `java Organizer.java` 
