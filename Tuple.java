import java.util.*;

public class Tuple {

  private ArrayList<String> attributes;
  private ArrayList<String> domains;
  private ArrayList<Comparable> tuple;

  private String tToString = "";

  // METHODS

  // Constructor; set instance variables
  public Tuple (ArrayList<String> attr, ArrayList<String> dom) {
    attributes = attr;
    domains = dom;
    tuple = new ArrayList<Comparable>();
  }

  // Add String s at the end of the tuple
  public void addStringComponent(String s) {
    tuple.add(s);
  }

  // Add Double d at the end of the tuple
  public void addDoubleComponent(Double d) {
    tuple.add(d);
  }

  // Add Integer i at the end of the tuple
  public void addIntegerComponent(Integer i) {
    tuple.add(i);
  }

  // Return true if this tuple is equal to compareTuple; false otherwise
  public boolean equals(Tuple compareTuple) {
      if((compareTuple.toString()).equals(this.toString())){
          return true;
      } else {
          return false;
      }
  }

  public Tuple clone(ArrayList<String> attr) {
    Tuple cTuple = new Tuple(attr, this.domains);
    for(int i = 0; i < this.tuple.size(); i++) {
      switch (cTuple.domains.get(i)) {
          case "VARCHAR": cTuple.addStringComponent((String)this.tuple.get(i));
          break;
          case "INTEGER": cTuple.addIntegerComponent((int)this.tuple.get(i));
          break;
          case "DECIMAL": cTuple.addDoubleComponent((double)this.tuple.get(i));
          break;
      }
    }
    return cTuple;
  }

  public Tuple concatenate(Tuple t2, ArrayList<String> attrs, ArrayList<String>
  doms) {
    Tuple cTuple = new Tuple(attrs, doms);
    for(int i=0; i < this.tuple.size(); i++) {
      switch (cTuple.domains.get(i)) {
          case "VARCHAR": cTuple.addStringComponent((String)this.tuple.get(i));
          break;
          case "INTEGER": cTuple.addIntegerComponent((int)this.tuple.get(i));
          break;
          case "DECIMAL": cTuple.addDoubleComponent((double)this.tuple.get(i));
          break;
      }
    }
    for(int i=0; i < t2.tuple.size(); i++) {
      switch (cTuple.domains.get(i)) {
          case "VARCHAR": cTuple.addStringComponent((String)t2.tuple.get(i));
          break;
          case "INTEGER": cTuple.addIntegerComponent((int)t2.tuple.get(i));
          break;
          case "DECIMAL": cTuple.addDoubleComponent((double)t2.tuple.get(i));
          break;
      }
    }
    return cTuple;
  }

  public Tuple project(ArrayList<String> cnames) {
    Tuple newTuple = new Tuple(this.attributes, this.domains);
    for(int i = 0; i < this.tuple.size(); i++) {
      for(int j = 0; j < cnames.size(); j++){
        if(this.attributes.get(i) == cnames.get(j)){
          switch (newTuple.domains.get(i)) {
              case "VARCHAR": newTuple.addStringComponent((String)this.tuple.get(i));
              break;
              case "INTEGER": newTuple.addIntegerComponent((int)this.tuple.get(i));
              break;
              case "DECIMAL": newTuple.addDoubleComponent((double)this.tuple.get(i));
              break;
          }
        }
      }
    }
    return newTuple;
  }

  public boolean select(String lopType, String lopValue, String comparison, String ropType, String ropValue) {
      if(lopType.equals("col") && ropType.equals("num")) {
          for(int i = 0; i < attributes.size(); i++){
            if(domains.get(i).equals("INTEGER")){

              if(comparison.equals("=")){
                return (Integer.parseInt(ropValue) == (int)this.tuple.get(i));
              } else if (comparison.equals(">")){
                return (Integer.parseInt(ropValue) > (int)this.tuple.get(i));
              } else if (comparison.equals("<")){
                return (Integer.parseInt(ropValue) < (int)this.tuple.get(i));
              } else if (comparison.equals(">=")){
                return (Integer.parseInt(ropValue) >= (int)this.tuple.get(i));
              } else if (comparison.equals("<=")){
                return (Integer.parseInt(ropValue) <= (int)this.tuple.get(i));
              } else {
                return false;
              }
            } else if (domains.get(i).equals("DECIMAL")) {
              if(comparison.equals("=")){
                return (Integer.parseInt(ropValue) == (Double)this.tuple.get(i));
              } else if (comparison.equals(">")){
                return (Integer.parseInt(ropValue) > (Double)this.tuple.get(i));
              } else if (comparison.equals("<")){
                return (Integer.parseInt(ropValue) < (Double)this.tuple.get(i));
              } else if (comparison.equals(">=")){
                return (Integer.parseInt(ropValue) >= (Double)this.tuple.get(i));
              } else if (comparison.equals("<=")){
                return (Integer.parseInt(ropValue) <= (Double)this.tuple.get(i));
              } else {
                return false;
              }
            }
            }
      } else if (lopType.equals("col") && ropType.equals("str")) {
          for(int i = 0; i < attributes.size(); i++){
              if(attributes.get(i).equals(lopValue)){
                  if(ropValue.equals(this.tuple.get(i))){
                    return true;
                  }
                }
              }
            }

        return false;

  }

  // return String representation of tuple; See output of run for format.
  public String toString() {
       tToString = "";
        for(int i = 0; i< tuple.size() ;i++) {
                tToString += tuple.get(i) + ":";
        }

        //System.out.println("This is the tuple: " + tToString);
        return tToString;
  }

}
