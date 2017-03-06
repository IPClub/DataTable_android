package am.ipc.datatable.data.table.entry;

import java.util.Comparator;

public class EntryComparator implements Comparator<DataTableEntry> {
    private int compareParamIndex;
    private boolean isAscending;
    public EntryComparator(int index,boolean b){
        compareParamIndex = index;
        isAscending = b;
    }
    @Override
    public int compare(DataTableEntry e1, DataTableEntry e2) {
        if(isAscending){
            if(isNumeric(e1.getRow()[compareParamIndex]) && isNumeric(e2.getRow()[compareParamIndex]) ){
                return (int)(Double.parseDouble(e1.getRow()[compareParamIndex]) - Double.parseDouble(e2.getRow()[compareParamIndex]));
            }
            return e1.getRow()[compareParamIndex].compareTo(e2.getRow()[compareParamIndex]);
        }else{
            if(isNumeric(e2.getRow()[compareParamIndex]) && isNumeric(e1.getRow()[compareParamIndex]) ){
                return (int)(Double.parseDouble(e2.getRow()[compareParamIndex]) - Double.parseDouble(e1.getRow()[compareParamIndex]));
            }
            return e2.getRow()[compareParamIndex].compareTo(e1.getRow()[compareParamIndex]);
        }

    }


    public boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
