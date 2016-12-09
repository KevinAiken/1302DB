import java.util.*;

public class Query2 {
  public static void main(String args[]) {
    Database db = new Database();
    db.initializeDatabase(args[0]);
    Relation r1 = db.getRelation("EMPLOYEE");
    Relation r2 = db.getRelation("DEPARTMENT");
    Relation r3 = db.getRelation("PROJECTS");

    Relation t1 = r3.select("col", "PLOCATION", "=", "str", "Stafford");
    ArrayList<String> cnames1 = new ArrayList<String>();
    cnames1.add("DNAME");
    cnames1.add("DNUM");
    cnames1.add("SSN");
    cnames1.add("MGRSTARTDATE");

    Relation t2 = r2.rename(cnames1);
    Relation t3 = t1.join(t2);

    Relation t4 = t3.join(r1);
    ArrayList<String> cnames2 = new ArrayList<String>();
    cnames2.add("PNUMBER");
    cnames2.add("DNUM");
    cnames2.add("LNAME");
    cnames2.add("BDATE");
    cnames2.add("ADDRESS");

    Relation t5 = t4.project(cnames2);
    t5.setName("ANSWER");
    System.out.println(t5);

    //select projects at stafford
    //need the department manager SSNs
    //select
    //print pnumber, dnum, lname, address, bdate
  }
}
