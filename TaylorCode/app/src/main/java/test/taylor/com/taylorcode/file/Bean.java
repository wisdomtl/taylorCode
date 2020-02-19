package test.taylor.com.taylorcode.file;

import java.io.Serializable;

public class Bean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    private int count;

    private String msgType;

    public Bean(String name, int count, String msgType) {
        this.name = name;
        this.count = count;
        this.msgType = msgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", msgType='" + msgType + '\'' +
                '}';
    }
}
