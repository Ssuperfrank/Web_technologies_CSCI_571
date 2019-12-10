import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetStateSealService {
  private customResult: any;
  private  url = 'http://cs-usc-571.us-west-1.elasticbeanstalk.com/stateSeal?state=';
  constructor(private  http: HttpClient) { }

  getSeal(state: string) {
    this.http.get(this.url + state).subscribe((response: any) => {
      if (response) {
        this.customResult = response;
        return response;
      }
    });
  }

  getSealLink() {
    if (this.customResult && this.customResult.items) {
        return this.customResult.items[0].link;
    }
    return '';
  }
  setSeal(link: string) {
    this.customResult = link;
  }
}
