import { Component, Input } from '@angular/core';
import {DeckSummary} from "../../models/deck-summary";

@Component({
  selector: 'app-deck-card',
  templateUrl: './deck-card.component.html',
  styleUrls: ['./deck-card.component.css'],
})
export class DeckCardComponent {
  @Input() deck!: DeckSummary;
}
