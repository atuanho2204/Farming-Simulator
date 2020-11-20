package main.farm.crops;


import java.util.HashMap;

public class CropCatalog {
    private HashMap<CropTypes, CropDetails> cropsToDetails;
    private static CropCatalog instance;
    private final int minBuyPrice = 8;

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
        cropsToDetails.put(CropTypes.CARROT, new CropDetails(10, 20, 5));
        cropsToDetails.put(CropTypes.MELON, new CropDetails(5, 10, 3));
        cropsToDetails.put(CropTypes.EGGPLANT, new CropDetails(10, 15, 4));
        cropsToDetails.put(CropTypes.TOMATO, new CropDetails(3, 7, 2));
    }

    public CropDetails getCropDetails(CropTypes type) {
        return new CropDetails(cropsToDetails.get(type));
    }

    public int getMinBuyPrice() {
        return minBuyPrice;
    }
}
