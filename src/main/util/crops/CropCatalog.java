package main.util.crops;


import java.util.HashMap;

public class CropCatalog {
    private HashMap<CropTypes, CropDetails> cropsToDetails;
    private static CropCatalog instance;

    private CropCatalog() {
        setCatalogs();
    }

    public static CropCatalog getInstance() {
        if (instance == null) {
            synchronized (CropCatalog.class) {
                // check again to avoid multi-thread access
                if (instance == null) {
                    instance = new CropCatalog();
                }
            }
        }
        return instance;
    }

    private void setCatalogs() {
        cropsToDetails = new HashMap<>();
        cropsToDetails.put(CropTypes.CORN, new CropDetails(10, 20, 8));
        cropsToDetails.put(CropTypes.WHEAT, new CropDetails(5, 10, 5));
        cropsToDetails.put(CropTypes.LETTUCE, new CropDetails(10, 15, 6));
        cropsToDetails.put(CropTypes.COTTON, new CropDetails(3, 7, 3));

        //        cropsToItems = new HashMap<>();
        //        cropsToItems.put(CropTypes.CORN, new InventoryItem(10, 20, "Corn"));
        //        cropsToItems.put(CropTypes.WHEAT, new InventoryItem(5, 10, "Wheat"));
        //        cropsToItems.put(CropTypes.LETTUCE, new InventoryItem(10, 15, "Lettuce"));
        //        cropsToItems.put(CropTypes.COTTON, new InventoryItem(3, 7, "Cotton"));
    }

    public CropDetails getCropDetails(CropTypes type) {
        return new CropDetails(cropsToDetails.get(type));
    }
}
