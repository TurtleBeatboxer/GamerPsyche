package com.todoback.todobackend.service.orianna;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.todoback.todobackend.domain.LOLServer;
import com.todoback.todobackend.domain.User;

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
        } else if (enumServerIndex == LOLServer.LAN) {
            return Region.LATIN_AMERICA_NORTH;
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
}
