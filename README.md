# Family-Tree

This project is to demonstrate knowledge of Java, and OO principle design.

A family tree of a particular family is designed and implemented. GIven a 'name' and 'relationship', it outputs the people corresponding to the relationship in the ordeer in which they are added in family tree and it also allows to add a child to any family in tree through the mother.

The program reads text from a text file as a parameter while running the application.

Relationship defined are: 
Son, Daughter, Siblings, Husband, Wife, Father, Mother, Brother, Sister, Paternal_Uncle,Maternal_Uncle,Paternal_Aunt,Maternal_Aunt,Sister_In_Law,Brother_In_Law

Sample input/Output: <br/>
ADD_CHILD "MothersName" "ChildName" "Gender"<br/>
eg: Input - ADD_CHILD Flora Pooja Female <br/>
Output - CHILD_ADDED<br/>
GET_RELATIONSHIP "Name" "Relationship"<br/>
eg: Input -  GET_RELATIONSHIP Pooja Siblings<br/>
Output - Victoire Dominique Louis <br/>
Following figure depicts sample family tree:<br/>
![Screenshot of family tree](familyTree.JPG)

