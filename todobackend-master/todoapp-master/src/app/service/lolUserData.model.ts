import { RecentActivity } from "./recentActivity.model";
import { WinRateDTO } from './winRateDTO.model'
export interface IlolUserData {
  activityList: RecentActivity;
  userWinrate: WinRateDTO;
}

export class LolUserData implements IlolUserData{
  constructor(public activityList: RecentActivity, public userWinrate: WinRateDTO){}
}
