package com.todoback.todobackend.domain;


//dobra to generalnie stwiedzilem ze to zrobie jak bylo
//mozliwie najblizej tej wczesniejszej kalsy Address
//zamiast street i city jest LOLUsername i LOLServer (wewnetrzny typ wyliczeniowy, nie string!)
//pawel we to podepnij i nie SPIERDOL KURWA enuma bo moze sie pojsc DUZO JEBAC
//jak cos zjebalem to sory zrobilem refractor->safe delete
//zmiany w User, RegisterDTO, UserService

//dokonuje zmiany halo dlaczego to sie nie pushuje i nie dziala

public class LOLData {

    private LOLServer lolServer;

    private String lolUsername = new String();

    public String getLolUsername() {
        return lolUsername;
    }

    public void setLolUsername(String lolUsername) {
        this.lolUsername = lolUsername;
    }

    public LOLServer getLolServer() {
        return lolServer;
    }

    public void setLolServer(LOLServer lolServer) {
        this.lolServer = lolServer;
    }



    //a tu stwierdzilem ze wewnetrzna bedzie git bo nigdize indzeij
    //sie tego nie uzyje jakby jakis problem to moge odkrecic czy cos
    //mozna to jakos zmienic ***i wyroznic szczgolnie PBE***
    //bo nie wiem na jakiej zasadzei dziala PBE (czy to jest oddzielny serwer)
    //czy huj wie co lista jest z lolfandom
    //mozliwa bedzie potrzeba zrobienia COMPARATORA do ENUMA bo chyba tak najlatwiej porownac
    //eee wszystko jest raczej gotowe do podpiecia dla Pawla

}
