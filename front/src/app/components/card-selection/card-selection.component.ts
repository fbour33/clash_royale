import {Component, Output, EventEmitter} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import { cr_cards } from '../../../assets/deck-manager';
import {Card} from "../../models/card";

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
  @Output() cardSelectionChanged = new EventEmitter<Card[]>();
  cr_cards = cr_cards;
  selectedCards: Card[] = [];

  viewStatistics() {
    this.cardSelectionChanged.emit(this.selectedCards);
  }

  toggleCardSelection(selectedCard: Card) {
    const index = this.selectedCards.findIndex(card => card.id === selectedCard.id);

    if (index === -1 && this.selectedCards.length < 3) {
      this.selectedCards.push(selectedCard);
    } else if (index !== -1) {
      this.selectedCards.splice(index, 1);
    }
  }


}
