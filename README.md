# CSE-5058 ARTIFICIAL INTELLIGENCE [Constraint Satisfaction Problem Implementation]
This project is for CSE-5058 `Introduction to Artificial Intelligence` course at the University of Akdeniz. 
The goal is to implement generic approach for given Constraint Satisfaction Problems (CSP) which is inside _.../src/main/resources/CSP-Project-Classroom.txt_.

#PROBLEM APPROACH
I have constructed the project around significant abstraction of CSPs and implemented pseudocode for solving given problem that is assignment of classroom course pair.
A Problem class that is generic CSP class is modeled as a subclass of the CSCScheduleProblem class. 
This class serves set of "Domains", "Variables" and "Constraints". 

                                                    Main Structure
                                            
                                                 ____constraint____
                                                |                  |
                                                |   __variable__   |
                                                |  |            |  |
                                                |  |  _domain_  |  |
                                                |  |  |      |  |  |
                                                |  |  |______|  |  |
                                                |  |____________|  |
                                                |__________________|

_A Domain_ that is one of the main unit for the problem space has a domain description.
_A Variable_ that is yet another main unit for the problem space has its description, set of possible domains and its assignment.
_A Constraint_ may be any arbitrary function and it must operate over unassigned variable.
There is also generic _Assignment_ class which satisfies all constraints on problem space is modeled as a subclass of the CSCScheduleAssignment class. 
This class is simple hash table which is mapping between variable and problem specific time range.
To solve the CSP we provide a _Backtrack_ class which implements simple backtracking algorithm.
Firstly It assigns the whole possible domain of variable for preparing problem search tree and continues to satisfy the constraint until all options have been exhausted or a solution is found.
This approach works well for simple problems
For more complicated problems, we need some heuristics which generalize like `Ordering` and `Filtering` such as **Minimum-Remaining-Values (MRV)** and **Least-Constraining-Value (LCV)**.
MRV a.k.a Most-Constraint-Value (MCV) chooses the variable with the most constraints on remaining variables.
LCV chooses the least constraints on remaining variables.

#USAGE
To test the program, just check CSCScheduleProgramSpec class that is unit test of CSP solving approach