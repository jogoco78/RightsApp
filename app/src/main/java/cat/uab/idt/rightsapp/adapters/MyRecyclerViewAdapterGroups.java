package cat.uab.idt.rightsapp.adapters;

import android.view.LayoutInflater;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.models.TagModel;

public class MyRecyclerViewAdapterGroups extends RecyclerView.Adapter<MyRecyclerViewAdapterGroups.ViewHolder> {
    private ArrayList<TagModel> dataSet;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;


    //Data set is passed through the constructor
    public MyRecyclerViewAdapterGroups(Context context, ArrayList<TagModel> dataSet){
        this.inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    //Inflates the row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.rv_group_layout, parent, false);
        return new ViewHolder(view);
    }

    //Binds the data set to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        //String s = dataSet.get(position).getDescription();
        TagModel tm = dataSet.get(position);
        System.out.println("Query: Position: " + position + " Text: " + tm.getDescription());

        holder.tv_group.setText(tm.getDescription());
    }

    //Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_group;

        ViewHolder(View itemView) {
            super(itemView);
            tv_group = itemView.findViewById(R.id.tv_group);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    //Returns the total number of rows
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    //Convenience method for getting data at click position
    public TagModel getItem(int pos) {
        return dataSet.get(pos);
    }

    //Allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    //Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
