package com.selfapps.rav.alltogether.utilites;



public class ListenerGetter {
    static String id;
    static String name;
    static String role;
    static String[] values;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        ListenerGetter.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ListenerGetter.name = name;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        ListenerGetter.role = role;
    }

    public static String[] getValues() {
        return values;
    }

    public static void setValues(String[] values) {
        ListenerGetter.values = values;
    }
}
