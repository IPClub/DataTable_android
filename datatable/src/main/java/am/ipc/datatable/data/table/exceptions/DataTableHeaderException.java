package am.ipc.datatable.data.table.exceptions;

public class DataTableHeaderException extends RuntimeException {
    private String message;

    public DataTableHeaderException(String m) {
        message = m;
    }

    @Override
    public String toString() {
        return message;
    }
}
