package am.ipc.datatable.data.table.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import am.ipc.datatable.R;
import am.ipc.datatable.data.table.entry.DataTableEntry;
import am.ipc.datatable.data.table.entry.EntryComparator;
import am.ipc.datatable.data.table.exceptions.DataTableEntryException;
import am.ipc.datatable.data.table.exceptions.DataTableHeaderException;

public class DataTableView extends LinearLayout {

    private DataTableParams params;
    private DataTableType type = DataTableType.SHOW;

    private Context context;
    private ScrollView scroll;
    private LinearLayout headerRow;

    private String[] headers;
    private List<DataTableEntry> entries;
    private List<DataTableEntry> searchedEntries;
    private List<DataTableEntry> showedEntries;
    private boolean sortAscending = true;
    private int searchIndex = -1;
    private int singleChoiceSelectedIndex = -1;


    public DataTableView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DataTableView(Context context, DataTableType type) {
        super(context);
        this.context = context;
        this.type = type;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        params = new DataTableParams();
        params.rowHeight = 100;
        params.rowWidth = 10;
        params.rowColor_selected = 0xFF9999;
        params.rowColor = 0xFFFFFFF;
        params.isSearchEnabled = true;
        params.isSortable = true;
        params.borderColor = 0xFF000000;
        params.headerColor =0x00000000;
    }

    public void create() {
        validateData();
        if(params.isSearchEnabled)
            createSearchBar();
        createTableHeaders();
        if(entries!=null && entries.size()>0){
            showedEntries = new ArrayList<>(entries);
            drawContent(showedEntries);
        }

    }

    public void drawContent(List<DataTableEntry> data) {
        createTableRows(data);
    }


    private void createSearchBar() {
        LinearLayout searchLayout = new LinearLayout(context);
        searchLayout.setOrientation(HORIZONTAL);

        final SearchView search = new SearchView(context);
        search.setQueryHint("Search");
        search.setIconified(false);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(entries!=null && entries.size()>0){
                    findQuery(newText);
                }
                return false;
            }
        });
        search.clearFocus();
        LinearLayout tab1 = new LinearLayout(context);
        searchLayout.setOrientation(HORIZONTAL);
        tab1.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f));

        LinearLayout tab2 = new LinearLayout(context);
        searchLayout.setOrientation(HORIZONTAL);
        tab2.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
        tab2.setFocusableInTouchMode(true);
        tab2.addView(search);

        searchLayout.addView(tab1);
        searchLayout.addView(tab2);


        addView(searchLayout);
    }

    private void findQuery(String newText) {
        searchedEntries = new ArrayList<>();
        for (DataTableEntry e : entries) {
            for (String d : e.getRow()) {
                if (d.toLowerCase().contains(newText.toLowerCase())) {
                    searchedEntries.add(e);
                    break;
                }
            }
        }
        deleteContent();
        showedEntries = new ArrayList<>(searchedEntries);
        drawContent(showedEntries);
    }

    private void validateData() {
        if (headers == null || headers.length == 0) {
            throw new DataTableHeaderException("Headers must be provided");
        } else {
            if (entries != null && entries.size() > 0) {
                for (DataTableEntry d : entries) {
                    if (headers.length != d.getRow().length) {
                        throw new DataTableEntryException("Mismatch, column and row length must be the same: " +
                                "headers(size = " + headers.length + ") and entries(size = " + d.getRow().length + ")");
                    }
                }

            }
        }

    }

    private void deleteContent() {
        scroll.removeAllViews();
    }

    private void createTableHeaders() {
        headerRow = new LinearLayout(context);
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(1, params.borderColor);
        gd.setColor(params.headerColor);
        for (final String header : headers) {


            LinearLayout main = new LinearLayout(context);
            main.setOrientation(HORIZONTAL);
            main.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));

            RelativeLayout r = new RelativeLayout(context);


            LinearLayout l1 = new LinearLayout(context);
            l1.setOrientation(HORIZONTAL);
            l1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            l1.setGravity(Gravity.CENTER);

            Button btn = new Button(context);
            btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            btn.setText(header);
            btn.setBackground(null);
            l1.addView(btn);

            LinearLayout l2 = new LinearLayout(context);
            l2.setOrientation(VERTICAL);
            l2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            l2.setGravity(Gravity.RIGHT | Gravity.CENTER);
            ImageView up = new ImageView(context);
            up.setLayoutParams(new LayoutParams(40, params.rowHeight));
            up.setImageResource(R.drawable.upd);
            if(params.isSortable){
                l2.addView(up);
            }

            r.addView(l1);
            r.addView(l2);

            main.setBackground(gd);
            main.addView(r);
            if(params.isSortable){
                main.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entries!=null && entries.size()>0){
                            sortTable(headerRow.indexOfChild(v));
                            refreshButtonsSortingIcons();
                            LinearLayout main = (LinearLayout) v;
                            boolean ac = (headerRow.indexOfChild(v) != searchIndex || !sortAscending);
                            ((ImageView)((LinearLayout)((RelativeLayout)main.getChildAt(0))
                                    .getChildAt(1))
                                    .getChildAt(0))
                                    .setImageResource(!ac?R.drawable.up:R.drawable.down);
                        }

                    }
                });
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entries!=null && entries.size()>0){
                            LinearLayout main = (LinearLayout)v.getParent().getParent().getParent();
                            sortTable(headerRow.indexOfChild(main));
                            refreshButtonsSortingIcons();
                            boolean ac = (headerRow.indexOfChild(main) != searchIndex || !sortAscending);
                            ((ImageView)((LinearLayout)((RelativeLayout)main.getChildAt(0))
                                    .getChildAt(1))
                                    .getChildAt(0))
                                    .setImageResource(!ac?R.drawable.up:R.drawable.down);


                        }

                    }

                });
            }

            headerRow.addView(main);
        }
        addView(headerRow);
    }

    private void refreshButtonsSortingIcons() {
        if(headerRow.getChildCount()>0){
            for (int i=0;i<headerRow.getChildCount();i++){
                LinearLayout main = (LinearLayout) headerRow.getChildAt(i);
                ((ImageView)((LinearLayout)((RelativeLayout)main.getChildAt(0))
                        .getChildAt(1))
                        .getChildAt(0))
                        .setImageResource(R.drawable.upd);
            }
        }


    }

    private void sortTable(int selectedButtonIndex) {
        sortAscending = selectedButtonIndex != searchIndex || !sortAscending;
        searchIndex = selectedButtonIndex;
        Collections.sort(showedEntries, new EntryComparator(selectedButtonIndex, sortAscending));
        deleteContent();
        drawContent(showedEntries);
    }


    private void createTableRows(List<DataTableEntry> data) {
        scroll = new ScrollView(context);
        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        LinearLayout scrollContainer = new LinearLayout(context);
        scrollContainer.setOrientation(VERTICAL);
        addRowsToContainer(scrollContainer, data);
        scroll.addView(scrollContainer);
        addView(scroll);

    }

    public void addRowsToContainer(LinearLayout container, List<DataTableEntry> data) {
        switch (type) {
            case SHOW:
                createShowTable(container, data);
                break;
            case SINGLE_CHOICE:
                createSingleChoiceTable(container, data);
                break;
            case MULTY_CHOICE:
                createMultiChoiceTable(container, data);
                break;
            default:
                break;
        }


    }

    private void createMultiChoiceTable(LinearLayout container, List<DataTableEntry> data) {

        for (DataTableEntry e : data) {
            LinearLayout row = new LinearLayout(context);
            for (int j = 0; j < headers.length; j++) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LayoutParams(0, params.rowHeight, 1f));
                tv.setText(e.getRow()[j]);
                GradientDrawable gd = new GradientDrawable();
                gd.setStroke(1, params.borderColor);
                if (e.isSelected()) {
                    gd.setColor(params.rowColor_selected);
                } else {
                    gd.setColor(params.rowColor);
                }
                tv.setBackground(gd);
                tv.setGravity(Gravity.CENTER);
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GradientDrawable gd = new GradientDrawable();
                        gd.setStroke(1, params.borderColor);
                        gd.setColor(params.rowColor_selected);
                        LinearLayout row = (LinearLayout) view.getParent();
                        LinearLayout scrollContainer = (LinearLayout) scroll.getChildAt(0);
                        int index = scrollContainer.indexOfChild(row);

                        if (showedEntries.get(index).isSelected()) {
                            gd = new GradientDrawable();
                            gd.setStroke(1, params.borderColor);
                            gd.setColor(params.rowColor);
                            showedEntries.get(index).setSelected(false);
                            entries.get(showedEntries.get(index).getEntry_index()).setSelected(false);
                            for (int i = 0; i < headers.length; i++) {
                                row.getChildAt(i).setBackground(gd);
                            }
                        }else{

                            gd = new GradientDrawable();
                            gd.setStroke(1, params.borderColor);
                            gd.setColor(params.rowColor_selected);
                            showedEntries.get(index).setSelected(true);
                            entries.get(showedEntries.get(index).getEntry_index()).setSelected(true);

                            for (int i = 0; i < headers.length; i++) {
                                row.getChildAt(i).setBackground(gd);
                            }
                        }
                    }
                });
                row.addView(tv);
            }
            container.addView(row);

        }
    }

    private void createSingleChoiceTable(LinearLayout container, List<DataTableEntry> data) {


        for (DataTableEntry e : data) {
            LinearLayout row = new LinearLayout(context);
            for (int j = 0; j < headers.length; j++) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LayoutParams(0, params.rowHeight, 1f));
                tv.setText(e.getRow()[j]);
                GradientDrawable gd = new GradientDrawable();
                gd.setStroke(1, params.borderColor);
                if (e.isSelected()) {
                    gd.setColor(params.rowColor_selected);
                } else {
                    gd.setColor(params.rowColor);
                }
                tv.setBackground(gd);
                tv.setGravity(Gravity.CENTER);
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GradientDrawable gd = new GradientDrawable();
                        gd.setStroke(1, params.borderColor);
                        gd.setColor(params.rowColor_selected);
                        LinearLayout row = (LinearLayout) v.getParent();
                        LinearLayout scrollContainer = (LinearLayout) scroll.getChildAt(0);
                        int index = scrollContainer.indexOfChild(row);

                        if (showedEntries.get(index).isSelected()) {
                            gd = new GradientDrawable();
                            gd.setStroke(1, params.borderColor);
                            gd.setColor(params.rowColor);
                            showedEntries.get(index).setSelected(false);
                            entries.get(showedEntries.get(index).getEntry_index()).setSelected(false);
                            singleChoiceSelectedIndex = -1;
                            for (int i = 0; i < headers.length; i++) {
                                row.getChildAt(i).setBackground(gd);
                            }
                        } else {
                            if (singleChoiceSelectedIndex != -1) {
                                gd = new GradientDrawable();
                                gd.setStroke(1, params.borderColor);
                                gd.setColor(params.rowColor);
                                int lastIndex = -1;
                                for (int i = 0; i < showedEntries.size(); i++) {
                                    if (showedEntries.get(i).isSelected()) {
                                        lastIndex = i;
                                        break;
                                    }
                                }
                                showedEntries.get(lastIndex).setSelected(false);
                                entries.get(showedEntries.get(lastIndex).getEntry_index()).setSelected(false);


                                LinearLayout prevouseRow = (LinearLayout) scrollContainer.getChildAt(lastIndex);
                                for (int i = 0; i < headers.length; i++) {
                                    prevouseRow.getChildAt(i).setBackground(gd);
                                }
                            }
                            singleChoiceSelectedIndex = scrollContainer.indexOfChild(row);

                            gd = new GradientDrawable();
                            gd.setStroke(1, params.borderColor);
                            gd.setColor(params.rowColor_selected);
                            showedEntries.get(singleChoiceSelectedIndex).setSelected(true);
                            entries.get(showedEntries.get(singleChoiceSelectedIndex).getEntry_index()).setSelected(true);

                            for (int i = 0; i < headers.length; i++) {
                                row.getChildAt(i).setBackground(gd);
                            }
                        }


                    }
                });
                row.addView(tv);
            }
            container.addView(row);

        }
    }

    private void createShowTable(LinearLayout container, List<DataTableEntry> data) {
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(1, params.borderColor);
        gd.setColor(params.rowColor);
        for (DataTableEntry e : data) {
            LinearLayout row = new LinearLayout(context);
            for (int j = 0; j < headers.length; j++) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LayoutParams(0, params.rowHeight, 1f));
                tv.setText(e.getRow()[j]);
                tv.setBackground(gd);
                tv.setGravity(Gravity.CENTER);
                row.addView(tv);
            }
            container.addView(row);

        }
    }


    public void setHeaders(String[] headers) {
        this.headers = headers;
    }


    public void setEntries(List<DataTableEntry> entries) {
        this.entries = entries;
    }

    public int getSingleChosenIndex() {
        for (DataTableEntry e : entries) {
            if (e.isSelected()) {
                return e.getEntry_index();
            }
        }
        return -1;
    }

    public List<Integer> getMultiChosenIndexes() {
        List<Integer> indexes = new ArrayList<>();

        for (DataTableEntry e : entries) {
            if (e.isSelected()) {
                indexes.add(e.getEntry_index());
            }
        }
        return indexes;
    }

    class DataTableParams {
        int rowHeight;
        int rowWidth;
        int rowColor;
        int rowColor_selected;
        boolean isSearchEnabled;
        boolean isSortable;
        int headerColor;
        int borderColor;
    }
    public void setHeaderColor(int c){
        if(params==null){
            params = new DataTableParams();
        }
        params.headerColor = ContextCompat.getColor(context, c);
    }
    public void setBorderColor(int c){
        if(params==null){
            params = new DataTableParams();
        }
        params.borderColor = ContextCompat.getColor(context, c);
    }
    public void enableSearch(boolean es){
        if(params==null){
            params = new DataTableParams();
        }
        params.isSearchEnabled = es;
    }

    public void enableSort(boolean es){
        if(params==null){
            params = new DataTableParams();
        }
        params.isSortable = es;
    }

    public void setRowColor(int c){
        if(params==null){
            params = new DataTableParams();
        }
        params.rowColor = ContextCompat.getColor(context, c);
    }
    public void setSelectedRowColor(int c){
        if(params==null){
            params = new DataTableParams();
        }
        params.rowColor_selected = ContextCompat.getColor(context,c);
    }
    public void setRowHeight(int h){
        if(params==null){
            params = new DataTableParams();
        }
        params.rowHeight = h;
    }

    public void setRowWidth(int w){
        if(params==null){
            params = new DataTableParams();
        }
        params.rowWidth = w;
    }

}
