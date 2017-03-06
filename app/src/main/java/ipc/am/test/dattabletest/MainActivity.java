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

//        DataTable.init(this, 300, DataTableType.SINGLE_CHOICE)
//                .headers(headers)
//                .entries(data)
//                .rowHeight(100)
//                .rowColor(R.color.row_color)
//                .rowSelectedColor(R.color.selected_row_color)
//                .enableSearch(true)
//                .enableSort(true)
//                .headerColor(R.color.header_color)
//                .borderColor(R.color.border_color)
//                .showIn(main);

        DataTable.init(this, 300)
                .headers(headers)
                .entries(data)
                //.rowHeight(100)
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
        users.add(new User("Hayk","Avdalyan",9,"aaaaa@mail.ru","avan"));
        users.add(new User("Anna","Baxyan",124,"ssss@gmail.com","talin"));
        users.add(new User("Vzgo","Vanyan",25,"dddd@dd.ru","sd"));
        users.add(new User("Mko","Gyan",22,"ffff@a.ru","aviuan"));
        users.add(new User("Hayk","Pyan",22,"gggg@mail.ru","rrr"));
        users.add(new User("Ani","Tyan",22,"shhhhh@mail.ru","tt"));
        users.add(new User("Valod","valodyan",22,"jjjj@mail.ru","uiu"));

        users.add(new User("Anan","Hamazaryab",32,"www@mail.ru","avan"));
        users.add(new User("Mukuch","Vardanyan",34,"ee@gmail.com","talin"));
        users.add(new User("Nairi","Tankyan",35,"rt@dd.ru","sd"));
        users.add(new User("Hamazasp","Sargsyan",32,"yy@a.ru","aviuan"));
        users.add(new User("Gor","Tatevosyan",42,"ffgh@mail.ru","rrr"));
        users.add(new User("Babken","Gevorgyan",52,"yut@mail.ru","tt"));
        users.add(new User("Vlad","devdeosyan",62,"rwe@mail.ru","uiu"));
        return users;
    }

    public void index(View view) {
        Log.e("__datatable",""+DataTable.getMultiChosenIndexes(200));
    }
}
