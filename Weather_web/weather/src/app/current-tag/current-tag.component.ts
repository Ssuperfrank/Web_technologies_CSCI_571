import { Component, OnInit, Input, SimpleChanges} from '@angular/core';


import { WeatherResultService } from '../service/weather-result.service';
import {  WeatherCurrently } from '../weatherCurrently';

@Component({
  selector: 'app-current-tag',
  templateUrl: './current-tag.component.html',
  styleUrls: ['./current-tag.component.css']
})
export class CurrentTagComponent implements OnInit {

  @Input()  searchTime: number;

  public city: string;
  public timezone: string;
  public currentWeatherInfo: WeatherCurrently;
  public state: string;
  public sealSrc: string;
  constructor(private weatherService: WeatherResultService) { }

  ngOnChanges(changes: SimpleChanges): void {
    this.currentWeatherInfo = this.weatherService.getCurrently();
    this.timezone = this.weatherService.getTimezone();
    this.city  = this.weatherService.getCity();
    this.state = this.weatherService.getState();
    this.sealSrc = this.weatherService.getSealLink();
  }
  ngOnInit() {
  }
}
