package test.taylor.com.taylorcode.jcheck;

/**
 * represents customized type(non-basic java type)
 * Created on 17/4/18.
 */
public class Type {

    private int integer;
    private String string ;
    private IType iType ;

    public void setValue(int value) {
        this.integer = value;
    }

    public int getValue() {
        return integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public IType getIType() {
        return iType;
    }

    public void setIType(IType iType) {
        this.iType = iType;
    }
}
