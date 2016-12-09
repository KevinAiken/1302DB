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
  private ArrayList<String> tempAttr;
  private ArrayList<String> tempDom;

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

  // Returns a new relation with same tuples but different column names
  public Relation rename(ArrayList<String> cnames) {
    Relation rel = new Relation(this.name, cnames, this.domains);
    for(int i = 0; i < this.table.size(); i++) {
            rel.addTuple(this.table.get(i).clone(cnames));
        }
    return rel;
  }

  // Cartesion product of two relations.
  public Relation times(Relation r2) {
      ArrayList<String> newAttr = new ArrayList<String>();
      ArrayList<String> newDom = new ArrayList<String>();
      for(int i = 0; i < this.domains.size(); i++) {
        newDom.add(this.domains.get(i));
      }
      for(int i = 0; i < r2.domains.size(); i++) {
        newDom.add(r2.domains.get(i));
      }
      for(int i = 0; i < this.attributes.size(); i++) {
        newAttr.add(this.name + "." + this.attributes.get(i));
      }
      for(int i = 0; i < r2.attributes.size(); i++) {
        newAttr.add(r2.name + "." + r2.attributes.get(i));
      }
      Relation newRelation = new Relation(this.name, newAttr, newDom);
      for(int i = 0; i < this.table.size(); i++) {
        for(int j = 0; j < r2.table.size(); j++) {
          newRelation.addTuple(this.table.get(i).concatenate(r2.table.get(j), newAttr, newDom));
        }
      }
      return newRelation;
  }

  public Relation project(ArrayList<String> cnames){
    ArrayList<String> newDom = new ArrayList<>();
    ArrayList<Tuple> tempAL = new ArrayList<>();

    for(int i = 0; i < this.table.size(); i++){
      tempAL.add(this.table.get(i).project(cnames));
    }
    for (int i = 0; i < cnames.size(); i++) {
        for (int j = 0; j < this.attributes.size(); j++) {
          if (cnames.get(i).equals(this.attributes.get(j))) {
              newDom.add(this.domains.get(j));
          }
        }
    }
    
    Relation rel = new Relation(this.name, cnames, newDom);
    rel.table = tempAL;
    rel.removeDuplicates();

    return rel;
  }

  public Relation select(String lopType, String lopValue, String comparison, String ropType, String ropValue){
    Relation newRelation = new Relation("sample", this.attributes, this.domains);

    for(int i = 0; i < this.table.size(); i++){
      if(this.table.get(i).select(lopType, lopValue, comparison, ropType, ropValue)){
        newRelation.table.add(this.table.get(i));
      }
    }
    return newRelation;
  }

  public Relation join(Relation r2) {
    ArrayList<String> newAttr = new ArrayList<String>(this.attributes);
    ArrayList<String> newDom = new ArrayList<String>(this.domains);
    for(int i = 0; i < this.domains.size(); i++) {
      newDom.add(this.domains.get(i));
    }
    for(int i = 0; i < r2.domains.size(); i++) {
      newDom.add(r2.domains.get(i));
    }
    for(int i = 0; i < this.attributes.size(); i++) {
      newAttr.add(this.attributes.get(i));
    }
    for(int i = 0; i < r2.attributes.size(); i++) {
      newAttr.add(r2.attributes.get(i));
    }
    tempAttr = newAttr;
    for(int i = 0; i < newAttr.size(); i++){
        for(int j = i+1; j < newAttr.size(); j++){
           if(tempAttr.get(i).equals(newAttr.get(j))){
               newAttr.remove(j);
               newDom.remove(j);
               j--;
           }
        }
    }

    Relation rel = new Relation("newRel", newAttr, newDom);

    for(int i = 0; i < this.table.size(); i++){
      for(int j = 0; j < r2.table.size(); j++){

        if(((this.table.get(i)).clone(this.attributes).join(r2.table.get(j).clone(r2.attributes), newAttr, newDom)) != null){

          rel.table.add(this.table.get(i).clone(this.attributes).join(r2.table.get(j).clone(r2.attributes), newAttr, newDom));
      }
    }
    }

    return rel;
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
        for(int i = 0; i < table.size(); i++) {
          rToString += "\n" + table.get(i);
        }
        rToString += "\n";
        rToString = rToString.replace("[", "").replace("]", "").replace(",", ",");
        return rToString;
  }

}
