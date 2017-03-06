package am.ipc.datatable.data.table.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataTableEntry {

    private int entry_index;
    private String[] row;
    private boolean isSelected;

    DataTableEntry(int id, String[] r) {
        row = r;
        entry_index = id;
    }

    public String[] getRow() {
        return row;
    }

    public static List<DataTableEntry> makeEntryList(List<String[]> list) {
        List<DataTableEntry> dataTableEntries = new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            dataTableEntries.add(new DataTableEntry(i,list.get(i)));
        }
        return dataTableEntries;

    }

    public int getEntry_index() {
        return entry_index;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataTableEntry)) return false;

        DataTableEntry that = (DataTableEntry) o;

        if (entry_index != that.entry_index) return false;
        if (isSelected != that.isSelected) return false;
        return Arrays.equals(row, that.row);

    }

    @Override
    public int hashCode() {
        int result = entry_index;
        result = 31 * result + Arrays.hashCode(row);
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
