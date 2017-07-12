package test.taylor.com.taylorcode.jcheck;

import org.jcheck.generator.primitive.BooleanGen;

import java.util.Random;

/**
 * define how to generate arbitrary interface
 * Created on 17/4/18.
 */

public class ITypeImpl implements IType {
    @Override
    public boolean isOnline() {
        BooleanGen booleanGen = new BooleanGen() ;
        return booleanGen.arbitrary(new Random(),0);
    }

    @Override
    public String[] getMembers() {

        return new String[0];
    }
}
