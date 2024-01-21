import { NgModule } from '@angular/core';
import { DeckStatisticsComponent } from './components/deck-statistics/deck-statistics.component';
import {DeckCardComponent} from "./components/deck-card/deck-card.component";
import {CommonModule, NgOptimizedImage} from "@angular/common";
import {MatTableModule} from "@angular/material/table";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCardModule} from "@angular/material/card";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    DeckStatisticsComponent,
    DeckCardComponent,
  ],
    imports: [
        CommonModule,
        MatTableModule,
        MatGridListModule,
        MatCardModule,
        NgOptimizedImage,
        FormsModule
    ],
  bootstrap: [],
  exports: [
    DeckStatisticsComponent
  ]
})
export class AppModule { }
