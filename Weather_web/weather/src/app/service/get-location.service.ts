import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class GetLocationService {

  private locationInfo: { [key: string]: string} = {
    lat : '',
    lon : '',
    city: '',
    state: ''
  };
  private  url = 'http://ip-api.com/json';
  constructor( private http: HttpClient) {
    this.http.get(this.url).subscribe( (data: any) => {
      this.locationInfo.lat = data.lat;
      this.locationInfo.lon = data.lon;
      this.locationInfo.city = data.city;
      this.locationInfo.state = data.regionName;
    });
  }

  getLocalInfo() {
    return this.locationInfo;
  }
  getLatitude() {
    return this.locationInfo.lat;
  }
  getLongitude() {
    return this.locationInfo.lon;
  }
  getCity() {
    return this.locationInfo.city;
  }
  getState() {
    return this.locationInfo.state;
  }

}
