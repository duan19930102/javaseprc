package current;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTest {

    public static void main(String[] args) {
        List<String> numList = new ArrayList<>();
        for(int i=0;i<10;i++) {
            String num= ""+i;
            numList.add(num);
        }

        while (numList.size()>=0) {
            Random random  = new Random();
            int a = random.nextInt(10);
            int b = numList.indexOf(""+a);
            if(b!=-1){
                System.out.println(numList.get(b));
                numList.remove(b);
            }
        }

    }



}
