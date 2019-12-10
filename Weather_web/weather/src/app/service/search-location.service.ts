import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchLocationService {
  private locationInfo: { [key: string]: string} = {
    lat : '',
    lon : ''
  };
  private  url = 'http://cs-usc-571.us-west-1.elasticbeanstalk.com/googleLocation?';
  constructor(private http: HttpClient) {
  }
  getLocationList(street: string, city: string, state: string) {
    return new Observable ((observer) => {
      this.http.get(this.url + 'street=' + street + '&city=' + city + '&state=' + state).subscribe((response: any) => {
        if (response !== '{}') {
          this.locationInfo.lat =  response.results[0].geometry.location.lat;
          this.locationInfo.lon =  response.results[0].geometry.location.lng;
          observer.next(this.locationInfo);
        }
      });
    });
  }
  getResult() {
    return this.locationInfo;
  }
  getLocationLat() {
    if (this.locationInfo.lat !== '' ) {
      return this.locationInfo.lat;
    }
    return '';
  }
  getLocationLon() {
    if (this.locationInfo.lon !== '' ) {
      return this.locationInfo.lon;
    }
    return '';
  }
}
