package cat.uab.idt.rightsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.models.ParticleModel;

public class RVASubjectsList extends RecyclerView.Adapter<RVASubjectsList.ViewHolder> {
    private ArrayList<ParticleModel> dataSet;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    //Data set is passed through the constructor
    public RVASubjectsList(Context context, ArrayList<ParticleModel> dataSet){
        this.inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    //Inflates the row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.rv_subjects_list_row, parent, false);
        return new ViewHolder(view);
    }

    //Binds the data set to the textview in each row
    @Override
    public void onBindViewHolder(RVASubjectsList.ViewHolder holder, int position){
        //String s = dataSet.get(position).getDescription();
        ParticleModel pm = dataSet.get(position);

        holder.tv_subject_item.setText(pm.getSubjectText());
    }
    //Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subject_item;

        ViewHolder(View itemView) {
            super(itemView);
            tv_subject_item = itemView.findViewById(R.id.tv_subject_item);

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
    public ParticleModel getItem(int pos) {
        return dataSet.get(pos);
    }

    //Allows clicks events to be caught
    public void setClickListener(RVASubjectsList.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    //Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
