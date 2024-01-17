import { Routes } from '@angular/router';
import {DeckStatisticsComponent} from "./components/deck-statistics/deck-statistics.component";
import {NgramsComponent} from "./components/ngrams/ngrams.component";

export const routes: Routes = [
  { path: '', component: DeckStatisticsComponent },
  { path: 'ngrams', component: NgramsComponent },
];
