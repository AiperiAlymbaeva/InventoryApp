package android.example.inventoryapp.Adapter;

import android.content.Context;
import android.example.inventoryapp.R;
import android.example.inventoryapp.data.Inventory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryHolder> {

    private List<Inventory> inventories = new ArrayList<>();
    private OnItemClickListener listener;




    public void setInventories(List<Inventory> inventory) {
        if (inventory != null) {
            this.inventories.clear();
            this.inventories.addAll(inventory);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new InventoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {
        Inventory currentInventory = inventories.get(position);
        holder.title.setText(currentInventory.getTitle());
        holder.model.setText(currentInventory.getModel());
        holder.supplier.setText(currentInventory.getSupplier());
        holder.price.setText(String.valueOf(currentInventory.getPrice()));
        holder.quantity.setText(String.valueOf(currentInventory.getQuantity()));
        String imgUri = currentInventory.getImg();
            Glide
                    .with(holder.itemView.getContext())
                    .load(imgUri)
                    .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }



    public interface OnItemClickListener {
        void onItemClick(Inventory inventory);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Inventory inventoryPosition(int position) {
        return inventories.get(position);
    }

    class InventoryHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView model;
        private TextView price;
        private TextView supplier;
        private TextView quantity;
        private ImageView image;

        public InventoryHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            model = itemView.findViewById(R.id.model);
            price = itemView.findViewById(R.id.price);
            supplier = itemView.findViewById(R.id.supplier);
            quantity = itemView.findViewById(R.id.quantity);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(inventories.get(position));
                    }
                }
            });
        }


    }
}
