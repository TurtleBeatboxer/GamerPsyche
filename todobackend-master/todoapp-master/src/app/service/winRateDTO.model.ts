export interface IwinRateDTO {
  rankedSolo: string;
  rankedFlex: string;
  normalDraft: string;
}

export class WinRateDTO implements IwinRateDTO {
  constructor(
    public rankedSolo: string,
    public rankedFlex: string,
    public normalDraft: string
  ) {}
}
