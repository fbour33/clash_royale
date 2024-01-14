import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CardSelectionService {
  private selectedCardIds: number[] = [];

  getSelectedCardIds(): number[] {
    return this.selectedCardIds;
  }

  setSelectedCardIds(ids: number[]) {
    this.selectedCardIds = ids;
  }
}
