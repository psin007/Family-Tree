package coreImplementation;

import java.util.HashMap;

import enums.Gender;
import enums.Message;
import enums.RelationName;
import model.Person;

/**
 * This class is responsible for passing query from view to 
 * @author Pooja
 *
 */
public class QueryParser {

	HashMap<String,Person> mapNamePerson;

	/**
	 * Constructor
	 */
	public QueryParser() {
		
	}
	
	/**
	 * Constructor
	 * @param map
	 */
	public QueryParser(HashMap<String,Person> map) {
		mapNamePerson = map;
		
	}
	
	/**
	 * Passes Query to add a new member to family 
	 * @param message command ADD_CHILD
	 * @param mother 
	 * @param child
	 * @param gender
	 */
	void passQueryToAdd(Message message, String mother, String child, Gender gender) {
		if(message.equals(Message.ADD_CHILD)) {
			BuildFamilyTree buildFamilyTree = new BuildFamilyTree(mapNamePerson);
			boolean addChildFlag = buildFamilyTree.addChild(mother, child, gender);	
			if(addChildFlag)	System.out.println(Message.CHILD_ADDED);
		}
		else {
			System.out.println("Invalid Command");
		}
	}
	
	/**
	 * query to find a relationship of a person
	 * @param message GET_RELATIONSHIP
	 * @param person
	 * @param relation
	 */
	void passQueryToRetrieve(Message message, String person, RelationName relation) {
		if(message.equals(Message.GET_RELATIONSHIP)) findRelation(relation,person);
		else System.out.println("Invalid Command");
		
	}
	
	/**
	 * calls appropriate method to find other member(s) of a relationship
	 * Defined Relations - Son, Daughter, Sibling, Husband, Wife, Paternal_Uncle,Maternal_Uncle,Paternal_Aunt,Maternal_Aunt,Sister_In_law,Brother_In_law
	 * @param relation
	 * @param personName
	 */
	void findRelation(RelationName relation, String personName ) {
		
		// if Person does not exist
		if(!mapNamePerson.containsKey(personName)) {
			System.out.println(Message.PERSON_NOT_FOUND);
		}
		
		else {
			QueryResultPrinter displayQueryResult = new QueryResultPrinter(mapNamePerson);
				
			switch(relation) {

				case Son: displayQueryResult.displayChild(RelationName.Son,personName);
						  break;	
						  
				case Daughter: displayQueryResult.displayChild(RelationName.Daughter,personName);
							   break;
							   
				case Siblings: displayQueryResult.displaySibling(personName);
							  break;
					
				case Husband: displayQueryResult.displaySpouse(RelationName.Husband,personName);
							  break;
							  
				case Wife: displayQueryResult.displaySpouse(RelationName.Wife,personName);
				           break;
				           
				case Brother: displayQueryResult.displayGenderWiseSibling(RelationName.Brother,personName);
							 break;
							 
				case Sister: displayQueryResult.displayGenderWiseSibling(RelationName.Sister,personName);
				 			break;
				 			
				case Father: displayQueryResult.displayGenderWiseParent(RelationName.Father, personName);
							break;
							
				case Mother: displayQueryResult.displayGenderWiseParent(RelationName.Mother, personName);
							 break;
				           
				case Paternal_Uncle: displayQueryResult.displayUncleOrAunt(RelationName.Paternal_Uncle,personName);
									 break;
									 
				case Maternal_Uncle: displayQueryResult.displayUncleOrAunt(RelationName.Maternal_Uncle,personName);
				 					 break;
				 					 
				case Paternal_Aunt: displayQueryResult.displayUncleOrAunt(RelationName.Paternal_Aunt,personName);
									break;
									
				case Maternal_Aunt: displayQueryResult.displayUncleOrAunt(RelationName.Maternal_Aunt,personName);
									break;
									
				case Sister_In_Law: displayQueryResult.displaySiblingInLaw(RelationName.Sister_In_Law,personName);
									break;
									
				case Brother_In_Law: displayQueryResult.displaySiblingInLaw(RelationName.Brother_In_Law,personName);
									 break;
					
				default : System.out.println("Invalid Relation Requested");	
			}
		}
	}

}
