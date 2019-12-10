import {Component, Input, OnInit, SimpleChanges, ElementRef} from '@angular/core';

import * as CanvasJS from './canvasjs.min';

import { WeatherResultService } from '../service/weather-result.service';
import { WeatherByDateService } from '../service/weather-by-date.service';

@Component({
  selector: 'app-weekly-tag',
  templateUrl: './weekly-tag.component.html',
  styleUrls: ['./weekly-tag.component.css']
})
export class WeeklyTagComponent implements OnInit {

  @Input() click: number;

  private dateInfo;
  private chart;

  public clickTime: number;
  constructor(private weatherService: WeatherResultService,  private dateService: WeatherByDateService, private el: ElementRef) {}

  ngOnInit() {
    this.clickTime = 0;
    // console.log('weekly init');
  }

  ngOnChanges( changes: SimpleChanges) {
    // console.log('weekly onchang');

    this.dateInfo = this.weatherService.getDaily();
    if (this.dateInfo) {
        var dataArray = [];
        var xnum = 80;
        for (let day of this.dateInfo) {
          dataArray.push({
            x: xnum,
            y: [day.temperatureLow, day.temperatureHigh],
            label: day.time,
          });
          xnum -= 10;
        }
        this.chart = new CanvasJS.Chart("chartContainer", {
          animationEnabled: true,
          exportEnabled: false,
          title: {
            text: "Weekly Weather"
          },
          axisX: {
            title: "Days",
            labelFormatter: function(e) {
              return CanvasJS.formatDate(new Date(e.label * 1000), "DD/MM/YYYY");
            }
          },
          axisY: {
            includeZero: false,
            title: "Temperature in Fahrenheit",
            interval: 10
          },
          legend: {
            horizontalAlign: "center", // left, center ,right
            verticalAlign: "top",  // top, center, bottom
          },
          dataPointMaxWidth: 15,
          toolTip: {
            contentFormatter: function(e) {
              var content = "<b>";
              content += CanvasJS.formatDate(new Date(e.entries[0].dataPoint.label * 1000), "DD/MM/YYYY");
              content += "</b>: " + e.entries[0].dataPoint.y[0].toFixed() + " to " + e.entries[0].dataPoint.y[1].toFixed();
              return content;
            }
          },
          data: [{
            click: (e) => {
              this.dateService.showModal();
              this.dateService.requestData(this.weatherService.getweatherLat(), this.weatherService.getweatherLon(), e.dataPoint.label);
              this.clickTime += 1;
            },
            color: "#A5D0EE",
            type: "rangeBar",
            showInLegend: true,
            yValueFormatString: "#0#",
            indexLabel: "{y[#index]}",
            legendText: "Day wise temperature range",
            dataPoints: dataArray
          }]
        });
      }

    setTimeout(() => {
      this.chart.render();
    }, 1000);
  }

}
