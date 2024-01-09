import {Component, signal} from '@angular/core';
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
  cr_cards = cr_cards;
  selectedCardIds: number[] = [];
  showSelectedIds = false;

  viewStatistics() {
    this.showSelectedIds = true;
  }
  toggleCardSelection(cardId: number) {
    const index = this.selectedCardIds.indexOf(cardId);

    if (index === -1) {
      this.selectedCardIds.push(cardId);
    } else {
      this.selectedCardIds.splice(index, 1);
    }
  }
}
