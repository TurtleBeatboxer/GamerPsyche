export interface IAuthenticationDTO {
    id: number;
    username: string;
    success: boolean;
    message: string;
}

export class AuthenticationDTO implements IAuthenticationDTO {
    constructor(
        public id: number,
        public username: string,
        public success: boolean,
        public message: string
    ){}
}