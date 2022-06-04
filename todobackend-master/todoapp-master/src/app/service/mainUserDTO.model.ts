export interface ImainUserDTO {
  lolUsername: string;
  lolServer: string;
  email: string;
  firstName: string;
  lastName: string;
  username: string;
}

export class MainUserDTO implements ImainUserDTO {
  constructor(
    public lolUsername: string,
    public lolServer: string,
    public email: string,
    public firstName: string,
    public lastName: string,
    public username: string
  ) {}
}
