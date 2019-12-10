import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { WeatherDate } from '../weatherDate';

@Injectable({
  providedIn: 'root'
})
export class WeatherByDateService {

  private displayModalStatus: boolean;
  private weatherDaily: WeatherDate;
  private date: number;
  private  url = 'http://cs-usc-571.us-west-1.elasticbeanstalk.com/weatherByDate?';
  constructor(private  http: HttpClient) { }

  requestData(lat: string, lon: string, time: number) {
    this.date = time;
    this.http.get(this.url + 'lati=' + lat + '&long=' +  lon + '&time=' + time).subscribe((response: any) => {
        this.weatherDaily  = response.currently;
    });
  }

  getWeatherDailyInfo() {
    return this.weatherDaily;
  }

  getWeatherDate() {
    return this.date;
  }

  showModal() {
    this.displayModalStatus = true;
  }
  hiddenModal() {
    this.displayModalStatus = false;
  }
  getModalStatus() {
    if ( this.displayModalStatus ) {
      return this.displayModalStatus;
    }
    return false;
  }

}
