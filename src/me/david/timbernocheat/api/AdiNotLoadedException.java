package me.david.timbernocheat.api;

import me.david.timbernocheat.exeptions.TimberNoCheatExpetion;

/**
 * This Exception is thrown is Api Methods are Called
 * but the Api is not loaded yet
 */
public class AdiNotLoadedException extends TimberNoCheatExpetion {

    public AdiNotLoadedException(){
        super("The Api is not ready yet! This is 99% caused by Plugins that use the Api Wrong or use Api parts that re not Supported anymore!");
    }

}
