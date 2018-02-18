package me.david.timbernocheat.config;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class old_Settings {
    public int chat_delay_schwelle = 6;
    public double chat_delay_small = 0.0;
    public double chat_delay_big = 0.0;

    public boolean chat_address_blockcompletly = false;

    public boolean chat_spamming_ignorecase = false;
    public int chat_spamming_toshort = 4;
    public List<String> chat_spamming_whitelist = new ArrayList<String>();
    public boolean chat_spamming_whitelist_ignorecase = false;

    public double chat_commanddelay_delay = 0.0;

    public List<String> chat_blacklist = new ArrayList<String>();


    public boolean shematica_kick = false;
    public boolean shematica_print = false;
    public boolean shematica_save = false;
    public boolean shematica_load = false;
    public boolean shematica_block = false;

    public boolean betterpvp_use = false;

    public boolean reiminimap_use = false;

    public boolean damageindicators_use = false;

    public boolean voxelmap_use = false;

    public boolean smartmoving_use = false;

    public boolean labymod_use_animations = false;
    public boolean labymod_use_armor = false;
    public boolean labymod_use_blockbuild = false;
    public boolean labymod_use_chat = false;
    public boolean labymod_use_damageindicator = false;
    public boolean labymod_use_extras = false;
    public boolean labymod_use_food = false;
    public boolean labymod_use_minimap_radar = false;
    public boolean labymod_use_nick = false;
    public boolean labymod_use_potions = false;
    public boolean labymod_use_gui = false;

    public boolean zig5_kick = false;
    public boolean wdl_kick = false;
    public boolean bsm_kick = false;
    public boolean bsprint_kick = false;
    public boolean fml_kick = false;
    public boolean liteloader_kick = false;


    public boolean exploit_sign_notiyplayer = false;
    public boolean exploit_sign_sendcommand = false;
    public String exploit_sign_command = "";

    public boolean exploit_book_cancel = false;
    public boolean exploit_book_sendcommand = false;
    public String exploit_book_command = "";

    public String exploit_nbt_nonbtdatacmd = "";
    public String exploit_nbt_nullcmd = "";
    public String exploit_nbt_nopagescmd = "";
    public String exploit_nbt_toomanypagescmd = "";
    public int exploit_nbt_toomanypages = 0;
    public String exploit_nbt_toolongcmd = "";
    public int exploit_nbt_toolong = 0;

    public String exploit_nbtchannel_cmd = "";
    public int exploit_nbtchannel_toomany = 0;

    public String exploit_nbtpacketfloor_cmd = "";
    public int exploit_nbtpacketfloor_toomany = 0;

    public int exploit_craher_itempersec = 0;
    public int exploit_craher_swingpersec = 0;


    public boolean other_mcleaks_sendcommand = false;
    public String other_mcleaks_command = "";

    public int other_pingspoof_checkspeed = 0;
    public int other_pingspoof_maxdiffrence = 0;
    public int other_pingspoof_maxping = 0;

    public int other_nuker_maxblockbreakssec = 0;


    public int movement_derb_maxpitch = 0;
    public int movement_derb_minpitch = 0;
    public String movement_derb_cmd = "";

    public int movement_blink_distance = 0;

    public boolean movement_clip_onlyfullblocks = false;

    public int movement_speed_timercheck = 0;
    public long movement_speed_timeraverage_inmilis = 0;
    public int movement_speed_timerflagtomessage = 0;
    public int movement_speed_togglesnekinsec = 0;
    public double movement_speed_normal_normal = 0;
    public double movement_speed_normal_normalground = 0;
    public double movement_speed_normal_modistairs = 0;
    public double movement_speed_normal_modislabs = 0;
    public double movement_speed_normal_modisolid = 0;
    public double movement_speed_normal_modiice = 0;
    public double movement_speed_normal_modiicesolic = 0;
    public double movement_speed_normal_modispeed = 0;
    public double movement_speed_normal_modispeedground = 0;
    public long movement_speed_normal_elapsedtoret = 0;
    public long movement_speed_normal_elapsedtoretflag = 0;
    public int movement_speed_normal_flagcountertoflag = 0;
    public int movement_speed_normal_tofastnewcount = 0;

    public int movement_fly_nodowntonotify = 0;


    public int fight_fightspeed_maxhitspersecond = 0;
    public int fight_fightspeed_maxinteractspersecond = 0;

    public double fight_fastbow_ignoreforce = 0;
    public int fight_fastbow_delay   = 0;

    public double fight_killaura_defaultrange = 0;
    public double fight_killaura_rangespeed1 = 0;
    public double fight_killaura_rangespeed2 = 0;
    public double fight_killaura_rangeping_100_200 = 0;
    public double fight_killaura_rangeping_200_250 = 0;
    public double fight_killaura_rangeping_250_300 = 0;
    public double fight_killaura_rangeping_300_350 = 0;
    public double fight_killaura_rangeping_350_400 = 0;
    public double fight_killaura_rangeping_over400 = 0;
    public double fight_killaura_rangevectorlen = 0;
    public double fight_killaura_rangeisup = 0;
    public long fight_killaura_rangeclearaftermilis = 0;
    public double fight_killaura_range_levelrange = 0;
    public int fight_killaura_range_toohitslongtoreport = 0;
    public long fight_killaura_multiauradelay = 0;

    public long fight_regen_delay = 0;


    public int interact_fasplace_cps = 0;

    public int interact_noswing_delayticks = 0;

    public boolean interact_water = false;
    public boolean interact_ghosthand = false;
    public boolean interact_keepalive = false;
    public boolean interact_block = false;
    public boolean interact_sleep = false;
    public boolean interact_invisible = false;
    public boolean interact_openinv = false;

    public int player_fasteat_delay = 0;

    public long player_inv_delay_inmilis = 0;
    public boolean player_inv_sneak = false;
    public boolean player_inv_sprint = false;
    public boolean player_inv_block = false;
    public boolean player_inv_sleep = false;
    public long player_inv_hitdelayinmili = 0;
    public boolean player_inv_openinvhit = false;
    public long player_inv_invchatdelay = 0;
    public boolean player_inv_openinvchat = false;

    public long player_morepackets_elapsed = 0;
    public int player_morepackets_maxpackets = 0;
    public int player_morepackets_blacklistadd = 0;
    public int player_morepackets_blacklistremove = 0;
    public int player_morepackets_worlddownloadingdelayinticks = 0;

    public boolean player_skinblinker_sleep = false;
    public boolean player_skinblinker_sprint = false;
    public boolean player_skinblinker_block = false;
    public boolean player_skinblinker_sneak = false;
    public int player_skinblinker_movemindelay = 0;

    public int player_badpackets_maxmoves = 0;

    public int player_fastswitch_maxping = 0;
    public int player_fastswitch_delaymili = 0;

    public long player_cheststeler_delay = 0;
    public boolean player_cheststeler_consistent = false;
    public long player_cheststeler_consistent_cacheinmilis = 0;
    public int player_cheststeler_consistent_maxdelaymilis = 0;
    public int player_cheststeler_consistent_maxcachesize = 0;
    public int player_cheststeler_consistent_mincachesize = 0;
    public int player_cheststeler_maxping = 0;

    @Deprecated
    public old_Settings(){
        load();
    }

    @Deprecated
    public void load(){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(TimberNoCheat.instance.config);
        TimberNoCheat.instance.prefix = yml.getString("generel_prefix");
        //TimberNoCheat.instance.curconfigversion = yml.getInt("generel_configversion");

        chat_delay_schwelle = yml.getInt("chat_delay_schwelle");
        chat_delay_small = yml.getDouble("chat_delay_small_milis");
        chat_delay_big = yml.getDouble("chat_delay_big_milis");

        chat_address_blockcompletly = yml.getBoolean("delete_domain_complety");

        chat_spamming_ignorecase = yml.getBoolean("chat_spamming_equlsignore");
        chat_spamming_toshort = yml.getInt("chat_spamming_toshort");
        chat_spamming_whitelist = yml.getStringList("chat_spamming_whitelist");
        chat_spamming_whitelist_ignorecase = yml.getBoolean("chat_spamming_whitelist_equlsignore");

        chat_commanddelay_delay = yml.getDouble("chat_commanddelay_delay");

        chat_blacklist = yml.getStringList("chat_blacklist");


        shematica_kick = yml.getBoolean("shematica_kick");
        shematica_load = yml.getBoolean("shematica_load");
        shematica_print = yml.getBoolean("shematica_print");
        shematica_save = yml.getBoolean("shematica_save");
        shematica_block = yml.getBoolean("shematica_block");

        labymod_use_animations = yml.getBoolean("labymod_use_animations");
        labymod_use_armor = yml.getBoolean("labymod_use_armor");
        labymod_use_blockbuild = yml.getBoolean("labymod_use_blockbuild");
        labymod_use_chat = yml.getBoolean("labymod_use_chat");
        labymod_use_damageindicator = yml.getBoolean("labymod_use_damageindicator");
        labymod_use_extras = yml.getBoolean("labymod_use_extras");
        labymod_use_food = yml.getBoolean("labymod_use_food");
        labymod_use_gui = yml.getBoolean("labymod_use_gui");
        labymod_use_minimap_radar = yml.getBoolean("labymod_use_minimap_radar");
        labymod_use_nick = yml.getBoolean("labymod_use_nick");
        labymod_use_potions = yml.getBoolean("labymod_use_potions");

        betterpvp_use = yml.getBoolean("betterpvp_use");
        reiminimap_use = yml.getBoolean("reiminimap_use");
        damageindicators_use = yml.getBoolean("damageindicators_use");
        voxelmap_use = yml.getBoolean("voxelmap_use");
        smartmoving_use = yml.getBoolean("smartmoving_use");
        zig5_kick = yml.getBoolean("zig5_kick");
        wdl_kick = yml.getBoolean("wdl_kick");
        bsprint_kick = yml.getBoolean("bsprint_kick");
        bsm_kick = yml.getBoolean("bsm_kick");
        fml_kick = yml.getBoolean("fml_kick");
        liteloader_kick = yml.getBoolean("liteloader_kick");


        exploit_sign_notiyplayer = yml.getBoolean("expoit_sign_notiyplayer");
        exploit_sign_sendcommand = yml.getBoolean("expoit_sign_sendcommand");
        if(exploit_sign_sendcommand){
            exploit_sign_command = yml.getString("expoit_sign_command");
        }

        exploit_book_cancel = yml.getBoolean("exploit_book_cancel");
        exploit_book_sendcommand = yml.getBoolean("exploit_book_sendcommand");
        exploit_book_command = yml.getString("exploit_book_command");

        exploit_nbt_nonbtdatacmd = yml.getString("exploit_nbt_nonbtdatacmd");
        exploit_nbt_nullcmd = yml.getString("exploit_nbt_nullcmd");
        exploit_nbt_nopagescmd = yml.getString("exploit_nbt_nopagescmd");
        exploit_nbt_toomanypagescmd = yml.getString("exploit_nbt_toomanypagescmd");
        exploit_nbt_toomanypages = yml.getInt("exploit_nbt_toomanypages");
        exploit_nbt_toolongcmd = yml.getString("exploit_nbt_toolongcmd");
        exploit_nbt_toolong = yml.getInt("exploit_nbt_toolong");

        exploit_nbtchannel_cmd = yml.getString("exploit_nbtchannel_cmd");
        exploit_nbtchannel_toomany = yml.getInt("exploit_nbtchannel_toomany");

        exploit_nbtpacketfloor_cmd = yml.getString("exploit_nbtpacketfloor_cmd");
        exploit_nbtpacketfloor_toomany = yml.getInt("exploit_nbtpacketfloor_toomany");

        exploit_craher_itempersec = yml.getInt("exploit_craher_itempersec");
        exploit_craher_swingpersec = yml.getInt("exploit_craher_swingpersec");




        other_mcleaks_sendcommand = yml.getBoolean("other_mcleaks_sendcommand");
        if(other_mcleaks_sendcommand){
            other_mcleaks_command = yml.getString("other_mcleaks_command");
        }

        other_pingspoof_checkspeed = yml.getInt("other_pingspoof_checkspeed");
        other_pingspoof_maxdiffrence = yml.getInt("other_pingspoof_maxdiffrence");
        other_pingspoof_maxping = yml.getInt("other_pingspoof_maxping");

        other_nuker_maxblockbreakssec = yml.getInt("other_nuker_maxblockbreakssec");


        movement_derb_cmd = yml.getString("player_derb_cmd");
        movement_derb_maxpitch = yml.getInt("player_derb_maxpitch");
        movement_derb_minpitch = yml.getInt("player_derb_minpitch");

        movement_blink_distance = yml.getInt("movement_blink_distance");

        movement_clip_onlyfullblocks = yml.getBoolean("movement_clip_onlyfullblocks");

        movement_speed_timeraverage_inmilis = yml.getLong("movement_speed_timeraverage_inmilis");
        movement_speed_timercheck = yml.getInt("movement_speed_timercheck");
        movement_speed_togglesnekinsec = yml.getInt("movement_speed_togglesnekinsec");
        movement_speed_timerflagtomessage = yml.getInt("movement_speed_timerflagtomessage");
        movement_speed_normal_normal = yml.getDouble("movement_speed_normal_normal");
        movement_speed_normal_normalground = yml.getDouble("movement_speed_normal_normalground");
        movement_speed_normal_modistairs = yml.getDouble("movement_speed_normal_modistairs");
        movement_speed_normal_modislabs = yml.getDouble("movement_speed_normal_modislabs");
        movement_speed_normal_modisolid = yml.getDouble("movement_speed_normal_modisolid");
        movement_speed_normal_modiice = yml.getDouble("movement_speed_normal_modiice");
        movement_speed_normal_modiicesolic = yml.getDouble("movement_speed_normal_modiicesolic");
        movement_speed_normal_modispeed = yml.getDouble("movement_speed_normal_modispeed");
        movement_speed_normal_modispeedground = yml.getDouble("movement_speed_normal_modispeedground");
        movement_speed_normal_elapsedtoret = yml.getLong("movement_speed_normal_elapsedtoret");
        movement_speed_normal_elapsedtoretflag = yml.getLong("movement_speed_normal_elapsedtoretflag");
        movement_speed_normal_flagcountertoflag = yml.getInt("movement_speed_normal_flagcountertoflag");
        movement_speed_normal_tofastnewcount = yml.getInt("movement_speed_normal_tofastnewcount");

        movement_fly_nodowntonotify = yml.getInt("movement_fly_nodowntonotify");


        fight_fightspeed_maxhitspersecond = yml.getInt("fight_fightspeed_maxhitspersecond");
        fight_fightspeed_maxinteractspersecond = yml.getInt("fight_fightspeed_maxinteractspersecond");

        fight_fastbow_delay = yml.getInt("fight_fastbow_delay");
        fight_fastbow_ignoreforce = yml.getDouble("fight_fastbow_ignoreforce");

        fight_killaura_defaultrange = yml.getDouble("fight_killaura_defaultrange");
        fight_killaura_rangespeed1 = yml.getDouble("fight_killaura_rangespeed1");
        fight_killaura_rangespeed2 = yml.getDouble("fight_killaura_rangespeed2");
        fight_killaura_rangeping_100_200 = yml.getDouble("fight_killaura_rangeping_100_200");
        fight_killaura_rangeping_200_250 = yml.getDouble("fight_killaura_rangeping_200_250");
        fight_killaura_rangeping_250_300 = yml.getDouble("fight_killaura_rangeping_250_300");
        fight_killaura_rangeping_300_350 = yml.getDouble("fight_killaura_rangeping_300_350");
        fight_killaura_rangeping_350_400 = yml.getDouble("fight_killaura_rangeping_350_400");
        fight_killaura_rangeping_over400 = yml.getDouble("fight_killaura_rangeping_over400");
        fight_killaura_rangevectorlen = yml.getDouble("fight_killaura_rangevectorlen");
        fight_killaura_rangeisup = yml.getDouble("fight_killaura_rangeisup");
        fight_killaura_rangeclearaftermilis = yml.getLong("fight_killaura_rangeclearaftermilis");
        fight_killaura_range_levelrange = yml.getDouble("fight_killaura_range_levelrange");
        fight_killaura_range_toohitslongtoreport = yml.getInt("fight_killaura_range_toohitslongtoreport");
        fight_killaura_multiauradelay = yml.getLong("fight_killaura_multiauradelay");


        fight_regen_delay = yml.getLong("fight_regen_delay");


        interact_fasplace_cps = yml.getInt("interact_fasplace_cps");

        interact_noswing_delayticks = yml.getInt("interact_noswing_delayticks");

        interact_block = yml.getBoolean("interact_block");
        interact_ghosthand = yml.getBoolean("interact_ghosthand");
        interact_keepalive = yml.getBoolean("interact_keepalive");
        interact_water = yml.getBoolean("interact_water");
        interact_sleep = yml.getBoolean("interact_sleep");
        interact_invisible = yml.getBoolean("interact_invisible");
        interact_openinv = yml.getBoolean("interact_openinv");


        player_fasteat_delay = yml.getInt("player_fasteat_delay");

        player_inv_delay_inmilis = yml.getLong("player_inv_delay_inmilis");
        player_inv_sneak = yml.getBoolean("player_inv_sneak");
        player_inv_sprint = yml.getBoolean("player_inv_sprint");
        player_inv_block = yml.getBoolean("player_inv_block");
        player_inv_sleep = yml.getBoolean("player_inv_sleep");
        player_inv_hitdelayinmili = yml.getLong("player_inv_hitdelayinmili");
        player_inv_openinvchat = yml.getBoolean("player_inv_openinvchat");
        player_inv_openinvhit = yml.getBoolean("player_inv_openinvhit");
        player_inv_invchatdelay = yml.getLong("player_inv_invchatdelay");

        player_morepackets_elapsed = yml.getLong("player_morepackets_elapsed");
        player_morepackets_maxpackets = yml.getInt("player_morepackets_maxpackets");
        player_morepackets_blacklistadd = yml.getInt("player_morepackets_blacklistadd");
        player_morepackets_blacklistremove = yml.getInt("player_morepackets_blacklistremove");
        player_morepackets_worlddownloadingdelayinticks = yml.getInt("player_morepackets_worlddownloadingdelayinticks");

        player_skinblinker_block = yml.getBoolean("player_skinblinker_block");
        player_skinblinker_sleep = yml.getBoolean("player_skinblinker_sleep");
        player_skinblinker_sneak = yml.getBoolean("player_skinblinker_sneak");
        player_skinblinker_sprint = yml.getBoolean("player_skinblinker_sprint");
        player_skinblinker_movemindelay = yml.getInt("player_skinblinker_movemindelay");

        player_badpackets_maxmoves = yml.getInt("player_badpackets_maxmoves");

        player_fastswitch_delaymili = yml.getInt("player_fastswitch_delaymili");
        player_fastswitch_maxping = yml.getInt("player_fastswitch_maxping");

        player_cheststeler_delay = yml.getLong("player_cheststeler_delay");
        player_cheststeler_consistent = yml.getBoolean("player_cheststeler_consistent");
        player_cheststeler_consistent_cacheinmilis = yml.getLong("player_cheststeler_consistent_cacheinmilis");
        player_cheststeler_consistent_maxdelaymilis = yml.getInt("player_cheststeler_consistent_maxdelaymilis");
        player_cheststeler_consistent_maxcachesize = yml.getInt("player_cheststeler_consistent_maxcachesize");
        player_cheststeler_consistent_mincachesize = yml.getInt("player_cheststeler_consistent_mincachesize");
        player_cheststeler_maxping = yml.getInt("player_cheststeler_maxping");
    }

}
