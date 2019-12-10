import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatTabsModule, MatAutocompleteModule, MatFormFieldModule, MatInputModule, MatNativeDateModule, MatRippleModule} from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChartsModule } from 'ng2-charts';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


// import component
import { AppComponent } from './app.component';
import { SearchFormComponent } from './search-form/search-form.component';
import { ResultContainerComponent } from './result-container/result-container.component';
import { CurrentTagComponent } from './current-tag/current-tag.component';
import { HourlyTagComponent } from './hourly-tag/hourly-tag.component';
import { WeeklyTagComponent } from './weekly-tag/weekly-tag.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { FavoriteResultTagComponent } from './favorite-result-tag/favorite-result-tag.component';
import { WeeklyCardComponent } from './weekly-card/weekly-card.component';


// import service
import { GetLocationService } from './service/get-location.service';
import { AutoCompleteService } from './service/auto-complete.service';
import { SearchLocationService } from './service/search-location.service';
import { WeatherResultService } from './service/weather-result.service';
import { GetStateSealService } from './service/get-state-seal.service';
import { FavoriteCitiesService } from './service/favorite-cities.service';
import { WeatherByDateService } from './service/weather-by-date.service';
import { DatePipe } from '@angular/common';


@NgModule({
  declarations: [
    AppComponent,
    SearchFormComponent,
    ResultContainerComponent,
    CurrentTagComponent,
    HourlyTagComponent,
    WeeklyTagComponent,
    BarChartComponent,
    FavoriteResultTagComponent,
    WeeklyCardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatAutocompleteModule,
    MatNativeDateModule,
    MatInputModule,
    MatFormFieldModule,
    MatRippleModule,
    BrowserAnimationsModule,
    MatTabsModule,
    ChartsModule,
    NgbModule
  ],
  providers: [
    GetLocationService,
    AutoCompleteService,
    SearchLocationService,
    WeatherResultService,
    GetStateSealService,
    FavoriteCitiesService,
    WeatherByDateService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
