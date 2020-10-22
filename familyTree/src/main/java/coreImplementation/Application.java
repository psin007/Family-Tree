package coreImplementation;

import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import enums.Gender;
import enums.Message;
import enums.RelationName;
import model.Person;

/**
 * 
 * @author Pooja
 * Application Launcher for application to start 
 * It build existing family tree and reads from a text file for queries
 */
public class Application {
	
	/**
	 * Program starts here
	 * @param args file location
	 */
	public static void main(String args[]) {
		
		HashMap<String,Person> mapNamePerson = new HashMap<>();
		BuildFamilyTree buildFamilyTree = new BuildFamilyTree(mapNamePerson); 
		//populate with existing tree
		buildFamilyTree.buildTree(); 
		QueryParser queryParser = new QueryParser(mapNamePerson);
		if(args.length == 1) {
			Application app = new Application();
			String fileLocation = args[0];
			if(app.ifTextFile(fileLocation)) {
				app.readFromTextFile(fileLocation,queryParser);
			}
			else System.out.println("Argument does not point to a txt file");
			
		}
		else System.out.println(" Family tree is built. Enter argument as location of text file to re-build and query");
	}
	
	/**
	 * checks if argument is a .txt file
	 * @param fileLocation
	 * @return
	 */
	public boolean ifTextFile(String fileLocation) {
		String[] file = fileLocation.split("\\.");
		//after . comes the extension of file
		if(file.length == 2 && file[1].equals("txt"))	return true;
		return false;
	}

	/**
	 * reads line by line from text file
	 * @param fileLocation
	 * @param queryParser
	 */
	private void readFromTextFile(String fileLocation, QueryParser queryParser) {
		try{
			  FileInputStream fstream = new FileInputStream(fileLocation);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  while ((strLine = br.readLine()) != null)   {
				  if(strLine.trim().length() > 0) {
					  String[] sentence = strLine.split(" ");
					  if(sentence.length == 4) createAddChildQuery(sentence,queryParser);  
					  else if (sentence.length == 3) createGetRelationshipQuery(sentence,queryParser);
					  else  {
						  System.out.println("Program stopped due to invalid text in file");
						  break;
					  }
				  }  
			  }
			  in.close();
		}
		catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
		}
		
	}

	/**
	 * checks if text in text file can be used to query relationship of a person
	 * @param sentence
	 * @param queryParser
	 */
	void createGetRelationshipQuery(String[] sentence, QueryParser queryParser) {
		
		//check if sentence[0] == Message
		//check if sentence[1] == String
		//check if sentence[2] == RelationName
		Message command = null;
		RelationName relation = null;
		if(checkStringInMessage(sentence[0])) command = Message.valueOf(sentence[0]);
		if(checkStringInRelation(sentence[2])) relation = RelationName.valueOf(sentence[2]);
		String personName = sentence[1];
		if(command != null && relation != null)
			queryParser.passQueryToRetrieve(command, personName, relation);
		else
			System.out.println("Invalid Input");
		
	}

	/**
	 * checks if command in text file can be used to query to add child
	 * @param sentence
	 * @param queryParser
	 */
	void createAddChildQuery(String[] sentence, QueryParser queryParser) {
		
		//check if sentence[0] == Message
		//check if sentence[1] == String
		//check if sentence[2] == String
		//check if sentence[3] == Gender
		Message addCommand = null;
		Gender genderChild = null;
		if(checkStringInMessage(sentence[0])) addCommand = Message.valueOf(sentence[0]);
		if(checkStringInGender(sentence[3])) genderChild = Gender.valueOf(sentence[3]);
		if(addCommand != null && genderChild != null) {
			String motherName = sentence[1];
			String childName = sentence[2];
			queryParser.passQueryToAdd(addCommand,motherName,childName,genderChild);
		}
		else {
			System.out.println("Invalid Input");
			

		}
		
	}
	
	/**
	 * check if string is included in Message Enum or not
	 * @param word string to check
	 * @return
	 */
	public boolean checkStringInMessage(String word) {
		    for (Message msg : Message.values()) {
		        if (msg.name().equals(word.trim())) {
		            return true;
		        }
		    }
		    return false;
	}

	/**
	 * check if string is included in Gender Enum or not
	 * @param word string to check
	 * @return
	 */
	public boolean checkStringInGender(String word) {
	    for (Gender gender : Gender.values()) {
	        if (gender.name().equals(word)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * check if string is included in RelationName Enum or not
	 * @param word string to check
	 * @return
	 */
	boolean checkStringInRelation(String word) {
	    for (RelationName relation : RelationName.values()) {
	        if (relation.name().equals(word)) {
	            return true;
	        }
	    }
	    return false;
	}

}
