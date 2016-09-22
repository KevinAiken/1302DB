import java.io.*;
import java.util.*;

public class Database {

    private Map<String,Relation> relations;

    private int i;
    private int j;
    private int k;

    private FileInputStream fin1 = null;
    private BufferedReader infile1 = null;

    private FileInputStream fin2 = null;
    private BufferedReader infile2 = null;

    private int numberOfRelations;
    private String nameOfRelation;
    private int numberOfColumns;
    private int numberOfTuples;

    // METHODS

    // Constructor; creates the empty HashMap object
    public Database() {
        relations = new HashMap<String, Relation>();
    }

    // Create the database object by reading from directory
    public void initializeDatabase(String dir) {
        
        try {
            fin1 = new FileInputStream(dir);
            infile1 = new BufferedReader(new InputStreamReader(fin1));
            numberOfRelations = Integer.parseInt(infile1.readLine());
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        // crafting the relation full of tuples
        for(i = 0; i <  numberOfRelations; i++) {
            ArrayList<String> attr1 = new ArrayList<String>(); 
            ArrayList<String> dom1 = new ArrayList<String>();
            
            // Getting information about the relation from the files
            try {
                nameOfRelation = infile1.readLine();
                numberOfColumns = Integer.parseInt(infile1.readLine());
            } catch (IOException e) {
                System.out.println("Error reading file");
            }
            
            // Get attributes and domains to declare the relation
            for(j = 0; j < numberOfColumns; j++) {
                try {
                    attr1.add(infile1.readLine());
                    dom1.add(infile1.readLine());	
                } catch (IOException e) {
                    System.out.println("Error reading file");
                }
            }

            // relation is declared
            Relation rTemp = new Relation(nameOfRelation, attr1, dom1);

            // Getting number of Tuples inside other reading file
            try {
                //Relation rTemp = new Relation(nameOfRelation, attr1, dom1);
                fin2 = new FileInputStream(nameOfRelation + ".dat");
                infile2 = new BufferedReader(new InputStreamReader(fin2));
                numberOfTuples = Integer.parseInt(infile2.readLine());
            } catch (IOException e) {
                System.out.println("Error reading inner file");
            }

            // crafting the tuple 
            for(j = 0; j < numberOfTuples; j++) {
                Tuple tTemp = new Tuple(attr1,dom1);
                try {
                    for(k = 0; k < dom1.size(); k++){
                        switch (dom1.get(k)) {
                            case "VARCHAR": tTemp.addStringComponent((infile2.readLine()));
                            break;
                            case "INTEGER": tTemp.addIntegerComponent(Integer.parseInt((infile2.readLine())));
                            break;
                            case "DECIMAL": tTemp.addDoubleComponent(Double.parseDouble((infile2.readLine())));
                            break;
                        } 
                    }
                } catch (IOException c) {
                    System.out.println("Error reading file");
                }


                // the finished tuple is added to relation
                rTemp.addTuple(tTemp);
            }
            
            // the finished relation is added to the relation
            this.addRelation(nameOfRelation, rTemp);		  
        }
        
        // files are closed
        try {
            fin1.close();
            fin2.close();
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }

    // Add relation r with name rname to HashMap
    // if relation does not already exists.
    // Make sure to set the name within r to rname.
    // return true on successful add; false otherwise
    public boolean addRelation(String rname, Relation r) {
        if(relations.containsValue(r)) {
        return false;
        } else {
        relations.put(rname, r);
        return true;
        }
    }

    // Delete relation with name rname from HashMap
    // if relation exists. return true on successful delete; false otherwise
    public boolean deleteRelation(String rname) {
        if(!relations.containsKey(rname)) {
        return false;
        } else {
        relations.remove(rname);
        return true;
        }       
    }

    // Return true if relation with name rname exists in HashMap
    // false otherwise
    public boolean relationExists(String rname) {
        if(relations.containsKey(rname)) {
        return true;
        } else {
        return false;
        }
    }

    // Retrieve and return relation with name rname from HashMap;
    // return null if it does not exist.
    public Relation getRelation (String rname) {
        if(!relations.containsKey(rname)){
        return null;
        } else {
        return relations.get(rname);
        }
    }

    // Print database schema to screen.
    public void displaySchema() {
        for (Map.Entry<String, Relation> entry : relations.entrySet()) {
            System.out.print(entry.getKey() + "(");
            entry.getValue().displayRelation();           
            System.out.print(")");
            System.out.println();
        }
        System.out.println();
    }
}