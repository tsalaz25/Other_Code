# Lab - MultiChefs
*CS351L Fall 2024*

In this lab, you will be given a multithreaded program that has a bug. You will need to fix the bug with the described restrcitions below.
It is not necessary to create and submit a jar file, simply commit and push your code changes with the bugfix.  

## The Program - MultiChefs

> DISCLAIMER: This program does not follow all best practices for multithreading nor
> does it adhere to the coding standards of the class. So don't use this as an example of good code.

The program simulates a Restaurant with multiple chefs. 
The chefs are simulated by threads, each taking orders from a queue to cook.
The restaurant has the following members:
* Queue of dishes to cook
* Set of chefs
* Set of cooking stations
* Map of recipes, defining the dish, cook station, and time to cook.

### The Bug and What to Fix
The program currently works for a single thread/chef. But increasing the number of chefs causes the program to crash.
Specifically, the program crashes as a result of multiple chefs attempting to cook at the same station at the same time.
You will need to fix the program so that multiple chefs can cook at the same time without crashing.

In order to fix the program, you must modify the `CookStation` class to be thread-safe.
With the following restrictions:
* **Must** modify the _body_ of `occupyStation` and `releaseStation` methods to be thread-safe, ensuring
they also correctly set the `currentChef`. **Do not change their signature**
* **Must** use some form of proper synchronization, but you can use any synchronization method you wish, such as an atomic variable, a lock, or a semaphore, or synchronized code blocks.
* **May** _add_ additional methods or members to the `CookStation` class to fix the bug (and any necessary initialization of a new member in the constructor of `CookStation`). 
* **Cannot** modify any _existing function_ other than `occupyStation` and `releaseStation`,
  in the `CookStation` class or any other class.

> **Any modification of the program beyond the restrictions will not receive full credit.**

### Running the Program

The program takes in a text file as a command line argument to configure the restaurant.
You have been provided with four config files in `test_files`, two multithreaded and two single threaded.
The program will read the configuration file and simulate the restaurant. 
On a successful execution,it will output the time it took to cook all the dishes.
On a failed execution/crash, it will output an error message. 
Note: The program may not always crash, but it will not output a consistent time, 
therefore you should test your fix multiple times to ensure it works.
