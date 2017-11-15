package test.taylor.com.taylorcode.ui.pagers;


/**
 * tab indicator dimension info
 */
public class IndicatorInfo {
    /**
     * the x coordinate of tab's left edge
     */
    private float left;
    /**
     * the with of tab
     */
    private float width;

    public IndicatorInfo(float left, float width) {
        this.left = left;
        this.width = width;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return get the x coordinate or tab's right edge
     */
    public float getRight() {
        return left + width;
    }
}
