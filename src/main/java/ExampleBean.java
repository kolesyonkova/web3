
import lombok.Data;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ApplicationScoped
@Data
public class ExampleBean {

    private Entry newEntry = new Entry();

    private List<Entry> entries = new ArrayList<>();


    public void addEntry() {
        entries.add(newEntry);
        newEntry = new Entry();
    }
}