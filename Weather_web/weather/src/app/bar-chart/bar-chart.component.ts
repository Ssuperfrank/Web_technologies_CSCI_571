import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

  @Input() chartData;
  @Input() charTitle;

  // private max: number;
  // private min: number;

  barOptions: { [key: string]: ChartOptions; } = {
    Temperature : {
      responsive: true,
      scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Time difference between current hour'
        }
      }],
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Fahrenheit'
        }
      }]
      },
      legend: {
        onClick(event: MouseEvent, legendItem: Chart.ChartLegendLabelItem): void {
        }
      }
    },
    Pressure: {
      responsive: true,
      scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Time difference between current hour'
        }
      }],
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Millibars'
        }
      }]
      },
      legend: {
        onClick(event: MouseEvent, legendItem: Chart.ChartLegendLabelItem): void {
        }
      }
    },
    Humidity:{
      responsive: true,
      scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Time difference between current hour'
        }
      }],
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: '% Humidity'
        },
        ticks: {
          max: 105
        }
      }]
      },
      legend: {
        onClick(event: MouseEvent, legendItem: Chart.ChartLegendLabelItem): void {
        }
      }
    },
    Ozone: {
      responsive: true,
      scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Time difference between current hour'
        }
      }],
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Dobson Units'
        }
      }]
      },
      legend: {
        onClick(event: MouseEvent, legendItem: Chart.ChartLegendLabelItem): void {
        }
      }
    },
    Visibility: {
      responsive: true,
      scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Time difference between current hour'
        }
      }],
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Miles (Maximum 10)'
        }
        ,
        ticks: {
          max: 11,
          min: 0
        }
      }]
      },
      legend: {
        onClick(event: MouseEvent, legendItem: Chart.ChartLegendLabelItem): void {
        }
      }
    },
    'Wind Speed': {
      responsive: true,
      scales: {
        xAxes: [{
          scaleLabel: {
            display: true,
            labelString: 'Time difference between current hour'
          }
        }],
        yAxes: [{
          scaleLabel: {
            display: true,
            labelString: 'Miles Per Hour'
          }
        }]
      },
      legend: {
        onClick(event: MouseEvent, legendItem: Chart.ChartLegendLabelItem): void {
        }
      }
    }
  };

  barChartOptions: ChartOptions = this.barOptions.Temperature;
  barChartLabels: Label[] = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
    { data: [], label: 'Temperature' , backgroundColor: '#A5D0EE'}
  ];

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
    this.barChartData[0].data = this.chartData;
    this.barChartData[0].label = this.charTitle;
    this.barChartOptions = this.barOptions[this.charTitle];
    // this.getMaxMin( this.chartData );
  }

  ngOnInit() {
  }
  // getMaxMin( data: []) {
  //   var min = 100000;
  //   var max = -1;
  //   for (var value of data) {
  //     min = min < value ? min : value;
  //     max = max > value ? max : value;
  //   }
  //   this.min = min;
  //   this.max = max;
  // }


}
