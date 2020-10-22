package coreImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import enums.Gender;
import enums.Message;
import model.Person;

/**
 * build pre defined family tree of King Arthur and Queen Margret 
 * and uses same method to add new members in future as well
 * @author Pooja
 *
 */
public class BuildFamilyTree {

	HashMap<String,Person> mapNamePerson;

	/**
	 * Constructor
	 */
	public BuildFamilyTree() {
		mapNamePerson = new HashMap<>();
	}

	/**
	 * Constructor with parameter
	 * @param map
	 */
	public BuildFamilyTree(HashMap<String,Person> map) {
		mapNamePerson = map;
	}
	
	/**
	 * builds up given family tree
	 */
	public void buildTree() {
		setRoot("Arthur","Margret");
		//1st gen
		addChild("Margret","Bill", Gender.Male);
		addChild("Margret","Charlie", Gender.Male);
		addChild("Margret","Percy", Gender.Male);
		addChild("Margret","Ronald", Gender.Male);
		addChild("Margret","Ginerva", Gender.Female);
		marry("Bill","Flora",Gender.Female);
		marry("Percy","Audrey",Gender.Female);
		marry("Ronald","Helen",Gender.Female);
		marry("Ginerva","Harry",Gender.Male);
		//2nd gen
		addChild("Flora","Victoire",Gender.Female);
		marry("Victoire","Ted",Gender.Male);
		addChild("Flora","Dominique",Gender.Female);
		addChild("Flora","Louis",Gender.Male);
		addChild("Audrey","Molly",Gender.Female);
		addChild("Audrey","Lucy",Gender.Female);
		
		addChild("Helen","Rose",Gender.Female);
		addChild("Helen","Hugo",Gender.Male);
		marry("Rose","Malfoy",Gender.Male);
		
		addChild("Ginerva","James",Gender.Male);
		addChild("Ginerva","Albus",Gender.Male);
		addChild("Ginerva","Lily",Gender.Female);
		marry("James","Darcy",Gender.Female);
		marry("Albus","Alice",Gender.Female);
		
		//3rd gen

		addChild("Victoire","Remus",Gender.Male);
		addChild("Rose","Draco",Gender.Male);
		addChild("Rose","Aster",Gender.Female);
		addChild("Darcy","William",Gender.Male);
		addChild("Alice","Ron",Gender.Male);
		addChild("Alice","Ginny",Gender.Female);

	}
	
	
	/**
	 * Method to initiate marriage of an existing family member with a new member 
	 * @param InFamilyMemberName
	 * @param newMemberName
	 * @param newMemberGender
	 */
	public boolean marry(String InFamilyMemberName, String newMemberName, Gender newMemberGender) {
		boolean successFlag = false;
		//first member should be already existing in family and does not have spouse
		//second member should not be already in family
		if(checkPersonInFamily(InFamilyMemberName) && !checkPersonInFamily(newMemberName) && !mapNamePerson.get(InFamilyMemberName).hasSpouse()) {
			//both partners should have different gender
			if(!mapNamePerson.get(InFamilyMemberName).getGender().equals(newMemberGender)) {
				Person newMember = new Person(newMemberName,newMemberGender);
				mapNamePerson.put(newMemberName,newMember);
				setSpouse(InFamilyMemberName,newMemberName);
				successFlag = true;
			}
			else {
				successFlag = false;
				System.out.println(Message.UNION_FAIL);
			}
		}
		return successFlag;
	}

	/**
	 * Create and sets the root of family tree
	 * @param kingName
	 * @param queenName
	 */
	 void setRoot(String kingName, String queenName) {
			Person king = new Person(kingName,Gender.Male);
			mapNamePerson.put(kingName, king);
			Person queen = new Person(queenName,Gender.Female);
			mapNamePerson.put(queenName, queen);
			setSpouse(kingName,queenName);
	  }
	 
	 /**
	  * sets  2 family members as spouses
	  * @param partner1
	  * @param partner2
	  */
	 boolean setSpouse(String partner1Name, String partner2Name) {
		 boolean successflag = false;
		 //if both partners exist and doesn't have a partner already
		 if(checkPersonInFamily(partner1Name) && checkPersonInFamily(partner2Name)) {
			 Person partner1 = mapNamePerson.get(partner1Name);
			 Person partner2 = mapNamePerson.get(partner2Name);
			 partner1.setSpouse(partner2);
			 partner2.setSpouse(partner1);
			 successflag = true;
		 }
		 else {
			 System.out.println(Message.UNION_FAIL);
		 }
		return successflag;
	}
	
	 /**
	  * complete method to add a new child to the family tree
	  * @param motherName
	  * @param childName
	  * @param gender
	  * @return true if child added
	  */
	 public boolean addChild(String motherName,String childName, Gender gender) {
		boolean addFlag = true;
		//Mother should be in family
		if(checkPersonInFamily(motherName)) { 
			Person mother = mapNamePerson.get(motherName);
			//mother has to female; should have a spouse; no duplicate member addition to family 
			if(mother.getGender() != Gender.Female || mother.getSpouse() == null || checkPersonInFamily(childName)) {
				System.out.println(Message.CHILD_ADDITION_FAILED); 	
				addFlag = false;
			}
			else {
				addParentsToChild(motherName, childName, gender);
				addChildToParents(mother,childName);		
			}
		}
		else {
			System.out.println(Message.PERSON_NOT_FOUND);
			addFlag = false;
		}
		return addFlag;
	}
	
	 /**
	  * set parents for new child
	  * @param motherName
	  * @param childName
	  * @param gender
	  */
	 void addParentsToChild(String motherName,String childName, Gender gender) {
		if(checkPersonInFamily(motherName)) { //PERSON EXIST IN TREE
			Person mother = mapNamePerson.get(motherName);
			
			if(mother.getGender() != Gender.Female) {
				System.out.println(Message.CHILD_ADDITION_FAILED);
			}
			
			else {
				Person newChild = new Person(childName,gender);
				mapNamePerson.put(childName,newChild);
				Set<Person> newParents = new HashSet<>(); 
				newParents.add(mother);
				newParents.add(mother.getSpouse());
				newChild.setParents(newParents);	//Parents added to child
			}	
		}
		
		else {
			System.out.println(Message.PERSON_NOT_FOUND);
		}
	}
	
	 /**
	  * add child to parents
	  * @param mother
	  * @param childName
	  */
	 void addChildToParents(Person mother,String childName) {
		//need to check if child has a parent or not
		Person child = mapNamePerson.get(childName);
		if(child.hasParents()) {
			ArrayList<Person> childrenOfMother = mother.getChildren();
			//child added to mother
			childrenOfMother.add(child); 
			Person father = mother.getSpouse();
			ArrayList<Person> childrenOfFather = father.getChildren();
			//child added to father
			childrenOfFather.add(child);
		}
	}
	 
	 /**
	  * checks if person already exist in tree or not 
	  * @param personName
	  * @return true if person exist already
	  */
	public boolean checkPersonInFamily(String personName) {
			if(mapNamePerson.containsKey(personName)) { 
				return true;
			}
			return false;
	} 
	
	/**
	 * returns HashMap 
	 * to be used in unit tests
	 * @return
	 */
	HashMap<String,Person> getmapNamePerson(){
		return mapNamePerson;
	}
}
