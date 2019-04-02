package cat.uab.idt.rightsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.R;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ArrayList<String>> childList;
    private String[] parents;

    public ExpandableAdapter(Context context, ArrayList<ArrayList<String>> childList, String[] parents){

        this.context = context;
        this.childList = childList;
        this.parents = parents;
    }

    @Override
    public int getGroupCount(){
        return childList.size();
    }

    @Override
    public int getChildrenCount(int parent){
        return childList.get(parent).size();
    }

    @Override
    public Object getGroup(int parent){
        return parents[parent];
    }

    @Override
    public Object getChild(int parent, int child) {
        return childList.get(parent).get(child);
    }

    @Override
    public long getGroupId(int parent) {
        return parent;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, parentView, false);
        }

        TextView parent_textview = (TextView) convertView.findViewById(R.id.tv_parent);
        parent_textview.setText(parents[parent]);

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, parentView, false);
        }

        TextView child_textview = (TextView) convertView.findViewById(R.id.tv_child);
        child_textview.setText(getChild(parent, child).toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {

        return false;
}
}
