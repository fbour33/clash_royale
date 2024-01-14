import {Component, Output, EventEmitter} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import { cr_cards } from '../../../assets/deck-manager';

@Component({
  selector: 'app-card-selection',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './card-selection.component.html',
  styleUrl: './card-selection.component.css'
})
export class CardSelectionComponent {
  @Output() cardSelectionChanged = new EventEmitter<number[]>();
  cr_cards = cr_cards;
  selectedCardIds: number[] = [];

  viewStatistics() {
    this.cardSelectionChanged.emit(this.selectedCardIds);
  }

  toggleCardSelection(cardId: number) {
    const index = this.selectedCardIds.indexOf(cardId);

    if (index === -1 && this.selectedCardIds.length < 8) {
      this.selectedCardIds.push(cardId);
    } else if (index !== -1) {
      this.selectedCardIds.splice(index, 1);
    }
  }

}
