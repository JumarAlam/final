# COSC 516 Final Exam

**This is an individual take home exam. No communication with others are allowed. You may use course and Internet resources.**

**The exam is available from Saturday, December 17th at 8 a.m. until Sunday, December 18th at 8 p.m. (36 hours).**

**Submit the exam by pushing to GitHub repository and submitting your repository URL on Canvas.**

## Problem Statement

## Part I: Describe Possibilities and Your Solution (10 marks)

Create a document consisting of no more than **4 pages** that describes at least 3 different database systems and how they may apply to the problem. Provide benefits and challenges with each system. Select a particular database system and argue why it is the best choice for this problem. Note: The database system chosen does not have to be the same database system that you implement in the second part.

### Marking Guide

- +2 marks for explaining some of the considerations of the problem (data size, queries, consistency, etc.).
- +3 marks for providing benefits/challenges with at least 3 potential database systems
- +2 marks for arguing why you would select a particular database
- +3 marks for writing style, organization, and grammar

## Part II: Implement a Solution (20 marks)

Implement a database system that solves the problem statement. Create unit tests that verify that the data is loaded and queried properly. You may select any database except a traditional relational database such as MySQL/PostgreSQL. Submit your code and a screenshot of the tests passing within your repository.

### Marking Guide

- +2 marks - Write a method `create()` to create tables to store data.

- +3 marks - Write a method `load()` to load two data sets: `GameState` stores current game state for each players and `GameEvent` that stores information on each game event that changes a player's state.
 
- +2 marks - Write a method `insert()` that loads the sample data for each data set. Available in the files `gamestate.csv` and `gameevent.csv`.

- +2 marks - Write a method `update()` that updates the current game state for a player.

- +2 marks - Write a method `query1()` that returns the game state for a player given the player `id`. Return a string with the state information in any format.

- +2 marks - Write a method `query2()` that returns the top 10 players by level in a given region.

- +2 marks - Write a method `query3()` that returns the top 5 most active players between time X and Y in a given region.

- +5 marks - Write unit tests to demonstrate that your previously constructed methods are working properly.

