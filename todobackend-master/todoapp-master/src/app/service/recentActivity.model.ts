export interface IrecentActivity {
  date: string;
  gamesRatio: string;
  winRatio: string;
  hoursPlayed: string;
}

export class RecentActivity implements IrecentActivity {
  constructor(
    public date: string,
    public gamesRatio: string,
    public winRatio: string,
    public hoursPlayed: string
  ) {}
}
