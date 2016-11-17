import java.util.*;

public class Query1 {
  public static void main(String args[]) {
  Database db = new Database();
  db.initializeDatabase(args[0]);
  db.displaySchema();
  Relation r1 = db.getRelation("EMPLOYEE");
  Relation r2 = db.getRelation("DEPARTMENT");
  ArrayList<String> cnames = new ArrayList<String>();
  cnames.add("DNUMBER");
  Relation r3 = r1.project(cnames);
  System.out.println(r3);
  System.out.println(r1);
  System.out.println(r2);
  }
}
