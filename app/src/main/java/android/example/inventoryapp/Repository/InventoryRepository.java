package android.example.inventoryapp.Repository;

import android.app.Application;
import android.example.inventoryapp.data.Inventory;
import android.example.inventoryapp.data.InventoryDao;
import android.example.inventoryapp.data.InventoryDatabase;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InventoryRepository {
    private InventoryDao inventoryDao;
    private LiveData<List<Inventory>> allInventories;

    public InventoryRepository(Application application) {
        InventoryDatabase database = InventoryDatabase.getInstance(application);
        inventoryDao = database.inventoryDao();
        allInventories = inventoryDao.getAllInventories();

    }

    public void insert(Inventory inventory) {
        new InsertInventoryAsyncTask(inventoryDao).execute(inventory);

    }

    public void update(Inventory inventory) {
        new UpdateInventoryAsyncTask(inventoryDao).execute(inventory);
    }

    public void delete(Inventory inventory) {
        new DeleteInventoryAsyncTask(inventoryDao).execute(inventory);
    }

    public void deleteAllInventories() {
        new DeleteAllInventoriesAsyncTask(inventoryDao).execute();
    }

    public LiveData<List<Inventory>> getAllInventories() {
        return allInventories;
    }

    private static class InsertInventoryAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao inventoryDao;

        private InsertInventoryAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.insert(inventories[0]);
            return null;
        }


    }

    private static class UpdateInventoryAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao inventoryDao;

        private UpdateInventoryAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.update(inventories[0]);
            return null;
        }
    }

    private static class DeleteInventoryAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao inventoryDao;

        private DeleteInventoryAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.delete(inventories[0]);
            return null;
        }
    }

    private static class DeleteAllInventoriesAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao inventoryDao;

        private DeleteAllInventoriesAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.deleteAllInventories();
            return null;
        }
    }
}
