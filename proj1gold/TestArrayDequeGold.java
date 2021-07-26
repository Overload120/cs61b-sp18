import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque(){
        StudentArrayDeque<Integer> stu=new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution=new ArrayDequeSolution<>();
        Integer currentnum;
        Integer actual;
        Integer expected;
        String a="";
        while (true){
            currentnum=StdRandom.uniform(15);
            switch (currentnum%4){
                case 0:
                    stu.addFirst(currentnum);
                    solution.addFirst(currentnum);
                    a+="addFirst(" + currentnum + ")\n";
                    break;
                case 1:
                    stu.addLast(currentnum);
                    solution.addLast(currentnum);
                    a+="addLast(" + currentnum + ")\n";
                    break;
                case 2:
                    if(solution.size()!=0 && stu.size()!=0){
                        actual=stu.removeFirst();
                        expected=solution.removeFirst();
                        a+="removeFirst()\n";
                        assertEquals(a,expected,actual);
                    }
                case 3:
                    if(solution.size()!=0 && stu.size()!=0){
                        actual=stu.removeLast();
                        expected=solution.removeLast();
                        a+="removeLast()\n";
                        assertEquals(a,expected,actual);
                    }
            }
        }

    }
}
