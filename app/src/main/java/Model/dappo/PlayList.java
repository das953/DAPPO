package Model.dappo;

import java.util.ArrayList;

/**
 * Created by das953 on 18-Dec-17.
 */

public class PlayList<Soundtrack> extends ArrayList<Soundtrack> {

    private String listName;

    public String getListName() {
        return listName;
    }

    public PlayList(String listName) {
        this.listName = listName;
    }
}
