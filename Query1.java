import java.util.*;

public class Query1 {
  public static void main(String args[]) {
    Database db = new Database();
    db.initializeDatabase(args[0]);
    Relation r1 = db.getRelation("EMPLOYEE");
    Relation t1 = r1.select("col", "DNO", "=", "num", "5" );
    ArrayList<String> attr = new ArrayList<String>();
    attr.add("FNAME");
    attr.add("LNAME");
    attr.add("ADDRESS");
    Relation result = t1.project(attr);
    result.setName("ANSWER");
    System.out.println(result);
  }
}
