import java.io.*;
import java.util.*;

public class Relation {
  // Name of the relation.
  private String name;

  // Attribute names for the relation
  private ArrayList<String> attributes;

  // Domain classes or types of attributes; possible values: INTEGER, DECIMAL, VARCHAR
  private ArrayList<String> domains;

  // Actual data storage (list of tuples) for the relation.
  private ArrayList<Tuple> table;



  private String rToString = "";

  private ArrayList<Tuple> tempTable;

  private Relation sampleRelation;
  private Relation sampleRelation2;

  private Tuple tempTuple;
  private Tuple tempTuple2;

  // METHODS

  // Constructor; set instance variables
  public Relation (String name, ArrayList<String> attributes, ArrayList<String> dNames) {
        this.name = name;
        this.attributes = attributes;
        domains = dNames;
        table = new ArrayList<Tuple>();
  }

  // returns true if attribute with name aname exists in relation schema
  // false otherwise
  public boolean attributeExists(String aname) {
        for(int i = 0; i < attributes.size(); i++) {
            if(attributes.get(i) == aname) {
                return true;
            }
        }
        return false;
  }

  // returns domain type of attribute aname; return null if not present
  public String attributeType(String aname) {
        for(int i = 0;  i < attributes.size(); i++) {
            if(attributes.get(i).equals(aname)) {
                return domains.get(i);
            }
        }
        return null;
  }

  // Print relational schema to screen.
  public void displaySchema() {
      for(int i = 0;  i < attributes.size();i++) {
        System.out.println(attributes.get(i));
      }
  }

  // Set name of relation to rname
  public void setName(String rname) {
        name = rname;
  }

  // Add tuple tup to relation; Duplicates are fine.
  public void addTuple(Tuple tup) {
        table.add(tup);
  }

  // Print relation to screen (see output of run for formatting)
  // Use this for displaySchema();
  public void displayRelation() {
        for(int i = 0;  i < attributes.size() ;i++) {
            System.out.print(attributes.get(i) + ":" + domains.get(i));
            if(attributes.size() > i+1) {
                System.out.print(",");
            }
        }
  }

  // Remove duplicate tuples from this relation
  public void removeDuplicates() {
      tempTable = table;
      for(int i = 0; i < table.size(); i++){
          for(int j = i+1; j < table.size(); j++){
             if(tempTable.get(i).equals(table.get(j))){
                 table.remove(j);
                 j--;
             }
          }
      }
  }

  // Check Membership, return true if tuple t is present
  public boolean member(Tuple t) {
    for(int i = 0; i < this.table.size(); i++) {
      if((this.table.get(i)).equals(t)) {
        return true;
      }
    }
    return false;
  }

  // Union of two relations, merge them basically
  public Relation union(Relation rX) {
    Relation sampleRelation = new Relation("sRelation", this.attributes,
    this.domains);
    for(int i = 0; i < this.table.size(); i++) {
      tempTuple = this.table.get(i).clone(this.attributes);
      tempTuple2 = rX.table.get(i).clone(rX.attributes);
      sampleRelation.addTuple(tempTuple);
      sampleRelation.addTuple(tempTuple2);
    }

    sampleRelation.removeDuplicates();
    return sampleRelation;
  }

  // Returns a relation of the tuples that both relations have
  public Relation intersect(Relation rX) {
    Relation sampleRelation = new Relation("sRelation", this.attributes,
    this.domains);
    for(int i = 0; i < this.table.size(); i++) {
      if((rX.member(this.table.get(i)))) {
          tempTuple = this.table.get(i).clone(this.attributes);
          sampleRelation.addTuple(tempTuple);
      }
    }
    return sampleRelation;
  }

  // Returns the difference of two relations
 public Relation minus(Relation rX) {
    Relation sampleRelation = new Relation("sRelation", this.attributes,
    this.domains);
    for(int i = 0; i < this.table.size(); i++) {
      tempTuple = this.table.get(i).clone(this.attributes);
      if(!(rX.member(this.table.get(i)))) {
        sampleRelation.addTuple(tempTuple);
      }
    }
    return sampleRelation;
  }

  // Return String version of relation; See output of run for format.
  public String toString() {
        rToString = "";
        rToString += this.name + "(";
        for(int i = 0; i < attributes.size(); i++) {
            rToString += (attributes.get(i) + ":" + domains.get(i));
            if(i+1 < attributes.size()) {
                rToString += ",";
            }
        }
        rToString += ")";
        rToString += "\n";
        rToString += "Number of tuples: " + table.size();
        rToString += "\n";
        rToString += table.toString();
        rToString += "\n";
        rToString = rToString.replace("[", "").replace("]", "").replace(",", "");
        return rToString;
  }

}