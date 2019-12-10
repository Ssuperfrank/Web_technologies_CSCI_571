import { Injectable,  } from '@angular/core';

import { FavoriteRecord } from '../favoriteRecord';

@Injectable({
  providedIn: 'root'
})
export class FavoriteCitiesService {

  constructor() { }

  storeKey(cityName: string, stateName: string, sealLink: string, lat: number, lon: number) {
    localStorage.setItem(JSON.stringify({city: cityName, state: stateName}), JSON.stringify({city: cityName, state: stateName, seal: sealLink, lat: lat, lon: lon}));
  }

  getAllStorage() {
    var storage: FavoriteRecord[] =  [];
    for(let i = 0; i < localStorage.length; i++) {
        var key = JSON.parse(localStorage.key(i));
        storage[i] = {
          city: this.getValue(key.city, key.state).city,
          state: this.getValue(key.city, key.state).state,
          seal: this.getValue(key.city, key.state).seal,
          lat: this.getValue(key.city, key.state).lat,
          lon: this.getValue(key.city, key.state).lon
        }
    }
    return storage;
  }

  getValue(cityName: string, stateName: string) {
    var key = JSON.stringify({city: cityName, state: stateName});
    return JSON.parse(localStorage.getItem(key));
  }

  deleteKey(cityName: string, stateName: string) {
    localStorage.removeItem(JSON.stringify({city: cityName, state: stateName}));
  }

  hasKey(cityName: string, stateName: string) {
    if ( localStorage.getItem(JSON.stringify({city: cityName, state: stateName}))) {
      return true;
    } else {
      return false;
    }
  }

  isEmpty() {
    if ( localStorage.length === 0 ) {
      return true;
    }
    return false;
  }

}
