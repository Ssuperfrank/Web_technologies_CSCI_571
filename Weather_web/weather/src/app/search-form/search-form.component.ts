import { Component, OnInit } from '@angular/core';
import {AbstractControl, ValidatorFn, Validators, FormBuilder, FormControl, FormGroup} from '@angular/forms';


import { GetLocationService } from '../service/get-location.service';
import { AutoCompleteService } from '../service/auto-complete.service';
import { WeatherResultService } from '../service/weather-result.service';
import { FavoriteRecord } from '../favoriteRecord';


@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {
  public stateList = [
    {
      Abbreviation: 'default',
      State: 'Select State'
    },
    {
      Abbreviation: 'AL',
      State: 'Alabama'
    },
    {
      Abbreviation: 'AK',
      State: 'Alaska'
    },
    {
      Abbreviation: 'AZ',
      State: 'Arizona'
    },
    {
      Abbreviation: 'AR',
      State: 'Arkansas'
    },
    {
      Abbreviation: 'CA',
      State: 'California'
    },
    {
      Abbreviation: 'CO',
      State: 'Colorado'
    },
    {
      Abbreviation: 'CT',
      State: 'Connecticut'
    },
    {
      Abbreviation: 'DE',
      State: 'Delaware'
    },
    {
      Abbreviation: 'DC',
      State: 'District Of Columbia'
    },
    {
      Abbreviation: 'FL',
      State: 'Florida'
    },
    {
      Abbreviation: 'GA',
      State: 'Georgia'
    },
    {
      Abbreviation: 'HI',
      State: 'Hawaii'
    },
    {
      Abbreviation: 'ID',
      State: 'Idaho'
    },
    {
      Abbreviation: 'IL',
      State: 'Illinois'
    },
    {
      Abbreviation: 'IN',
      State: 'Indiana'
    },
    {
      Abbreviation: 'IA',
      State: 'Iowa'
    },
    {
      Abbreviation: 'KS',
      State: 'Kansas'
    },
    {
      Abbreviation: 'KY',
      State: 'Kentucky'
    },
    {
      Abbreviation: 'LA',
      State: 'Louisiana'
    },
    {
      Abbreviation: 'ME',
      State: 'Maine'
    },
    {
      Abbreviation: 'MD',
      State: 'Maryland'
    },
    {
      Abbreviation: 'MA',
      State: 'Massachusetts'
    },
    {
      Abbreviation: 'MI',
      State: 'Michigan'
    },
    {
      Abbreviation: 'MN',
      State: 'Minnesota'
    },
    {
      Abbreviation: 'MS',
      State: 'Mississippi'
    },
    {
      Abbreviation: 'MO',
      State: 'Missouri'
    },
    {
      Abbreviation: 'MT',
      State: 'Montana'
    },
    {
      Abbreviation: 'NE',
      State: 'Nebraska'
    },
    {
      Abbreviation: 'NV',
      State: 'Nevada'
    },
    {
      Abbreviation: 'NH',
      State: 'New Hampshire'
    },
    {
      Abbreviation: 'NJ',
      State: 'New Jersey'
    },
    {
      Abbreviation: 'NM',
      State: 'New Mexico'
    },
    {
      Abbreviation: 'NY',
      State: 'New York'
    },
    {
      Abbreviation: 'NC',
      State: 'North Carolina'
    },
    {
      Abbreviation: 'ND',
      State: 'North Dakota'
    },
    {
      Abbreviation: 'OH',
      State: 'Ohio'
    },
    {
      Abbreviation: 'OK',
      State: 'Oklahoma'
    },
    {
      Abbreviation: 'OR',
      State: 'Oregon'
    },
    {
      Abbreviation: 'PA',
      State: 'Pennsylvania'
    },
    {
      Abbreviation: 'RI',
      State: 'Rhode Island'
    },
    {
      Abbreviation: 'SC',
      State: 'South Carolina'
    },
    {
      Abbreviation: 'SD',
      State: 'South Dakota'
    },
    {
      Abbreviation: 'TN',
      State: 'Tennessee'
    },
    {
      Abbreviation: 'TX',
      State: 'Texas'
    },
    {
      Abbreviation: 'UT',
      State: 'Utah'
    },
    {
      Abbreviation: 'VT',
      State: 'Vermont'
    },
    {
      Abbreviation: 'VA',
      State: 'Virginia'
    },
    {
      Abbreviation: 'WA',
      State: 'Washington'
    },
    {
      Abbreviation: 'WV',
      State: 'West Virginia'
    },
    {
      Abbreviation: 'WI',
      State: 'Wisconsin'
    },
    {
      Abbreviation: 'WY',
      State: 'Wyoming'
    }
  ];
  public searchForm: FormGroup;
  autoCompleteList = [];
  currentP: { [key: string]: string} = {
    lat : '',
    lon : '',
    city: '',
    state: ''
  };

  public showResult: boolean;
  public clickTime = 0;
  constructor( private fg: FormBuilder,
               public locationService: GetLocationService,
               private autoService: AutoCompleteService,
               private weatherService: WeatherResultService) {
    this.searchForm = this.fg.group({
    street: new FormControl({value: '', disabled: false}, [Validators.required, Validators.pattern('^(?=.*\\S).+$')]),
    city: new FormControl({value: '', disabled: false}, [Validators.required, Validators.pattern('^(?=.*\\S).+$')]),
    state: ['default', forbiddenValidator(/default/i)],
    current: [false]
  });
  }

  ngOnInit() {
        this.showResult = this.weatherService.getSearchStatus();
  }

  autoComplete(input: string) {
    this.autoCompleteList = [];
    this.autoCompleteList = this.autoService.getResult(input);
  }

  clear() {
    this.searchForm.reset();
    this.clearFormContent();
    this.enableForm();
    this.showResult =  this.weatherService.clear();
  }

  clearFormContent() {
    this.searchForm.controls.street.setValue('');
    this.searchForm.controls.city.setValue('');
    this.searchForm.controls.state.setValue('default');
    this.searchForm.controls.current.setValue(false);
  }
  enableForm() {
    this.searchForm.controls.street.enable();
    this.searchForm.controls.city.enable();
    this.searchForm.controls.state.enable();
  }
  disableForm() {
    this.searchForm.controls.street.disable();
    this.searchForm.controls.city.disable();
    this.searchForm.controls.state.disable();
  }
  getLocation() {
    this.currentP.lat = this.locationService.getLatitude();
    this.currentP.lon = this.locationService.getLongitude();
  }

  currentLocation() {
    if (!this.searchForm.controls.current.value) {
      this.clear();
      this.disableForm();
      this.searchForm.controls.current.setValue(true);
      this.getLocation();
    } else {
      this.enableForm();
      this.searchForm.controls.current.setValue(false);
    }
  }

  search() {
    if (this.searchForm.controls.current.value ) {
        this.weatherService.requestAllWeatherInfoByPoint( this.currentP.lat, this.currentP.lon).subscribe((response: any) => {});
    } else {
      this.weatherService.requestAllWeatherInfoByName(this.searchForm.controls.street.value, this.searchForm.controls.city.value, this.searchForm.controls.state.value).subscribe((response: any) => {
      });
    }
    this.showResult = this.weatherService.search();
    this.clickTimePlus();
  }

  clickTimePlus() {
    this.clickTime += 1;
  }

  searchCity(response: FavoriteRecord) {
    this.searchForm.reset();
    this.clearFormContent();
    this.enableForm();
    this.showResult = this.weatherService.clear();
    setTimeout(() => {
      this.showResult = this.weatherService.search();
      this.weatherService.requestAllWeatherInfoByFavorite(response.lat, response.lon, response.city, response.state, response.seal);
      this.clickTimePlus();
    }, 200);
  }

}

export function forbiddenValidator(nameRe: RegExp): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const forbidden = nameRe.test(control.value);
    return forbidden ? {forbidden: {value: control.value}} : null;
  };
}
