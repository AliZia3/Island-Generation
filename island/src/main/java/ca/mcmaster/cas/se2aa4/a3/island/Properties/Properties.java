package ca.mcmaster.cas.se2aa4.a3.island.Properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Properties {
    public static String volcanoTier1Colors = "255,0,0";
    public static String volcanoTier2Colors = "32,32,32";
    public static String volcanoTier3Colors = "64,64,64";
    public static String volcanoTier4Colors = "96,96,96";
    public static String lagoonColors = "0,0,100";
    public static String lakeColors = "66,135,245";
    public static String landColors = "122,171,135";
    public static String oceanColors = "0,0,55";
    public static String aquaColors = "0,0,30";
    public static String beachColors = "194,178,128";

    public static Structs.Property getVolcanoTier1Props() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(volcanoTier1Colors)
                .build();
    }

    public static Structs.Property getVolcanoTier2Props() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(volcanoTier2Colors)
                .build();
    }

    public static Structs.Property getVolcanoTier3Props() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(volcanoTier3Colors)
                .build();
    }

    public static Structs.Property getVolcanoTier4Props() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(volcanoTier4Colors)
                .build();
    }

    public static Structs.Property getLagoonProps() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(lagoonColors)
                .build();
    }

    public static Structs.Property getLakeProps() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(lakeColors)
                .setKey("humidity")
                .setValue("1")
                .build();
    }

    public static Structs.Property getLakeHumidityProps() {
        return Structs.Property.newBuilder()
                .setKey("humidity")
                .setValue("1")
                .build();
    }

    public static Structs.Property getLandProps() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(landColors)
                .build();
    }

    public static Structs.Property getOceanProps() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(oceanColors)
                .build();
    }

    public static Structs.Property getAquaProps() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(aquaColors)
                .build();
    }

    public static Structs.Property getMoistProps() {
        return Structs.Property.newBuilder()
                .setKey("moisture")
                .setValue("1")
                .build();
    }

    public static Structs.Property getBeachProps() {
        return Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(beachColors)
                .build();
    }

}
