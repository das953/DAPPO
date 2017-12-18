package Model.dappo;

import java.util.ArrayList;

/**
 * Created by das953 on 18-Dec-17.
 */

public class PlayList extends ArrayList<String> {
    private String[] fileNames;

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }
}
