import { Component, OnInit } from '@angular/core';
import { DeckSummary } from '../../models/deck-summary';
import { DeckService } from '../../services/deck.service';

@Component({
  selector: 'app-deck-statistics',
  templateUrl: './deck-statistics.component.html',
  styleUrls: ['./deck-statistics.component.css']
})
export class DeckStatisticsComponent implements OnInit {
  public deckSummaries: DeckSummary[] = [];

  constructor(private deckService: DeckService) {}

  ngOnInit() {
    this.deckService.getDeckSummaries().subscribe(
      (data) => {
        this.deckSummaries = data;
        // console.log('Deck Summaries:', this.deckSummaries);
      },
      (error) => {
        console.error('Error fetching deck summaries:', error);
      }
    );
  }
}
