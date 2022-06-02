export interface ImainUserDTO {
  lolUsername: string;
  lolServer: string;
  email: string;
  firstName: string;
  lastName: string;
}

export class MainUserDTO implements ImainUserDTO {
  constructor(public lolUsername: string, public lolServer: string, public email: string, public firstName: string, public lastName: string){

  }
}
