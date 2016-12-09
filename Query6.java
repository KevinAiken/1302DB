import java.util.*;

public class Query6 {
  public static void main(String args[]) {
    Database db = new Database();
    db.initializeDatabase(args[0]);
    Relation r1 = db.getRelation("EMPLOYEE");
    Relation r2 = db.getRelation("DEPENDENT");
    ArrayList<String> cnames1 = new ArrayList<String>();
    cnames1.add("SSN");
    Relation t1 = r1.project(cnames1);
    ArrayList<String> cnames2 = new ArrayList<String>();
    cnames2.add("ESSN");
    Relation t2 = r2.project(cnames2);
    Relation t3 = t1.minus(t2);
    Relation t4 = t3.join(r1);
    ArrayList<String> cnames3 = new ArrayList<String>();
    cnames3.add("FNAME");
    cnames3.add("LNAME");
    Relation t5 = t4.project(cnames3);
    t5.setName("ANSWER");
    System.out.println(t5);
  }
}
