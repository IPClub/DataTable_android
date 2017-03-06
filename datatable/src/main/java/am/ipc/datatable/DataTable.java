package am.ipc.datatable;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import am.ipc.datatable.data.table.entry.DataTableEntry;
import am.ipc.datatable.data.table.view.DataTableType;
import am.ipc.datatable.data.table.view.DataTableView;

public class DataTable {

    private static DataTable table;
    private DataTableView tableView;
    private static HashMap<Integer,DataTableView> allTables = new HashMap<>();

    private DataTable(Context context,Integer id){
        tableView = new DataTableView(context);
        allTables.put(id,tableView);
    }

    private DataTable(Context context,Integer id,DataTableType type){
        tableView = new DataTableView(context,type);
        allTables.put(id,tableView);
    }

    public static DataTable init(Context context,Integer id){
        table = new DataTable(context,id);
        return table;
    }

    public static DataTable init(Context context,Integer id,DataTableType type){
        table = new DataTable(context,id,type);
        return table;
    }

    public static DataTable headers(String[] headers){
        table.tableView.setHeaders(headers);
        return table;
    }

    public static DataTable rowHeight(int h){
        table.tableView.setRowHeight(h);
        return table;
    }

//    public static DataTable rowWidth(int w){
//        table.tableView.setRowWidth(w);
//        return table;
//    }
    public static DataTable rowSelectedColor(int color){
        table.tableView.setSelectedRowColor(color);
        return table;
    }

    public static DataTable rowColor(int color){
        table.tableView.setRowColor(color);
        return table;
    }

    public static DataTable headerColor(int color){
        table.tableView.setHeaderColor(color);
        return table;
    }

    public static DataTable borderColor(int color){
        table.tableView.setBorderColor(color);
        return table;
    }

    public static DataTable entries(List<String[]> entries){

        table.tableView.setEntries(DataTableEntry.makeEntryList(entries));
        return table;
    }

    public static DataTable enableSearch(boolean es){
        table.tableView.enableSearch(es);
        return table;
    }

    public static DataTable enableSort(boolean es){
        table.tableView.enableSort(es);
        return table;
    }

    public static void showIn(LinearLayout container){
        table.tableView.create();
        container.addView(table.tableView);
    }

    public static int getSingleChosenIndex(Integer id){
        return allTables.get(id).getSingleChosenIndex();
    }

    public static List<Integer> getMultiChosenIndexes(Integer id){
        return allTables.get(id).getMultiChosenIndexes();
    }
}
