package cat.uab.idt.rightsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.models.WhatsNextModel;

public class RVAWhatsNext extends RecyclerView.Adapter<RVAWhatsNext.ViewHolder>{
    private ArrayList<WhatsNextModel> dataSet;
    private LayoutInflater inflater;
    private RVAWhatsNext.ItemClickListener clickListener;

    //Data set is passed through the constructor
    public RVAWhatsNext(Context context, ArrayList<WhatsNextModel> dataSet){
        this.inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    //Inflates the row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.rv_whats_next_row, parent, false);
        return new ViewHolder(view);
    }

    //Binds the data set to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.tv_whats_next.setText(dataSet.get(position).getMessage());
    }

    //Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_whats_next;

        ViewHolder(View itemView) {
            super(itemView);
            tv_whats_next = itemView.findViewById(R.id.tv_whats_next);

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
    //public TagModel getItem(int pos) {
       // return dataSet.get(pos);
    //}

    //Allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    //Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
