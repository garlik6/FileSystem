# FileSystem

Group study project aimed at improving teamwork skills and becoming familiar with development best practices such as:
- OOP
- TDD 
- SOLID, Low Coupling, High Cohesion
- Design patterns (Singleton, Factory Method, Command and so on)

File system should resemble [RT 11](https://ru.wikipedia.org/wiki/RT-11) filesystem which adds additional constraints (refactoring etc.)

## Noteworthy technologies
- Heavy Stream API usage throughout implementation of collections related functionality
- Junit5
Was used for unit testing
- Jackson 
	- communication between different modules in project utilizes JSON as a preferred format so Jackson was used for object mapping4
	- Different JSON views  were added for internal and public mapping 
	- For additional JSON tweaking Mix Ins mechanism was used
- Lombok
