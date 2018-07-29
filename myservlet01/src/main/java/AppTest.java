import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-08 下午6:09
 **/
public class AppTest {

    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();
        set.add(5);
        set.add(10);
        set.add(0);

        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
