import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SearchLocationService } from './search-location.service';
import { GetStateSealService } from './get-state-seal.service';
import { GetLocationService } from './get-location.service';


@Injectable({
  providedIn: 'root'
})
export class WeatherResultService {
  private city: string;
  private state: string;
  private searchStatus: boolean;
  private weatherInfo: any;
  private stateList = {
      Alabama : 'AL',
      Alaska  : 'AK',
      Arizona : 'AZ',
      Arkansas : 'AR',
      California : 'CA',
      Colorado : 'CO',
      Connecticut : 'CT',
      Delaware : 'DE',
      'District Of Columbia': 'DC',
      Florida: 'FL',
      Georgia  : 'GA',
      Hawaii : 'HI',
      Idaho : 'ID',
      Illinois: 'IL',
      Indiana: 'IN',
      Iowa: 'IA',
      Kansas: 'KS',
      Kentucky: 'KY',
      Louisiana  : 'LA',
      Maine     : 'ME',
      Maryland   : 'MD',
      Massachusetts   : 'MA',
      Michigan  : 'MI',
      Minnesota    : 'MN',
      Mississippi    : 'MS',
      Missouri   : 'MO',
      Montana   : 'MT',
      Nebraska    : 'NE',
      Nevada   : 'NV',
      'New Hampshire'  : 'NH',
      'New Jersey' : 'NJ',
      'New Mexico'   : 'NM',
      'New York'   : 'NY',
      'North Carolina'  : 'NC',
      'North Dakota'   : 'ND',
      Ohio    : 'OH',
      Oklahoma  : 'OK',
      Oregon    : 'OR',
      Pennsylvania   : 'PA',
      'Rhode Island'  : 'RI',
      'South Carolina'  : 'SC',
      'South Dakota'    : 'SD',
      Tennessee   : 'TN',
      Texas  : 'TX',
      Utah: 'UT',
      Vermont  : 'VT',
      Virginia  : 'VA',
      Washington   : 'WA',
      'West Virginia' : 'WV',
      Wisconsin   : 'WI',
      Wyoming   : 'WY'
    };
  private url = 'http://cs-usc-571.us-west-1.elasticbeanstalk.com/weather?';
  constructor(private http: HttpClient,
              private locationService: SearchLocationService,
              private sealService: GetStateSealService,
              private IPService: GetLocationService) { }

  /*
    get weather info by point only when we click current place
  */
  requestAllWeatherInfoByPoint(lat: string, lon: string) {
      this.city =  this.IPService.getCity();
      this.state =  this.stateList[this.IPService.getState()];
      this.sealService.getSeal(this.state);
      return new Observable ((observer) => {
        this.http.get(this.url + 'lati=' + lat + '&long=' + lon).subscribe((response: any) => {
            this.weatherInfo = response;
            observer.next(response);
        });
      });
  }

  /*
    get weather info by point only when we click search
  */
  requestAllWeatherInfoByName(street: string, city: string , state: string) {
      this.city = city;
      this.state = state;
      this.sealService.getSeal(this.state);
      return new Observable((observer) => {
        this.locationService.getLocationList(street, city, state).subscribe((response: any) => {
          this.http.get(this.url + 'lati=' + response.lat + '&long=' + response.lon).subscribe((res: any) => {
                this.weatherInfo = res;
                observer.next(res);
            });
        });
      });
  }

  /*
    get weather info by point only when we search favorite city, exact point
  */
  requestAllWeatherInfoByFavorite(lat: string, lon: string, city: string, state: string, seal: string) {
    this.city = city;
    this.state = state;
    this.sealService.getSeal( this.state);
    // this.sealService.setSeal(seal);
    return new Observable ((observer) => {
      this.http.get(this.url + 'lati=' + lat + '&long=' + lon).subscribe((response: any) => {
          this.weatherInfo = response;
          observer.next(response);
      });
    });
  }

  getResult() {
    return this.weatherInfo;
  }

  getweatherLat() {
    if (this.weatherInfo) {
      return this.weatherInfo.latitude;
    }
    return '';
  }

  getweatherLon() {
    if (this.weatherInfo) {
      return this.weatherInfo.longitude;
    }
    return '';
  }

  getSealLink() {
    if (this.sealService.getSealLink() !== '') {
      return this.sealService.getSealLink();
    }
    return '';
  }

  getCity() {
    if (this.city) {
        return this.city;
    }
    return '';
  }

  getState() {
    if (this.state) {
        return this.state;
    }
    return '';
  }

  getTimezone() {
    if (this.weatherInfo) {
        return this.weatherInfo.timezone;
    }
    return '';
  }

  getCurrently() {
    if (this.weatherInfo) {
      return this.weatherInfo.currently;
    }
    return '';
  }

  getHourly() {
    if (this.weatherInfo) {
      return this.weatherInfo.hourly.data;
    }
    return '';
  }

  getDaily() {
    if (this.weatherInfo) {
      return this.weatherInfo.daily.data;
    }
    return '';
  }

  search() {
    this.searchStatus = true;
    return true;
  }

  clear() {
    this.searchStatus = false;
    return false;
  }

  getSearchStatus() {
    if ( this.searchStatus ) {
      return this.searchStatus;
    }
    return false;
  }

}
