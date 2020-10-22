package coreImplementation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import enums.RelationName;
import model.Person;

class RelationFinderTest {
	
	RelationFinder searchRelation;
	BuildFamilyTree buildFamilyTree;
	HashMap<String,Person> map;
	
	@BeforeEach
	void init() {
		buildFamilyTree = new BuildFamilyTree();
		buildFamilyTree.buildTree();
		map = buildFamilyTree.getmapNamePerson();
		searchRelation = new RelationFinder(map);

	}

	@Test
	void testGetGenderWiseSibling() {
		ArrayList<Person> siblings = new ArrayList<>();
		siblings.add(map.get("Charlie"));
		siblings.add(map.get("Percy"));
		siblings.add(map.get("Ronald"));
		assertEquals(siblings, searchRelation.getGenderWiseSibling("Bill", RelationName.Brother));
		siblings.clear();
		siblings.add(map.get("Victoire"));
		assertEquals(siblings, searchRelation.getGenderWiseSibling("Dominique", RelationName.Sister));
		siblings.clear();
		assertEquals(siblings, searchRelation.getGenderWiseSibling("Molly", RelationName.Brother));
		siblings.clear();
		assertEquals(siblings, searchRelation.getGenderWiseSibling(" ", RelationName.Sister));
		siblings.clear();
		siblings.add(map.get("Hugo"));
		assertEquals(siblings, searchRelation.getGenderWiseSibling("Rose", RelationName.Brother));
		siblings.clear();
		siblings.add(map.get("Molly"));
		assertEquals(siblings, searchRelation.getGenderWiseSibling("Lucy", RelationName.Sister));
		
		
	}

	@Test
	void testGetGenderWiseParent() {
		assertEquals(null,searchRelation.getGenderWiseParent("Arthur", RelationName.Father));
		assertEquals(map.get("Bill"),searchRelation.getGenderWiseParent("Victoire", RelationName.Father));
		assertEquals(null,searchRelation.getGenderWiseParent("Ted", RelationName.Father));
		assertEquals(map.get("Audrey"),searchRelation.getGenderWiseParent("Molly", RelationName.Mother));
		assertEquals(null,searchRelation.getGenderWiseParent(" ", RelationName.Father));

	}
	
	
	/**
	 * Test over loaded method
	 */
	@Test
	void testGetSpouse() {
		assertEquals(map.get("Margret"),searchRelation.getSpouse("Arthur"));
		assertEquals(null,searchRelation.getSpouse(" "));
		assertEquals(null,searchRelation.getSpouse("Remus"));
		//overriden method
		assertEquals(map.get("Margret"),searchRelation.getSpouse(RelationName.Wife,"Arthur"));
		assertEquals(null,searchRelation.getSpouse(RelationName.Husband,"Arthur"));
		assertEquals(null,searchRelation.getSpouse(RelationName.Wife," "));
		assertEquals(map.get("Ted"),searchRelation.getSpouse(RelationName.Husband,"Victoire"));
		assertEquals(null,searchRelation.getSpouse(RelationName.Wife,"Victoire"));
	}
	
	@Test
	void testGetSibling() {
		ArrayList<Person> siblings = new ArrayList<>();
		assertEquals(siblings, searchRelation.getSibling("Arthur"));
		siblings.clear();
		siblings.add(map.get("Charlie"));
		siblings.add(map.get("Percy"));
		siblings.add(map.get("Ronald"));
		siblings.add(map.get("Ginerva"));
		assertEquals(siblings, searchRelation.getSibling("Bill"));
		siblings.clear();
		siblings.add(map.get("James"));
		siblings.add(map.get("Lily"));
		assertEquals(siblings, searchRelation.getSibling("Albus"));
		siblings.clear();
		assertEquals(siblings, searchRelation.getSibling("Remus"));
		siblings.clear();
		assertEquals(siblings, searchRelation.getSibling(" "));
		
	}
	
	@Test
	void testGetGenderWiseChild() {
		ArrayList<Person> children = new ArrayList<>();
		children.add(map.get("Louis"));
		assertEquals(children, searchRelation.getGenderWiseChild(RelationName.Son,"Bill"));
		children.clear();
		assertEquals(children, searchRelation.getGenderWiseChild(RelationName.Son,"Remus"));
		children.clear();
		assertEquals(children, searchRelation.getGenderWiseChild(RelationName.Daughter,"Pooja"));
		children.clear();
		children.add(map.get("Molly"));
		children.add(map.get("Lucy"));
		assertEquals(children, searchRelation.getGenderWiseChild(RelationName.Daughter,"Percy"));
	}
	
	@Test
	void testPaternalUncle() {
		ArrayList<Person> relatives = new ArrayList<>();
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Uncle,"Arthur"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Uncle,"Flora"));
		relatives.add(map.get("Charlie"));
		relatives.add(map.get("Percy"));
		relatives.add(map.get("Ronald"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Uncle,"Victoire"));
		relatives.clear();
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Uncle,"Pooja"));
	}
	
	@Test
	void testMaternalUncle() {
		ArrayList<Person> relatives = new ArrayList<>();
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Maternal_Uncle,"Pooja"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Uncle,"Margret"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Maternal_Uncle,"Molly"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Maternal_Uncle,"Darcy"));
		relatives.add(map.get("Bill"));
		relatives.add(map.get("Charlie"));
		relatives.add(map.get("Percy"));
		relatives.add(map.get("Ronald"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Maternal_Uncle,"Lily"));
	}
	
	@Test 
	void testPaternalAunt() {
		ArrayList<Person> relatives = new ArrayList<>();
		relatives.add(map.get("Lily"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Aunt,"Ginny"));
		relatives.clear();
		relatives.add(map.get("Lily"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Aunt,"William"));
		relatives.clear();
		relatives.add(map.get("Ginerva"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Paternal_Aunt,"Louis"));
		
	}
	
	@Test
	void testMaternalAunt() {
		ArrayList<Person> relatives = new ArrayList<>();
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Maternal_Aunt,"James"));
		relatives.clear();
		relatives.add(map.get("Dominique"));
		assertEquals(relatives, searchRelation.getUncleOrAunt(RelationName.Maternal_Aunt,"Remus"));
	}
	
	@Test
	void testBrotherInLaw() {
		ArrayList<Person> relatives = new ArrayList<>();
		relatives.add(map.get("Louis"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Brother_In_Law,"Ted"));
		relatives.clear();
		relatives.add(map.get("Malfoy"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Brother_In_Law,"Hugo"));
		relatives.clear();
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Brother_In_Law,"Arthur"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Brother_In_Law,"Pooja"));
		relatives.add(map.get("Harry"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Brother_In_Law,"Bill"));
	}
	
	@Test
	void testSisterInLaw() {
		ArrayList<Person> relatives = new ArrayList<>();
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Sister_In_Law,"Arthur"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Sister_In_Law,"Pooja"));
		relatives.add(map.get("Dominique"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Sister_In_Law,"Ted"));
		relatives.clear();
		relatives.add(map.get("Flora"));
		relatives.add(map.get("Audrey"));
		relatives.add(map.get("Helen"));
		assertEquals(relatives, searchRelation.getSiblingInLaw(RelationName.Sister_In_Law,"Ginerva"));
		relatives.clear();	
	}
	
	
	
}
