package com.todoback.todobackend.service.LOL;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.League;
import com.todoback.todobackend.domain.LOLServer;
import com.todoback.todobackend.domain.User;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

public class OriannaUsagePreparationService {

    public String getRiotUserPuuId(User user) {
        String RiotUserPuuId = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get().getPuuid();
        return RiotUserPuuId;
    }


    public Region translateEnumServerToRiotRegion(LOLServer enumServerIndex) {
        if (enumServerIndex == LOLServer.BR) {
            return Region.BRAZIL;
        } else if (enumServerIndex == LOLServer.EUNE) {
            return Region.EUROPE_NORTH_EAST;
        } else if (enumServerIndex == LOLServer.EUW) {
            return Region.EUROPE_WEST;
        } else if (enumServerIndex == LOLServer.LAN) {
            return Region.LATIN_AMERICA_NORTH;
        } else if (enumServerIndex == LOLServer.LAS) {
            return Region.LATIN_AMERICA_SOUTH;
        } else if (enumServerIndex == LOLServer.NA) {
            return Region.NORTH_AMERICA;
        } else if (enumServerIndex == LOLServer.OCE) {
            return Region.OCEANIA;
        } else if (enumServerIndex == LOLServer.RU) {
            return Region.RUSSIA;
        } else if (enumServerIndex == LOLServer.TR) {
            return Region.TURKEY;
        } else if (enumServerIndex == LOLServer.JP) {
            return Region.JAPAN;
        } else if (enumServerIndex == LOLServer.KR) {
            return Region.KOREA;
        } else {
            return null;
        }
    }
    public LeagueShard translateEnumToLeagueShard(LOLServer enumServerIndex) {
        if (enumServerIndex == LOLServer.BR) {
            return LeagueShard.BR1;
        } else if (enumServerIndex == LOLServer.EUNE) {
            return LeagueShard.EUN1;
        } else if (enumServerIndex == LOLServer.EUW) {
            return LeagueShard.EUW1;
        } else if (enumServerIndex == LOLServer.LAN) {
            return LeagueShard.LA1;
        } else if (enumServerIndex == LOLServer.LAS) {
            return LeagueShard.LA2;
        } else if (enumServerIndex == LOLServer.NA) {
            return LeagueShard.NA1;
        } else if (enumServerIndex == LOLServer.OCE) {
            return LeagueShard.OC1;
        } else if (enumServerIndex == LOLServer.RU) {
            return LeagueShard.RU;
        } else if (enumServerIndex == LOLServer.TR) {
            return LeagueShard.TR1;
        } else if (enumServerIndex == LOLServer.JP) {
            return LeagueShard.JP1;
        } else if (enumServerIndex == LOLServer.KR) {
            return LeagueShard.KR;
        } else {
            return LeagueShard.PBE1;
        }
    }
}
