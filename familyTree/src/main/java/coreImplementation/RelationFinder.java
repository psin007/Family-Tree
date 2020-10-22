package coreImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import enums.Gender;
import enums.RelationName;
import model.Person;

public class RelationFinder {
	HashMap<String,Person> mapNamePerson;

	/**
	 * Constructor
	 */
	public RelationFinder() {
		mapNamePerson = new HashMap<>();
	}
	
	/**
	 * Constructor
	 * @param map of Person name and its object
	 */
	public RelationFinder(HashMap<String,Person> map) {
		mapNamePerson = map;
	}
	
	/**
	 * This method return list of either brothers or sisters
	 * @param personName person whose brother/sister we are looking
	 * @param personName person whose brother/sister we are looking
	 * @return ArraList of all brothers or sisters
	 */
	ArrayList<Person> getGenderWiseSibling(String personName,RelationName relation){
		ArrayList<Person> genderWiseSibling = new ArrayList<>();
		ArrayList<Person> siblings = getSibling(personName);
		if(siblings.size() > 0) {
			//For Brother, Gender is male and for sister, it's female
			Gender genderRelation = null;
			if(relation.equals(RelationName.Brother)) genderRelation = Gender.Male;
			else if(relation.equals(RelationName.Sister)) genderRelation = Gender.Female;
			else return genderWiseSibling;
			for(Person sibling : siblings) {
				if(sibling.getGender().equals(genderRelation)) {
					genderWiseSibling.add(sibling);
				}
			}	
		}
		return genderWiseSibling;
	}
	
	/**
	 * This method returns either mother or father   
	 * @param personName Name of person of child whose father or mother we are searching
	 * @param relation should be either father or mother
	 * @return mother/father object
	 */
	Person getGenderWiseParent(String personName,RelationName relation) {
		Person parent = null;
		if(mapNamePerson.containsKey(personName)) {
			Person child = mapNamePerson.get(personName);
			if(child.getParents() != null) {
				for(Person person : child.getParents()) {
					//For father, Gender is male and for Mother, it's female
					Gender genderRelation = null;
					if(relation.equals(RelationName.Father)) genderRelation = Gender.Male;
					else if(relation.equals(RelationName.Mother)) genderRelation = Gender.Female;
					else return parent;
					if(person.getGender().equals(genderRelation)) {
						parent = person;
					}
				}
			}
		}
		return parent;
	}
	
	/**
	 * to fetch all the siblings
	 * @param personName 
	 * @return ArrayList of all the siblings, if any
	 */
	ArrayList<Person> getSibling(String personName){
		ArrayList<Person> siblings = new ArrayList<>();

		if(!mapNamePerson.containsKey(personName)) return siblings;
		
		Person person = mapNamePerson.get(personName);
		Person mother = getGenderWiseParent(person.getName(),RelationName.Mother);
		if(mother != null) {
			ArrayList<Person> children = mother.getChildren();	
			for(Person child: children) {
				if(!child.getName().equals(person.getName())) {
					siblings.add(child);
				}
			}
		}	
		return siblings;
		
	}
	
	/**
	 * 
	 * @param relation either husband or wife
	 * @param otherSpouseName partner who we are searching for 
	 * @return spouse object if any
	 */
	Person getSpouse(RelationName relation, String otherSpouseName) {
		Person spouse = null;
		if(!mapNamePerson.containsKey(otherSpouseName)) return null;
		Person otherSpouse = mapNamePerson.get(otherSpouseName);
		//Husband = male and Wife = female
		Gender spouseGender = null;
		if(relation.equals(RelationName.Husband)) spouseGender = Gender.Male;
		else if(relation.equals(RelationName.Wife)) spouseGender = Gender.Female;
		else return spouse;
		
		//spouse exist and is male for husband or female for wife
		if(otherSpouse.getSpouse() != null && otherSpouse.getSpouse().getGender().equals(spouseGender)) { 
			spouse = otherSpouse.getSpouse();
			return spouse;
		} 
		return spouse;
	}
	
	/**
	 * Overloading method
	 * just to find spouse without knowing about asker's gender
	 * @param otherSpouseName
	 * @return
	 */
	Person getSpouse(String otherSpouseName) {
		Person spouse = null;
		if(mapNamePerson.containsKey(otherSpouseName)) {
			Person otherSpouse = mapNamePerson.get(otherSpouseName);
			spouse = otherSpouse.getSpouse();
		}
		return spouse;
	}
	
	/**
	 * fetches either son or daughter of a person
	 * @param relation either Son or Daughter
	 * @param parent person whose relation we are looking for
	 * @return array list of all sons or daughters, if any
	 */
	ArrayList<Person> getGenderWiseChild(RelationName relation,String parentName) {
		ArrayList<Person> specifiedGenderChildList = new ArrayList<>();
		if(!mapNamePerson.containsKey(parentName)) return specifiedGenderChildList;
		Person parent = mapNamePerson.get(parentName);
		//Son = male ; Daughter = female
		Gender childGender = null;
		if(relation.equals(RelationName.Son)) childGender = Gender.Male;
		else if(relation.equals(RelationName.Daughter)) childGender = Gender.Female;
		else return specifiedGenderChildList;
		ArrayList<Person> children = parent.getChildren();
		if(children != null) {
			for(Person child: children) {
				if(child.getGender().equals(childGender)) {
					specifiedGenderChildList.add(child);
				}
			}
		}
		
		return specifiedGenderChildList;
	}
	
	/**
	 * paternal_uncle or maternal_uncle or paternal_aunt or maternal_aunt
	 * parent = father and sibling = brother => Paternal_uncle
	 * parent = father and sibling = sister => Paternal_aunt
	 * parent = mother and sibling = brother => Maternal_uncle
	 * parent = mother and sibling = sister => Maternal_aunt
	 * @param relation
	 * @param personName
	 * @return arrayList of uncles or aunts if any
	 */
	ArrayList<Person> getUncleOrAunt(RelationName relation, String personName){
		ArrayList<Person> relatives = new ArrayList<>();
		RelationName specificParent;
		RelationName specificGendersibling;
		
		switch(relation) {
			case Paternal_Uncle: specificParent = RelationName.Father;
								 specificGendersibling = RelationName.Brother;
								 break;
			case Paternal_Aunt : specificParent = RelationName.Father;
			 					 specificGendersibling = RelationName.Sister;
			 					 break;
			case Maternal_Uncle: specificParent = RelationName.Mother;
			 					 specificGendersibling = RelationName.Brother;
			 					 break;
			case Maternal_Aunt : specificParent = RelationName.Mother;
			 					 specificGendersibling = RelationName.Sister;
			 					 break;	
			default: specificParent = null;
					 specificGendersibling = null;
		}
		
		if(specificParent != null && specificGendersibling != null) {
			Person parent = getGenderWiseParent(personName, specificParent);
			if(parent != null) {
				String parentName = parent.getName();
				relatives = getGenderWiseSibling(parentName, specificGendersibling);
			}	
		}
		return relatives;
	}
	
	/**
	 * Sister in law => Spouse sisters + Siblings' wives
	 * Brother in law => Spouse brothers + Siblings' husbands
	 * Common method for both of them
	 * @param personName
	 * @return ArrayList of Sisters or brothers in law
	 */
	ArrayList<Person> getSiblingInLaw(RelationName relation,String personName){
		ArrayList<Person> siblingInLaws = new ArrayList<Person>();
		Person spouse = getSpouse(personName);
		RelationName spouseSpecificSibling; //either brother or sister of spouse
		RelationName siblingSpecificSpouse; //either wife for sis in law or husband for bro in law
		switch(relation){
			case Sister_In_Law: spouseSpecificSibling = RelationName.Sister;
								siblingSpecificSpouse = RelationName.Brother;
								break;
			case Brother_In_Law: spouseSpecificSibling = RelationName.Brother;
								 siblingSpecificSpouse = RelationName.Sister; // Husband of sisters
								 break;
			default: spouseSpecificSibling = null;
					 siblingSpecificSpouse = null;
		}
		//for either brothers or sisters of spouse
		if(spouse != null) { 
			ArrayList<Person> spouseSpecificSiblingsList = getGenderWiseSibling(spouse.getName(), spouseSpecificSibling);
			for(Person spouseSpecificSiblingElem: spouseSpecificSiblingsList) {
				siblingInLaws.add(spouseSpecificSiblingElem);
			}
		}
		//for siblings' wives or husbands
		ArrayList<Person> specificSiblings = getGenderWiseSibling(personName, siblingSpecificSpouse);
		if(specificSiblings != null && specificSiblings.size() > 0) {
			ArrayList<Person> siblingSpouses = new ArrayList<>();
			for(Person sibling:specificSiblings) {
				if(sibling.hasSpouse()) {
					siblingSpouses.add(sibling.getSpouse());
				}
			}
			for(Person siblingSpouse: siblingSpouses) {
				siblingInLaws.add(siblingSpouse);
			}
		}
		return siblingInLaws;
	}
}
