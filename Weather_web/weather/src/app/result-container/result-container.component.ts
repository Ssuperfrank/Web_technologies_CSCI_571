import {Component, OnInit, Input, SimpleChanges, Output, EventEmitter} from '@angular/core';
import { WeatherResultService } from '../service/weather-result.service';
import { FavoriteCitiesService } from '../service/favorite-cities.service';
import { FavoriteRecord } from '../favoriteRecord';
import {ɵangular_packages_platform_browser_platform_browser_l} from '@angular/platform-browser';

@Component({
  selector: 'app-result-container',
  templateUrl: './result-container.component.html',
  styleUrls: ['./result-container.component.css']
})
export class ResultContainerComponent implements OnInit {

  @Input() searchTime: number;
  @Input() showResult: boolean;
  @Output() toParent = new EventEmitter();

  public clickNumber = 0;

  public resultOrFavorite: boolean;
  public favoriteStatus: boolean;
  public loading: boolean;
  public resultIfExist: boolean;
  public loadNum: number;
  constructor(private weatherService: WeatherResultService, private favoriteService: FavoriteCitiesService) {
  }

  ngOnInit() {
    this.resultOrFavorite = true;
  }

  ngOnChanges( changes: SimpleChanges) {
    if ( (changes.showResult && changes.showResult.currentValue) || ( changes.searchTime && this.showResult ) ) {
      this.loading = true;
      this.loadNum = 0;
      this.resultIfExist = true;
      this.loadingResult();
    }
    this.favoriteStatus = this.favoriteService.hasKey(this.weatherService.getCity(), this.weatherService.getState());
  }

  loadingResult() {
    let timerId = setInterval( () => {
        if ( this.weatherService.getCity() !== undefined) {
          this.loadNum = this.loadNum > 0 ? this.loadNum : 1;
        }
        if ( this.weatherService.getState() !== undefined) {
          this.loadNum = this.loadNum > 1 ? this.loadNum : 2;
        }
        if ( this.weatherService.getResult() !== undefined) {
          this.loadNum = this.loadNum > 2 ? this.loadNum : 3;
        }
        if ( this.weatherService.getSealLink() !== undefined) {
          this.loadNum = this.loadNum > 3 ? this.loadNum : 4;
        }
    }, 100);

    setTimeout( () => {
      if ( this.weatherService.getResult() === undefined ) {
        this.resultIfExist = false;
      }
      clearInterval(timerId);
      this.loading = false;
    }, 1500);
  }


  showResults() {
    this.favoriteStatus = this.favoriteService.hasKey(this.weatherService.getCity(), this.weatherService.getState());
    this.resultOrFavorite = true;
  }

  showFavorites() {
    if ( this.showResult ) {
      this.resultOrFavorite = false;
    }
  }

  tweet() {
    let url = "https://twitter.com/intent/tweet?text=";
    url += "The current temperature at " + this.weatherService.getCity()
        + " is " + this.weatherService.getCurrently().temperature + "° F. The weather conditions are "
        + this.weatherService.getCurrently().summary + ".";
    url += "&hashtags=CSCI571WeatherSearch";
    window.open(url, '_blank');
  }


  starButton() {
    if (!this.favoriteStatus) {
      this.favoriteService.storeKey(this.weatherService.getCity(), this.weatherService.getState(), this.weatherService.getSealLink(), this.weatherService.getweatherLat(), this.weatherService.getweatherLon());
      this.favoriteStatus = true;
    } else {
      this.favoriteService.deleteKey(this.weatherService.getCity(), this.weatherService.getState());
      this.favoriteStatus = false;
    }
  }

  weeklyTab() {
    this.clickNumber ++;
    // console.log( this.clickNumber);
  }

  /* get back to parent */
  searchCity(e: FavoriteRecord) {
    this.resultOrFavorite = true;
    this.toParent.emit(e);
  }

}

