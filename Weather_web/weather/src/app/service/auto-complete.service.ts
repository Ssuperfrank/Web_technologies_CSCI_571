import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AutoCompleteService {
  private locationList: Array<string>;
  private  url = 'http://cs-usc-571.us-west-1.elasticbeanstalk.com/autoComplete?input=';
  constructor(private http: HttpClient) {
  }
  getLocationList(input: string) {
    this.http.get(this.url + input).subscribe((response: any) => {
      if (response === '' || response == null) { return; }
      for (let name of response.predictions) {
        this.locationList.push(name.structured_formatting.main_text);
      }
    });
  }
  getResult(input: string) {
    this.locationList = [];
    if ( input === "") {
      return this.locationList;
    }
    this.getLocationList(input);
    return this.locationList;
  }

}
