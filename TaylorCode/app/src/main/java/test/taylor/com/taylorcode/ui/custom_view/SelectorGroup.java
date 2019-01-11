package test.taylor.com.taylorcode.ui.custom_view;

import java.util.HashSet;
import java.util.Set;

/**
 * a group contains several Selectors
 */
public class SelectorGroup {

    private Set<Selector> selectors = new HashSet<>();

    public void addSelector(Selector selector) {
        selectors.add(selector);
    }

    /**
     * set one Selector selected by tag
     * @param tag
     */
    public void setSelected(String tag) {
        for (Selector s : selectors) {
            if (s.getTag().equals(tag)) {
                s.switchSelector();
            }
        }
    }

    /**
     * ensure just one Selector in this group is selected at one time
     *
     * @param selector the Selector which is selected right now
     */
    public void setSelected(Selector selector) {
        cancelPreSelector(selector);
    }

    /**
     * cancel selected state of one Selector when another is selected
     *
     * @param selector the Selector which is selected right now
     */
    private void cancelPreSelector(Selector selector) {
        for (Selector s : selectors) {
            if (!s.equals(selector) && s.isSelected()) {
                s.switchSelector();
            }
        }
    }

    /**
     * get the selected one
     *
     * @return
     */
    public Selector getSelected() {
        for (Selector s : selectors) {
            if (s.isSelected()) {
                return s;
            }
        }
        return null;
    }

    public void clear() {
        if (selectors != null) {
            selectors.clear();
        }
    }
}
