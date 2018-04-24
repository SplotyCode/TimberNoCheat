
package me.david.timbernocheat.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.*;

public class PrettyPrint {

    private Object object;
    private boolean color;
    private String prefix;

    public PrettyPrint(Object object, boolean color, String prefix){
        this.object = object;
        this.color = color;
        this.prefix = prefix;
    }

    public String prettyPrint(int tab){
        tab++;
        StringBuilder builder = new StringBuilder();
        if(color) builder.append("§4");
        builder.append(object.getClass().getSimpleName());
        if(color) builder.append("§7");
        builder.append("[\n").append(prefix);
        for(Field field : object.getClass().getDeclaredFields()){
            for(int i = 0;i < tab;i++) builder.append("    ");
            field.setAccessible(true);
            System.out.println(field.getName() + " " + object.getClass().getSimpleName());
            try {
                if(color) builder.append("§6");
                builder.append(field.getName());
                if(color) builder.append("§5");
                builder.append(": ");
                if(color) builder.append("§b");
                builder.append(getValue(field, tab)).append("\n").append(prefix);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0;i < tab-1;i++) builder.append("    ");
        builder.append(']');
        return builder.toString();
    }

    private String getValue(Field field, int tab) throws IllegalAccessException {
        field.setAccessible(true);
        Object o = field.get(object);
        if (o == null)
            return "null";

        Class<?> arraytype = o.getClass().getComponentType();
        if (arraytype != null) {
            if (!arraytype.isPrimitive()) {
                return Arrays.deepToString((Object[]) o);
            } else if (arraytype.equals(Integer.TYPE)) {
                return Arrays.toString((int[]) o);
            } else if (arraytype.equals(Double.TYPE)) {
                return Arrays.toString((double[]) o);
            } else if (arraytype.equals(Boolean.TYPE)) {
                return Arrays.toString((boolean[]) o);
            } else if (arraytype.equals(Short.TYPE)) {
                return Arrays.toString((short[]) o);
            } else if (arraytype.equals(Long.TYPE)) {
                return Arrays.toString((long[]) o);
            } else if (arraytype.equals(Float.TYPE)) {
                return Arrays.toString((float[]) o);
            } else if (arraytype.equals(Character.TYPE)) {
                return Arrays.toString((char[]) o);
            } else if (arraytype.equals(Byte.TYPE)) {
                return Arrays.toString((byte[]) o);
            } else {
                return "?????????";
            }
        } else if(field.getType().isEnum()){
            return field.getClass().getName().toUpperCase() + "." + o.toString() + (color?"§e(enum)§8":"(enum)");
        }else if (o instanceof Set) {
            Set<Object> set = (Set<Object>) o;
            ArrayList<String> list = new ArrayList<>(set.size());
            for(Object obj: set) {
                list.add(obj.toString());
            }
            Collections.sort(list);
            return list.toString();
        } else if (o instanceof Map) {
            Map<Object,Object> map= (Map<Object, Object>) o;
            ArrayList<String> list= new ArrayList<>(map.size());
            for(Object key: map.keySet()) {
                if(color)list.add("§6" + key.toString()+"§5=§b"+map.get(key) + "§7");
                else list.add(key.toString()+"="+map.get(key));
            }
            Collections.sort(list);
            return (color?"§7":"") + list.toString() + (color?"§b":"");
        } else if (o instanceof Collection) {
            return o.toString();
        } else if (o instanceof java.util.Calendar) {
            DateFormat f = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.UK);
            Calendar c = (Calendar) o;
            return f.format(c.getTime()) + " millisec=" + c.get(Calendar.MILLISECOND);
        } else if(o instanceof String || o instanceof Number || o instanceof Boolean || o instanceof Location || o instanceof Entity || o instanceof net.minecraft.server.v1_8_R3.Entity || o instanceof Vector){
            return o.toString();
        }else {
            PrettyPrint obj = new PrettyPrint(o, color, prefix);
            return obj.prettyPrint(tab);
        }
    }
}
