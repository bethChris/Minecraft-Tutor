# Minecraft-Tutor
This is the start of the readme


## Bethany Notes

### Adaptive Cycle
Capture:

	- Clicks/clicked objects
	- Right/wrong final outputs
	- Distance from tutoring system 

Analyze:

	- ah

Select:

	- ah

Present:

	-ah


### Student Model
Attributes:

	- Keeps track of all learners gained Knowledge Components
	- current task
	- completed tasks?


### Knowledge Component Class
Attributes:

	- Title : Name of the component
	- successes : how many times they've successfully demonstrated the knowledge component
	- failures : how many times they've failed to demonstrate the knowledge component
	- Master level = successes / successes + failures 

Notes:
- Could potentially make this an abstract class and then have "potion" and "ingrediant" components that way we can keep track of the item that represents the product of knowing the component?
- We can then have knowledge components keep track of other knowledge components that they build off of. (Like any effect potion building off of an awkward potion and an awkward potion building off of water and nethwart)


### Tasks Class
Attributes:

	- Prompt : what we feed to the user in chat to prompt their lesson
	- Final State : the correct inventory state and product of brewing to grade with
	- Knowledge component lists : all the knowledge components that are associated with this task

Notes:
- Might add a difficulty rating?
- Tasks should probably include all knowledge components associated with the task, including the knowledge component for the potion it is trying to teach. 



## Fun Quirky Little Notes:
- Q matrix for defining relationship between knowledge components / tasks
