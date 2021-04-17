package com.gegessen.model;

public enum  ProductCategory {

    // italian, mexican, asian, vegan

    BURGER("Burger", "https://ik.imagekit.io/pyvwhvv76v/FWR/burger_site_8psVlJqzi.jpg"),
    FINE_DINING("Fine Dining", "https://ik.imagekit.io/pyvwhvv76v/FWR/fine-dining1_M1mFVxJ6g.jpg"),
    BRUNCH("Brunch", "https://ik.imagekit.io/pyvwhvv76v/FWR/mediterranean_site_KqJZf8-3M.jpg"),
    GROCERY("Grocery", " https://ik.imagekit.io/pyvwhvv76v/FWR/grocery_QzIwWVEZrT.jpg"),
    VEGETARIAN("Vegetarian", "https://ik.imagekit.io/pyvwhvv76v/FWR/vegetarian_site_43E6skM6p7P.jpg"),
    STREET_FOOD("Street food", "https://ik.imagekit.io/pyvwhvv76v/FWR/street-food_Z9dJ5-wdc.jpg"),
    GREEK("Greek", "https://ik.imagekit.io/pyvwhvv76v/FWR/greek_site_nA7KajosvlQ.jpg"),
    HEALTHY("Healthy", "https://ik.imagekit.io/pyvwhvv76v/FWR/healthy-food_gwwrmk5RkB.jpg"),
    FISH("Fish", " https://ik.imagekit.io/pyvwhvv76v/FWR/fish-food_oBugiI6qdi.jpg"),
    PIZZA("Pizza", "https://ik.imagekit.io/pyvwhvv76v/FWR/pizza-food_WTOl8tPzlX1.jpg"),
    VEGAN("Vegan", "https://ik.imagekit.io/pyvwhvv76v/FWR/vegan_site_hhqW_aHZC.jpg"),
    ITALIAN("Italian", "https://ik.imagekit.io/pyvwhvv76v/FWR/italian_site_HWgbbysoz.jpg"),
    ASIAN("Asian", "https://ik.imagekit.io/pyvwhvv76v/asian_LGVyVtEyYbu.jpg"),
    BULGARIAN("Bulgarian", "https://ik.imagekit.io/pyvwhvv76v/FWR/BG2_site_Sscai374K.jpg"),
    MEXICAN("Mexican", " https://ik.imagekit.io/pyvwhvv76v/FWR/mexican_site_z5uzjN_sX1a.jpg"),
    TURKEY("Turkish","https://ik.imagekit.io/pyvwhvv76v/FWR/turkish_site_S4tlCMqNr.jpg"),
    SUSHI("Sushi","https://ik.imagekit.io/pyvwhvv76v/FWR/sushiii_NdOIMAmrS.jpg");

    private String name;
    private String url;

    ProductCategory(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
