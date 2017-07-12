package test.taylor.com.taylorcode.jcheck;

import org.jcheck.generator.Gen;
import org.jcheck.generator.primitive.IntegerGen;
import org.jcheck.generator.primitive.StringGen;

import java.util.Random;

/**
 * define how to generate arbitrary customized type
 * Created on 17/4/18.
 */
public class TypeGen implements Gen<Type>{

    @Override
    public Type arbitrary(Random random, long l) {
        Type type = new Type() ;
        IntegerGen integerGen = new IntegerGen() ;
        type.setValue(integerGen.arbitrary(new Random(),100));
        StringGen stringGen = new StringGen() ;
        type.setString(stringGen.arbitrary(new Random(),30));
        IType iType = new ITypeImpl() ;
        type.setIType(iType);
        return type;
    }
}
