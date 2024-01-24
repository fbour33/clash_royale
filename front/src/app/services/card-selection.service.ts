import { Injectable } from '@angular/core';
import { Card } from "../../models/card";

@Injectable({
  providedIn: 'root',
})
export class CardSelectionService {
  private selectedCards: Card[] = [];

  getSelectedCards(): Card[] {
    return this.selectedCards;
  }

  setSelectedCards(cards: Card[]) {
    this.selectedCards = cards;
  }
}
