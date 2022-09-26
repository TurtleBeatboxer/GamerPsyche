package com.todoback.todobackend.service.LOL;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.todoback.todobackend.domain.LOLServer;
import com.todoback.todobackend.domain.User;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

public class OriannaUsagePreparationService {

    public String getRiotUserPuuId(User user) {
        String RiotUserPuuId = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get().getPuuid();
        return RiotUserPuuId;
    }

    public Region translateEnumServerToRiotRegion(LOLServer enumServerIndex) {
        switch (enumServerIndex) {
            case BR:
                return Region.BRAZIL;
            case EUNE:
                return Region.EUROPE_NORTH_EAST;
            case EUW:
                return Region.EUROPE_WEST;
            case LAN:
                return Region.LATIN_AMERICA_NORTH;
            case LAS:
                return Region.LATIN_AMERICA_SOUTH;
            case NA:
                return Region.NORTH_AMERICA;
            case OCE:
                return Region.OCEANIA;
            case RU:
                return Region.RUSSIA;
            case TR:
                return Region.TURKEY;
            case JP:
                return Region.JAPAN;
            case KR:
                return Region.KOREA;
            default:
                return null;
        }
    }

    public LeagueShard translateEnumServerToRiotRegionR4J(LOLServer enumServerIndex) {
        switch (enumServerIndex) {
            case BR:
                return LeagueShard.BR1;
            case EUNE:
                return LeagueShard.EUN1;
            case EUW:
                return LeagueShard.EUW1;
            case LAN:
                return LeagueShard.LA1;
            case LAS:
                return LeagueShard.LA2;
            case NA:
                return LeagueShard.NA1;
            case OCE:
                return LeagueShard.OC1;
            case RU:
                return LeagueShard.RU;
            case TR:
                return LeagueShard.TR1;
            case JP:
                return LeagueShard.JP1;
            case KR:
                return LeagueShard.KR;
            default:
                return null;
        }
    }
    public LeagueShard translateEnumToLeagueShard(LOLServer enumServerIndex) {
        switch (enumServerIndex) {
            case BR:
                return LeagueShard.BR1;
            case EUNE:
                return LeagueShard.EUN1;
            case EUW:
                return LeagueShard.EUW1;
            case LAN:
                return LeagueShard.LA1;
            case LAS:
                return LeagueShard.LA2;
            case NA:
                return LeagueShard.NA1;
            case OCE:
                return LeagueShard.OC1;
            case RU:
                return LeagueShard.RU;
            case TR:
                return LeagueShard.TR1;
            case JP:
                return LeagueShard.JP1;
            case KR:
                return LeagueShard.KR;
            default:
                return LeagueShard.PBE1;
        }

    }
}
