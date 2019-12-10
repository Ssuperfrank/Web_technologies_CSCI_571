import {Component, OnInit, Input, SimpleChanges, ElementRef} from '@angular/core';

import { WeatherByDateService } from '../service/weather-by-date.service';
import { WeatherResultService } from '../service/weather-result.service';
import { DatePipe } from '@angular/common';

import { WeatherDate } from '../weatherDate';


@Component({
  selector: 'app-weekly-card',
  templateUrl: './weekly-card.component.html',
  styleUrls: ['./weekly-card.component.css']
})
export class WeeklyCardComponent implements OnInit {

  @Input() click;

  public displayStatus: boolean;
  public showResult: boolean;
  public city: string;
  public dateOfModal: string;
  public weatherInfo: WeatherDate;
  public icon = {
    'clear-day': "assets/weather-icon/sun-512.png",
    'clear-night': "assets/weather-icon/sun-512.png",
    rain: "assets/weather-icon/rain-512.png",
    snow: "assets/weather-icon/snow-512.png",
    sleet: "assets/weather-icon/lightning-512.png",
    wind: "assets/weather-icon/wind-512.png",
    fog: "assets/weather-icon/cloudy-512.png",
    cloudy: "assets/weather-icon/cloud-512.png",
    'partly-cloudy-day': "assets/weather-icon/sunny-512.png",
    'partly-cloudy-night': "assets/weather-icon/sunny-512.png"
  };
  constructor(private weatherService: WeatherResultService, private weatherDateService: WeatherByDateService,  private el: ElementRef, private datePipe: DatePipe) { }

  ngOnChanges(changes: SimpleChanges) {
      this.showResult = false;
      this.displayStatus = this.weatherDateService.getModalStatus();
      setTimeout( () => {
        if (this.displayStatus) {
          this.showResult = true;
          this.city = this.weatherService.getCity();
          this.dateOfModal = this.datePipe.transform( new Date(this.weatherDateService.getWeatherDate() * 1000), 'dd/MM/yyyy');
          this.weatherInfo = this.weatherDateService.getWeatherDailyInfo();
          // console.log('weather card', this.weatherInfo);
          this.el.nativeElement.querySelector('#myModal').style.display = "block" ;
        }
      }, 1000);

  }

  ngOnInit() {
  }
  closeModal() {
    this.el.nativeElement.querySelector('#myModal').style.display = "none" ;
    this.weatherDateService.hiddenModal();
  }
}
