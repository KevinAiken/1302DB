import java.util.*;

public class Query1 {
  public static void main(String args[]) {
  Database db = new Database();
  db.initializeDatabase(args[0]);

  Relation r1 = db.getRelation("EMPLOYEE");
  //Relation r2 = db.getRelation("DEPARTMENT");
  Relation t1 = r1.select("col", "DNO", "=", "num", "6" );
  //System.out.println(t1);
  //Relation t2 = r1.join(t1);
  ArrayList<String> attr = new ArrayList<String>();
  attr.add("FNAME");
//  attr.add("LNAME");
//  attr.add("ADDRESS");

  Relation result = t1.project(attr);
  result.setName("ANSWER");
  System.out.println(result);
  System.out.println(t1);

  }
}
