package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enums.Gender;
/**
 * 
 * @author Pooja
 * This class contains information about a person, and his basic relationships in a family which are parents, a partner and children.
 */
public class Person {
	private String name;
	private Gender gender;
	private Set<Person> parents; //Assuming there are unique names for everyone in family
	private Person spouse;
	private ArrayList<Person> children; //ArrayList- To keep track of order of children
	
	//Constructors
	public Person() {
		parents = new HashSet<>();
		children = new ArrayList<>();
	}
	
	public Person(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
		parents = new HashSet<>();
		children = new ArrayList<>();
	}
	
	public Person(String name,Gender gender,Person parent,Person spouse, Person child) {
		this.name = name;
		this.gender = gender;
		if (parents == null) parents = new HashSet<>();
		this.parents.add(parent);
		this.spouse = spouse;
		if(children == null) children = new ArrayList<>();
		this.children.add(child);
	}
	
	//Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Person> getParents() {
        return parents;
    }

    public void setParents(Set<Person> parents) {
        this.parents = parents;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Person> children) {
        this.children = children;
    }
    
    public boolean hasSpouse() {
    	if(getSpouse() != null) return true;
    	
    	return false;
    }
	
    public boolean hasParents() {
    	if(getParents() != null) return true;
    	
    	return false;
    }
}
