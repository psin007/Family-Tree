package coreImplementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import coreImplementation.BuildFamilyTree;
import enums.Gender;

class BuildFamilyTreeTest {

	BuildFamilyTree buildFamilyTree;
	
	@BeforeEach
	void init() {
		buildFamilyTree = new BuildFamilyTree();
		buildFamilyTree.buildTree();
	}
	
	@Test
	void testCheckPersonInFamily() {
		
		assertEquals(true,buildFamilyTree.checkPersonInFamily("Arthur"));
		assertEquals(true,buildFamilyTree.checkPersonInFamily("Victoire"));
		assertEquals(false,buildFamilyTree.checkPersonInFamily("Pooja"));
		assertEquals(false,buildFamilyTree.checkPersonInFamily(" "));
		assertEquals(true,buildFamilyTree.checkPersonInFamily("Dominique"));
	}
	
	@Test
	void testAddChild() {
		//Negative test cases
		//Male father
		assertEquals(false,buildFamilyTree.addChild("Arthur","Chakku",Gender.Female));
		//No father
		assertEquals(false,buildFamilyTree.addChild("Dominique","Riya",Gender.Male));
		//mother Not exist
		assertEquals(false,buildFamilyTree.addChild("Riya","Margret",Gender.Male));
		assertEquals(false,buildFamilyTree.addChild("Pooja","Chakku",Gender.Female));
		// child name already exist 
		assertEquals(false,buildFamilyTree.addChild("Flora","Margret",Gender.Male));
		//Positive test cases
		assertEquals(true,buildFamilyTree.addChild("Margret","Chakku",Gender.Female));
		assertEquals(true,buildFamilyTree.addChild("Flora","Chakkz",Gender.Female));
		assertEquals(true,buildFamilyTree.addChild("Victoire","Chakkrae",Gender.Female));
		assertEquals(true,buildFamilyTree.addChild("Alice","Chakkusae",Gender.Female));

		
	}
	
	@Test
	void testMarry() {
		// partner already married 
		assertEquals(false,buildFamilyTree.marry("Margret","Chakku",Gender.Male));
		// both partners same gender
		assertEquals(false,buildFamilyTree.marry("Dominique","Chakkz",Gender.Female));
		//partner does not exist already
		assertEquals(false,buildFamilyTree.marry("Ishan","Chakkz",Gender.Female));
		assertEquals(true,buildFamilyTree.marry("Dominique","John",Gender.Male));
		assertEquals(true,buildFamilyTree.marry("Charlie","Chakkz",Gender.Female));
		assertEquals(true,buildFamilyTree.marry("Molly","Alexander",Gender.Male));
	}
	
	

}
