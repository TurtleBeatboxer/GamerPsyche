export interface IUserDATA {
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  activationId: string;
  codeNumber: string;
  lolUsername: string;
  lolServer: string;
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
    public lolUsername: string,
    public lolServer: string,
    public activated: boolean
  ) {}
}
