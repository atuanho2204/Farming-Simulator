package main.util.crops;

public enum CropTypes {
    WHEAT,
    COTTON,
    LETTUCE,
    CORN;

    public static CropTypes getTypeFromString(String s) {
        switch (s) {
            case "wheat":
                return CropTypes.WHEAT;
            case "cotton":
                return CropTypes.COTTON;
            case "lettuce":
                return CropTypes.LETTUCE;
            default:
                return CropTypes.CORN;
        }
    }
}


