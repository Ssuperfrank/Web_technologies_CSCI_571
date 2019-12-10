import { Component, OnInit, ElementRef, Input, Output, EventEmitter} from '@angular/core';
import { FavoriteCitiesService} from '../service/favorite-cities.service';
import { FavoriteRecord } from '../favoriteRecord';

@Component({
  selector: 'app-favorite-result-tag',
  templateUrl: './favorite-result-tag.component.html',
  styleUrls: ['./favorite-result-tag.component.css']
})
export class FavoriteResultTagComponent implements OnInit {
  @Output() toSearchForm = new EventEmitter()

  public isEmpty: boolean;
  public storage: FavoriteRecord[];
  constructor(private favoriteService: FavoriteCitiesService, private ef: ElementRef) { }

  ngOnInit() {
    this.isEmpty = this.favoriteService.isEmpty();
    this.storage = this.favoriteService.getAllStorage();
  }

  searchCity(record: FavoriteRecord) {
    this.toSearchForm.emit(record);
  }

  deleteCity(record: FavoriteRecord) {
    this.storage.splice(this.storage.indexOf(record), 1);
    this.favoriteService.deleteKey(record.city, record.state);
    this.isEmpty = this.favoriteService.isEmpty();
  }

}
