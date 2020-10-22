package coreImplementation;

import java.util.ArrayList;
import java.util.HashMap;

import enums.Message;
import enums.RelationName;
import model.Person;

/**
 * This class displays the the other member(s) of a relationship
 * @author Pooja
 *
 */
public class QueryResultPrinter {

	HashMap<String,Person> mapNamePerson;
	RelationFinder searchRelation;
	
	/**
	 * Constructor
	 */
	public QueryResultPrinter() {
		mapNamePerson = new HashMap<>();
		searchRelation = new RelationFinder();
	}
	
	/**
	 * Constructor
	 * @param map 
	 */
	public QueryResultPrinter(HashMap<String,Person> map) {
		mapNamePerson = map;	
		searchRelation = new RelationFinder(map);
	}

	/**
	 * common method to display either son or daughter
	 * @param relation - either Son or Daughter
	 * @param personName
	 */
	void displayChild(RelationName relation,String personName) {
		ArrayList<Person> specifiedGenderChildren = searchRelation.getGenderWiseChild(relation,personName);
		displayList(specifiedGenderChildren);
	}
		 
	/**
	 * common method which query Parse parses to find either brothers or sisters
	 * @param relation either brother or sister
	 * @param personName
	 */
	void displayGenderWiseSibling(RelationName relation, String personName) {
		ArrayList<Person> specifiedGenderSibling = searchRelation.getGenderWiseSibling(personName,relation);
		displayList(specifiedGenderSibling);
	}	
	
	/**
	 * Displays names of persons in a ArrayList of Person object
	 * @param persons
	 */
	void displayList(ArrayList<Person> persons) {
		if(persons.size() > 0) {
			for(Person person: persons) {
				System.out.print(person.getName() + " ");
			}
			System.out.println();
		}
		else {
			System.out.println(Message.NONE);
			
		}
		
	}
	
	/**
	 * Displays Spouse of a person if any
	 * @param relation either Husband or Wife
	 * @param otherSpouseName
	 */
	void displaySpouse(RelationName relation, String otherSpouseName) {
		Person spouse = searchRelation.getSpouse(relation,otherSpouseName);
		displayPerson(spouse);
	}
	
	/**
	 * Displays father or mother of a child
	 * @param relation
	 * @param childName
	 */
	void displayGenderWiseParent(RelationName relation, String childName) {
		Person parent = searchRelation.getGenderWiseParent(childName, relation);
		displayPerson(parent);
	}
	
	/**
	 * Displays name of person object if not null
	 * @param person 
	 */
	void displayPerson(Person person) {
		if(person != null) {
			System.out.println(person.getName());
		}
		else {
			System.out.println(Message.PERSON_NOT_FOUND);
		}
	}
		
	/**
	 * Displays all siblings of a person, if any
	 * @param personName
	 */
	void displaySibling(String personName) {
		if(mapNamePerson.containsKey(personName)) {
			ArrayList<Person> siblings = searchRelation.getSibling(personName);
			if(siblings != null && siblings.size() > 0) {
				displayList(siblings);
			}
			else {
				System.out.println(Message.NONE);
				
			}	
		}
		else {
			System.out.println(Message.PERSON_NOT_FOUND);	
		}
	}

	/**
	 * Displays paternal or maternal uncle if any
	 * Paternal Uncle - brother of father
	 * Maternal Uncle - brother of mother
	 * @param relation - either Paternal Uncle or Maternal Uncle
	 * @param personName
	 */
	void displayUncleOrAunt(RelationName relation, String personName) {
		ArrayList<Person> relatives = searchRelation.getUncleOrAunt(relation,personName);
		displayList(relatives);
	}

	/**
	 * Display sister in laws of a person if any
	 * @param personName
	 */
	void displaySiblingInLaw(RelationName relation, String personName) {
		ArrayList<Person> sisterInLaws = searchRelation.getSiblingInLaw(relation,personName);
		displayList(sisterInLaws);
	}

}
