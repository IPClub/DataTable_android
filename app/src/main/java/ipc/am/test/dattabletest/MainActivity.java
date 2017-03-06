package ipc.am.test.dattabletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import am.ipc.datatable.DataTable;
import am.ipc.datatable.data.table.view.DataTableType;

public class MainActivity extends AppCompatActivity {

    public static final int table1Id= 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<User> users = getUsers();

        String[] headers = {"Name","Surname","Age","Email","Address"};
        List<String[]> data = new ArrayList<>();
        for(User u:users){
            data.add(new String[]{u.getFirstName(),u.getLastName(),u.getAge()+"",u.getEmail(),u.getAddress()});
        }
        LinearLayout main = (LinearLayout) findViewById(R.id.dataTable);

        DataTable.init(this, table1Id, DataTableType.SINGLE_CHOICE)
                .headers(headers)
                .entries(data)
                .rowHeight(100)
                .rowColor(R.color.row_color)
                .rowSelectedColor(R.color.selected_row_color)
                .enableSearch(true)
                .enableSort(true)
                .headerColor(R.color.header_color)
                .borderColor(R.color.border_color)
                .showIn(main);
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Bob","Amber",9,"bob99@mail.com","NY City"));
        users.add(new User("Ann","Smith",23,"ann.sm@gmail.com","Boston"));
        users.add(new User("Rey","Johns",20,"rey.basket@yahoo.com","Texas"));
        users.add(new User("Nick","Grey",44,"nikola@gmail.com","California"));
        users.add(new User("Chris","Noris",22,"noris33@gmail.com","Washington"));
        users.add(new User("Herta","Meyton",32,"her_mey@yahoo.com","Arizona"));
        users.add(new User("Jeck","Gordon",12,"ggg_jeck@mail.com","Florida"));

        users.add(new User("Samantha","Noon",23,"sami_mami@yahoo.com","Washington"));
        users.add(new User("Linda","Vento",34,"linven@gmail.com","Paris"));
        users.add(new User("Nikolay","Pushkin",25,"nikol@yandex.ru","Moscow"));
        users.add(new User("Hayk","Avdalyan",23,"info@ipc.am","Yerevan"));
        users.add(new User("Vera","Bikova",22,"vera.bikova@mail.ru","Moscow"));
        users.add(new User("Anahit","Gevorgyan",20,"anahit.gevorg@yandex.ru","Yerevan"));
        return users;
    }

    public void index(View view) {
        Log.e("__datatable",""+DataTable.getSingleChosenIndex(table1Id));
    }
}
