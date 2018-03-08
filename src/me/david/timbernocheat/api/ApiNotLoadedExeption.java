package me.david.timbernocheat.api;

import me.david.timbernocheat.exeptions.TimberNoCheatExpetion;

public class ApiNotLoadedExeption extends TimberNoCheatExpetion {

    public ApiNotLoadedExeption(){
        super("The Api is not ready yet! This is 99% caused by Plugins how use the Api Wrong or Api parts that re not Supported anymore!");
    }
}
