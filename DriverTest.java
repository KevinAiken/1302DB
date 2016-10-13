import java.util.*;

public class DriverTest {
  public static void main(String args[]) {
    Database db = new Database();
    ArrayList<String> attr1 = new ArrayList<String>();
    attr1.add("COL1");
    attr1.add("COL2");
    ArrayList<String> dom1 = new ArrayList<String>();
    dom1.add("INTEGER");
    dom1.add("VARCHAR");
    Relation r1 = new Relation("REL1",attr1,dom1);
    Tuple t1 = new Tuple(attr1,dom1);
    t1.addIntegerComponent(1111);
    t1.addStringComponent("Robert Adams");
    Tuple t2 = new Tuple(attr1,dom1);
    t2.addIntegerComponent(1112);
    t2.addStringComponent("Charles Bailey");


    System.out.println(t1.concatenate(t2));
  }
}
