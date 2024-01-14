import { Component } from '@angular/core';
import {CardSelectionComponent} from "../card-selection/card-selection.component";
import {ResultComponent} from "../result/result.component";

@Component({
  selector: 'app-ngrams',
  standalone: true,
  imports: [
    CardSelectionComponent,
    ResultComponent
  ],
  templateUrl: './ngrams.component.html',
  styleUrl: './ngrams.component.css'
})
export class NgramsComponent {
  selectedCardIds: number[] = [];

  onCardSelectionChanged(selectedIds: number[]) {
    this.selectedCardIds = [...selectedIds];
  }

}
