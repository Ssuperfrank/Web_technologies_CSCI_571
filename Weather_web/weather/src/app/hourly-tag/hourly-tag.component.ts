import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, FormControl} from '@angular/forms';


import { WeatherResultService } from '../service/weather-result.service';
import { WeatherHourly } from '../weatherHourly';


@Component({
  selector: 'app-hourly-tag',
  templateUrl: './hourly-tag.component.html',
  styleUrls: ['./hourly-tag.component.css']
})
export class HourlyTagComponent implements OnInit {

  @Input() searchTime: number;

  public optionForm: FormGroup;
  public hourlyInfo: WeatherHourly[] = [];
  public chartData = {
    Temperature : [],
    Pressure: [],
    Humidity: [],
    Ozone: [],
    Visibility: [],
    'Wind Speed': []
  };
  public selectOptions = ['Temperature', 'Pressure', 'Humidity', 'Ozone', 'Visibility', 'Wind Speed'];
  constructor(private weatherService: WeatherResultService, private fg: FormBuilder) {
    this.optionForm = this.fg.group(
      {options: 'Temperature'}
    );
  }

  ngOnChanges(changes: SimpleChanges) {
      this.hourlyInfo = this.weatherService.getHourly();
      this.pushData();
      // console.log(this.chartData);
      // console.log(this.hourlyInfo);
  }

  pushData() {
    if (this.hourlyInfo) {
        this.chartData.Temperature = [];
        this.chartData.Humidity = [];
        this.chartData.Pressure = [];
        this.chartData.Ozone = [];
        this.chartData.Visibility = [];
        this.chartData['Wind Speed'] = [];

      for (const data of this.hourlyInfo) {
        this.chartData.Temperature.push(data.temperature.toFixed());
        this.chartData.Humidity.push((data.humidity * 100).toFixed());
        this.chartData.Pressure.push(data.pressure);
        this.chartData.Ozone.push(data.ozone);
        this.chartData.Visibility.push(data.visibility);
        this.chartData['Wind Speed'].push(data.windSpeed);
      }

      // console.log(this.chartData);
    }
  }



  ngOnInit() {
  }


}
