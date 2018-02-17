package me.david.TimberNoCheat.command.blocktrigger;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.storage.YamlComponent;
import me.david.TimberNoCheat.storage.YamlFile;
import me.david.api.utils.NumberUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AtributeList implements YamlComponent {

    private ArrayList<Boolean> booleans = new ArrayList<>();
    private ArrayList<Double> doubles = new ArrayList<>();
    private ArrayList<Float> floats = new ArrayList<>();
    private ArrayList<Long> longs = new ArrayList<>();
    private ArrayList<Integer> integers = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();
    private ArrayList<Check> checks = new ArrayList<>();

    private ArrayList<AtributeParseErrors> errors = new ArrayList<>();

    public AtributeList(){}
    public AtributeList(String[] objects, Class[] classes){
        if(objects.length != classes.length) errors.add(AtributeParseErrors.ARGUMENT_LENGHT);
        int i = 0;
        for(String object : objects){
            Class clazz = classes[i];
            if(clazz == boolean.class){
                if(!NumberUtil.isBoolean(object)) {
                    errors.add(AtributeParseErrors.NOBOOLEAN);
                    continue;
                }
                booleans.add(NumberUtil.getBoolean(object));
            }else if(clazz == double.class){
                if(!NumberUtil.isDouble(object)) {
                    errors.add(AtributeParseErrors.NODOUBLE);
                    continue;
                }
                doubles.add(NumberUtil.getDouble(object));
            }else if(clazz == float.class){
                if(!NumberUtil.isFloat(object)) {
                    errors.add(AtributeParseErrors.NOFLOAT);
                    continue;
                }
                floats.add(NumberUtil.getFloat(object));
            }else if(clazz == long.class){
                if(!NumberUtil.isLong(object)) {
                    errors.add(AtributeParseErrors.NOLONG);
                    continue;
                }
                longs.add(NumberUtil.getLong(object));
            }else if(clazz == int.class){
                if(!NumberUtil.isNumber(object)) {
                    errors.add(AtributeParseErrors.NOINT);
                    continue;
                }
                integers.add(NumberUtil.getnumber(object));
            }else if(clazz == String.class){
                strings.add(object);
            }else if(clazz == Check.class){
                Check check  = TimberNoCheat.checkmanager.getCheckbyName(object);
                if(check == null){
                    errors.add(AtributeParseErrors.CHECKNOTEXSITS);
                    continue;
                }
                checks.add(check);
            }else errors.add(AtributeParseErrors.INTERNAL);
            i++;
        }
    }

    public ArrayList<Boolean> getBooleans() {
        return booleans;
    }

    public ArrayList<Double> getDoubles() {
        return doubles;
    }

    public ArrayList<Float> getFloats() {
        return floats;
    }

    public ArrayList<Long> getLongs() {
        return longs;
    }

    public ArrayList<Integer> getIntegers() {
        return integers;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public ArrayList<Check> getChecks() {
        return checks;
    }

    public ArrayList<AtributeParseErrors> getErrors() {
        return errors;
    }

    @Override
    public void read(YamlFile yaml) {
        booleans = (ArrayList<Boolean>) yaml.getBooleanList("booleans");
        doubles = (ArrayList<Double>) yaml.getDoubleList("doubles");
        floats = (ArrayList<Float>) yaml.getFloatList("floates");
        longs = (ArrayList<Long>) yaml.getLongList("longs");
        integers = (ArrayList<Integer>) yaml.getIntegerList("integers");
        strings = (ArrayList<String>) yaml.getStringList("strings");
        for(String check : yaml.getStringList("checks"))
            checks.add(TimberNoCheat.checkmanager.getCheckbyName(check));
    }

    @Override
    public void save(YamlFile yaml) {

    }
}
