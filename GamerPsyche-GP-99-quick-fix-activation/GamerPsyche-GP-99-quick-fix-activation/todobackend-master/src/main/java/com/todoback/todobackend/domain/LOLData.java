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

}
