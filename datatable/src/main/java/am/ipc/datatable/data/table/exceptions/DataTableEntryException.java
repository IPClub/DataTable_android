package am.ipc.datatable.data.table.exceptions;


public class DataTableEntryException extends RuntimeException {
    private String message;

    public DataTableEntryException(String m) {
        message = m;
    }

    @Override
    public String toString() {
        return message;
    }
}