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
        cropsToDetails.put(CropTypes.CORN, new CropDetails(10, 20, 2));
        cropsToDetails.put(CropTypes.WHEAT, new CropDetails(5, 10, 1));
        cropsToDetails.put(CropTypes.LETTUCE, new CropDetails(10, 15, 3));
        cropsToDetails.put(CropTypes.COTTON, new CropDetails(3, 7, 2));
    }

    public CropDetails getCropDetails(CropTypes type) {
        return new CropDetails(cropsToDetails.get(type));
    }
}
