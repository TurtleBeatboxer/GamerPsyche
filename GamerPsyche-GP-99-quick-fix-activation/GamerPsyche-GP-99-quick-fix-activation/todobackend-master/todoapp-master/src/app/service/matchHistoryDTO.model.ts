export interface ImatchHistoryDTO {
  championName: string;
  summonerName: string;
  didWin: boolean;
  kill: number;
  deaths: number;
  assists: number;
}

export class MatchHistoryDTO implements ImatchHistoryDTO {
  constructor(
    public championName: string,
    public summonerName: string,
    public didWin: boolean,
    public kill: number,
    public deaths: number,
    public assists: number
  ) {}
}
