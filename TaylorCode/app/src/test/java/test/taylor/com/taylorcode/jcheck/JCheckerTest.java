package test.taylor.com.taylorcode.jcheck;

import org.jcheck.annotations.Configuration;
import org.jcheck.annotations.Generator;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created on 17/4/18.
 */
@RunWith(org.jcheck.runners.JCheckRunner.class)
public class JCheckerTest {
    @Test
    public void addTest(int i , int j){
        System.out.println("addTest"+",i="+i+",j="+j);
        Adder adder = new Adder() ;
        int result = adder.add(i , j) ;
        assertThat(result, equalTo(i+j));
    }

    @Test
    @Generator(klass = Type.class,generator = TypeGen.class)
    @Configuration(tests = 10) //times of test will be done
    public void addTestByType1(Type i , Type j){
        System.out.println("JCheckerTest.addTestByType1() "+ "type iValue="+i.getValue()+",type jValue="+j.getValue()) ;
        System.out.println("JCheckerTest.addTestByType1() "+" type stringValue="+i.getString());
        System.out.println("JCheckerTest.addTestByType1() "+"iType.isOnline="+i.getIType().isOnline());
        Adder adder = new Adder() ;
        int result = adder.addByType1(i,j);
        int iValue = i.getValue() ;
        int jValue = j.getValue() ;
        assertThat(result,equalTo(iValue+jValue));
    }


    private class Adder {
        private int i ;
        private int j ;
        public Adder (){

        }

        public int add(int i,int j){
            return i+j;
        }

        public int addByType1(Type i , Type j){
            return i.getValue()+j.getValue() ;
        }
    }
}
