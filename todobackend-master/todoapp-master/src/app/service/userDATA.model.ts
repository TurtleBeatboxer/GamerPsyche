export interface IUserDATA {
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  activationId: string;
  codeNumber: string;
  lolusername: string;
  lolserver: string;
  activated: boolean;
}

export class UserDATA implements IUserDATA {
  constructor(
    public username: string,
    public password: string,
    public email: string,
    public firstName: string,
    public lastName: string,
    public activationId: string,
    public codeNumber: string,
    public lolusername: string,
    public lolserver: string,
    public activated: boolean
  ) {}
}
