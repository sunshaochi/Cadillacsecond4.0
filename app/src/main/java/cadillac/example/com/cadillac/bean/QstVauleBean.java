package cadillac.example.com.cadillac.bean;

import java.util.List;

/**
 * Created by iris on 2018/1/27.
 */

public class QstVauleBean {


    /**
     * values : [-43434,-62652,-74251,-45325,-57361,-61054,-73609,-58646,-80522,-69060,-77467,-80566,10654]
     * name : ATS-L
     */

    private String name;
    private List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
