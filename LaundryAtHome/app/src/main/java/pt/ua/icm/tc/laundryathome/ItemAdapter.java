package pt.ua.icm.tc.laundryathome;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.ua.icm.tc.laundryathome.model.Order;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Order> itemList1;
    private static ClickListener clickListener;

    public ItemAdapter(List<Order> itemList) {
        this.itemList1=itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem, parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemOrderId.setText("Order #" + itemList1.get(position).getId());
        holder.itemIsCompleted.setText("Completed: " + itemList1.get(position).isCompleted());
    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView itemOrderId, itemIsCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemOrderId = itemView.findViewById(R.id.itemOrderId);
            itemIsCompleted = itemView.findViewById(R.id.itemIsCompleted);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ItemAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
