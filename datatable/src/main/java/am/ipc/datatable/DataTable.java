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

    /**
     * Constructor to initialize DataTableView
     * @param context context from activity
     * @param id int number, unique for each data table
     */
    private DataTable(Context context,Integer id){
        tableView = new DataTableView(context);
        allTables.put(id,tableView);
    }

    /**
     * Constructor to initialize DataTableView
     * @param context context from activity
     * @param id int number, unique for each data table
     * @param type DataTable type - SINGLE_CHOICE, MULTI_CHOICE
     */
    private DataTable(Context context,Integer id,DataTableType type){
        tableView = new DataTableView(context,type);
        allTables.put(id,tableView);
    }

    /**
     * Initializing DataTable
     * @param context context from activity
     * @param id int number, unique for each data table
     * @return instance of DataTable
     */
    public static DataTable init(Context context,Integer id){
        table = new DataTable(context,id);
        return table;
    }

    /**
     * Initializing DataTable
     * @param context context from activity
     * @param id int number, unique for each data table
     * @param type DataTable type - SINGLE_CHOICE, MULTI_CHOICE
     * @return instance of DataTable
     */
    public static DataTable init(Context context,Integer id,DataTableType type){
        table = new DataTable(context,id,type);
        return table;
    }

    /**
     * Setting headers for DataTableView
     * @param headers String array for headers
     * @return instance of DataTable
     */
    public static DataTable headers(String[] headers){
        table.tableView.setHeaders(headers);
        return table;
    }

    /**
     * Setting height of DataTableView row
     * @param h height of each row
     * @return instance of DataTable
     */
    public static DataTable rowHeight(int h){
        table.tableView.setRowHeight(h);
        return table;
    }

    /**
     * Setting color for selected DataTableView row
     * @param color int color resource
     * @return instance of DataTable
     */
    public static DataTable rowSelectedColor(int color){
        table.tableView.setSelectedRowColor(color);
        return table;
    }

    /**
     * Setting color for DataTableView row
     * @param color int color resource
     * @return instance of DataTable
     */
    public static DataTable rowColor(int color){
        table.tableView.setRowColor(color);
        return table;
    }

    /**
     * Setting color for DataTableView header row
     * @param color int color resource
     * @return instance of DataTable
     */
    public static DataTable headerColor(int color){
        table.tableView.setHeaderColor(color);
        return table;
    }

    /**
     * Setting color for DataTableView border
     * @param color int color resource
     * @return instance of DataTable
     */
    public static DataTable borderColor(int color){
        table.tableView.setBorderColor(color);
        return table;
    }

    /**
     * Main function for setting data to DataTableView
     * @param entries string array of data
     * @return instance of DataTable
     */
    public static DataTable entries(List<String[]> entries){

        table.tableView.setEntries(DataTableEntry.makeEntryList(entries));
        return table;
    }

    /**
     * enables search function for DataTableView
     * @param es enable search boolean parameter
     * @return instance of DataTable
     */
    public static DataTable enableSearch(boolean es){
        table.tableView.enableSearch(es);
        return table;
    }
    /**
     * enables sorting function for DataTableView
     * @param es enable sorting boolean parameter
     * @return instance of DataTable
     */
    public static DataTable enableSort(boolean es){
        table.tableView.enableSort(es);
        return table;
    }

    /**
     * function to put DataTable to corresponding layout
     * @param container layout to show DataTableView in
     */
    public static void showIn(LinearLayout container){
        table.tableView.create();
        container.addView(table.tableView);
    }

    /**
     * returns index for DataTable of SINGLE_CHOICE type
     * @param id unique id of data table
     * @return int index
     */
    public static int getSingleChosenIndex(Integer id){
        return allTables.get(id).getSingleChosenIndex();
    }
    /**
     * returns list of indexes for DataTable of MULTI_CHOICE type
     * @param id unique id of data table
     * @return list of indexes
     */
    public static List<Integer> getMultiChosenIndexes(Integer id){
        return allTables.get(id).getMultiChosenIndexes();
    }
}
