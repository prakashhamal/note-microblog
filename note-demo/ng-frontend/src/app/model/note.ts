export class Note{
  id: number;
  title: string;
  description: string;
  archived: boolean;
  readmore: boolean;
  descJSON : any;
  attachmentList =  [];

  constructor(title: string, archived: boolean) {
    this.title = title;
    this.archived = false;
  }

  public getDescJSON(){
     return JSON.parse(this.description);
  }
}
